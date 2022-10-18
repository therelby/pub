package all

import org.apache.poi.ss.usermodel.CellType

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.commons.lang.StringUtils

/**
 * Excel files handling
 * @author akudin
 */
class Excel {

	// File path
	def filePath = null
	// Sheet number
	def sheetNumber = 0
	// Sheet data
	def data = []


	// Class constructor
	// Opens xls(x) file and reads its first sheet content to data
	def Excel(String fileName, int num = 0) {
		filePath = fileName
		sheetNumber = num

		// opening file
		def Workbook book = null
		FileInputStream inputStream = new FileInputStream(new File(fileName))
		try {
			book = new XSSFWorkbook(inputStream)
		} catch (any) {
			inputStream.close()
			inputStream = new FileInputStream(new File(fileName))
			book = new HSSFWorkbook(inputStream)
		}

		// selecting first sheet
		def Sheet sheet = book.getSheetAt(sheetNumber)

		// reading data
		def iterator = sheet.iterator()
		while (iterator.hasNext()) {
			data << []
			Row nextRow = iterator.next()
			Iterator<Cell> cellIterator = nextRow.cellIterator()
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next()
				data[data.size()-1] += [data: getCellValue(cell), update: false]
			}
		}

		// close file
		book.close()
		inputStream.close()
	}


	// Get cell value
	private Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
			case CellType.STRING:
				return cell.getStringCellValue()
			case CellType.BOOLEAN:
				return cell.getBooleanCellValue()
			case CellType.NUMERIC:
				return cell.getNumericCellValue()
		}
		return null;
	}


	// Update data in a cell
	def putToCell(int x, int y, txt) {
		def maxY = data.size()
		if (y > maxY) {
			for (i in (1..y-maxY)) {
				data << []
				maxY++
			}
		}
		def maxX = data[maxY - 1].size()
		if (x > maxX) {
			for (i in (1..x-maxX)) {
				data[y-1] << [data: null, update: false]
			}
		}
		data[y-1][x-1].data = txt
		data[y-1][x-1].update = true
	}


	// Read cell data
	def readCell(int x, int y) {
		if (data[y-1][x-1]) {
			return data[y-1][x-1].data
		} else {
			return null
		}
	}


	// Read cell data by column name
	// (!) Sheet must have column names in the first row
	def readCellByColumnName(String name, int y) {
		for (i in (0..data[0].size()-1)) {
			if (data[0][i].data == name) {
				return readCell(i+1, y)
			}
		}
		throw new Exception("No column named $name found in $filePath sheet $sheetNumber.")
	}


	// Save updated data to the file
	def saveToFile() {

		// opening file
		FileInputStream inputStream = new FileInputStream(new File(filePath))
		Workbook workbook = WorkbookFactory.create(inputStream)
		Sheet sheet = workbook.getSheetAt(sheetNumber)
		int rowCount = sheet.getLastRowNum() + 1

		def yy = data.size()-1
		if (yy < 0) {
			yy = 0
		}
		for (y in (0..yy)) {
			def row = null
			def cell = null
			def rowDone = false
			if (rowCount > y) { // existing row?
				row = sheet.getRow(y)
				if (row) {
					def xx = data[y].size()-1
					if (xx < 0) {
						xx = 0
					}
					for (x in (0..xx)) {
						cell = row.getCell(x)
						if (!cell) {
							cell = row.createCell(x)
						}
						if (data[y][x] && data[y][x].update) {
							setCellValue(cell, data[y][x].data)
						}
					}
					rowDone = true
				}
			}
			if (!rowDone) { // not existing row
				row = sheet.createRow(y)
				def xx = data[y].size()-1
				if (xx < 0) {
					xx = 0
				}
				for (x in (0..xx)) {
					cell = row.createCell(x)
					if (data[y][x] && data[y][x].update) {
						setCellValue(cell, data[y][x].data)
					}
				}
			}
		}
		inputStream.close()

		// saving file
		FileOutputStream outputStream = new FileOutputStream(filePath)
		workbook.write(outputStream)
		workbook.close()
		outputStream.close()
	}


	// setCellValue
	def setCellValue(Cell cell, dt) {
		if (dt instanceof String) {
			cell.setCellValue((String) dt)
		} else if (dt instanceof Integer) {
			cell.setCellValue((Integer) dt)
		} else if (dt instanceof Boolean) {
			cell.setCellValue((Boolean) dt)
		} else {
			cell.setCellValue((String) dt.toString())
		}
	}


	// Put value in file that must have column names in the first row
	def putToCellByColumnName(String name, int y, txt) {
		def notFound = true
		for (i in (0..data[0].size()-1)) {
			if (data[0][i].data == name) {
				notFound = false
				putToCell(i+1, y, txt)
				break
			}
		}
		if (notFound) {
			throw new Exception("No column named $name found in $filePath sheet $sheetNumber.")
		}
	}


	// Get count of rows
	def getRowsCount() {
		return data.size()
	}


	// Get count of columns
	def getColumnsCount() {
		def cnt = 0
		data.each {
			if (cnt < it.size()) {
				cnt = it.size()
			}
		}
		return cnt
	}


	// Search text in row
	def isRowContainsText(row, text) {
		for (x in (0..data[0].size()-1)) {
			if (readCell(x, row).contains(text)) {
				return true
			}
		}
		return false
	}

}

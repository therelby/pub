package above.report

import all.DbTools
import java.awt.Color
import java.awt.Font

/**
 * 		Daily Report Data
 */
abstract class ReportDailyData {

	// Select last report
	static selectLastReport = "SELECT TOP 1 date_report, date_created FROM Stat_Reports_Daily ORDER BY id DESC"
	// Select report data for a date
	static selectReportBetweenDates = "SELECT * FROM [QA_Automation].[dbo].[Stat_Reports_Daily] where date_report >= '%period1' AND date_report <= '%period2'"
	// Insert daily report data
	static insertReport = "INSERT INTO [QA_Automation].[dbo].[Runs_Reports] (report_period, report_data) VALUES ('%period', '%data')"

	// Automation goals
	static goals = [
		'2020': 12000,
		'2021': 12000
	]

	// Chart pics paths
	static pics = []



	// Format seconds to nice time string
	def static private formatSecondsToReadableTime(Integer seconds) {
		return sprintf('%02d:%02d:%02d', (int) (seconds / 3600), (int) (seconds % 3600 / 60), (int) (seconds % 60))
	}


	// Save daily report to database
	def static private saveReport(report, String body) {

		def query = insertReport
				.replace('%period', report.reportDate)
				.replace('%data', new groovy.json.JsonBuilder(report).toPrettyString().replace("'", "''"))

		new all.Db('qa').updateQuery(query)
	}



	/** Get formatted time diff string */
	def static getTimeDiff(Integer t1, Integer t2) {
		def execTimeDiff = t2 - t1
		def execTimeDiffClean = execTimeDiff
		if (execTimeDiff < 0) {
			execTimeDiffClean  = execTimeDiff * -1
		}
		def diffFormated = formatSecondsToInterval(execTimeDiffClean)
		def timeDiff = '00:00:00'
		if (execTimeDiff > 0) {
			timeDiff = '+ ' + diffFormated
		} else if (execTimeDiff < 0) {
			timeDiff = '- ' + diffFormated
		}
		return timeDiff
	}


	/** Format seconds to string like '02:31:56' */
	static String formatSecondsToInterval(seconds) {
		return sprintf('%02d:%02d:%02d', (int) (seconds / 3600), (int) (seconds % 3600 / 60), (int) (seconds % 60))
	}


	/** Number to formatter string */
	static String numberToFormattedString(Integer number) {
		if (number > 999) {
			String str = ''
			int i = 0
			for (it in number.toString().reverse()) {
				i += 1
				str = it + str
				if (i ==3) {
					str = ' ' + str
				}
			}
			return str.trim()
		} else {
			return number.toString()
		}
	}



	/** Unreported dates calculator */
	static getReportDateList() {

		List dates = []
		List dbData = all.DbTools.selectAll(selectLastReport, 'qa')
		if (!dbData) { return dates }

		java.util.Date reportPoint = all.DateTools.sqlTimestampToDate(dbData[0].date_report).plus(1)
		java.util.Date stopDate = new Date()

		if (new Date().format('HH').toInteger() >= 7) {
			stopDate = stopDate.plus(1)
		}

		while (reportPoint.format('yyyy-MM-dd') != stopDate.format('yyyy-MM-dd')) {
			dates << reportPoint
			reportPoint = reportPoint.plus(1)
		}

		return dates
	}

}

package above.report

/**
 * THML Email Template Easy Creation
 * @author akudin
 */

public class EmailTemplate {


	// Template data with prepared title/subtitle part
	// - part format: [text or list of texts for columns, 'part table border color; text color; font size; font weight']
	def body = [['<span style="font-size: 22pt; color: #008000;">%title</span><br /><span style="color: #000000;">%subtitle</span>', 'FFFFFF;000000;12;normal']]

	// HTML
	def html

	// Last part code
	def lastPart = null


	// Add columns data
	def addTitles(List row) {
		def idx = body.size() - 1
		if (!(body[idx][0] instanceof Map)) {
			throw new Exception("Approproate part should be added first")
		}
		if (body[idx][0].titles.size() != row.size()) {
			throw new Exception("Wrong list size compire to the column titles")
		}
		body[idx][0].rows << row
	}


	// Add part
	def addPart(data, String borderColor = 'EEFFEE', String fontStyle = '000000;12;normal') {
		body << [data, "$borderColor;$fontStyle"]
	}


	// Check part
	def checkPart(part) {

		if (!lastPart) {
			lastPart = ['', '-;-;0;-']
		}

		def style = part[1].split(';')*.trim()
		def lastStyle = lastPart[1].split(';')*.trim()

		if (part[0].getClass() != lastPart[0].getClass() || 
			(part[0] instanceof List && lastPart[0] instanceof List && part[0].size() != lastPart[0].size()) || 
			style[0] != lastStyle[0]) {

			// part closing
			if (lastPart[1].length() > 10) {
				html += '\n</tbody></table>\n'
				html += '<table border="0" cellspacing="0" cellpadding="10"></tbody><tr><td>&nbsp;</td></tr></tbody></table>'
			}

			// part opening
			def border
			if (style[0].toUpperCase() == 'FFFFFF') {
				border = '0'
			} else {
				border = '1'
			}
			html += "<table style=\"width: 100%; border-color: #${style[0]};\" border=\"${border}\" cellspacing=\"0\" cellpadding=\"10\"><tbody>"
		}

		// adding string content to a single column
		if (part[0] instanceof String) {
			html += "\n<tr><td><span style=\"font-family: Arial; color: #${style[1]}; font-size: ${style[2]}pt;\">${part[0]}</span></td></tr>"
		}

		// adding list of strings content to multiple columns
		if (part[0] instanceof List) {
			html += "\n<tr>"
			for (row in part[0]) {
				//println row
				def r = row
				def a = 'left'
				if (r.endsWith('::center')) {
					a = 'center'
					r = r[0..r.length()-9]
				} else if (r.endsWith('::right')) {
					a = 'center'
					r = r[0..r.length()-8]
				}
				html += "\n<td style=\"text-align: ${a}\"><span style=\"font-family: Arial; color: #${style[1]}; font-size: ${style[2]}pt; font-weight: ${style[3]}\">${r}</span></td>"
			}
			html += "\n</tr>"
		}

		lastPart = part
	}


	// Get HTML
	def getHtml() {

		//common.Print.nicePrint(body)

		// finalizing HTML data
		html = '<html><body>'
		for (part in body) {
			checkPart(part)
		}

		// last part closing
		if (lastPart) {
			html += '\n</tbody></table>\n'
			html += '<table border="0" cellspacing="0" cellpadding="10"><tbody><tr><td>&nbsp;</td></tr></tbody></table>'
		}

		return html + '\n</body></html>'
	}


	// Set subtitle
	def setSubtitle(String text) {
		body[0][0] = body[0][0].replace('%subtitle', text)
	}


	// Set title
	def setTitle(String text) {
		body[0][0] = body[0][0].replace('%title', text)
	}


}

package above.azure

/**
 * Azure DevOps Data Tools
 * @author akudin
 *
 */
class AzureDevOpsDataTools {

	// Testcase update dates
	static dates = []
	// Data path
	static path = []


	/**
	 * Scan data structure
	 * (!) recursion
 	 */
	synchronized static scanData(data) {

		if (data instanceof String) {
			if (data.length() > 20 && data[4] == '-' && data[7] == '-'
				&& data[5..6].isNumber() && data[8..9].isNumber() && data[0..3].isNumber()
				&& data[0..3].toInteger() < new Date().format('yyyy').toInteger()
				&& data[0..3].toInteger() > 1900) {
				dates << path.join('/') + '/' + data
			}

		} else if (data instanceof List) {

			data.each { scanData(it) }

		} else if (data instanceof Map) {

			data.keySet().each {
				path << it
				scanData(data[it])
				path.remove(path.last())
			}

		}
	}


	/**
	 * Scan dates in data structure
	 * returns the last date
 	 */
	synchronized static getLastDate(data) {

		dates = []
		path = []

		scanData(data)

		def day = new Date('01/01/1900')
		dates.each {
			def i = it.split('/').last()
			def td = new Date(i[5..6] + '/' + i[8..9] + '/' + i[0..3])
			if (td > day) {
				day = td
			}
		}

		return day
	}


}

package all

import java.sql.Timestamp

/**
 * Date Tools
 * @author akudin
 */

class DateTools {


	/** SQL Timestamp to java.util.Date converting */
	synchronized static sqlTimestampToDate(java.sql.Timestamp date) {
		Calendar calendar = Calendar.getInstance()
		calendar.setTimeInMillis(date.getTime())
		return calendar.getTime()
	}


	/** String like 'yyyy-mm-dd ...' to Date **/
	synchronized static dateFromYmd(text) {

		// already date?
		if (text instanceof Date) {
			return text
		} else if (text instanceof Timestamp) {
			return new Date(text.getTime())
		}

		// wrong format?
		if (!text || text.length() < 8 || !text[5..6].isNumber() || !text[8..9].isNumber() || !text[0..3].isNumber()) {
			return null
		}

		// return date parsed from string
		return new Date(text[5..6] + '/' + text[8..9] + '/' + text[0..3])
	}

	// Format seconds to nice time string
	synchronized static String formatSecondsToReadableTime(int seconds) {
		return sprintf('%02d:%02d:%02d', (int) (seconds / 3600), (int) (seconds % 3600 / 60), (int) (seconds % 60))
	}


}

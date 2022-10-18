package all.util

import above.RunWeb
import org.jsoup.Jsoup

/**
 *     Utility methods for HTML
 *      Refactored from Katalon common.Html
 *     akudin vdiachuk
 *
 */
class HtmlUtil {

    // Decoding string with stuff like &nbsp;
   synchronized def static decode(String text) {
        def res = (new XmlSlurper().parseText("<wsstag>${text}</wsstag>")).toString()
        return res.replace('<wsstag>', '').replace('</wsstag>', '').replace('  ', ' ').trim()
    }

    // Get visible page text
    synchronized static String getVisibleText() {
        RunWeb r = run()
        return r.getText('/html/body')
    }

    /**
     * This method will refactor the HTML text to a better text can be compared with the DB and API return text
     * @param html text
     * @return normal text
     */
    static String html2text(String html) {
        return Jsoup.parse(html).text()
    }
}

package above.web

import above.RunWeb
import org.openqa.selenium.WebElement

/**
 * Class for SetTextCharByChar method only
 * @vdiachuk
 */
class FuncTextCharByChar {

    RunWeb r = run()

    /**
     * Setting Text Char By Char using xpath
     * @param xpath
     * @param text
     * @param delayInMilliseconds
     * @return success
     */
    boolean setTextCharByChar(String xpath, String text, Integer delayInMilliseconds) {
        WebElement element = r.find(xpath)
        if (!element) {
            r.addIssueTrackerEvent("Can not set text [$text] char by char, element [$xpath] not found")
            return false
        }
        return r.setTextCharByChar(element, text, delayInMilliseconds)
    }

    /**
     * Setting Text Char By Char using Webelement
     * @param element - input/textArea element
     * @param text - String to set
     * @param delayInMilliseconds - sleep thread in case of positive value
     * @return success
     */
    boolean setTextCharByChar(WebElement element, String text, Integer delayInMilliseconds) {
        if (!text) {
            return false
        }
        def listOfChars = text as List
        if (!(listOfChars as boolean)) {
            return false
        }
        for (String letter in listOfChars) {
            try {
                element.sendKeys(letter.toString())
            } catch (e) {
                r.addIssueTrackerEvent("Can not set text: [$text] char by char ", e)
                return false
            }
            if (delayInMilliseconds as boolean && delayInMilliseconds > 0) {
                sleep(delayInMilliseconds)
            }
        }
        return true
    }
}

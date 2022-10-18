package above.web

import above.RunWeb
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 * Class to store verify methods that not used very often
 * @vdiachuk
 */
class FuncCustomVerify {
    RunWeb r = run()

    /** Verify element has scrolling by xpath */
    Boolean verifyScrollable(String xpath) {
        r.logDebug "Verifying element scrollable: $xpath"
        if (!xpath) {
            return null
        }
        WebElement element = r.find(xpath)
        if (!element) {
            return null
        }
        return r.verifyScrollable(element)
    }

    /** Verify element has scrolling by WebElement */
    Boolean verifyScrollable(WebElement element) {
        WebDriver driver = r.getWebDriver()
        JavascriptExecutor je = (JavascriptExecutor) driver
        String script = "return arguments[0].scrollHeight > arguments[0].offsetHeight;"

        Boolean result = null
        try {
            result = (Boolean) je.executeScript(script, element)
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not verify is Element scrollable - Exception", e)
            return null
        }
        return result
    }
}

package above.web

import above.Run
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 *      Web Element Style by JS
 *      @akudin
 */

class FuncStyleJs {

    Run r = run()

    /** Java Script Set Style - by WebElement */
    Boolean jsSetStyle(WebElement element, String attrName, String attrValue) {
        try {
            WebDriver driver = r.getWebDriver()
            JavascriptExecutor executor = (JavascriptExecutor) driver
            executor.executeScript("arguments[0].style.$attrName='$attrValue';", element)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("jsSetStyle: Can not set the attribute to the element", e)
            return false
        }
    }

    /** Java Script Set Style - by xpath */
    Boolean jsSetStyle(String xpath, String attrName, String attrValue) {
        try {
            jsSetStyle(r.findElements(xpath)[0], attrName, attrValue)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("jsSetStyle: Can not set the attribute to the element because of the xpath issue", e)
            return false
        }
    }

}

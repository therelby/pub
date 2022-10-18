package above.web

import above.RunWeb
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement
/**
 * Select  Functional using JavaScript JS
 * @vdiachuk
 */
class FuncSelectJS {
    RunWeb r = run()

    /**
     * Select with Xpath by Visible text using JavaScript JS
     */
    boolean jsSelectByVisibleText(String xpath, String visibleText) {
        WebElement element = r.find(xpath)
        if (!element) {
            return false
        }
        return jsSelectByVisibleText(element, visibleText)
    }

    /**
     * Select with WebElement by Visible text using JavaScript JS
     */
    boolean jsSelectByVisibleText(WebElement element, String visibleText) {
        r.logDebug "Selecting by visible text: $visibleText using JavaScript.."
        try {
            def driver = r.getWebDriver()
            ((JavascriptExecutor) driver).executeScript("var select = arguments[0]; for(var i = 0; i < select.options.length; i++){ if(select.options[i].text == arguments[1]){ select.options[i].selected = true;}}", element, visibleText)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not select by visible text: $visibleText using JavaScript", e)
            return false
        }
    }

    /**
     * Select with Xpath by Index using JavaScript JS
     */
    boolean jsSelectByIndex(String xpath, Integer index) {
        WebElement element = r.find(xpath)
        if (!element) {
            return false
        }
        return jsSelectByIndex(element, index)
    }

    /**
     * Select with WebElement by Index using JavaScript JS
     */
    boolean jsSelectByIndex(WebElement element, Integer index) {
        r.logDebug "Selecting by index: $index using JavaScript.."
        try {
            def driver = r.getWebDriver()
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver
            jsExecutor.executeScript("arguments[0].getElementsByTagName('option')[$index].selected = 'selected';", element)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not select by index: $index using JavaScript", e)
            return false
        }
    }

    /**
     * Select  with Xpath by Value using JavaScript JS
     */
    boolean jsSelectByValue(String xpath, String value) {
        WebElement element = r.find(xpath)
        if (!element) {
            return false
        }
        return jsSelectByValue(element, value)
    }

    /**
     *     Select  with WebElement by Value using JavaScript JS
     */
    boolean jsSelectByValue(WebElement element, String value) {
        r.logDebug "Selecting by value: $value using JavaScript.."
        try {
            def driver = r.getWebDriver()
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver
            jsExecutor.executeScript("arguments[0].value = '$value'", element)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not select by value: $value using JavaScript", e)
            return false
        }
    }
}

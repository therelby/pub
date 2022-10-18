package above.web

import above.RunWeb
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 *      Web Element Click Functional
 * @vdiacuk @kyilmaz  @micurtis
 */
class FuncClick {

    RunWeb r = run()
    // WebDriver driver = r.getWebDriver() (!) getting driver inside method if needed - to avoid null if driver reinstating


    /** Click Page Element by webelement*/
    Boolean click(WebElement element) {
        try {
            element.click()
            r.logDebug("Clicked element: $element")
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can't click element", e)
            return false
        }
    }

    /** Click Page Element by xpath*/
    Boolean click(String xpath) {
        try {
            def element = r.find(xpath)
            if (!element) {
                return false
            }
            element.click()
            r.logDebug("Clicked XPath: $xpath")
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can't click: $xpath", e)
            return false
        }
    }

    /** Click and page load high level handling */
    boolean clickAndTryLoad(Object xpathOrElement, String expectedUrl = '*') {
        return r.tryLoad(xpathOrElement, expectedUrl)
    }


    /** try Click Page Element by xpath*/
    Boolean tryClick(String xpath, tryTimes = 5) {
        WebElement element = r.find(xpath)
        if (!element) {
            return false
        }
        return r.tryClick(element, tryTimes)
    }

    /** try Click Page Element by WebElement*/
    Boolean tryClick(WebElement element, tryTimes = 5) {
        try {
            def beforeUrl = r.getCurrentUrl()
            def result = r.click(element)
            r.waitForPage(10)
            r.sleep(200)
            def afterUrl = r.getCurrentUrl()
            def error = false
            if (beforeUrl != afterUrl) {
                for (def i = tryTimes; i > 0; i--) {
                    if (!r.isPageError()) {
                        error = false
                        break
                    } else {
                        error = true
                        r.refresh()
                    }
                }
            }
            return !error && result
        } catch (Exception e) {
            r.addIssueTrackerEvent("Try click: Can not click on element", e)
            return false
        }
    }

    /** Java Script js Click Page Element by webelement */
    Boolean jsClick(WebElement element) {
        try {
            WebDriver driver = r.getWebDriver()
            JavascriptExecutor executor = (JavascriptExecutor) driver
            executor.executeScript("arguments[0].click();", element)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("jsClick: Can not click on element", e)
            return false
        }
    }

    /** Java Script js Click Page Element by xpath*/
    Boolean jsClick(String xpath) {
        try {
            WebElement element = r.find(xpath)
            if (!element) {
                return false
            }
            return r.jsClick(element)
        } catch (Exception e) {
            r.addIssueTrackerEvent("jsClick: Can not click on element: $xpath", e)
            return false
        }
    }
}

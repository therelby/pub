package above.web

import above.RunWeb
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
/**
 *     Find Element  Functional
 *      vdiachuk kyilmaz
 */
class FuncFind {
    RunWeb r = run()
    // WebDriver driver = r.getWebDriver() (!) getting driver inside method if needed - to avoid null if driver reinstating


    /**
     *     Find Element  by xpath
     */
    WebElement find(String xpath) {
        r.logDebug "Find Element by: $xpath"
        try {
            WebDriver driver = r.getWebDriver()
            return driver.findElement(By.xpath(xpath))
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not find Element by xpath: $xpath", e)
            return null
        }
    }

    /**
     *     Find Element in Element by xpath
     */
    WebElement findInElement(WebElement element, String xpath) {
        r.logDebug "Find Element: $xpath in Element"
        try {
            return element.findElement(By.xpath("." + xpath))
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not find Element by xpath: $xpath in Element", e)
            return null
        }
    }

    /**
     *     Find Element in Xpath Element by xpath
     *     find 1st element by elementXpath, and searching 2nd element within 1st by xpath
     */
    WebElement findInElement(String elementXpath, String xpath) {
        r.logDebug "Find Element: $xpath in Element: $elementXpath"
        try {
            WebElement element = r.find(elementXpath)
            if (!element) {
                return null
            }
            return r.findInElement(element, xpath)
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not find Element by xpath: $xpath in Element: $elementXpath", e)
            return null
        }
    }

    /**
     *     Find Elements  by xpath
     *     (!) Wait for page load before use this method
     */
    List findElements(String xpath) {
        r.logDebug "Finding Elements by xpath: $xpath"
        try {
            WebDriver driver = r.getWebDriver()
            return driver.findElements(By.xpath(xpath))
        } catch (Exception e) {
            if(!r.testBrowser?.contains('safari')) {
                r.addIssueTrackerEvent("Can not find Elements by xpath: $xpath", e)
            }
            //changed from null to maintain safari compatibility
            return []
        }
    }

    /**
     *     Find Elements  in Element by xpath
     *     (!) Wait for page load before use this method
     */
    List findElementsInElement(WebElement element, String xpath) {
        r.logDebug "Finding Elements in Element.."
        try {
            return element.findElements(By.xpath("." + xpath))
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not find Elements by xpath: $xpath in Element: $element", e)
            return []
        }
    }

    /**
     *     Find Elements  in xpathElement by xpath
     *     looking for 1st element by elementXpath, searching for 2nd element in 1st element by xpath
     *     (!) Wait for page load before use this method
     */
    List findElementsInElement(String elementXpath, String xpath) {
        r.logDebug "Finding Elements in Element.."
        try {
            WebElement element = r.find(elementXpath)
            if (!element) {
                return []
            }
            return r.findElementsInElement(element, xpath)
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not find Elements by xpath: $xpath in Element: $elementXpath", e)
            return []
        }
    }

    /**
     *     Find Random Element  by xpath
     *     // refactored from Katalon common.Page
     */
    WebElement findRandomElement(String xpath) {
        r.logDebug "Finding random element xpaht: $xpath"
        try {
            def list = r.findElements(xpath)
            if (list.size() <= 0) {
                return null
            }
            def rnd = new Random()
            return list[rnd.nextInt(list.size())]
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not find Ranodm Element: $xpath", e)
            return null
        }
    }

    /**
     *     Focus on Element  by WebElement
     */
    Boolean focus(WebElement element) {
        r.logDebug "Focusing on element.."
        try {
            element.sendKeys(Keys.SHIFT)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not focus on Element", e)
            return false
        }
    }

    /**
     *     Focus on Element  by WebElement
     */
    Boolean focus(String xpath) {
        r.logDebug "Focusing on element:$xpath"
        WebElement element = r.find(xpath)
        return element ? r.focus(element) : false
    }


}

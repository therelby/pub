package above.web

import above.RunWeb
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 *     Handling Iframes
 *     kdogan vdiachuk
 */

class FuncIframe {

    RunWeb r = run()
    // WebDriver driver = r.getWebDriver() (!) getting driver inside method if needed - to avoid null if driver reinstating


    /**
     * Switch to iframe using xpath
     */
    Boolean switchToIframe(String xpath) {
        r.logDebug "Switching to iframe by xpath: $xpath"
        try {
            WebElement iFrameElement = r.find(xpath)
            return r.switchToIframe(iFrameElement)
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not switch to iframe using xpath: $xpath", e)
            return false
        }
    }

    /**
     * Switch to iframe using WebElement
     */
    Boolean switchToIframe(WebElement iFrameElement) {
        r.logDebug "Switching to iframe by WebElement"
        try {
            if(!iFrameElement){
                r.log "Can not switch to Iframe - iframe element is missing"
                return false
            }
            WebDriver driver = r.getWebDriver()
            driver.switchTo().frame(iFrameElement)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not switch to iframe using webElement", e)
            return false
        }
    }

    /**
     * Switch back to default content
     */
    Boolean switchToDefaultContent() {
        r.logDebug"Switching to Default Content from Iframe"
        try {
            WebDriver driver = r.getWebDriver()
            driver.switchTo().defaultContent()
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not switch to Default Content from Iframe", e)
            return false
        }
    }

}

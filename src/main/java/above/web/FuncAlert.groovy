package above.web

import above.RunWeb
import org.openqa.selenium.NoAlertPresentException
import org.openqa.selenium.WebDriver
/**
 *      Alerts Handling
 *       vdiachuk
 *      get text, close
 */
class FuncAlert {

    RunWeb r= run()
    // WebDriver driver = r.getWebDriver() (!) getting driver inside every method - to avoid null if driver reinstating

    /** Accept alert */
    boolean acceptAlert() {
        r.logDebug "Accepting alert..."
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return false
        }
        try {
            driver.switchTo().alert().accept()
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not accept Alert", e)
            return false
        }
    }

    /** Dismiss alert */
    boolean dismissAlert() {
        r.logDebug "Dismissing Alert..."
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return false
        }
        try {
            driver.switchTo().alert().dismiss()
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not dismiss Alert", e)
            return false
        }
    }

    /** Get current alert text */
    String getAlertText() {
        r.logDebug "Getting text from alert..."
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return null
        }
        try {
            return driver.switchTo().alert().getText()
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not get Alert text", e)
            return null
        }
    }

    /** Send(set) text to current alert */
    boolean setTextToAlert(String text) {
        r.logDebug "Setting text: $text to alert..."
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return false
        }
        try {
            driver.switchTo().alert().sendKeys(text)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not set Alert text", e)
            return false
        }
    }

    /** Check if alert present */
    Boolean isAlertPresent() {
        r.logDebug"Checking is alert present..."
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return false
        }
        try {
            driver.switchTo().alert()
            return true
        } catch (NoAlertPresentException ignored) {
            r.logDebug "Alert Not Present"
            return false
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not check is Alert present", e)
            return null
        }
    }

    /** (!) Run calls only intended */
    Boolean isAlertPresentRunStuffOnly() {
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return false
        }
        try {
            driver.switchTo().alert()
            return true
        } catch (NoAlertPresentException ignored) {
            return false
        } catch (Exception e) {
            return null
        }
    }

}

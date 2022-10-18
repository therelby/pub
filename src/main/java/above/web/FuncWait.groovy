package above.web

import above.RunWeb
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import java.time.Duration
import java.util.concurrent.TimeoutException

/**
 *      Wait  functionality
 *      Other Waits extracted to FuncCustomWait class
 *        kyilmaz vdiachuk
 */
class FuncWait {

    RunWeb r = run()
    // WebDriver driver = r.getWebDriver() (!) getting driver inside method if needed - to avoid null if driver reinstating

/**
 *  sleep or pause
 *  parameter - time in milliseconds
 */
    Boolean sleep(milliseconds) {
        r.logDebug("Waiting for ${milliseconds} ms.")
        try {
            milliseconds = milliseconds.toLong()
            Thread.sleep(milliseconds)
            r.logDebug("Finished waiting.")
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Thread interrupted while waiting", e)
            return false
        }
    }

    /** Wait For Document ReadyState */
    boolean waitForDocumentReadyState(Integer timeOutInSeconds) {
        r.logDebug "Waiting for DOM. Will time out in ${timeOutInSeconds} seconds"
        try {
            WebDriverWait wait = r.getWebDriverWait(timeOutInSeconds)
            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState==\"complete\";"))
            /* wait.until(driver ->
                                ((JavascriptExecutor) driver).executeScript("return document.readyState") == "complete"
             )*/
        } catch (Exception e) {
            r.addIssueTrackerEvent("Timed out after waiting on DOM for ${timeOutInSeconds}", e)
            return false
        }
        r.logDebug "Finished waiting for DOM"
        return true
    }

    /** Wait For JavaScript  finished */
    boolean waitForJQuery(Integer timeOutInSeconds) {
        r.logDebug "Waiting for JavaScript on page to finish. Will time out in ${timeOutInSeconds} seconds"
        WebDriver driver = r.getWebDriver()
        try {
            WebDriverWait wait = r.getWebDriverWait()
            wait.until {
                ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0
            }
        } catch (TimeoutException e) {
            r.addIssueTrackerEvent("Timed out after waiting on DOM for ${timeOutInSeconds}", e)
            return false
        } catch (Exception ignored) {
            r.logDebug "No JQuery found. Continue execution.."
            return true
        }
        r.logDebug "Finished waiting for JavaScript on page."
        return true
    }

    /** Wait For Page to load */
    boolean waitForPage(Integer timeOutInSeconds) {
        return waitForDocumentReadyState(timeOutInSeconds) && waitForJQuery(timeOutInSeconds)
    }
}

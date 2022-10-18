package above.web

import above.RunWeb
import org.openqa.selenium.WebDriver

/**
 *      URLs Handling
 *      vdiachuk kyilmaz akudin
 */
class FuncUrl {

    RunWeb r = run()
    // WebDriver driver = r.getWebDriver() (!) getting driver inside method if needed - to avoid null if driver reinstating

    /** Get Current Url */
    String getCurrentUrl() {
        r.logDebug "Getting Current url.."
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return ''
        }
        try {
            return driver.getCurrentUrl()
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not get Current Url", e)
            return ''
        }
    }
}

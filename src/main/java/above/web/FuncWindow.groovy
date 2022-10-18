package above.web

import above.RunWeb
import org.openqa.selenium.WebDriver

/**
 *  Handling different windows of Browser
 *
 *  vdiachuk
 */
class FuncWindow {

    RunWeb r = run()
    // WebDriver driver = r.getWebDriver() (!) getting driver inside method if needed - to avoid null if driver reinstating



    /**
     * get Current window handle
     */
    String getWindowHandle() {
        r.logDebug "Getting Window handle.."
        try {
            WebDriver driver = r.getWebDriver()
            return driver.getWindowHandle()
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not get current window handle", e)
            return null
        }
    }

    /**
     * returns set of all windows handles
     */
    def getWindowHandles() {
        r.logDebug "Getting all windows handles.."
        try {
            WebDriver driver = r.getWebDriver()
            return driver.getWindowHandles()
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not get All window handles", e)
            return null
        }
    }

    /**
     * Switch to window by handle
     */
    Boolean switchToWidow(String handle) {
        r.logDebug "switching to window with handle: $handle"
        try {
            WebDriver driver = r.getWebDriver()
            driver.switchTo().window(handle)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not  swithc to window by handle: $handle", e)
            return false
        }
    }

    /**
     * Switch to window by Page title
     * going through windows and find window with desired title
     */
    Boolean switchToWindowTitle(String windowTitle) {
        r.logDebug "Switching to window with title: $windowTitle"
        try {
            def handles = r.getWindowHandles()
            for (handle in handles) {
                r.switchToWidow(handle)
                if (r.getTitle() == windowTitle) {
                    return true
                }
            }
            r.logDebug "Can not find window with title: $windowTitle"
            return false
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not switch to window by title: $windowTitle", e)
            return null
        }
    }
    /**
     * Switch to window by Partial url
     * iterating through windows and find window with desired partial url - first one found
     */
    Boolean switchToWindowPartialUrl(String partUrl) {
        r.logDebug "Switching to window with partial url: $partUrl"
        try {
            def handles = r.getWindowHandles()
            for (handle in handles) {
                r.switchToWidow(handle)
                if (r.getCurrentUrl().contains(partUrl)) {
                    return true
                }
            }
            r.logDebug "Can not find window with partial url: $partUrl"
            return false
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not switch to window by partial url: $partUrl", e)
            return null
        }
    }

/**
 * Close current webdriver window
 */
    Boolean closeWindow() {
        r.logDebug "Closing current browser window.."
        try {
            WebDriver driver = r.getWebDriver()
            def handles = r.getWindowHandles()
            if (handles.size() > 1) {
                driver.close()
                // switch to other window
                handles = r.getWindowHandles()
                for (handle in handles) {
                    r.switchToWidow(handle)
                }
                return true
            } else {
                r.logDebug "Can not close last Browser window"
                return false
            }
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not close current window", e)
            return null
        }
    }

    /**
     * Switch to original Window - (opened first) if it exists
     */
    Boolean switchToOriginalWindow() {
        r.logDebug "Switching to Original Browser Window .."
        try {
            WebDriver driver = r.getWebDriver()
            def handles = r.getWindowHandles()
            def originalWindowHandle = r.getOriginalWindowHandle()
            if (handles.contains(originalWindowHandle)) {
                driver.switchTo().window(originalWindowHandle)
                return true
            } else {
                r.addIssueTrackerEvent("Can not switch to Original Browser Window - it does not exist")
                return false
            }
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not switch to Original Browser Window", e)
            return false
        }
    }

    /**
     * Close all Browser windows except
     */
    Boolean closeAllWindowsButOriginal() {
        r.logDebug "Switching to Original Browser Window .."
        try {
            def handles = r.getWindowHandles()
            def originalWindowHandle = r.getOriginalWindowHandle()
            if (handles.contains(originalWindowHandle)) {
                for (def handle : handles) {
                    if (handle != originalWindowHandle) {
                        switchToWidow(handle)
                        closeWindow()
                    }
                }
                switchToOriginalWindow()
                return true
            } else {
                r.addIssueTrackerEvent("Can not close all windows But Original - Original Browser Window does not " +
                        "exist")
                return false
            }
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not close all windows But Original", e)
            return false
        }
    }


    /**
     * Get titles for all browser windows
     */
    List<String> getWindowTitles() {
        r.logDebug "Getting titles for all browser windows .."
        try {
            def listOfTiltes = []
            def currentHandle = getWindowHandle()
            def handles = getWindowHandles()
            for (handle in handles) {
                switchToWidow(handle)
                listOfTiltes << r.getTitle()
            }
            switchToWidow(currentHandle)
            return listOfTiltes
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not get titles for all browser windows", e)
            return null
        }
    }

}

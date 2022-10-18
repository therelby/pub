package above.web

import above.ConfigReader
import above.RunWeb
import org.openqa.selenium.Dimension
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.logging.LogEntries
import org.openqa.selenium.logging.LogType
import org.openqa.selenium.support.ui.ExpectedConditions
import java.time.Duration

/**
 *      Web Browser Handling
 *
 *      open, close, refresh, maximize, refresh
 *      kyilmaz, vdiachuk
 */
class FuncBrowser {

    RunWeb r = run()
    // WebDriver driver = r.getWebDriver() (!) getting driver inside every method - to avoid null if driver reinstating

    // This is if an empty value is given. Can change based on what we need. This is a placeholder.
    String defaultUrl = r.webProject.pages.default

    static final Map<String, List<Integer>> deviceResolutions = [
            "desktop": [1000, 800],
            "tablet" : [750, 800],
            "mobile" : [500, 800]
    ]

    /**
     *      Check if browser is open
     */
    boolean isOpen() {
        if (r.driverStorage.get()) {
            return true
        } else {
            return false
        }

    }

    /**
     *      open page with url
     */
    boolean open(String url = defaultUrl) {
        WebDriver driver = r.getWebDriver()
        try {
            driver.get(url)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Error opening browser", e)
            return false
        }
    }

    /**
     *     open page and try to wait until page is loaded multiple times
     */
    boolean openAndTryLoad(String url) {
        openAndMaximize()
        return r.tryLoad(url)
    }

    /**
     *     open page and maximize browser window
     */
    boolean openAndMaximize(String url = defaultUrl) {
        return open(url) && maximize()
    }

    /**
     *     open page and resize browser window to width and height
     */
    boolean openAndResize(String url = defaultUrl, int width, int height) {
        return open(url) && resize(width, height)
    }

    /**
     *     open page and resize browser window to device resolution
     */
    boolean openAndResizeToDevice(String url = defaultUrl, String deviceName) {
        return open(url) && resizeToDevice(deviceName)
    }

    /**
     *     open new tab
     */
    boolean newTab(String url = defaultUrl) {
        WebDriver driver = r.getWebDriver()
        def currTabCount = getTabCount()
        r.log "Waiting for tab count = ${currTabCount + 1}"
        try {
            ((JavascriptExecutor) driver).executeScript("window.open('${url}');")
            return waitForTabCount(currTabCount + 1)
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not open new tab", e)
            return false
        }
    }

    private boolean waitForTabCount(int numTabs) {
        try {
            def wait = r.getWebDriverWait(10)
            wait.until(ExpectedConditions.numberOfWindowsToBe(numTabs))
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not wait for Tab count: $numTabs", e)
            return false
        }
    }

    /**
     *     switch to tab by tab index
     */
    boolean switchToTab(int index) {
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return false
        }
        try {
            driver.switchTo().window(getTabs()[index])
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not switch to tab index: $index", e)
            return false
        }
    }

    /**
     *     switch to tab by tab window handle
     */
    boolean switchToTab(String tabWindowHandle) {
        r.logDebug "Switching to tab with Handle.."
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return false
        }
        try {
            driver.switchTo().window(tabWindowHandle)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not switch to tab handle: $tabWindowHandle", e)
            return false
        }
    }

    /**
     *     switch to the last tab
     */
    boolean switchToLastTab() {
        r.logDebug "Switching to last tab"
        def index = getTabs().size() - 1
        return switchToTab(index)
    }

    /**
     *     switch to the main(original) tab
     */
    boolean switchToMainTab() {
        r.log "Switching to main tab.."
        return switchToTab(0)
    }

    /**
     *    get window handles
     */
    LinkedHashSet getTabs() {
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return null
        }
        try {
            return driver.getWindowHandles()
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not get tabs handles", e)
            return null
        }
    }

    /**
     *    get quantity of tabs
     */
    Integer getTabCount() {
        try {
            return getTabs().size()
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not get tab count", e)
            return null
        }
    }

    /**
     *    close current tab
     */
    boolean closeTab() {
        WebDriver driver = r.getWebDriver()
        def numTabs = getTabCount()
        r.log "Tab count = ${numTabs}"
        if (numTabs > 1) {
            try {
                driver.close()
            } catch (Exception e) {
                r.addIssueTrackerEvent("Error closing tab", e)
                return false
            }
            r.log "Tab closed."
            waitForTabCount(numTabs - 1)
            numTabs = getTabCount()
            r.log "Tabs after close = ${numTabs}"
            switchToLastTab()
        } else {
            open()
            r.log("Can't close last tab (will break RunWeb). Current tab was reset to the default page")
        }
        return true
    }

    /**
     *    Close browser window
     */
    boolean close() {
        r.logDebug("Closing the browser...")
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return
        }
        try {
            if (!r.testBrowser.contains('-lt')) {
                driver.close()
            }
            if (!r.testBrowser.startsWith('safari') && !r.testBrowser.startsWith('remotesafari')) {
                driver.quit()
            }
            driver = null // TODO: current class issue
            r.testBrowserDriverCreated = null
            r.driverStorage.remove()
            System.gc()
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Error closing browser", e)
            return false
        }
    }

    /**
     *    maximize browser window
     */
    boolean maximize() {
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return false
        }
        try {
            driver.manage().window().maximize()
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Error maximizing browser", e)
            return false
        }
    }

    /**
     *    resize browser window to dimensions
     */
    boolean resizeOuterWindow(int width, int height) {
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return false
        }
        try {
            driver.manage().window().setSize(new Dimension(width, height))
            return true
        } catch (e) {
            r.addIssueTrackerEvent("Error resizing browser to outer window, width: $width, height: $height", e)
            return false
        }
    }

    // Not completely accurate
    boolean resize(int width, int height) {
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return false
        }
        def (outerWidth, outerHeight) = getSizeOfOuterWindow()
        def (innerWidth, innerHeight) = getSize()
        try {
            def padding = [outerWidth - innerWidth, outerHeight - innerHeight]
            driver.manage().window().setSize(new Dimension(
                    width + (int) padding[0],
                    height + (int) padding[1]
            ));
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Error resizing browser. Width: $width, height: $height", e)
            return false
        }
    }

    /**
     *    resize browser window to device resolution
     */
    boolean resizeToDevice(String deviceName) {
        def res = deviceResolutions[deviceName.toLowerCase()]
        return resize(res[0], res[1])
    }

    /**
     *   get size of the browser window (outer)
     */
    List getSizeOfOuterWindow() {
        r.logDebug "Getting Size of Outer Window"
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return [-1, -1]
        }
        try {
            Dimension window = driver.manage().window().getSize()
            return [window.getWidth(), window.getHeight()]
        } catch (Exception e) {
            r.addIssueTrackerEvent("Error getting size of outer window", e)
            return [-1, -1]
        }
    }

    /**
     *   get size of the browser window (inner)
     */
    List getSize() {
        r.logDebug "Getting size of browser window(inner)"
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return [-1, -1]
        }
        try {
            return (List) ((JavascriptExecutor) driver).executeScript(
                    "return [window.innerWidth, window.innerHeight];")
        } catch (Exception e) {
            r.addIssueTrackerEvent("Error getting size of viewport", e)
            return [-1, -1]
        }
    }


    /**
     *   refresh current page
     */
    boolean refresh() {
        r.logDebug "Refreshing page.."
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return false
        }
        try {
            driver.navigate().refresh()
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not refresh the page", e)
            return false
        }
    }


    /**
     *   Returns  console log messages
     */
    LogEntries getConsoleMessages() {
        r.log "Getting log Entries.."
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return null
        }
        try {
            LogEntries logEntries = driver.manage().logs().get(LogType.BROWSER)
            return logEntries
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not get log Entries", e)
            return null
        }
    }

    /**
     *   Navigate back
     */
    boolean back() {
        r.logDebug "Navigating back.."
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return false
        }
        try {
            driver.navigate().back()
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not navigate Back", e)
            return false
        }
    }

    /**
     *   Set implicit timeout to the webdriver
     */
    Boolean setImplicitTimeout(Integer timeOutInSeconds) {
        r.logDebug "Setting Implicit timeout for WebDriver.."
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return false
        }
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeOutInSeconds))
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can Not set Implicit timeout:$timeOutInSeconds", e)
            return false
        }
    }

    /**
     *   Set DEFAULT implicit timeout to the webdriver
     */
    Boolean setImplicitTimeoutToDefault() {
        r.logDebug "Setting Implicit timeout to DEFAULT for WebDriver.."
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return false
        }
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(new Long(ConfigReader.get('implicitWaitSeconds'))))
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can Not set Implicit timeout to DEFAULT", e)
            return false
        }
    }

    /**
     *   Set Page Load timeout to the webdriver
     */
    Boolean setPageLoadTimeout(Integer timeOutInSeconds) {
        r.logDebug "Setting Page Load timeout for WebDriver.."
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return false
        }
        try {
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeOutInSeconds))
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can Not set Page Load timeout:$timeOutInSeconds", e)
            return false
        }
    }

    /**
     *   Set DEFAULT Page Load timeout to the webdriver
     */
    Boolean setPageLoadTimeoutToDefault() {
        r.logDebug "Setting Page Load timeout to DEFAULT for WebDriver.."
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return false
        }
        try {
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(new Long(r.pageLoadWaitSeconds)))
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can Not set Page Load timeout to DEFAULT", e)
            return false
        }
    }
}

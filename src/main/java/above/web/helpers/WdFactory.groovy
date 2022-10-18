package above.web.helpers

import above.ConfigReader
import above.RunWeb
import io.appium.java_client.remote.IOSMobileCapabilityType
import io.appium.java_client.remote.MobileCapabilityType
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.ie.InternetExplorerOptions
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.LocalFileDetector
import org.openqa.selenium.remote.RemoteWebDriver
import java.util.logging.Level
import java.util.logging.Logger

/**
 *          Web Driver Factory
 */

class WdFactory {

    RunWeb r = run()
    def browserVersions = null

    /** Trying to get new WebDriver instance */
    WebDriver getDriver(String browserName, int browserVersionOffset = 0, int tries = 3, int pause = 7000) {
        WebDriver driver = null
        int cnt = 0
        while (!driver) {
            try {
                cnt++
                driver = getActualDriver(browserName, browserVersionOffset)
            } catch(e) {
                if (e.getMessage().startsWith('WdFactory')) {
                    throw new Exception(e)
                }
                r.log("getDriver(): ${e.getMessage()}", r.console_color_yellow)
                if (cnt >= tries) {
                    // driver creation failed
                    r.xStop("getDriver(): ${e.getMessage()}")
                } else {
                    // next attempt
                    r.log("getDriver(): ${pause/1000} seconds delay before try #${cnt+1}/$tries", r.console_color_yellow)
                    sleep(pause)
                }
            }
        }
        return driver
    }


    /** Creating new WebDriver instance */
    private WebDriver getActualDriver(String browserName, int browserVersionOffset = 0) throws MalformedURLException {
        // checking version offset
        if (browserVersionOffset > 0) {
            throw new Exception("Wrong browser version offset: $browserVersionOffset")
        }

        // previous browser versions can be remote only
        if (browserVersionOffset < 0 && !browserName.startsWith('remote') && !browserName.startsWith('mobile') &&
                !browserName.startsWith('ios') && !browserName.startsWith('android')) {
            browserName = "remote$browserName"
        }

        WebDriver driver = null
        DesiredCapabilities capabilities = new DesiredCapabilities()

        Logger.getLogger('org.openqa.selenium').setLevel(Level.OFF)
        r.log "Creating WebDriver for: $browserName version offset: $browserVersionOffset"

        switch (browserName) {

            case 'iossafari':
            case 'remoteiossafari':
                List caps = WebDriverGrid.getFreeIosDeviceCapabilities()
                def udidCaps = caps.find{ it[0] == 'udid' }
                r.log "Requesting with udid = ${udidCaps[1]}"
                capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, '14.5')
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, 'iPhone 11')
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,"iOS");
                capabilities.setCapability(MobileCapabilityType.UDID, udidCaps[1])
                capabilities.setCapability(IOSMobileCapabilityType.BROWSER_NAME, 'Safari')
                capabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 120000)
                driver = new RemoteWebDriver(new URL(ConfigReader.get('gridHub')), capabilities)
                break

            case 'iossafari-flexble':
            case 'remoteiossafari-flexible':
                List caps = WebDriverGrid.getFreeIosDeviceCapabilities()
                if (!caps) {
                    log "Grid server doesn't have free iOS nodes"
                    return null
                }
                caps.each {
                    capabilities.setCapability(it[0], it[1])
                }
                r.log "Requesting driver with capabilities $caps ..."
                driver = new RemoteWebDriver(new URL(ConfigReader.get('gridHub')), capabilities)
                break

            case 'safari':
            case 'remotesafari':
                capabilities.setCapability('browserName', 'safari')
                capabilities.setCapability('platform', 'MAC')
                capabilities.setCapability('platformName', 'MAC')
                capabilities.setCapability(IOSMobileCapabilityType.LAUNCH_TIMEOUT, 120000)

                r.logDebug "Requested Safari browserVersionOffset = $browserVersionOffset"
                r.testBrowserVersion = getBrowserVersion(browserName, browserVersionOffset)
                capabilities.setCapability(CapabilityType.BROWSER_VERSION, r.testBrowserVersion)
                r.log "Requesting remote Safari version: ${r.testBrowserVersion} ..."

                driver = new RemoteWebDriver(new URL(ConfigReader.get('gridHub')), capabilities)
                break

            case 'remotechrome':
                capabilities.setCapability('browserName', 'chrome')

                r.logDebug "Requested Chrome browserVersionOffset = $browserVersionOffset"
                r.testBrowserVersion = getBrowserVersion(browserName, browserVersionOffset)
                capabilities.setCapability(CapabilityType.BROWSER_VERSION,r.testBrowserVersion)
                r.log "Requesting remote Chrome version: ${r.testBrowserVersion} ..."

                capabilities.setCapability('platform', 'WINDOWS')
                capabilities.setCapability('platformName', 'WINDOWS')
                capabilities.setCapability('acceptInsecureCerts', true)
                ChromeOptions options = new ChromeOptions()
                options.addArguments('--disable-gpu')
                capabilities.setCapability(ChromeOptions.CAPABILITY, options)
                driver = new RemoteWebDriver(new URL(ConfigReader.get('gridHub')), capabilities)
                break

            case 'remotefirefox':
                capabilities.setCapability('browserName', 'firefox')
                capabilities.setCapability('acceptInsecureCerts', true)
                driver = new RemoteWebDriver(new URL(ConfigReader.get('gridHub')), capabilities)
                break

            case 'chrome':
                System.setProperty('webdriver.chrome.silentOutput', 'true')
                WebDriverManager.chromedriver().setup()
                driver = new ChromeDriver()
                break

            case 'headlesschrome':
                System.setProperty('webdriver.chrome.silentOutput', 'true')
                WebDriverManager.chromedriver().setup()
                driver = new ChromeDriver(new ChromeOptions().setHeadless(true))
                break

            case 'firefox':
                System.setProperty('webdriver.firefox.silentOutput', 'true')
                WebDriverManager.firefoxdriver().setup()
                driver = new FirefoxDriver()
                break

            case 'headlessfirefox':
                System.setProperty('webdriver.firefox.silentOutput', 'true')
                WebDriverManager.firefoxdriver().setup()
                driver = new FirefoxDriver(new FirefoxOptions().setHeadless(true))
                break

            case 'ie':
                WebDriverManager.iedriver().setup()
                InternetExplorerOptions ieOptions = new InternetExplorerOptions()
                ieOptions.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false)
                ieOptions.destructivelyEnsureCleanSession()
                driver = new InternetExplorerDriver(ieOptions)
                break

            case 'remoteie':
                capabilities.setCapability('browserName', 'internet explorer')
                capabilities.setCapability('platform', 'WINDOWS')
                capabilities.setCapability('platformName', 'WINDOWS')
                driver = new RemoteWebDriver(new URL(ConfigReader.get('gridHub')), capabilities)
                break

            case 'edge':
                WebDriverManager.edgedriver().setup()
                driver = new EdgeDriver()
                break

            case 'remoteedge':
                capabilities.setCapability('browserName', 'MicrosoftEdge')
                capabilities.setCapability('platform', 'WINDOWS')
                capabilities.setCapability('platformName', 'WINDOWS')

                r.logDebug "Requested Edge browserVersionOffset = $browserVersionOffset"
                r.testBrowserVersion = getBrowserVersion(browserName, browserVersionOffset)
                capabilities.setCapability(CapabilityType.BROWSER_VERSION,r.testBrowserVersion)
                r.log "Requesting remote Edge version: ${r.testBrowserVersion} ..."

                driver = new RemoteWebDriver(new URL(ConfigReader.get('gridHub')), capabilities)
                break

            default:
                throw new Exception("getActualDriver(): Unknown browser name: $browserName")
                break
        }

        // success
        if (driver) {
            // global driver settings
            if (!browserName.contains('mobile') && !browserName.contains('ios') && !browserName.contains('android')) {
                // TODO: check when mobile be added
                driver.manage().window().maximize()
            }
            //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(new Long(ConfigReader.get('implicitWaitSeconds'))))
            //driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(new Long(ConfigReader.get('pageLoadWaitSeconds'))))
            driver.manage().deleteAllCookies()
            r.originalWindowHandle = driver.getWindowHandle()

            // settings for remote drivers only
            if (browserName.startsWith('remote') || browserName.startsWith('mobile')) {
                driver.setFileDetector(new LocalFileDetector())
            }

            // console printing
            String dCaps = driver.getCapabilities()
            if (dCaps.length() > 160) {
                dCaps = dCaps[0..140]
            }
            r.log "WebDriver created: ${dCaps}"
        }

        // ready
        return driver
    }


    /** Get browser version **/
    private String getBrowserVersion(String browserName, int browserVersionOffset) {
        try {
            String[] versions = ConfigReader.get(browserName.replaceFirst('remote', '') + 'Versions').split(',')*.trim()
            return versions[versions.size() - 1 + browserVersionOffset]
        } catch (e) {
            if (!r.isRunDebug())
                r.log e.getMessage()
            else
                e.printStackTrace()
            throw new Exception('getBrowserVersion: Wrong browser name/version offset requested')
        }
    }

}

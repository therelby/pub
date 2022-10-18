package above


import above.web.helpers.WebProject
import above.web.helpers.WdFactory
import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.logging.LogEntries
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

/**
 *      Web Testing Concept Stuff
 *
 *      Versions:
 *          1.0 - New Framework Start
 *          1.0.0111 getWebDriver moved inside methods bodies - to avoid null pointer if driver reopen
 *          1.1.0128 WebDriverFactory got browser versions list handling basic functional
 *          1.2.0205 full browser versions support
 *          1.3-0610 driver health check up added
 *          2.0-0611 WebDriverFactory became dynamic and moved into RunWeb/test object
 */
abstract class RunWeb extends Run {

    final String webVersion = '3.0506.Selenium.4'

    // Concept - must be defined for correct test execution
    final String concept = 'web'
    // Web project instance
    WebProject webProject

    //Default wait time in seconds
    final Integer defaultWaitSeconds = ConfigReader.get('defaultWaitSeconds') as Integer
    final Integer pageLoadWaitSeconds = ConfigReader.get('pageLoadWaitSeconds') as Integer

    // Selenium WebDriver thread-safe storage
    final static ThreadLocal<WebDriver> driverStorage = new ThreadLocal<WebDriver>()
    // Browser
    Long testBrowserDriverCreated = null
    String testBrowser = ConfigReader.get('defaultBrowser')
    int testBrowserVersionOffset = 0
    String testBrowserVersion = 'not set'
    Boolean testBrowserRemoteLocally = false

    // Using
    Boolean usingBrowserCalled = false
    Boolean usingBrowserVersionOffsetCalled = false
    Boolean usingRemoteBrowserCalled = false
    Boolean usingEnvironmentCalled = false

    // Test Environment
    String testEnv = null
    List<String> testEnvSupported = []


    String originalWindowHandle

    /** Creating Instance for given class name */
    private Object xWeb(String className) {
        return xInst("above.web.$className")
    }
//======================================================================================================================
// Native logic
//======================================================================================================================

    /**
     * Get WebDriver instance reference with creating when needed
     */
    WebDriver getWebDriver() {

        // getting stored or creating and storing
        WebDriver driver = driverStorage.get()
        if (!driver) {
            logDebug "Creating and storing new WebDriver instance for $testBrowser"
            if (isLocalRun()) {
                driver = new WdFactory().getDriver(testBrowser, testBrowserVersionOffset)
            } else {
                driver = new WdFactory().getDriver(testBrowser, runSuite.suiteCurrentBrowserVersionOffset)
            }
            testBrowserDriverCreated = getRealTimeStamp()
            logDebug "-- Webdriver instance: $driver"
            driverStorage.set(driver)
        } else {
            // driver health checker
            try {
                driver.getSessionId()
            } catch (e) {
                testStop("STOPPING TEST BECAUSE WEBDRIVER HAS CRASHED... ${e.getMessage()}")
            }
        }

        return driver
    }


    /**
     * Set environment
     * -- changes default environment on-fly
     */
    void setEnvironment(String envName) {
        if (!webProject.envs.containsKey(envName)) {
            throw new Exception("Unsupported environment [$envName]")
        }
        webProject.testEnv = envName
        log("Set environment [$envName]", console_color_yellow)
    }


    /** Get current environment */
    String getCurrentEnvironment() {
        return webProject.testEnv
    }


    /**
     * Runs test with remote browser even locally
     * -- for example default browser 'chrome' will be changed to 'remotechrome'
     * -- if @param browserName provided it also calls .usingBrowser()
     * (!) For test class executing via Execute.suite() only
     * -- can be use in stack like TestClass.usingXxx().usingYYY().usingZZZ()
     * @return the Run/Test instance reference
     */
    RunWeb usingRemoteBrowser(String browserName) {
        if (browserName) {
            usingBrowser(browserName)
        }
        testBrowserRemoteLocally = true
        usingRemoteBrowserCalled = true
        return this
    }

    /**
     * Runs test with @param browserName browser
     * (!) browserName must be present in the configuration.properties/browserList
     * (!) For test class executing via Execute.suite() only
     * -- can be use in stack like TestClass.usingXxx().usingYYY().usingZZZ()
     * @return the Run/Test instance reference
     */
    RunWeb usingBrowser(String browserName) {
        if (browserName) {
            if (ConfigReader.get('browserList').split(',')*.trim().contains(browserName)) {
                testBrowser = browserName
            } else {
                throw new Exception('browserName must be in configuration.properties/browserList')
            }
        } else {
            throw new Exception('browserName must be provided')
        }
        usingBrowserCalled = true
        return this
    }

    /**
     * Runs test with @param browserVersionOffset
     * (!) browserName must be allowed in the configuration.properties
     * (!) For test class executing via Execute.suite() only
     * -- can be use in stack like TestClass.usingXxx().usingYYY().usingZZZ()
     * @return the Run/Test instance reference
     */
    RunWeb usingBrowserVersionOffset(int browserVersionOffset) {
        testBrowserVersionOffset = browserVersionOffset
        usingBrowserVersionOffsetCalled = true
        return this
    }

    /**
     * Runs test with @param envName environment
     * (!) envName must be present in the test class .setup() 'product:NAME|envNameList' option
     * (!) For test class executing via Execute.suite() only
     * -- can be use in stack like TestClass.usingXxx().usingYYY().usingZZZ()
     * @return the Run/Test instance reference
     */
    RunWeb usingEnvironment(String envName) {
        if (envName) {
            testEnv = envName
        } else {
            throw new Exception('envName must be provided')
        }
        usingEnvironmentCalled = true
        return this
    }

//======================================================================================================================
// FuncUrl
//======================================================================================================================

    String getCurrentUrl() {
        return (xWeb('FuncUrl') as above.web.FuncUrl).getCurrentUrl()
    }

//======================================================================================================================
// FuncUrlTool
//======================================================================================================================

    /** Get named page url based on WebProject instance data */
    String getUrl(String pageName) {
        return (xWeb('FuncUrlTools') as above.web.FuncUrlTools).getUrl(pageName)
    }

    /** Get named page url based on WebProject instance data */
    String getUrlRaw(String pageName) {
        return (xWeb('FuncUrlTools') as above.web.FuncUrlTools).getUrlRaw(pageName)
    }

    /** Check if current URL = url and navigate if needed */
    Boolean goOrStay(String url) {
        return (xWeb('FuncUrlTools') as above.web.FuncUrlTools).goOrStay(url)
    }

//======================================================================================================================
// FuncUrlParam
//======================================================================================================================

    /** Get parameters from url */
    List getAllParamsFromUrl(String url) {
        return (xWeb('FuncUrlParam') as above.web.FuncUrlParam).getAllParamsFromUrl(url)
    }
    /** Get param value from url */
    String getParamValueFromUrl(String url, String paramName) {
        return (xWeb('FuncUrlParam') as above.web.FuncUrlParam).getParamValueFromUrl(url, paramName)
    }
    /** Add param to url */
    String addParamToUrl(String url, String param) {
        return (xWeb('FuncUrlParam') as above.web.FuncUrlParam).addParamToUrl(url, param)
    }
    /** Remove all occurrences of param from url */
    String removeParamFromUrl(String url, String paramName) {
        return (xWeb('FuncUrlParam') as above.web.FuncUrlParam).removeParamFromUrl(url, paramName)
    }
    /** Remove all parameters from url, if no parameters returns original url*/
    String removeAllParamsFromUrl(String url) {
        return (xWeb('FuncUrlParam') as above.web.FuncUrlParam).removeAllParamsFromUrl(url)
    }

    /** Add parameters to url from parameters List */
    String addAllParamsToUrl(String url, List params) {
        return (xWeb('FuncUrlParam') as above.web.FuncUrlParam).addAllParamsToUrl(url, params)
    }
    /** Change first occurrence of param in url */
    String changeParamValueToUrl(String url, String paramName, String paramValue) {
        return (xWeb('FuncUrlParam') as above.web.FuncUrlParam).changeParamValueToUrl(url, paramName, paramValue)
    }

    /** Change first occurrence of param in url if exists, if not exists - add parameter */
    String changeOrAddParamToUrl(String url, String paramName, String paramValue) {
        return (xWeb('FuncUrlParam') as above.web.FuncUrlParam).changeOrAddParamToUrl(url, paramName, paramValue)
    }

    /** Get Parameter Value from url parameter, urlParameter 'order=price_asc'*/
    String getParamValueFromUrlParam(String urlParameter) {
        return (xWeb('FuncUrlParam') as above.web.FuncUrlParam).getParamValueFromUrlParam(urlParameter)
    }
    /** Get Parameter Name from url parameter, urlParameter 'order=price_asc'*/
    String getParamNameFromUrlParam(String urlParameter) {
        return (xWeb('FuncUrlParam') as above.web.FuncUrlParam).getParamNameFromUrlParam(urlParameter)
    }

//======================================================================================================================
// FuncClick
//======================================================================================================================

    /** Standard click */
    boolean click(xpathOrElement) {
        return (xWeb('FuncClick') as above.web.FuncClick).click(xpathOrElement)
    }

    /** Click and load page */
    boolean clickAndTryLoad(Object xpathOrElement, String expectedUrl) {
        return (xWeb('FuncClick') as above.web.FuncClick).clickAndTryLoad(xpathOrElement, expectedUrl)
    }

    /** Trying to click multiple times*/
    boolean tryClick(xpathOrElement, tryTimes = 5) {
        return (xWeb('FuncClick') as above.web.FuncClick).tryClick(xpathOrElement, tryTimes)
    }

    /** JavaScript click */
    boolean jsClick(xpathOrElement) {
        return (xWeb('FuncClick') as above.web.FuncClick).jsClick(xpathOrElement)
    }

    /** JavaScript set attribute */
    boolean jsSetStyle(WebElement element, String attrName, String attrValue) {
        return (xWeb('FuncStyleJs') as above.web.FuncStyleJs).jsSetStyle(element, attrName, attrValue)
    }

//======================================================================================================================
// FuncFind
//======================================================================================================================

    /** Find WebElement by xpath*/
    WebElement find(String xpath) {
        return (xWeb('FuncFind') as above.web.FuncFind).find(xpath)
    }

    /** Find WebElements by xpath*/
    List<WebElement> findElements(xpath) {
        return (xWeb('FuncFind') as above.web.FuncFind).findElements(xpath)
    }

    /** Find WebElements in element */
    List<WebElement> findElementsInElement(xpathOrElement, xpath) {
        return (xWeb('FuncFind') as above.web.FuncFind).findElementsInElement(xpathOrElement, xpath)
    }

    /** Find WebElement inside WebElement */
    WebElement findInElement(xpathOrElement, String xpath) {
        return (xWeb('FuncFind') as above.web.FuncFind).findInElement(xpathOrElement, xpath)
    }

    /** Find Random WebElement by xpath */
    WebElement findRandomElement(String xpath) {
        return (xWeb('FuncFind') as above.web.FuncFind).findRandomElement(xpath)
    }

    /** Focus on element by Xpath or WebElement */
    Boolean focus(xpathOrElement) {
        return (xWeb('FuncFind') as above.web.FuncFind).focus(xpathOrElement)
    }

//======================================================================================================================
// FuncWait
//======================================================================================================================
    /** Get new WebDriverWait obj */
    WebDriverWait getWebDriverWait(int timeOutInSec = defaultWaitSeconds) {
        return new WebDriverWait(getWebDriver(), Duration.ofSeconds(new Long(timeOutInSec)))
    }

    /** Wait for page to load */
    boolean waitForPage(Integer timeOutInSec = pageLoadWaitSeconds) {
        return (xWeb('FuncWait') as above.web.FuncWait).waitForPage(timeOutInSec)
    }

    /** Explicit wait in milliseconds */
    void sleep(milliseconds) {
        (xWeb('FuncWait') as above.web.FuncWait).sleep(milliseconds)
    }

    /** Wait for Jquery (JavaScript) to load on the page */
    boolean waitForJQuery(Integer timeOutInSec = defaultWaitSeconds) {
        return (xWeb('FuncWait') as above.web.FuncWait).waitForJQuery(timeOutInSec)
    }

    /** Wait for Document Ready to state */
    boolean waitForDocumentReadyState(Integer timeOutInSec = defaultWaitSeconds) {
        return (xWeb('FuncWait') as above.web.FuncWait).waitForDocumentReadyState(timeOutInSec)
    }

    /** Wait for element in the DOM */
    boolean waitForElement(String xpath, Integer timeOutInSec = defaultWaitSeconds) {
        return (xWeb('FuncCustomWait') as above.web.FuncCustomWait).waitForElement(xpath, timeOutInSec)
    }

    /** Wait for no element in the DOM */
    boolean waitForNoElement(String xpath, Integer timeOutInSec = defaultWaitSeconds) {
        return (xWeb('FuncCustomWait') as above.web.FuncCustomWait).waitForNoElement(xpath, timeOutInSec)
    }

    /** Wait for element clickable */
    boolean waitForElementClickable(xpathOrWebElement, Integer timeOutInSec = defaultWaitSeconds) {
        return (xWeb('FuncCustomWait') as above.web.FuncCustomWait).waitForElementClickable(xpathOrWebElement, timeOutInSec)
    }

    /** Wait for element unclickable */
    boolean waitForElementUnclickable(xpathOrWebElement, Integer timeOutInSec = defaultWaitSeconds) {
        return (xWeb('FuncCustomWait') as above.web.FuncCustomWait).waitForElementUnclickable(xpathOrWebElement, timeOutInSec)
    }

    /** Wait for Element Visible */
    boolean waitForElementVisible(xpathOrWebElement, Integer timeOutInSec = defaultWaitSeconds) {
        return (xWeb('FuncCustomWait') as above.web.FuncCustomWait).waitForElementVisible(xpathOrWebElement, timeOutInSec)
    }

    /** Wait for Element Invisible */
    boolean waitForElementInvisible(xpathOrWebElement, Integer timeOutInSec = defaultWaitSeconds) {
        return (xWeb('FuncCustomWait') as above.web.FuncCustomWait).waitForElementInvisible(xpathOrWebElement, timeOutInSec)
    }

    /** Wait for Attribute not Empty */
    boolean waitForAttributeNotEmpty(xpathOrWebElement, String attribute, Integer timeOutInSec = defaultWaitSeconds) {
        return (xWeb('FuncCustomWait') as above.web.FuncCustomWait).waitForAttributeNotEmpty(xpathOrWebElement, attribute, timeOutInSec)
    }
    /** Wait for Alert Present */
    boolean waitForAlertPresent(Integer timeOutInSec = defaultWaitSeconds) {
        return (xWeb('FuncCustomWait') as above.web.FuncCustomWait).waitForAlertPresent(timeOutInSec)
    }

    /** Wait for Input interactable */
    boolean waitForInputElementInteractable(xpath, Integer timeOutInSec = defaultWaitSeconds) {
        return (xWeb('FuncCustomWait') as above.web.FuncCustomWait).waitForInputElementInteractable(xpath, timeOutInSec)
    }
//======================================================================================================================
// FuncSelect
//======================================================================================================================

    /** Is Select allows multiple selections */
    boolean selectIsMultiple(xpathOrElement) {
        return (xWeb('FuncSelect') as above.web.FuncSelect).selectIsMultiple(xpathOrElement)
    }

    /** Select By Index */
    boolean selectByIndex(xpathOrElement, Integer index) {
        //   return funcStorage.getFuncSelect().selectByIndex(xpathOrElement, index)
        return (xWeb('FuncSelect') as above.web.FuncSelect).selectByIndex(xpathOrElement, index)
    }

    /** Select By Visible text */
    boolean selectByVisibleText(xpathOrElement, String visibleText) {
        return (xWeb('FuncSelect') as above.web.FuncSelect).selectByVisibleText(xpathOrElement, visibleText)
    }

    /** Select By value */
    boolean selectByValue(xpathOrElement, String value) {
        return (xWeb('FuncSelect') as above.web.FuncSelect).selectByValue(xpathOrElement, value)
    }

    /** Select Random Option */
    boolean selectRandomOption(xpathOrElement) {
        return (xWeb('FuncSelect') as above.web.FuncSelect).selectRandomOption(xpathOrElement)
    }

    /** Select Random Option and Return value attribute */
    String selectRandomOptionWithValue(xpathOrElement) {
        return (xWeb('FuncSelect') as above.web.FuncSelect).selectRandomOptionWithValue(xpathOrElement)
    }

    /** Deselect By Index */
    boolean deselectByIndex(xpathOrElement, Integer index) {
        return (xWeb('FuncSelect') as above.web.FuncSelect).deselectByIndex(xpathOrElement, index)
    }

    /** Deselect By Visible text */
    boolean deselectByVisibleText(parameter, String visibleText) {
        return (xWeb('FuncSelect') as above.web.FuncSelect).deselectByVisibleText(parameter, visibleText)
    }

    /** Deselect By value */
    boolean deselectByValue(parameter, String value) {
        return (xWeb('FuncSelect') as above.web.FuncSelect).deselectByValue(parameter, value)
    }

    /** Get All options from the Select Element */
    List<WebElement> selectGetOptions(xpathOrElement) {
        return (xWeb('FuncSelect') as above.web.FuncSelect).selectGetOptions(xpathOrElement)
    }

    /** Deselect All options */
    boolean selectDeselectAll(xpathOrElement) {
        return (xWeb('FuncSelect') as above.web.FuncSelect).selectDeselectAll(xpathOrElement)
    }

    /** Get All Selected Options from Select */
    List selectAllSelectedOptions(xpathOrElement) {
        return (xWeb('FuncSelect') as above.web.FuncSelect).selectAllSelectedOptions(xpathOrElement)
    }

    /** Get First Selected Option from Select  */
    WebElement selectFirstSelectedOption(xpathOrElement) {
        return (xWeb('FuncSelect') as above.web.FuncSelect).selectFirstSelectedOption(xpathOrElement)
    }
//======================================================================================================================
// FuncSelectJS
//======================================================================================================================

    /** Select By Index Using JavaScript JS */
    boolean jsSelectByIndex(xpathOrElement, index) {
        return (xWeb('FuncSelectJS') as above.web.FuncSelectJS).jsSelectByIndex(xpathOrElement, index)
    }

    /** Select By Index Using JavaScript JS */
    boolean jsSelectByVisibleText(xpathOrElement, visibleText) {
        return (xWeb('FuncSelectJS') as above.web.FuncSelectJS).jsSelectByVisibleText(xpathOrElement, visibleText)
    }

    /** Select By Index Using JavaScript JS */
    boolean jsSelectByValue(xpathOrElement, value) {
        return (xWeb('FuncSelectJS') as above.web.FuncSelectJS).jsSelectByValue(xpathOrElement, value)
    }
//======================================================================================================================
// FuncBrowserZoom
//======================================================================================================================
    /** Setting Browser zoom level using JS **/
    boolean jsSetBrowserZoom(double zoomLevel) {
        return (xWeb('FuncBrowserZoom') as above.web.FuncBrowserZoom).jsSetBrowserZoom(zoomLevel)
    }
//======================================================================================================================
// FuncBrowser
//======================================================================================================================

    /** Returns true if browser is open **/
    boolean isBrowserOpen() {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).isOpen()
    }

    /** Opens the browser **/
    boolean openBrowser(String url = webProject.pages.default) {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).open(url)
    }

    /** Opens the browser and maximizes the window **/
    boolean openAndMaximize(String url = webProject.pages.default) {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).openAndMaximize(url)
    }

    boolean openAndResize(String url = webProject.pages.default, int width, int height) {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).openAndResize(url, width, height)
    }

    boolean openAndResizeToDevice(String url = webProject.pages.default, deviceName) {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).openAndResizeToDevice(url, deviceName)
    }

    boolean newTab(String url = webProject.pages.default) {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).newTab(url)
    }

    boolean switchToTab(String handle) {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).switchToTab(handle)
    }

    /** Returns window handles of all tabs **/
    LinkedHashSet getTabs() {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).getTabs()
    }

    /** Gets number of tabs open **/
    Integer getTabCount() {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).getTabCount()
    }

    /** Closes current tab **/
    boolean closeTab() {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).closeTab()
    }

    boolean maximizeBrowser() {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).maximize()
    }

    boolean resizeBrowser(int width, int height) {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).resize(width, height)
    }

    boolean resizeToDevice(String deviceName) {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).resizeToDevice(deviceName)
    }

    /** Returns outer window dimensions in [width, height] **/
    List getSizeOfWindow() {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).getSizeOfOuterWindow()
    }

    /** Returns Viewport dimension in [width, height] **/
    List getSizeOfViewport() {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).getSize()
    }

    boolean refresh() {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).refresh()
    }

    /** Closes browser. */
    boolean closeBrowser() {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).close()
    }

    /** Open Browser Blanc, maximazing window, try load Page from url */
    boolean openAndTryLoad(String url) {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).openAndTryLoad(url)
    }

    /** Returns  console log */
    LogEntries getConsoleMessages() {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).getConsoleMessages()
    }

    /** Navigate back to previous page */
    Boolean back() {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).back()
    }

    /** Set implicit timeout to the webdriver */
    Boolean setImplicitTimeout(Integer timeOutInSeconds) {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).setImplicitTimeout(timeOutInSeconds)
    }

    /** Set DEFAULT (from configuration) implicit timeout to the webdriver */
    Boolean setImplicitTimeoutToDefault() {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).setImplicitTimeoutToDefault()
    }

    /** Set Page Load timeout to the webdriver */
    Boolean setPageLoadTimeout(Integer timeOutInSeconds) {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).setPageLoadTimeout(timeOutInSeconds)
    }

    /** Set DEFAULT (from configuration) Page Load timeout to the webdriver */
    Boolean setPageLoadTimeoutToDefault() {
        return (xWeb('FuncBrowser') as above.web.FuncBrowser).setPageLoadTimeoutToDefault()
    }

//======================================================================================================================
// FuncRead
//======================================================================================================================

    /** read text from webelement or xpath */
    String getText(xpathOrElement) {
        return (xWeb('FuncRead') as above.web.FuncRead).getText(xpathOrElement)
    }

    /** read text from webelement or xpath return empty string if not found*/
    String getTextSafe(xpathOrElement) {
        return (xWeb('FuncRead') as above.web.FuncRead).getTextSafe(xpathOrElement)
    }

    /** read text from webelement and exclude all child node text's*/
    String getNodeText(xpathOrElement) {
        return (xWeb('FuncRead') as above.web.FuncRead).getNodeText(xpathOrElement)
    }

    /** set text(input) from webelement or xpath */
    boolean setText(xpathOrElement, String text) {
        return (xWeb('FuncRead') as above.web.FuncRead).setText(xpathOrElement, text)
    }
    /** set text(input) webelement or xpath char by char with delay */
    boolean setTextCharByChar(xpathOrElement, String text, Integer delayInMilliseconds = 0) {
        return (xWeb('FuncTextCharByChar') as above.web.FuncTextCharByChar).setTextCharByChar(xpathOrElement, text, delayInMilliseconds)
    }
    /** set text(input) from webelement or xpath using JavaScript */
    boolean jsSetText(xpathOrElement, String text) {
        return (xWeb('FuncRead') as above.web.FuncRead).jsSetText(xpathOrElement, text)
    }
    /** read or get one attribute from xpath or webelement by it's name  */
    String getAttribute(xpathOrElement, String attribute) {
        return (xWeb('FuncRead') as above.web.FuncRead).getAttribute(xpathOrElement, attribute)
    }
    /** read or get one attribute Safe from xpath or webelement by it's name - no element return "" */
    String getAttributeSafe(xpathOrElement, String attribute) {
        return (xWeb('FuncRead') as above.web.FuncRead).getAttributeSafe(xpathOrElement, attribute)
    }
    /**This method NOT Properly returns 'value' attr from input elements - use getAttribute(inputElement,'value')*/
    Map getAllAttributes(xpathOrElement) {
        return (xWeb('FuncRead') as above.web.FuncRead).getAllAttributes(xpathOrElement)
    }
    /**Get All Attributes - Empty map if no element*/
    Map getAllAttributesSafe(xpathOrElement) {
        return (xWeb('FuncRead') as above.web.FuncRead).getAllAttributesSafe(xpathOrElement)
    }
    /** returns current page title and null in case of exception */
    String getTitle() {
        return (xWeb('FuncRead') as above.web.FuncRead).getTitle()
    }

    /** read text from webelement or xpath using JavaScript JS*/
    String jsGetText(xpathOrElement) {
        return (xWeb('FuncRead') as above.web.FuncRead).jsGetText(xpathOrElement)
    }

    /** clear text(input) for webelement or xpath */
    boolean clearText(xpathOrElement) {
        return (xWeb('FuncRead') as above.web.FuncRead).clearText(xpathOrElement)
    }

    /** clear text(input) using Actions for webelement or xpath */
    boolean clearTextAction(xpathOrElement) {
        return (xWeb('FuncClearTextAction') as above.web.FuncClearTextAction).clearTextAction(xpathOrElement)
    }

    /** get Css value for webelement or xpath */
    String getCssValue(xpathOrElement, String cssProperty) {
        return (xWeb('FuncRead') as above.web.FuncRead).getCssValue(xpathOrElement, cssProperty)
    }
    /** get Tag Name for webelement or xpath */
    String getTagName(xpathOrElement) {
        return (xWeb('FuncRead') as above.web.FuncRead).getTagName(xpathOrElement)
    }
//======================================================================================================================
// FuncCoordinate
//======================================================================================================================

    /** get Element Size for webelement or xpath */
    List getElementSize(xpathOrElement) {
        return (xWeb('FuncCoordinate') as above.web.FuncCoordinate).getElementSize(xpathOrElement)
    }

    /** get Element Size for webelement or xpath */
    List getElementLocation(xpathOrElement) {
        return (xWeb('FuncCoordinate') as above.web.FuncCoordinate).getElementLocation(xpathOrElement)
    }

//======================================================================================================================
// FuncPageLoad
//======================================================================================================================

    /** Try load page with known issues handling */
    boolean tryLoad(String targetUrl = null) {
        return (xWeb('FuncPageLoad') as above.web.FuncPageLoad).tryLoad(targetUrl)
    }

    /** Try load page with known issues handling */
    boolean tryLoad(String xpath, String targetUrl) {
        return (xWeb('FuncPageLoad') as above.web.FuncPageLoad).tryLoad(targetUrl, xpath)
    }

    /** Try load page with known issues handling */
    boolean tryLoad(WebElement element, String targetUrl) {
        return (xWeb('FuncPageLoad') as above.web.FuncPageLoad).tryLoad(targetUrl, element)
    }

    /** Try load page with known issues handling */
    boolean loadPage(String url) {
        return (xWeb('FuncPageLoad') as above.web.FuncPageLoad).loadPage(url)
    }

    /** Load page with wait */
    boolean loadPageAndWait(String url) {
        return (xWeb('FuncPageLoad') as above.web.FuncPageLoad).loadPageAndWait(url)
    }

    /** Get page error message or null if none */
    String getPageError() {
        return (xWeb('FuncPageLoad') as above.web.FuncPageLoad).getPageError()
    }

    /** Get page error message or null if none */
    boolean isPageError() {
        return (xWeb('FuncPageLoad') as above.web.FuncPageLoad).isPageError()
    }

    /** Get page source code */
    String getPageSource() {
        return (xWeb('FuncPageLoad') as above.web.FuncPageLoad).getPageSource()
    }

    /** Get visible page text */
    String getPageText() {
        return (xWeb('FuncPageLoad') as above.web.FuncPageLoad).getPageText()
    }

//======================================================================================================================
// FuncPageText
//======================================================================================================================

    /** Is page contains text */
    boolean isPageContainsText(String text, String pageText) {
        return (xWeb('FuncPageText') as above.web.FuncPageText).isPageContainsText(text, pageText)
    }

    /** Check if an element contains text */
    boolean isElementContainsText(String text, String xpathOrElement) {
        return (xWeb('FuncPageText') as above.web.FuncPageText).isElementContainsText(text, xpathOrElement)
    }

//======================================================================================================================
// FuncRadioButton
//======================================================================================================================

    /** Element
     * Checking is Element Enabled or Disabled on the page
     * parameter - xpath, WebElement
     * */

    boolean isEnabled(xpathOrElement) {
        return (xWeb('FuncCheckElements') as above.web.FuncCheckElements).isEnabled(xpathOrElement)
    }

    /** Radio Button check is it selected -> Radio checkbox element is checked
     * parameter - xpath, WebElement
     * */
    boolean isChecked(xpathOrElement) {
        return (xWeb('FuncCheckElements') as above.web.FuncCheckElements).isChecked(xpathOrElement)
    }
    /** Radio Button select Radio Button
     * Check element if if was unchecked(if checked - do nothing)
     * parameter - xpath, WebElement
     * */
    boolean checkCheckbox(xpathOrElement) {
        return (xWeb('FuncCheckElements') as above.web.FuncCheckElements).checkCheckbox(xpathOrElement)
    }
    /** Radio Button select Radio Button -> Check radio or checkbox element
     * Uncheck element if if was unchecked(if checked - do nothing)
     * parameter - xpath, WebElement
     * */
    boolean uncheckCheckbox(xpathOrElement) {
        return (xWeb('FuncCheckElements') as above.web.FuncCheckElements).uncheckCheckbox(xpathOrElement)
    }

//======================================================================================================================
// FuncFile
//======================================================================================================================

    /** Download file from url
     *
     * url - link to file, fileName - desired file name, if null
     * */

    String downloadFile(String url, String fileName = null) {
        return (xWeb('FuncFile') as above.web.FuncFile).downloadFile(url, fileName)
    }

//======================================================================================================================
// FuncVerify
//======================================================================================================================

    /** Verify Element present on the current page */
    Boolean verifyElement(xpath) {
        return (xWeb('FuncVerify') as above.web.FuncVerify).verifyElement(xpath)
    }

    /** Verify Element visible on the current page without wait*/
    Boolean elementVisible(xpathOrElement) {
        return (xWeb('FuncVerify') as above.web.FuncVerify).verifyVisible(xpathOrElement)
    }

    /** Verify Element Clickable on the current page without wait*/
    Boolean elementClickable(xpathOrElement) {
        return (xWeb('FuncVerify') as above.web.FuncVerify).verifyClickable(xpathOrElement)
    }

    /** Verify Element present inside given Webelement */
    Boolean verifyElementInElement(xpathOrWebElement, String xpath) {
        return (xWeb('FuncVerify') as above.web.FuncVerify).verifyElementInElement(xpathOrWebElement, xpath)
    }
//======================================================================================================================
// FuncCustomVerify (for verify methods that used not very often)
//======================================================================================================================
    /** Verify Element scrollable */
    Boolean verifyScrollable(xpathOrWebElement) {
        return (xWeb('FuncCustomVerify') as above.web.FuncCustomVerify).verifyScrollable(xpathOrWebElement)
    }
//======================================================================================================================
// FuncAlert
//======================================================================================================================


    Boolean acceptAlert() {
        return (xWeb('FuncAlert') as above.web.FuncAlert).acceptAlert()
    }

    Boolean dismissAlert() {
        return (xWeb('FuncAlert') as above.web.FuncAlert).dismissAlert()
    }

    String getAlertText() {
        return (xWeb('FuncAlert') as above.web.FuncAlert).getAlertText()
    }

    Boolean isAlertPresent() {
        return (xWeb('FuncAlert') as above.web.FuncAlert).isAlertPresent()
    }

    Boolean isAlertPresentRunStuffOnly() {
        return (xWeb('FuncAlert') as above.web.FuncAlert).isAlertPresentRunStuffOnly()
    }

    Boolean setTextToAlert(String text) {
        return (xWeb('FuncAlert') as above.web.FuncAlert).setTextToAlert(text)
    }


//======================================================================================================================
// FuncCookie
//======================================================================================================================

    /** Set cookie to browser */
    boolean setCookie(String name, String value) {
        return (xWeb('FuncCookie') as above.web.FuncCookie).setCookie(name, value)
    }

    /** Confirms if cookies appears */
    boolean verifyCookie(String name) {
        return (xWeb('FuncCookie') as above.web.FuncCookie).verifyCookie(name)
    }

    /** Get cookie to browser */
    String getCookie(String name) {
        return (xWeb('FuncCookie') as above.web.FuncCookie).getCookie(name)
    }

    /** Removes specified cookie*/
    boolean removeCookie(String name) {
        return (xWeb('FuncCookie') as above.web.FuncCookie).removeCookie(name)
    }

    /** Removes All cookies*/
    boolean deleteAllCookies() {
        return (xWeb('FuncCookie') as above.web.FuncCookie).deleteAllCookies()
    }

//======================================================================================================================
// FuncAction
//======================================================================================================================

    /** Get Actions object */
    Actions getAction() {
        return (xWeb('FuncAction') as above.web.FuncAction).getAction()
    }

    /** Scroll to Element using Actions by WebElement or xpath */
    boolean scrollTo(elementOrXpath) {
        return (xWeb('FuncAction') as above.web.FuncAction).scrollTo(elementOrXpath)
    }
    /** Scroll to Element using JavaScript by WebElement or xpath */
    boolean jsScrollTo(elementOrXpath) {
        return (xWeb('FuncAction') as above.web.FuncAction).jsScrollTo(elementOrXpath)
    }

    /** Scroll to the Bottom of the page*/
    boolean scrollToBottom() {
        return (xWeb('FuncAction') as above.web.FuncAction).scrollToBottom()
    }
    /** Scroll to the Top of the page*/
    boolean scrollToTop() {
        return (xWeb('FuncAction') as above.web.FuncAction).scrollToTop()
    }

    /** Getting shadow webelement from its parent/parent element*/
    // THIS FUNCTIONALITY is not working in all browsers
    SearchContext getShadowElement(WebElement parentElement) {
        (xWeb('FuncAction') as above.web.FuncAction).getShadowElement(parentElement)
    }
    /** Get page vertical scroll Position offset*/
    Long pageOffsetVertical() {
        return (xWeb('FuncAction') as above.web.FuncAction).pageOffsetVertical()
    }

    /** Scroll by Pixels */
    boolean scrollByPixels(int xPixel, int yPixel) {
        return (xWeb('FuncAction') as above.web.FuncAction).scrollByPixels(xPixel, yPixel)
    }

    void jsDragAndDrop(String xpathObj, String xpathTo, Integer xOffset = 0, Integer yOffset = 0) {
        (xWeb('FuncAction') as above.web.FuncAction).jsDragAndDrop(xpathObj, xpathTo, xOffset, yOffset)
    }

    /** Put mouse cursor over an element */
    boolean mouseOver(xpathOrElement) {
        return (xWeb('FuncAction') as above.web.FuncAction).mouseOver(xpathOrElement)
    }

    /** Put mouse cursor over an element */
    boolean jsMouseOver(xpathOrElement) {
        return (xWeb('FuncAction') as above.web.FuncAction).jsMouseOver(xpathOrElement)
    }

    /** Click using Actions */
    boolean actionClick(xpathOrElement) {
        return (xWeb('FuncAction') as above.web.FuncAction).actionClick(xpathOrElement)
    }
//======================================================================================================================
// FuncWindow
//======================================================================================================================

    /** get Current window handle */
    String getWindowHandle() {
        return (xWeb('FuncWindow') as above.web.FuncWindow).getWindowHandle()
    }

    /** returns set of all windows handles */
    Set getWindowHandles() {
        return (xWeb('FuncWindow') as above.web.FuncWindow).getWindowHandles()
    }

    /** Switch to window by handle */
    Boolean switchToWidow(String handle) {
        return (xWeb('FuncWindow') as above.web.FuncWindow).switchToWidow(handle)
    }

    /** Switch to window by it's title */
    Boolean switchToWindowTitle(String windowTitle) {
        return (xWeb('FuncWindow') as above.web.FuncWindow).switchToWindowTitle(windowTitle)
    }
    /** Switch to window by it's partial url */
    Boolean switchToWindowUrlPart(String urlPart) {
        return (xWeb('FuncWindow') as above.web.FuncWindow).switchToWindowPartialUrl(urlPart)
    }

    /** Close current window (if there is more then 1 windows) and switch to other window*/
    Boolean closeWindow() {
        return (xWeb('FuncWindow') as above.web.FuncWindow).closeWindow()
    }

    /** get Original window handle */
    String getOriginalWindowHandle() {
        return originalWindowHandle
    }

    /** switch to Original window  */
    Boolean switchToOriginalWindow() {
        return (xWeb('FuncWindow') as above.web.FuncWindow).switchToOriginalWindow()
    }
    /** close All Browser windows except Original window  */
    Boolean closeAllWindowsButOriginal() {
        return (xWeb('FuncWindow') as above.web.FuncWindow).closeAllWindowsButOriginal()
    }
    /** Get Titles from all browser windows  */
    List<String> getWindowTitles() {
        return (xWeb('FuncWindow') as above.web.FuncWindow).getWindowTitles()
    }

//======================================================================================================================
// FuncIframe
//======================================================================================================================

    /** Switch to iframe by xpath or WebElement*/
    Boolean switchToIframe(xpathOrWebElement) {
        return (xWeb('FuncIframe') as above.web.FuncIframe).switchToIframe(xpathOrWebElement)
    }

    /** Switch back to default content from Iframe */
    Boolean switchToDefaultContent() {
        return (xWeb('FuncIframe') as above.web.FuncIframe).switchToDefaultContent()
    }

//======================================================================================================================
// FuncScreenshot
//======================================================================================================================

    /** Take entire page screenshot */
    String takeScreenshot() {
        return (xWeb('FuncScreenshot') as above.web.FuncScreenshot).takeScreenshot()
    }

    /** Take web element screenshot by Xpath or WebElement*/
    def takeScreenshot(webElementOrXpath) {
        return (xWeb('FuncScreenshot') as above.web.FuncScreenshot).takeScreenshot(webElementOrXpath)
    }

//======================================================================================================================
// FuncDOMEdit
//======================================================================================================================

    /** Add Element/node to the Current Page DOM */
    Boolean addElementToCurrentPage(String tagName, String elementText, WebElement parentElement = null) {
        return (xWeb('FuncDOMEdit') as above.web.FuncDOMEdit).addElementToCurrentPage(tagName, elementText, parentElement)
    }

    /** Add Element/node to the Current Page DOM */
    Boolean deleteElementFromCurrentPage(xpathOrWebElement) {
        return (xWeb('FuncDOMEdit') as above.web.FuncDOMEdit).deleteElementFromCurrentPage(xpathOrWebElement)
    }

    /** Add attribute to the element in Current Page DOM */
    Boolean addAttributeToElement(WebElement element, String attributeName, String attributeValue) {
        return (xWeb('FuncDOMEdit') as above.web.FuncDOMEdit).addAttributeToElement(element, attributeName, attributeValue)

    }

    /** Delete attribute from the element in Current Page DOM */
    Boolean deleteAttribute(xpathOrWebElement, String attributeName) {
        return (xWeb('FuncDOMEdit') as above.web.FuncDOMEdit).deleteAttribute(xpathOrWebElement, attributeName)
    }

    /** Mark Current Page by adding unique attribute to the body element */
    Boolean markPage() {
        return (xWeb('FuncDOMEdit') as above.web.FuncDOMEdit).markPage()
    }

    /** Check if current page was marked with unique attribute on body element*/
    Boolean isPageMarked() {
        return (xWeb('FuncDOMEdit') as above.web.FuncDOMEdit).isPageMarked()
    }

}

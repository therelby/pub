package above

import above.desktop.DesktopProject
import above.desktop.WindowsDriverFactory
import io.appium.java_client.AppiumDriver
import io.appium.java_client.windows.WindowsDriver
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

/**
 *      Desktop Testing Concept
 *      Variable and function, page init for all page and some testing functions for desktop app
 *
 * @author ttoanle
 */
abstract class RunDesktop extends Run {

    final String concept = 'desktop'
    DesktopProject desktopProject
    public static WindowsDriver windowsDriver

    /**
     * Close all current running application and start application
     * @param spsAppId
     * @param spsAppName
     * @return
     */
    def openApp(def appId, def appName,def appProcess, def driverUrl) {
        closeCurrentApplicationProcess(appProcess)
        WindowsDriverFactory.openApp(appId,driverUrl)
        String appHandler = WindowsDriverFactory.getAppHandle(appName,driverUrl)
        windowsDriver = WindowsDriverFactory.getDriver(appHandler,driverUrl)
        return windowsDriver
    }

    def checkIfAppNeedToUpdate(){
        if(windowsDriver.getTitle().equals('')){
            return true
        }else{
            return false
        }
    }

    /**
     * Kill all current windows app process to start the new one
     * @return
     */
    def closeCurrentApplicationProcess(def appProcess) {
        Runtime.getRuntime().exec("taskkill /f /im " + appProcess)
    }

    /**
     * Wait for application element to be displayed
     * @param windowsDriver
     * @param element
     */
    def waitForElementDisplayed(WindowsDriver driver=windowsDriver, WebElement element, def timeOutInSecond = 20) {
        try{
            new WebDriverWait(driver, timeOutInSecond).ignoring(StaleElementReferenceException.class).until(ExpectedConditions.visibilityOf(element))
            return true
        }catch(Exception e){
            return false
        }
    }

    /**
     * Wait for application element to be clickable
     * @param windowsDriver
     * @param element
     */
    def waitForElementClickable(WindowsDriver driver=windowsDriver, WebElement element) {
        new WebDriverWait(driver, 20).ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.elementToBeClickable(element));
    }

    /**
     * Wait for popUp to display
     */
    def waitForPopUpDisplay(timeInSecond = 20) {
        WebDriverWait wait = new WebDriverWait(windowsDriver, timeInSecond);
        try {
            wait.until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return (d.getWindowHandles().size() != 1);
                }
            });
        } catch (Exception e) {
            return false
        }
    }

    /**
     * Wait for some second for the application to load
     * @param seconds
     */
    def waitForSomeSecond(int seconds = 3){
        try{
            Thread.sleep(seconds * 1000)
        }catch(Exception ex){
            ex.printStackTrace()
        }
    }

    /**
     * Switch to popup windows if the popup don't have any name
     * Return the windows handle of the main application
     */
    String switchToPopUp(){
        waitForPopUpDisplay()
        Set<String> windows = windowsDriver.getWindowHandles()
        String currentWindow = windowsDriver.getWindowHandle()
        for(String window : windows){
            if(window != currentWindow) {
                windowsDriver.switchTo().window(window)
            }
        }
        return currentWindow
    }

    /**
     * Switch to windows application which is navigated from SPS
     * This function return the the windows as an Element and user can continue work on this element as other element
     * Incase switch To Windows, user dont need to switch to main application after finished
     * @param popUpName
     * @return
     * D
     */
    WebElement switchToWindows(String windowName, String driverUrl){
        AppiumDriver DesktopSession = WindowsDriverFactory.getDesktopSession(driverUrl)
        WebElement window = DesktopSession.findElementByName(windowName)
        return window
    }

    /**
     * Verify element is displayed on application and active on screen
     * @Note for window application, to check element display we have to use 2 attribute "IsEnabled"=True and "IsOffscreen" = False
     * The function isDisplayed() of selenium will not work
     * @param element
     * @return true or false
     */
    def verifyElementDisplayedAndEnabled(WebElement element) {
        try{
            waitForElementDisplayed(element)
            return element.getAttribute("IsEnabled").equals("True") & element.getAttribute("IsOffscreen").equals("False")
        }catch(Exception e){
            return false
        }
    }

    /**
     * Verify element is enabled on application
     * @param element
     * @return true or false
     */
    def verifyElementEnabled(WebElement element){
        return element.getAttribute("IsEnabled").equals('True')
    }

    /**
     * Verify Element is available on application
     * @param element
     * @return true or false
     */
    def verifyElementDisplay(WebElement element){
        return element.getAttribute("IsOffscreen").equals('False')
    }

    /**
     * Check if current element is on focus
     * @param element
     * @return
     */
    def verifyCursorOnElement(WebElement element){
        waitForElementDisplayed(element)
        WebElement focusElement = windowsDriver.switchTo().activeElement()
        return focusElement.equals(element)
    }

    /**
     * Function to execute command on windows
     * @param command can be String or String array
     * @return
     */
    def executeCmd(def command){
        try{
            Runtime runtime = Runtime.getRuntime()
            Process p2 = runtime.exec(command)
            int exitVal = p2.waitFor()
            log(exitVal)
            if(exitVal==0){
                return true
            }
            return false
        }catch(Exception e){
            log(e)
            return false
        }
    }

}

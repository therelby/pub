package above.desktop

import io.appium.java_client.AppiumDriver
import io.appium.java_client.windows.WindowsDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.DesiredCapabilities

import java.util.concurrent.TimeUnit

/**
 * Windows driver factory to get driver from the started application on desktop
 *
 * @author ttoanle
 */
class WindowsDriverFactory {

    /**
     * Get windows driver from the app handle of desktop app
     * @param appHandler
     * @param driverUrl
     * @return
     */
    static WindowsDriver getDriver(def windowsHandler, def driverUrl) {
        return getWindowsAppDriver(windowsHandler,driverUrl)
    }

    /**
     * Open universal application using AppiumDriver and set capabilities with the URL for winappdriver to listen
     * @param appId
     * @return
     */
    static openApp(def type = 'app', def appId, def driverUrl) {
        DesiredCapabilities capabilities = new DesiredCapabilities()
        capabilities.setCapability(type, appId)
        capabilities.setCapability("ms:waitForAppLaunch",'25')
        try {
            new AppiumDriver(new URL(driverUrl), capabilities)
        } catch (Exception e) {
        }
    }

    /**
     * Get current desktop session
     * @return
     */
    static getDesktopSession(def winAppDriverUrl) {
        DesiredCapabilities desktopCapabilities = new DesiredCapabilities();
        desktopCapabilities.setCapability("platormName", "Windows");
        desktopCapabilities.setCapability("app", "Root");
        desktopCapabilities.setCapability("deviceName", "WindowsPC")
        AppiumDriver DesktopSession = new WindowsDriver(new URL(winAppDriverUrl), desktopCapabilities);
        return DesktopSession
    }

    /**
     * Get App handle for the application with application Name
     * @param appName
     * @return windows handles for the application
     */
    static getAppHandle(def appName, def winAppDriverUrl) {
        AppiumDriver DesktopSession = getDesktopSession(winAppDriverUrl)
        List<WebElement> windowsElement = DesktopSession.findElementsByClassName('Window')
        for(WebElement window : windowsElement){
            def windowsHandle = window.getAttribute("NativeWindowHandle")
            try{
                if(window.getAttribute("Name").contains(appName))  return windowsHandle
            }catch(Exception e){
                try{
                    if(window.getAttribute('AutomationId').equals('UpdateWindow'))
                    return windowsHandle
                }catch(Exception e1){
                    return null
                }
            }
        }
    }

    /**
     * Get the windows Driver of the application
     * @param windowsHandles
     * @return driver for the windows application
     */
    static getWindowsAppDriver(def windowsHandle, def winAppDriverUrl) {
        try{
            def windowsHandles = Integer.toHexString(Integer.parseInt(windowsHandle))
            DesiredCapabilities appCapabilities = new DesiredCapabilities()
            appCapabilities.setCapability("deviceName", "WindowsPC")
            appCapabilities.setCapability("appTopLevelWindow", windowsHandles)
            WindowsDriver driver = new WindowsDriver(new URL(winAppDriverUrl), appCapabilities)
            return driver
        }catch(Exception e){
            println("Cannot start application and get app handle for this application with exception $e")
            return null
        }

    }

}

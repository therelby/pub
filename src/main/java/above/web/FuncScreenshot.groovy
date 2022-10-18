package above.web

import above.ConfigReader
import above.RunWeb
import all.Db
import all.DbTools
import org.apache.commons.io.FileUtils
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 *      Screenshot browser window or page elements
 *      (!) Different WebDriver handling way
 *      -- doesn't create new WebDriver
 *      -- takes screenshots only when browser already open
 * @author akudin vdiachuk
 */
class FuncScreenshot {

    RunWeb r = run()


    /** Entire page view screenshot - returns file name */
    String takeScreenshot() {

        r.log 'Taking screenshot...'

        // file name
        String fileName = "${System.getProperty("java.io.tmpdir").toString()}screenshot-${UUID.randomUUID().toString().replace('-', '')}.png"

        // taking screenshot
        if (r.concept == 'web') {

            // entire web page screenshot
            WebDriver driver = r.driverStorage.get() //r.getWebDriver()
            if (driver) {
                r.logDebug("Taking entire page screenshot to $fileName")
                try {
                    File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE)
                    FileUtils.copyFile(scrFile, new File(fileName))
                    return fileName
                } catch (Exception e) {
                    r.addIssueTrackerEvent("Can't take entire page screenshot", e)
                    return ''
                }
            } else {
                r.logDebug("Not taking screenshot because no browser opened")
                return ''
            }

        } else if (r.concept == 'desktop') {

            // Windows app current view screenshot
            //File scrFile = ((TakesScreenshot)windowsDriver).getScreenshotAs(OutputType.FILE)
            return ''

        } else {
            return ''
        }
    }


    /** Element screenshot - returns file name */
    String takeScreenshot(WebElement element) {
        try {
            WebDriver driver = r.getWebDriver()
            if (driver) {
                String fileName = "${System.getProperty("java.io.tmpdir").toString()}screenshot-${UUID.randomUUID().toString().replace('-', '')}.png"
                r.logDebug("Taking WebElement screenshot to $fileName")
                File fileScreenshot = element.getScreenshotAs(OutputType.FILE)
                // writing to the file
                FileUtils.copyFile(fileScreenshot, new File(fileName))
                return fileName
            } else {
                r.addIssueTrackerEvent("Not taking screenshot because no browser opened")
                return ''
            }
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not take Screenshot - Exception", e)
            return ''
        }
    }

    /** Element screenshot by Xpath - returns file name */
    String takeScreenshot(String xpath) {
        WebDriver driver = r.getWebDriver()
        if (driver) {
            WebElement webElement = r.find(xpath)
            if (!webElement) {
                r.addIssueTrackerEvent("Can not Take a Screenshot by xpath: $xpath - no WebElement found")
                return ''
            }
            return r.takeScreenshot(webElement)
        } else {
            r.addIssueTrackerEvent("Not taking screenshot because no browser opened")
            return ''
        }
    }

}

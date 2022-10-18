package all

import org.apache.commons.io.FileUtils
import org.openqa.selenium.OutputType

/**
 * Screenshots handling
 * @author akudin
 */
class ScreenCapture {

	/**
	 * Take element screenshot
	 * (!) Chrome browser only
 	 */
	def static elementScreenshot(String xpath, String fileName) {
		try {
			def elements = run().findElements(xpath)
			def scrFile = elements[0].getScreenshotAs(OutputType.FILE)
			FileUtils.copyFile(scrFile, new File(fileName))
		} catch (ignored) {}
	}

}


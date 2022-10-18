package above.web

import above.RunWeb
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver

/**
 * Handling browser zooming
 */
class FuncBrowserZoom {

    RunWeb r = run()

    /**
     * Setting Browser zoom level using JS
     * zoomLevel 0.5 for zoom out, 1.3 for zoom in
     * @return
     */
    boolean jsSetBrowserZoom(double zoomLevel){
        try{
            WebDriver driver = r.getWebDriver()
            JavascriptExecutor executor = (JavascriptExecutor) driver
            executor.executeScript("document.body.style.zoom = '$zoomLevel'")
            return true
        }catch(Exception e){
            r.addIssueTrackerEvent("Can not change Browser Zoom level to: [$zoomLevel] using JS - Exception", e)
            return false
        }
    }
}

package above.web

import above.RunWeb
import org.openqa.selenium.WebElement

/**
 * Handling location, size functionality
 * @author vdiachuk
 */
class FuncCoordinate {

    RunWeb r = run()

    /**
     *  Get size of the element by Xpath
     */
    List getElementSize(String xpath) {
        WebElement element = r.find(xpath)
        if (!element) {
            r.addIssueTrackerEvent("Error getting size of element - No Element: [$xpath]")
            return [-1, -1]
        }
        return getElementSize(element)
    }

    /**
     *   Get size of the element by WebElement
     */
    List getElementSize(WebElement element) {
        r.logDebug "Getting element size"
        try {
            return [element.getSize().getWidth(), element.getSize().getHeight()]
        } catch (Exception e) {
            r.addIssueTrackerEvent("Error getting size of the element", e)
            return [-1, -1]
        }
    }


    /**
     *   Get element location by Xpath
     */
    List getElementLocation(String xpath) {
        WebElement element = r.find(xpath)
        if (!element) {
            r.addIssueTrackerEvent("Error getting element Location - No Element: [$xpath]")
            return [-1, -1]
        }
        return getElementLocation(element)
    }
    /**
     *   Get element location by WebElement
     */
    List getElementLocation(WebElement element) {
        r.logDebug "Getting element Location"
        try {
            return [element.getLocation().getX(), element.getLocation().getY()]
        } catch (Exception e) {
            r.addIssueTrackerEvent("Error getting Location the of element", e)
            return [-1, -1]
        }
    }

}

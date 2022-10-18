package above.web

import above.RunWeb
import org.openqa.selenium.WebElement

/**
 *      Class Handling Verify functionality
 *       kyilmaz vdiachuk
 */

class FuncVerify {

    RunWeb r = run()


    /** Verify element present in DOM */
    Boolean verifyElement(String xpath) {
        r.logDebug "Verifying element: $xpath"
        if (r.findElements(xpath)) {
            return true
        } else {
            return false
        }
    }


    /** Verify element visible */
    Boolean verifyVisible(String xpath) {
        List elements = r.findElements(xpath)
        WebElement element = null
        if(elements.size()>0){
            element = elements[0]
        }else {
            r.logDebug("Element: $xpath not present on page")
            return false
        }
        if(!element){return false}
        return (!element ? false : r.elementVisible(element))
    }

    /** Verify element visible */
    Boolean verifyVisible(WebElement element) {
        r.logDebug "Verifying element visible.."
        if (element) {
            return element.isDisplayed()
        } else {
          r.logDebug "Element is not visible - element is null"
            return false
        }
    }

    /** Verify element clickable */
    Boolean verifyClickable(String xpath) {
        List elements = r.findElements(xpath)
        WebElement element = null
        if(elements.size()>0){
            element = elements[0]
        }else {
            r.logDebug("Element: $xpath not present on page")
            return false
        }
        if(!element){return false}
        return (!element ? false : r.elementClickable(element))
    }

    /** Verify element clickable */
    Boolean verifyClickable(WebElement element) {
        r.logDebug "Verifying element clickable.."
        try {
            return r.waitForElementClickable(element, 0)
        } catch (Exception e) {
            r.addIssueTrackerEvent("Element Not Clickable - Exception", e)
            return false
        }
    }


    /** Verify element present inside other element
     * @param
     * elementOrXpath - we are searching in
     * String xpath - element we are looking for
     */
    Boolean verifyElementInElement( elementOrXpath, String xpath) {
        r.logDebug "Verifying element: $xpath, present inside element"
        try {
            return r.findElementsInElement(elementOrXpath, xpath).size()
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not verify element in element - Exception", e)
            return null
        }
    }

}

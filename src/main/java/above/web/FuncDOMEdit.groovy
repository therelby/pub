package above.web

import above.RunWeb
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement
import wss.pages.element.MetaElement

/**
 * Editing Current Page DOM
 * Add node(webelement), Add attribute to Web element
 * @vdiacuk
 */
class FuncDOMEdit {
    RunWeb r = run()

    // Attribute name and value used to mark park page, check is page marked
    static String markAttributeName = "automationAttr"
    static String markAttributeValue = "marked"


    /**
     * Add node/WebElement to current page DOM
     * @param tagName - desired html tag name - ex. p, div, span
     * @param elementText - Element Text
     * @param parentElement - Parent Element, if missing add to the body element
     */
    Boolean addElementToCurrentPage(String tagName, String elementText, WebElement parentElement = null) {
        r.logDebug "Adding <$tagName> with text: $elementText, to current page DOM"
        if (!parentElement) {
            r.logDebug("Parent element is null or does not exists - adding attribute to body element instead")
            parentElement = r.find(MetaElement.bodyElementXpath)
            if (!parentElement) {
                r.addIssueTrackerEvent("Can not find Body element with xpath: $MetaElement.bodyElementXpath")
                return false
            }
        }
        try {
            String jsCode =
                    "element = document.createElement('$tagName');" +
                            "text = document.createTextNode('$elementText');" +
                            "element.appendChild(text);" +
                            "arguments[0].append(element);"
            JavascriptExecutor js = (JavascriptExecutor) r.getWebDriver()
            js.executeScript(jsCode, parentElement)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not add element to the current page DOM", e)
            return false
        }
    }


    /**
     * Add attribute to WebElement on current page
     * @param element - WebElement attribute add to
     * @param attributeName - attribute Name
     * @param attributeValue - attribute Value
     */
    Boolean addAttributeToElement(WebElement element, String attributeName, String attributeValue) {
        r.logDebug "Adding attribute: $attributeName, with value: $attributeValue"
        try {
            if (!element) {
                r.addIssueTrackerEvent("Webelement not exist, Can not add attribute")
                return false
            }
            JavascriptExecutor js = (JavascriptExecutor) r.getWebDriver()
            js.executeScript("arguments[0].setAttribute('$attributeName', '$attributeValue')", element)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not add attribute to the element - Exception", e)
            return false
        }
    }

    /**
     * Delete WebElement on current page
     */
    Boolean deleteElementFromCurrentPage(WebElement element) {
        r.logDebug "Deleting WebElement from current Page"
        try {
            if (!element) {
                r.addIssueTrackerEvent("WebElement not exist, Can not delete element")
                return false
            }
            JavascriptExecutor js = (JavascriptExecutor) r.getWebDriver()
            js.executeScript(
                    "arguments[0].parentNode.removeChild(arguments[0])", element)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not delete WebElement from the DOM - Exception", e)
            return false
        }
    }
    /**
     * Delete WebElement on current page by xpath
     */
    Boolean deleteElementFromCurrentPage(String xpath) {
        r.logDebug "Deleting WebElement from current Page by xpath: $xpath"
        WebElement element = r.find(xpath)
        return deleteElementFromCurrentPage(element)
    }

    /**
     * Delete Attribute from WebElement on current page by xpath
     */
    Boolean deleteAttribute(String xpath, String attributeName) {
        r.logDebug "Deleting Attribute on current Page by xpath: $xpath"
        WebElement element = r.find(xpath)
        return deleteAttribute(element, attributeName)
    }

    /**
     * Delete Attribute from WebElement on current page by WebElement
     */
    Boolean deleteAttribute(WebElement element, String attributeName) {
        r.logDebug "Deleting Attribute on current Page by WebElement"
        try {
            JavascriptExecutor js = (JavascriptExecutor) r.getWebDriver()
            js.executeScript(
                    "arguments[0].removeAttribute('$attributeName')", element)
            return true
        }catch(Exception e){
           // r.addIssueTrackerEvent("Can not delete Attribute in WebElement - Exception", e)
            return false
        }
    }

    /**
     *  Mark Current Page by adding unique attribute and it's value to the body element
     */
    Boolean markPage() {
        addAttributeToElement(r.find(MetaElement.bodyElementXpath), markAttributeName, markAttributeValue)
        return isPageMarked()
    }

    /**
     *  Check is page was marked before - by checking unique attribute in body element
     */
    Boolean isPageMarked() {
        return r.getAttribute(r.find(MetaElement.bodyElementXpath), markAttributeName) == markAttributeValue
    }
}

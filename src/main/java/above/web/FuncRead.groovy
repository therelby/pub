package above.web

import above.RunWeb
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

/**
 *     Read text,Set Text, read attribute, read All attributes, JS read text
 *     vdiachuk kyilmaz
 */

class FuncRead {

    RunWeb r = run()
    // WebDriver driver = r.getWebDriver() (!) getting driver inside method if needed - to avoid null if driver reinstating


    /** get text by xpath */
    String getText(String xpath) {
        r.logDebug "Getting text from element: $xpath"
        try {
            def element = r.find(xpath)
            if (!element) {
                r.addIssueTrackerEvent("Can not get text from element: $xpath - NO element found")
                return null
            }
            return r.getText(element)
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not get text from element: $xpath", e)
            return null
        }
    }
    /** get text by WebElement */
    String getText(WebElement element) {
        r.logDebug "Getting text from element.."
        try {
            return element.getText()
        } catch (e) {
            r.addIssueTrackerEvent("Can not get text from element", e)
            return null
        }
    }

    /** get text safe by xpath */
    String getTextSafe(String xpath) {
        return r.getText(xpath) ?: ""
    }

    /** get text safe by WebElement */
    String getTextSafe(WebElement element) {
        return r.getText(element) ?: ""
    }

    /** get text of node without child nodes by xpath */
    String getNodeText(String xpath) {
        WebElement element = r.find(xpath)
        if (!element) {
            return ''
        }
        return r.getNodeText(element)
    }

    /** get text of node without child nodes by WebElement */
    String getNodeText(WebElement element) {
        if (!element) {
            return ''
        }
        String result = r.getAttributeSafe(element, 'textContent')
        List<WebElement> children = r.findElementsInElement(element, '/*')
        for (WebElement child : children) {
            String childText = r.getAttributeSafe(child, 'textContent')
            result = result - childText
        }
        return result
    }

    /** set text by xpath */
    Boolean setText(String xpath, String text) {
        r.logDebug "Setting text: $text to element: $xpath"
        try {
            def element = r.find(xpath)
            if (!element) {
                return false
            }
            return setText(element, text)
        } catch (e) {
            r.addIssueTrackerEvent("Can not set text: $text to element: $xpath", e)
            return false
        }
    }

    /** set text by WebElement */
    Boolean setText(WebElement element, String text) {
        r.logDebug "Setting text: $text to element.."
        try {
            element.clear()
            element.sendKeys(text)
        } catch (e) {
            r.addIssueTrackerEvent("Can not set text: [$text] to element", e)
            return false
        }
        return true
    }
    /** set text by Xpath using JavaScript*/
    boolean jsSetText(String xpath, String text) {
        def element = r.find(xpath)
        if (!element) {
            return false
        }
        return jsSetText(element, text)
    }
    /** set text by WebElement using JavaScript*/
    boolean jsSetText(WebElement element, String text) {
        r.logDebug "Setting text: $text to element using JavaScript.."
        try {
            def driver = r.getWebDriver()
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver
            jsExecutor.executeScript("arguments[0].value='$text'", element)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not set text: $text to element using JavaScript", e)
            return false
        }
    }
/** get attribute from xpath by attribute name */
    String getAttribute(String xpath, String attribute) {
        r.logDebug "Getting attribute: $attribute from: $xpath"
        def element = r.find(xpath)
        if (!element) {
            return null
        }
        return getAttribute(element, attribute)
    }
/** get attribute from WebElement by attribute name */
    String getAttribute(WebElement element, String attribute) {
        r.logDebug "Getting attribute: $attribute from element.."
        try {
            return element.getAttribute(attribute)
        } catch (e) {
            r.addIssueTrackerEvent("Can not get attribute: $attribute", e)
            return null
        }

    }
    /** get attribute Safe from xpath by attribute name */
    String getAttributeSafe(String xpath, String attribute) {
        return getAttribute(xpath, attribute) ?: ''
    }
/** get attribute Save from WebElement by attribute name */
    String getAttributeSafe(WebElement element, String attribute) {
        return getAttribute(element, attribute) ?: ""
    }
/** get ALL attribute from xpath - to get right "value" from input element use getAttribute  */
    Map getAllAttributes(String xpath) {
        r.logDebug "Getting All attributes xpath: $xpath"
        def element = r.find(xpath)
        if (!element) {
            return null
        }
        return getAllAttributes(element)
    }
/** get ALL attribute from WebElement - to get right "value" from input element use getAttribute  */
    Map getAllAttributes(WebElement element) {
        r.logDebug "Getting All attributes from element.."
        try {
            WebDriver driver = r.getWebDriver()
            return ((JavascriptExecutor) driver).executeScript(
                    """
                    var items = {}; 
                    for (index = 0; index < arguments[0].attributes.length; ++index) { 
                        items[arguments[0].attributes[index].name] = arguments[0].attributes[index].value 
                    }; 
                    return items;""",
                    element)
        } catch (e) {
            r.addIssueTrackerEvent("Can not get ALL attributes from element", e)
            return null
        }
    }

    /** get ALL attribute from xpath - no Element - [:]  */
    Map getAllAttributesSafe(String xpath) {
        return r.getAllAttributes(xpath) ?: [:]
    }
/** get ALL attribute from WebElement - - no Element - [:]  */
    Map getAllAttributesSafe(WebElement element) {
        return r.getAllAttributes(element) ?: [:]
    }

/** get title from the current page  */
    String getTitle() {
        r.logDebug "Getting title from current page.."
        try {
            WebDriver driver = r.getWebDriver()
            return driver.getTitle()
        } catch (e) {
            r.addIssueTrackerEvent("Can not get title from the page", e)
            return null
        }
    }


    /** get text with JS JavaScript by xpath */
    String jsGetText(String xpath) {
        r.logDebug "JS Getting text from element: $xpath"
        try {
            def element = r.find(xpath)
            if (!element) {
                return null
            }
            return r.jsGetText(element)
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not get text from element with JS: $xpath", e)
            return null
        }
    }
    /** get text  with JS JavaScript by WebElement */
    String jsGetText(WebElement element) {
        r.logDebug "JS Getting text from element.."
        try {
            WebDriver driver = r.getWebDriver()
            JavascriptExecutor js = (JavascriptExecutor) driver
            return (String) js.executeScript("return arguments[0].value;", element)
        } catch (e) {
            r.addIssueTrackerEvent("Can not get text from element with JS", e)
            return null
        }
    }

    /** clear text by xpath */
    Boolean clearText(String xpath) {
        r.logDebug "Clear text of element: $xpath"
        try {
            def element = r.find(xpath)
            if (!element) {
                return false
            }
            return r.clearText(element)
        } catch (e) {
            r.addIssueTrackerEvent("Can not clear text to element: $xpath", e)
            return false
        }
    }

    /** clear text by WebElement */
    Boolean clearText(WebElement element) {
        r.logDebug "Clearing text of element.."
        try {
            element.clear()
        } catch (e) {
            r.addIssueTrackerEvent("Can not clear text to element", e)
            return false
        }
        return true
    }

    /** get Css value for  xpath */
    String getCssValue(String xpath, String cssProperty) {
        if (!xpath) {
            r.addIssueTrackerEvent("Can not read Css Value for Xpath - xpath is not present")
            return null
        }
        return r.getCssValue(r.find(xpath), cssProperty)
    }

    /** get Css value for webelement  */
    String getCssValue(WebElement element, String cssProperty) {
        r.logDebug "Getting Css value: [$cssProperty]"
        try {
            if (!element) {
                r.addIssueTrackerEvent("Can not read Css Value: [$cssProperty] - Webelement is null")
                return null
            }
            if (!cssProperty) {
                r.addIssueTrackerEvent("Can not read Css Value from Webelement - css property name is empty")
                return null
            }
            return element.getCssValue(cssProperty)
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not read Css value: [$cssProperty] from Webelement - Exception", e)
            return null
        }
    }
    /** get Tag Name for xpath */
    String getTagName(String xpath) {
        if (!xpath) {
            r.addIssueTrackerEvent("Can not Tag Name for Xpath - xpath is not present")
            return null
        }
        return r.getTagName(r.find(xpath))
    }
    /** get Tag Name for webelement */
    String getTagName(WebElement element) {
        r.logDebug "Getting Tag Name"
        try {
            if (!element) {
                r.addIssueTrackerEvent("Can not get Tag Name - Webelement is not present")
                return null
            }
            return element.getTagName()
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not get Tag Name - Exception", e)
            return null
        }
    }
}

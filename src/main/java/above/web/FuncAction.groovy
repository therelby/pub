package above.web

import above.RunWeb
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import wss.pages.element.MetaElement

/**
 *     Action Handling
 *       vdiachuk
 *
 */
class FuncAction {
    RunWeb r = run()
    // WebDriver driver = r.getWebDriver() (!) getting driver inside every method - to avoid null if driver reloaded

    /** Get Actions */
    Actions getAction() {
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return null
        }
        try {
            return new Actions(driver)
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not get Action", e)
            return null
        }
    }

    /**
     * Trying to Click using Actions by Xpath
     */
    boolean actionClick(String xpath) {
        WebElement element = r.find(xpath)
        if (!element) {
            return false
        }
        return r.actionClick(element)
    }

    /**
     * Trying to Click using Actions by WebElement
     */
    boolean actionClick(WebElement element) {
        Actions action = r.getAction()
        if (!action) {
            return false
        }
        try {
            action.moveToElement(element).click().build().perform()
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not Action Click on element - Exception", e)
            return false
        }
    }


    /** Put mouse cursor over an element */
    Boolean mouseOver(String xpath) {
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return false
        }
        try {
            WebElement element = driver.findElement(By.xpath(xpath))
            mouseOver(element)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can't put mouse cursor over $xpath because can't find the xpath", e)
            return false
        }
    }

    /** Put mouse cursor over an element */
    Boolean mouseOver(WebElement element) {
        Actions action = r.getAction()
        if (!action) {
            return false
        }
        try {
            action.moveToElement(element).build().perform()
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can't put mouse cursor over the element", e)
            return false
        }
    }

    /** Put mouse cursor over an element using JavaScript
     * Keep in mind that hover action takes time - add appropriate waitForElement or sleep after jsMouseOver
     * */
    boolean jsMouseOver(WebElement webElement) {
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return false
        }
        if (!webElement) {
            r.addIssueTrackerEvent("Can not jsMouseOver webelement - WebElement is null")
            return false
        }
        String javaScript = "var obj = document.createEvent('MouseEvents');" +
                "obj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
                "arguments[0].dispatchEvent(obj);"
        try {
            r.mouseOver(webElement)
            ((JavascriptExecutor) driver).executeScript(javaScript, webElement)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can't put mouse cursor over the element using JavaScript", e)
            return false
        }
    }

    /** Put mouse cursor over an element using JavaScript */
    boolean jsMouseOver(String xpath) {
        WebElement webElement = r.find(xpath)
        if (!webElement) {
            r.addIssueTrackerEvent("Can not js mouse over elemennt: $xpath - Element Missing")
            return false
        }
        return r.jsMouseOver(webElement)
    }

    /**
     *     Scroll by Pixels x, y
     *     Positive value of y - Down
     *     Negative value of y - UP
     */
    boolean scrollByPixels(int xPixels, int yPixels) {
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return false
        }
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver
            js.executeScript("window.scrollBy($xPixels,$yPixels)")
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not scroll by Pixels x: [$xPixels], y: [$yPixels]", e)
            return false
        }
    }

    /**
     *     Scroll to Element  By Xpath
     *
     */
    Boolean scrollTo(String xpath) {
        WebElement element = r.find(xpath)
        if (!element) {
            return false
        }
        return r.scrollTo(element)
    }
    /**
     *     Scroll to Element  By WebElement
     */
    Boolean scrollTo(WebElement element) {
        Actions actions = r.getAction()
        if (!actions) {
            return false
        }
        try {
            actions.moveToElement(element).build().perform()
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not scroll to element", e)
            return false
        }
    }

    /**
     *     Scroll to Element  By Xpath using JavaScript
     *
     */
    Boolean jsScrollTo(String xpath) {
        WebElement element = r.find(xpath)
        if (!element) {
            return false
        }
        return r.jsScrollTo(element)
    }
    /**
     *     Scroll to Element  By WebElement using JavaScript
     */
    Boolean jsScrollTo(WebElement element) {
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return false
        }
        try {
            JavascriptExecutor jse = (JavascriptExecutor) driver
            String script = "arguments[0].scrollIntoView(true);"
            jse.executeScript(script, element)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not scroll to element using JavaScript", e)
            return false
        }
    }
    /**
     *     Scroll to Bottom of the page
     *     This method allow to avoid "lazy load" problem, after scrolling to the bottom all Items should be visible
     */
    Boolean scrollToBottom() {
        r.logDebug "Scrolling to the Bottom of the page"
        WebDriver driver = r.driverStorage.get()
        WebElement bodyElement = r.find("//body")
        if (!driver) {
            return false
        }
        try {
            JavascriptExecutor jse = (JavascriptExecutor) driver
            Integer bodyHeight = bodyElement.getSize().getHeight()
            Integer scrolltimes = bodyHeight / 500 + 1
            for (def i = 0; i < scrolltimes; i++) {
                jse.executeScript("window.scrollBy(0,500)")
                bodyHeight = bodyElement.getSize().getHeight()
                scrolltimes = bodyHeight / 500 + 1
                sleep(100)
            }
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not scroll to bottom of the page", e)
            return false
        }
    }
    /**
     *     Scroll to Top of the page
     *     This method allow to hide Sticky Header
     */
    Boolean scrollToTop() {
        r.logDebug "Scrolling to the Top of the page"
        WebDriver driver = r.driverStorage.get()
        WebElement bodyElement = r.find(MetaElement.bodyElementXpath)
        if (!driver) {
            return false
        }
        try {
            JavascriptExecutor jse = (JavascriptExecutor) driver
            Integer bodyHeight = bodyElement.getSize().getHeight()
            Integer scrolltimes = bodyHeight / 500 + 1
            for (def i = 0; i < scrolltimes; i++) {
                jse.executeScript("window.scrollBy(0,-500)")
                bodyHeight = bodyElement.getSize().getHeight()
                scrolltimes = bodyHeight / 500 + 1
            }
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not scroll to the Top of the page", e)
            return false
        }
    }

    /**
     * Get vertical scroll bar Position*
     */
    Long pageOffsetVertical() {
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return null
        }
        try {
            JavascriptExecutor j = (JavascriptExecutor) driver
            Long position = (Long) j.executeScript("return window.pageYOffset;")
            return position
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not get page vertical offset position - Exception", e)
            return null
        }
    }

    /**
     * Copied from Katalon forums: https://ynot408.wordpress.com/2011/09/22/drag-and-drop-using-selenium-webdriver/
     * Made adjustment to allow for x and y offsets.
     */
    Boolean jsDragAndDrop(String xpathObj, String xpathTo, xOffset = 0, yOffset = 0) {

        String xto
        String yto
        String xFrom
        String yFrom
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return false
        }
        WebElement locatorFrom = r.find(xpathObj)
        if (!locatorFrom) {
            return false
        }
        WebElement locatorTo = r.find(xpathTo)
        if (!locatorTo) {
            return false
        }
        try {
            xto = Integer.toString(locatorTo.getLocation().x + xOffset)
            yto = Integer.toString(locatorTo.getLocation().y + yOffset)
            xFrom = Integer.toString(locatorFrom.getLocation().x)
            yFrom = Integer.toString(locatorFrom.getLocation().y)
            ((JavascriptExecutor) driver).executeScript(
                    'function simulate(f,c,d,e){var b,a=null;for(b in eventMatchers)if(eventMatchers[b].test(c)){a=b;break}if(!a)return!1;document.createEvent?(b=document.createEvent(a),a==\"HTMLEvents\"?b.initEvent(c,!0,!0):b.initMouseEvent(c,!0,!0,document.defaultView,0,d,e,d,e,!1,!1,!1,!1,0,null),f.dispatchEvent(b)):(a=document.createEventObject(),a.detail=0,a.screenX=d,a.screenY=e,a.clientX=d,a.clientY=e,a.ctrlKey=!1,a.altKey=!1,a.shiftKey=!1,a.metaKey=!1,a.button=1,f.fireEvent(\"on\"+c,a));return!0} var eventMatchers={HTMLEvents:/^(?:load|unload|abort|error|select|change|submit|reset|focus|blur|resize|scroll)$/,MouseEvents:/^(?:click|dblclick|mouse(?:down|up|over|move|out))$/}; ' +
                            'simulate(arguments[0],\"mousedown\",0,0); simulate(arguments[0],\"mousemove\",arguments[1],' +
                            'arguments[2]); simulate(arguments[0],\"mouseup\",arguments[1],arguments[2]);',
                    locatorFrom, xto, yto)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not JS Drag and Drop,xpath1: $xpathObj, xpathTo: $xpathTo, Xoffset: $xOffset, " +
                    "Yoffset: $yOffset (Dragging from ($xFrom, $yFrom) to ($xto, $yto))", e)
            return false
        }
    }

    /**
     * !! THIS functionality not working in all browsers
     * Getting Shadow element from Parent WebElement
     */
    SearchContext getShadowElement(WebElement parentElement) {
        WebDriver driver = r.driverStorage.get()
        if (!driver) {
            return null
        }
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver
            SearchContext element = js.executeScript("return arguments[0].shadowRoot", parentElement)
            return element
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not get Shadow element from Parent WebElement - Exception", e)
            return null
        }
    }
}

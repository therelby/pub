package above.web

import above.RunWeb
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

/**
 * Handling Clear Text using Actions class
 * @author vdiachuk
 */
class FuncClearTextAction {

    RunWeb r = run()

    boolean clearTextAction(String xpath){
        if(!xpath){
            r.addIssueTrackerEvent('Can not clear text using Action - xpath not present')
            return false
        }
        WebElement element = r.find(xpath)
        if(!element){
            return false
        }
        return r.clearTextAction(element)
    }

    /**
     * Clearing text from input element
     * @param element
     * @return success value is ''
     */
    boolean clearTextAction(WebElement element){
        if (!r.click(element)) {
            r.addIssueTrackerEvent('Can not clear text using Action - element not clickable')
            return false
        }
        Actions action = r.getAction()
        try {
            action.keyDown(Keys.CONTROL).sendKeys(Keys.chord("A")).keyUp(Keys.CONTROL).sendKeys(Keys.BACK_SPACE).build().perform()
        } catch (Exception e) {
            r.addIssueTrackerEvent('Can not clear text using Action - Exception', e)
            return false
        }
        return r.getAttribute(element,'value') == ''
    }
}

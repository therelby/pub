package above.web

import above.RunWeb
import org.openqa.selenium.WebElement
/**
 *      Radio button and check box functionality
 */

class FuncCheckElements {

    RunWeb r = run()
    // WebDriver driver = r.getWebDriver() (!) getting driver inside method if needed - to avoid null if driver reinstating

    /**
     *      Verifies radio button or checkbox is enabled on page by Xpath
     */
    Boolean isEnabled(String xpath) {
        r.logDebug "Checking is Enabled: $xpath"
        WebElement element = r.find(xpath)
        if (!element) {
            return false
        }
        return isEnabled(element)
    }

    /**
     *      Verifies radio button or checkbox is enabled by element
     */
    Boolean isEnabled(WebElement element) {
        r.logDebug "Checking is Enabled element.."
        try {
            if (element.isEnabled()) {
                r.logDebug 'Check element is enabled'
                return true
            } else {
                r.logDebug 'Check element is NOT enabled'
                return false
            }
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not check is element enabled", e)
            return null
        }
    }

    /**
     *      Verifies radio button or checkbox is checked by Xpath
     */
    Boolean isChecked(String xpath) {
        r.logDebug "Checking: $xpath is checked.."
        WebElement element = r.find(xpath)
        if (!element) {
            return false
        }
        return isChecked(element)
    }

    /**
     *      Verifies radio button or checkbox is checked by element
     */
    boolean isChecked(WebElement element) {
        r.logDebug "Checking is element checked.."
        try {
            if (element.isSelected()) {
                r.logDebug 'Check element is checked'
                return true
            } else {
                r.logDebug'Check element is unchecked'
                return false
            }
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not check is element(Radio or Checkbox) selected(checked)", e)
            return null
        }
    }

    /**
     *      Unchecks a checkbox by Xpath
     */
    boolean uncheckCheckbox(String xpath) {
        r.logDebug "Unchecking element: $xpath"
        WebElement element = r.find(xpath)
        if (!element) {
            return false
        }
        return uncheckCheckbox(element)
    }

    /**
     *      Unchecks a checkbox by WebElement
     */
    boolean uncheckCheckbox(WebElement element) {
        r.logDebug "Unchecking element.."
        try {
            if (element.isSelected()) {
                r.click(element)
                return !isChecked(element)
            } else {
                return true
            }
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not Uncheck element(Radio or Checkbox)", e)
            return null
        }
    }

    /**
     *      Selects the check box passed to method by xpath
     */
    boolean checkCheckbox(String xpath) {
        r.logDebug "Trying to check element: $xpath"
        WebElement element = r.find(xpath)
        if (!element) {
            return false
        }
        return checkCheckbox(element)
    }

    /**
     *      Selects the check box passed to method by element
     */
    boolean checkCheckbox(WebElement element) {
        r.logDebug "Trying to check element.."
        try {
            if (element.isSelected()) {
                return true
            } else {
                r.click(element)
                return isChecked(element)
            }
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not check element(Radio or Checkbox)", e)
            return null
        }
    }
}

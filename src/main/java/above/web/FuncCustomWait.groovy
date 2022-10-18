package above.web

import above.RunWeb
import org.openqa.selenium.Alert
import org.openqa.selenium.By
import org.openqa.selenium.NoAlertPresentException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import java.time.Duration

/**
 * Custom Wait functionality
 * CommonWaits can be found in FuncWait
 * @author vdiachuk
 */
class FuncCustomWait {
    RunWeb r = run()


// Generic wait. Only usable by methods in this class
    private boolean waitForExpectedCondition(ExpectedCondition condition, timeOutInSeconds, logBefore,
                                             logSuccess, logFailure) {
        WebDriver driver = r.getWebDriver()
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds))
        r.logDebug logBefore
        try {
            wait.until(condition)
            r.logDebug logSuccess
            return true
        } catch (e) {
            r.addIssueTrackerEvent(logFailure, e)
            return false
        }
    }

// There is no element version because the WebElement can't be captured before it exists in the DOM
    boolean waitForElement(String xpath, Integer timeOutInSeconds) {
        return waitForExpectedCondition(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)),
                timeOutInSeconds,
                "Waiting for presence of element at xpath: ${xpath}",
                "Found element at xpath: ${xpath}",
                "After waiting for $timeOutInSeconds, could not find element at xpath: $xpath")
    }

    boolean waitForNoElement(String xpath, Integer timeOutInSeconds) {
        return waitForExpectedCondition(ExpectedConditions.not(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(xpath))),
                timeOutInSeconds,
                "Waiting for absence of element at xpath: ${xpath}",
                "Found element at xpath: ${xpath}",
                "After waiting for $timeOutInSeconds, still Can find element at xpath: $xpath")
    }

/** Wait For Element Clickable by xpath*/
    boolean waitForElementClickable(String xpath, Integer timeOutInSeconds) {
        return waitForExpectedCondition(ExpectedConditions.elementToBeClickable(By.xpath(xpath)), timeOutInSeconds,
                "Waiting for element at xpath: ${xpath} to be clickable",
                "Element at xpath: ${xpath} is clickable",
                "After waiting for $timeOutInSeconds, element at xpath: $xpath was not clickable.")
    }

/** Wait For Element Clickable by webelement*/
    boolean waitForElementClickable(WebElement element, Integer timeOutInSeconds) {
        if (!element) {
            return false
        }
        return waitForExpectedCondition(ExpectedConditions.elementToBeClickable(element), timeOutInSeconds,
                "Waiting for element: ${element.toString()} to be clickable",
                "Element: ${element.toString()} is clickable",
                "After waiting for $timeOutInSeconds, element ${element.toString()} was not clickable.")
    }
/** Wait For Element Unclickable by xpath*/
    boolean waitForElementUnclickable(String xpath, Integer timeOutInSeconds) {
        return waitForExpectedCondition(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(By.xpath(xpath))), timeOutInSeconds,
                "Waiting for element at xpath: ${xpath} to be unclickable",
                "Element at xpath: ${xpath} is unclickable",
                "After waiting for $timeOutInSeconds, element at xpath: $xpath was still clickable.")
    }


/** Wait For Element Unclickable by webelement*/
    boolean waitForElementUnclickable(WebElement element, Integer timeOutInSeconds) {
        if (element) {
            return false
        }
        return waitForExpectedCondition(ExpectedConditions.not(ExpectedConditions.elementToBeClickable(element)), timeOutInSeconds,
                "Waiting for element: ${element.toString()} to be unclickable",
                "Element: ${element.toString()} is unclickable",
                "After waiting for $timeOutInSeconds, element ${element.toString()} was still clickable.")
    }

/** Wait For Element Visible by xpath*/
    boolean waitForElementVisible(String xpath, Integer timeOutInSeconds) {
        return waitForExpectedCondition(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)),
                timeOutInSeconds,
                "Waiting for element at xpath: ${xpath} to be visible",
                "Element at xpath: ${xpath} is visible",
                "After waiting for $timeOutInSeconds, element at xpath: $xpath was not visible.")
    }

/** Wait For Element Visible by webelement*/
    boolean waitForElementVisible(WebElement element, Integer timeOutInSeconds) {
        if (!element) {
            return false
        }
        return waitForExpectedCondition(ExpectedConditions.visibilityOfElementLocated(element),
                timeOutInSeconds,
                "Waiting for element: ${element.toString()} to be visible",
                "Element: ${element.toString()} is visible",
                "After waiting for $timeOutInSeconds, element ${element.toString()} was not visible.")
    }

/** Wait For Element Invisible by xpath*/
    boolean waitForElementInvisible(String xpath, Integer timeOutInSeconds) {
        return waitForExpectedCondition(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)),
                timeOutInSeconds,
                "Waiting for element at xpath: ${xpath} to be invisible",
                "Element at xpath: ${xpath} is invisible",
                "After waiting for $timeOutInSeconds, element at xpath: $xpath was visible.")
    }

/** Wait For Element Invisible by webelement*/
    boolean waitForElementInvisible(WebElement element, Integer timeOutInSeconds) {
        if (!element) {
            return false
        }
        return waitForExpectedCondition(ExpectedConditions.invisibilityOfElementLocated(element),
                timeOutInSeconds,
                "Waiting for element: ${element.toString()} to be invisible",
                "Element: ${element.toString()} is invisible",
                "After waiting for $timeOutInSeconds, element ${element.toString()} was visible.")
    }

    /** Wait For Element Attribute has value by xpath*/
    boolean waitForAttributeNotEmpty(String xpath, String attribute, Integer timeOutInSeconds) {
        if (!xpath || !attribute) {
            return false
        }
        WebElement element = r.find(xpath)
        if (!element) {
            return false
        }
        return r.waitForAttributeNotEmpty(element, attribute, timeOutInSeconds)
    }
    /** Wait For Element Attribute has value by webelement*/
    boolean waitForAttributeNotEmpty(WebElement element, String attribute, Integer timeOutInSeconds) {
        return waitForExpectedCondition(ExpectedConditions.attributeToBeNotEmpty(element, attribute),
                timeOutInSeconds,
                "Waiting for element attribute: ${attribute} to be not empty",
                "Element: ${attribute} is not empty",
                "After waiting for $timeOutInSeconds, attribute ${attribute} is empty")
    }

    /** Wait For Alert Present */
    boolean waitForAlertPresent( Integer timeOutInSeconds) {
        return waitForExpectedCondition(ExpectedConditions.alertIsPresent(),
                timeOutInSeconds,
                "Waiting for Alert present",
                "Alert is present",
                "After waiting for $timeOutInSeconds, Alert is NOT present")
    }

    /** Wait For Input Element Interactable */
    boolean waitForInputElementInteractable(String xpath, Integer timeOutInSeconds) {
        return waitForExpectedCondition(
                new ExpectedCondition<Boolean>() {
                    Boolean apply(WebDriver driver) {
                        return r.setText(xpath, '')
                    }
                },
                timeOutInSeconds,
                "Waiting for Input to be interactable",
                "Input is interactable",
                "After waiting for $timeOutInSeconds, Input is NOT interactable")
    }
}

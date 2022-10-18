package above.web

import above.RunWeb
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.Select

/**
 *     Select  Functional
 *     vdiachuk kyilmaz
 */
class FuncSelect {

    RunWeb r = run()
    // WebDriver driver = r.getWebDriver() (!) getting driver inside method if needed - to avoid null if driver reinstating


    /**
     *     Select  - checks is Multiple selecting possible by WebElement
     */
    Boolean selectIsMultiple(WebElement element) {
        r.logDebug "Checking is element have multiple selections.."
        try {
            Select select = new Select(element)
            return select.isMultiple()
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not check is element has multiple selection", e)
            return null
        }
    }
    /**
     *     Select  - checks is Multiple selecting possible by xpath
     */
    Boolean selectIsMultiple(String xpath) {
        r.logDebug "Checking is element have multiple selections, xpath: $xpath"
        try {
            WebElement element = r.find(xpath)
            if (!element) {
                return null
            }
            Select select = new Select(element)
            return select.isMultiple()
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not check is element has multiple selection, xpath: $xpath", e)
            return null
        }
    }

    /**
     *     Select  with WebElement by Visible text
     */
    Boolean selectByVisibleText(WebElement element, String text) {
        r.logDebug "Selecting by visible text: $text"
        try {
            Select select = new Select(element)
            select.selectByVisibleText(text)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not Select By Visible text. $text", e)
            return false
        }
    }

    /**
     *     Select  with xpath by Visible text
     */
    Boolean selectByVisibleText(String xpath, String text) {
        r.logDebug "Selecting by visible text: $text, xpath: $xpath"
        try {
            WebElement element = r.find(xpath)
            if (!element) {
                return false
            }
            return r.selectByVisibleText(element, text)
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not Select By Visible text: $text, xpath: $xpath", e)
            return false
        }
    }

    /**
     *     Select  with WebElement by value
     */
    Boolean selectByValue(WebElement element, String value) {
        r.logDebug "Seleting by value: $value"
        try {
            Select select = new Select(element)
            select.selectByValue(value)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not Select By Value: $value", e)
            return false
        }
    }

    /**
     *     Select  with xpath by value
     */
    Boolean selectByValue(String xpath, String value) {
        r.logDebug "Selecting by value: $value, xpath: $xpath"
        try {
            WebElement element = r.find(xpath)
            if (!element) {
                return false
            }
            return r.selectByValue(element, value)
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not Select By Value: $value, xpath: $xpath", e)
            return false
        }
    }

    /**
     *     Select  with WebElement by index
     */
    Boolean selectByIndex(WebElement element, int index) {
        r.logDebug "Selecting by Index:$index"
        try {
            Select select = new Select(element)
            select.selectByIndex(index)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not Select By Index: $index", e)
            return false
        }
    }

    /**
     *     Select  with xpath by index
     */
    Boolean selectByIndex(String xpath, int index) {
        r.logDebug "Selectiong by index: $index, xpath: $xpath"
        try {
            WebElement element = r.find(xpath)
            if (!element) {
                return false
            }
            return r.selectByIndex(element, index)
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not Select By Index: $index, xpath: $xpath", e)
            return false
        }

    }

    /**
     *  Select Random Option by WebElement
     */
    Boolean selectRandomOption(WebElement element) {
        try {
            List options = r.selectGetOptions(element)
            def activeOption
            activeOption = options.findAll() { !r.getAllAttributesSafe(it).containsKey('disabled') }.shuffled().getAt(0)

            if (!activeOption) {
                r.addIssueTrackerEvent("Can not select Random option - no active options found")
                return false
            }
            def activeOptionValue = r.getAttributeSafe(activeOption, "value")
            if (!activeOptionValue) {
                r.addIssueTrackerEvent("Can not select Random option - active option value is missing")
                return false
            }
            if (!r.selectByValue(element, activeOptionValue)) {
                r.addIssueTrackerEvent("Can not select Random option - selecting issue")
                return false
            }
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not Select Random Option - ex", e)
            return false
        }
    }

    /**
     * Select Random Option by xpath
     */
    Boolean selectRandomOption(String xpath) {
        r.logDebug "Selectiong random option, xpath: $xpath"
        WebElement element = r.find(xpath)
        if (!element) {
            return false
        }
        return r.selectRandomOption(element)
    }


    /**
     *  Select Random Option by WebElement
     *  @return value attribute of selected Options Element
     *  @return null in case of issue
     */
    String selectRandomOptionWithValue(WebElement element) {
        try {
            List options = r.selectGetOptions(element)
            def activeOption
            activeOption = options.findAll() { !r.getAllAttributesSafe(it).containsKey('disabled') }.shuffled().getAt(0)
            if (!activeOption) {
                r.addIssueTrackerEvent("Can not select Random option with value - no active options found")
                return null
            }
            def activeOptionValue = r.getAttributeSafe(activeOption, "value")
            if (!activeOptionValue) {
                r.addIssueTrackerEvent("Can not select Random option with value - active option value is missing")
                return null
            }
            if (!r.selectByValue(element, activeOptionValue)) {
                r.addIssueTrackerEvent("Can not select Random option with value - selecting issue")
                return null
            }
            return activeOptionValue
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not select Random option with value - exception", e)
            return false
        }
    }

    /**
     * Select Random Option by xpath
     *  @return value attribute of selected Options Element
     *  @return null in case of issue
     */
    String selectRandomOptionWithValue(String xpath) {
        r.logDebug "Selectiong random option with Value, xpath: $xpath"
        WebElement element = r.find(xpath)
        if (!element) {
            return null
        }
        return r.selectRandomOptionWithValue(element)
    }

    /**
     *     Deselect  with WebElement by Visible text
     */
    Boolean deselectByVisibleText(WebElement element, String text) {
        r.logDebug "Deselecting by visible text: $text"
        try {
            Select select = new Select(element)
            select.deselectByVisibleText(text)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not Deselect By Visible text: $text", e)
            return false
        }
    }

    /**
     *     Deselect  with xpath by Visible text
     */
    Boolean deselectByVisibleText(String xpath, String text) {
        r.logDebug "Deselecting by visible text: $text, xpath: $xpath"
        try {
            WebElement element = r.find(xpath)
            if (!element) {
                return false
            }
            return r.deselectByVisibleText(element, text)
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not deselect By Visible text: $text, xpath: $xpath", e)
            return false
        }
    }

    /**
     *     Deselect  with WebElement by value
     */
    Boolean deselectByValue(WebElement element, String value) {
        r.logDebug "Deselecting by value: $value"
        try {
            Select select = new Select(element)
            select.deselectByValue(value)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not deselect By Value: $value", e)
            return false
        }
    }

    /**
     *     Deselect  with xpath by value
     */
    Boolean deselectByValue(String xpath, String value) {
        r.logDebug "Deselecting by value: $value, xpath: $xpath"
        try {
            WebElement element = r.find(xpath)
            if (!element) {
                return false
            }
            return element ? r.deselectByValue(element, value) : false
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not deselect By Value: $value, xpath: $xpath", e)
            return false
        }
    }

    /**
     *     Deselect  with WebElement by index
     */
    Boolean deselectByIndex(WebElement element, int index) {
        r.logDebug "Deselecting By index: $index"
        try {
            Select select = new Select(element)
            select.deselectByIndex(index)
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not deselect By Index: $index", e)
            return false
        }
    }

    /**
     *     Deselect  with xpath by index
     */
    Boolean deselectByIndex(String xpath, int index) {
        r.logDebug "Deselecting by index: $index, xpath: $xpath"
        try {
            WebElement element = r.find(xpath)
            if (!element) {
                return false
            }
            return element ? r.deselectByIndex(element, index) : false
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not deselect By Index: $index, xpath: $xpath", e)
            return false
        }

    }

    /**
     *     Select  get All options by WebElement
     */
    List selectGetOptions(WebElement element) {
        r.logDebug "Getting all options from select element.."
        try {
            Select select = new Select(element)
            return select.getOptions()
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not get Options from Select", e)
            return []//null
        }
    }

    /**
     *     Select  get All options by xpath
     */
    List selectGetOptions(String xpath) {
        r.logDebug "Getting All options from select: $xpath"
        try {
            WebElement element = r.find(xpath)
            if (!element) {
                return []//null
            }
            Select select = new Select(element)
            return select.getOptions()
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not get Options from Select xpath: $xpath", e)
            return []//null
        }
    }

    /**
     *     Select - get All Selected options by WebElement
     */
    List selectAllSelectedOptions(WebElement element) {
        r.logDebug "Getting all Selected options from select.."
        try {
            Select select = new Select(element)
            return select.getAllSelectedOptions()
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not get All selected Options", e)
            return []//null
        }
    }

    /**
     *     Select  get All Selected options by xpath
     */
    List selectAllSelectedOptions(String xpath) {
        r.logDebug "Getting all Selected options from select, xpath: $xpath"
        try {
            WebElement element = r.find(xpath)
            if (!element) {
                return []//null
            }
            Select select = new Select(element)
            return select.getAllSelectedOptions()
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not get All selected Options xpath: $xpath", e)
            return []//null
        }
    }

    /**
     *     Select - get First Selected option by WebElement
     */
    WebElement selectFirstSelectedOption(WebElement element) {
        r.logDebug "Selecting first option from select element.."
        try {
            Select select = new Select(element)
            return select.getFirstSelectedOption()
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not get First selected Option", e)
            return null
        }
    }

    /**
     *     Select  get First Selected option by xpath
     */
    WebElement selectFirstSelectedOption(String xpath) {
        r.logDebug "Selecting first selecting option from select: $xpath"
        try {
            WebElement element = r.find(xpath)
            if (!element) {
                return null
            }
            Select select = new Select(element)
            return select.getFirstSelectedOption()
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not get First selected Option, xpath: $xpath", e)
            return null
        }
    }


    /**
     *     Select  Deselect All options by WebElement
     */
    Boolean selectDeselectAll(WebElement element) {
        r.logDebug "Deselecting All.."
        try {
            Select select = new Select(element)
            select.deselectAll()
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not Deselect All options", e)
            return false
        }
    }

    /**
     *     Select  Deselect All options by xpath
     */
    Boolean selectDeselectAll(String xpath) {
        r.logDebug "Deselecting All xpath: $xpath"
        try {
            WebElement element = r.find(xpath)
            if (!element) {
                return false
            }
            Select select = new Select(element)
            select.deselectAll()
            return true
        } catch (Exception e) {
            r.addIssueTrackerEvent("Can not Deselect All options, xpath: $xpath", e)
            return false
        }
    }


}

package above.web

import above.RunWeb
import org.openqa.selenium.WebDriver

/**
 *      Web Page Long Text Finding
 *      @autor akudin
 */
class FuncPageText {

    RunWeb r = run()
    // WebDriver driver = r.getWebDriver() (!) getting driver inside method if needed - to avoid null if driver reinstating

    /**
     * Check if current page contains text
     * -- tries to deal with the chars need to be replaced for HTML formatting
     * @param pageText - if not provided it uses current page text
     */
    boolean isPageContainsText(String text, String pageText = null) {
        if (!pageText) {
            pageText = r.getPageText()
        }
        return pageText.contains(prepareForHtmlComparing(text))
    }


    /**
     * Check if an element contains text
     */
    boolean isElementContainsText(String text, String xpathOrElement) {
        String elementText = r.getTextSafe(xpathOrElement)
        return elementText.contains(prepareForHtmlComparing(text))
    }


    // Text preparation
    private String prepareForHtmlComparing(String text) {
        return text.trim().replace(' ', ' ').replace('&nbsp;', ' ').replace('  ', ' ')
    }

}

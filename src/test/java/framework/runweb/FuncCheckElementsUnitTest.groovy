package framework.runweb

import org.openqa.selenium.WebElement
import above.RunWeb

/**
 *      Unit test for checkbox and radio button functionality
 */
class FuncCheckElementsUnitTest extends RunWeb {

    // Test
    def test() {

        //inherited from micurtis
		setup('kyilmaz', 'Checkbox Radio Button Unit Test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test radio checkbox', 'logLevel:info'])

        String url = "http://demo.guru99.com/test/radio.html"
        String checkboxXpath = "//input[@value='checkbox1']"
        String radioXpath = "//input[@value='Option 1']"
        openBrowser(url)
        log ("Page: $url")

        assert isEnabled(checkboxXpath)
        assert checkCheckbox(checkboxXpath)
        assert isChecked(checkboxXpath)
        assert uncheckCheckbox(checkboxXpath)
        assert !isChecked(checkboxXpath)

        WebElement radioButton = find(radioXpath)
        assert isEnabled(radioButton)
        assert checkCheckbox(radioButton)
        assert isChecked(radioButton)
        assert !uncheckCheckbox(radioButton)
        assert isChecked(radioButton)

        closeBrowser()
    }
}

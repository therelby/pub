package framework.runweb.funcread

import above.RunWeb
import org.openqa.selenium.WebElement

class UtFuncReadJsSetText extends RunWeb {

    static void main(String[] args) {
        new UtFuncReadJsSetText().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    def test() {
        setup([
                author  : 'vdiachuk',
                title   : 'Set Text to Xpath or Webelement using JavaScript | Framework Self Testing Tool',
                PBI     : 524824,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'runweb funcaction js javascript set text ',
                logLevel: 'info',
        ])
        tryLoad()

        String inputXpath = "//input[@id='searchval']"
        String textToSet = "Top red"
        assert jsSetText(inputXpath, textToSet)
        assert getAttributeSafe(inputXpath, 'value') == textToSet
        refresh()
        WebElement inputElement = find(inputXpath)
        assert jsSetText(inputElement, textToSet)
        assert getAttributeSafe(inputXpath, 'value') == textToSet

        tryLoad('https://www.google.com/')
        assert !jsSetText(inputXpath, textToSet)
        assert !jsSetText(inputElement, textToSet)
    }

}

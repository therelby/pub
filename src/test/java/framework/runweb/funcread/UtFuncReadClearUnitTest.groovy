package framework.runweb.funcread

import above.RunWeb

class UtFuncReadClearUnitTest extends RunWeb {
    def test() {

        setup('vdiachuk', 'clear functionality unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 "tfsTcIds:265471",
                 'keywords:unit test cleartext clear text delete ',
                 'logLevel:info'])

        String searchXpath = "//input[@name='searchval']"
        String sampleText = "Sample text"
        tryLoad('homepage')

        //check for xpath
        setText(searchXpath, sampleText)
        assert getAttribute(searchXpath, 'value') == sampleText
        clearText(searchXpath)
        assert !getAttribute(searchXpath, 'value').trim()

        //check for WebElement
        setText(searchXpath, sampleText)
        assert getAttribute(searchXpath, 'value') == sampleText
        clearText(find(searchXpath))
        assert !getAttribute(searchXpath, 'value').trim()



    }
}

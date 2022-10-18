package framework.runweb.functextcharbychar

import above.RunWeb

class UtFuncTextCharByChar extends RunWeb {
    static void main(String[] args) {
        new UtFuncTextCharByChar().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    def test() {
        setup([
                author  : 'vdiachuk',
                title   : 'Set Text Char By Char to Xpath or Webelement using JavaScript | Framework Self Testing Tool',
                PBI     : 711406,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'runweb funcaction set text char',
                logLevel: 'info',
        ])
        tryLoad()

        String inputXpath = "//input[@id='searchval']"
        String textToSet = "Top red"
        // Positive
        assert setTextCharByChar(inputXpath, textToSet)
        assert getAttributeSafe(inputXpath, 'value') == textToSet

        refresh()
        assert setTextCharByChar(find(inputXpath), textToSet)
        assert getAttributeSafe(inputXpath, 'value') == textToSet

        // Negative
        refresh()
        assert setTextCharByChar('//FAKENODE', textToSet) == false

        // Positive with delay
        refresh()
        assert setTextCharByChar(inputXpath, textToSet,300)
        assert getAttributeSafe(inputXpath, 'value') == textToSet

        tryLoad('/custom-cutting-board.html')
        def inputGrovesXpath = "//input[@id='numberOfGrooves']"
        def numberToSet = "123"
        assert setTextCharByChar(inputGrovesXpath, numberToSet)
        assert getAttributeSafe(inputGrovesXpath, 'value') == numberToSet

        refresh()
        assert setTextCharByChar(find(inputGrovesXpath), numberToSet)
        assert getAttributeSafe(inputGrovesXpath, 'value') == numberToSet

        refresh()
        def numberToSetWrong = "as1d"
        assert setTextCharByChar(inputGrovesXpath, numberToSetWrong)
        assert getAttributeSafe(inputGrovesXpath, 'value') == '1'

        refresh()
        assert setTextCharByChar(find(inputGrovesXpath), numberToSetWrong)
        assert getAttributeSafe(inputGrovesXpath, 'value') ==  '1'
    }
}

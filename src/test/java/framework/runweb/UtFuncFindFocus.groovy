package framework.runweb

import above.RunWeb

class UtFuncFindFocus extends RunWeb {
    static void main(String[] args) {
        new UtFuncFindFocus().testExecute([
                browser      : 'chrome',//'safari',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }
    def test() {
        setup('vdiachuk', 'FuncFind focus method unit test | Framework Self ' +
                'Testing Tool',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test focus element ',
               "tfsTcIds:265471", 'logLevel:info'])

        String url = "https://demoqa.com/automation-practice-form"
        String firstNameXpath = "//input[@id='firstName']"
        String currentAddressXpath = "//*[@id='currentAddress']"

        tryLoad(url)
        assert focus(firstNameXpath)
        sleep(2000)
        assert focus(currentAddressXpath)
        sleep(2000)
        assert focus(find(firstNameXpath))
        sleep(2000)
        assert focus(find(currentAddressXpath))

        assert !focus(firstNameXpath+"FAKE")
        assert !focus(find(currentAddressXpath+"FAKE"))
    }
}

package framework.runweb.funcread

import above.RunWeb

class UtFuncReadSetUnitTest extends RunWeb {

    def test() {

        setup('vdiachuk', 'setText functionality unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test setText sendKeys ',
                 'logLevel:info'])


        String url = "https://demoqa.com/text-box"
        String inputUserNameXpath = "//input[@id='userName']"
        String labelUserNameXpath = "//label[@id='userName-label']"
        String inputEmailXpath = "//input[@id='userEmail']"
        String sampleText = "Sample Text"
        String expectedLabelText = "Full Name"
        String homePage = getUrl("homepage")
        String expectedHomePageTitle = 'WebstaurantStore: Restaurant Supplies & Foodservice Equipment'


        openAndMaximize(url)
        waitForPage()

        assert setText(inputUserNameXpath, sampleText)
        log("UserName Label:${getText(labelUserNameXpath)}")
        assert getText(labelUserNameXpath) == expectedLabelText
        assert getTextSafe(labelUserNameXpath) == expectedLabelText
        assert getText(find(labelUserNameXpath)) == expectedLabelText
        assert getTextSafe(find(labelUserNameXpath)) == expectedLabelText
        assert getTextSafe("//fake") == ''
        assert getTextSafe(find("//fake")) == ''


        loadPage(homePage)
        assert expectedHomePageTitle == getTitle()
        assert getAttribute('//input[@name="searchval"]', 'id') == "searchval"
        assert getAttributeSafe('//input[@name="searchval"]', 'id') == "searchval"

        assert getAttribute('//input[@name="searchvalFAKE"]', 'id') == null
        assert getAttributeSafe('//input[@name="searchvalFAKE"]', 'id') == ''

        assert getAttribute('//input[@name="searchval"]', 'FAKE') == null
        assert getAttributeSafe('//input[@name="searchval"]', 'FAKE') == ''


    }
}

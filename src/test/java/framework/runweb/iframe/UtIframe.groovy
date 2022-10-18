package framework.runweb.iframe

import above.RunWeb
import wss.pages.element.VideoElement

class UtIframe extends RunWeb {
    static void main(String[] args) {
        new UtIframe().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    def test() {

        setup('vdiachuk', 'Ifram func unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test iframe defaultcontent switch ',
                 'logLevel:info'])
        tryLoad("https://demoqa.com/frames")
        String xpathIn = "//body[@style='background-color:#a9a9a9']"
        assert !verifyElement(xpathIn)

        def iframeElements = findElements("//div[@id='frame1Wrapper']//iframe")
        assert switchToIframe(iframeElements[0])
        assert verifyElement(xpathIn)

        assert switchToDefaultContent()
        assert !verifyElement(xpathIn)

    }
}

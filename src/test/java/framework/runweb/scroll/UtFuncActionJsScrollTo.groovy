package framework.runweb.scroll

import above.RunWeb
import all.Money
import org.openqa.selenium.WebElement
import wss.pages.productdetail.PDPWhatWeOffer
import wss.pages.productdetail.modal.PDPInstallationServicesModal
import wss.user.userurllogin.UserUrlLogin

class UtFuncActionJsScrollTo extends RunWeb {
    static void main(String[] args) {
        new UtFuncActionJsScrollTo().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])
    }

    def test() {
        final int PBI = 520021
        setup([
                author  : 'vdiachuk',
                title   : 'Actions Unit test for Scroll to element using Java Script| Framework Self Testing Tool',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' runweb func action scroll javasript',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        tryLoad()
        def xpath = "//button[@aria-label='Submit Feedback']"
        WebElement element2 = find(xpath)
        assert jsScrollTo(element2)

        scrollToTop()
        sleep(1000)
        assert jsScrollTo(xpath)

        def fakeXpath = "//button[@aria-label='FAKE']"
        assert jsScrollTo(fakeXpath) == false
        assert jsScrollTo(null) == false
    }
}

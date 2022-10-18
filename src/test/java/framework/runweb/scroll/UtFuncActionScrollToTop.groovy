package framework.runweb.scroll

import above.RunWeb
import wss.pages.productdetail.PDPStickyHeader

class UtFuncActionScrollToTop extends RunWeb {
    static void main(String[] args) {
        new UtFuncActionScrollToTop().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    def test() {
        setup([
                author  : 'vdiachuk',
                title   : 'Scroll to Top functionality | Framework Self Testing Tool',
                PBI     : 0,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'runweb funcaction scroll top',
                logLevel: 'info',
        ])

        tryLoad('/g/158/sr-max-srm2660-denali-men-s-brown-waterproof-composite-toe-non-slip-hiker-boot')
        PDPStickyHeader pdpStickyHeader = new PDPStickyHeader()
        assert !pdpStickyHeader.isStickyHeader()
        assert scrollToBottom()
        assert pdpStickyHeader.isStickyHeader()
        assert scrollToTop()
        waitForNoElement(PDPStickyHeader.stickyHeaderDivXpath)
        assert !pdpStickyHeader.isStickyHeader()

    }

}

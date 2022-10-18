package framework.runweb

import above.RunWeb
import wss.pages.productdetail.PDPStickyHeader

class UtPageOffsetVertical extends RunWeb {

    static void main(String[] args) {
        new UtPageOffsetVertical().testExecute([
                browser      : 'chrome',
                remoteBrowser: true,//false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    def test() {
        setup([
                author  : 'vdiachuk',
                title   : 'Page vertical Offset functionality | Framework Self Testing Tool',
                PBI     : 524824,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'runweb funcaction scroll offset position vertical window',
                logLevel: 'info',
        ])
        tryLoad()
        assert pageOffsetVertical() == 0
        tryLoad()
        assert pageOffsetVertical() == 0
        scrollToBottom()
        assert pageOffsetVertical() > 1000
        scrollToTop()
        assert pageOffsetVertical() == 0
    }

}

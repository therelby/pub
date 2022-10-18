package framework.wss.action

import above.RunWeb
import wss.actions.WssRenderingMode
import wss.actions.WssRenderingModeBanner

class UtWssRenderingMode extends RunWeb {
    def test() {

        setup('vdiachuk', 'Unit test for Wss Rendering Mode - change by Cookies| Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test mode  rendering sf cf coldfusion storefront update cookie',
                 "PBI: 0",
                 'logLevel: info'])

        WssRenderingModeBanner banner = new WssRenderingModeBanner()
        // page that can be set in
        tryLoad("https://www.dev.webstaurantstore.com/visions-5-1-2-black-plastic-asian-soup-spoon-case/130SPNASIABK.html")
        assert    WssRenderingMode.setCFMode()
        assert WssRenderingMode.getMode() == "CF"
        assert banner.getCurrentMode() == "CF"
        log "--"
        sleep(2000)
        assert WssRenderingMode.setSFMode()
        assert WssRenderingMode.getMode() == "SF"
        assert banner.getCurrentMode() == "SF"
    }
}

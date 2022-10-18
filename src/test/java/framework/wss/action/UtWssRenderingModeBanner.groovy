package framework.wss.action

import above.RunWeb
import wss.actions.WssRenderingModeBanner

class UtWssRenderingModeBanner extends RunWeb{
    def test() {

        setup('vdiachuk', 'Unit test for Wss Rendering Mode Banner | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test mode  rendering sf cf coldfusion storefront',
                 "tfsTcIds: 265471",
                 'logLevel: info'])


        tryLoad("homepage")
        WssRenderingModeBanner wssRenderingModeBanner = new WssRenderingModeBanner()
        log wssRenderingModeBanner.isRenderingBanner()

        assert wssRenderingModeBanner.getCurrentContentMode() == "ColdFusion Content"
       assert wssRenderingModeBanner.setServerAndContentMode("ColdFusion Server","Default Content (ColdFusion)")==true

        log "=="
        tryLoad("https://www.dev.webstaurantstore.com/3761/reusable-plastic-barware-and-dessert-shot-glasses.html")
        assert wssRenderingModeBanner.setServerAndContentMode("Default Server","ddd")== false

        assert wssRenderingModeBanner.setServerAndContentMode("StoreFront Server","") == false

          tryLoad("https://www.dev.webstaurantstore.com/myaccount.cfm?goto=register")
        assert wssRenderingModeBanner.setServerAndContentMode("ColdFusion Server","") == false

        log "=="
        // page with NO rendering bannere
        tryLoad("https://regexr.com/")
        assert ! wssRenderingModeBanner.isRenderingBanner()
        assert  wssRenderingModeBanner.getCurrentContentMode() == null



    }
}

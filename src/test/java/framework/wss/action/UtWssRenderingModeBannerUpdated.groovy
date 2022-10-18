package framework.wss.action

import above.RunWeb
import wss.actions.WssRenderingModeBanner

class UtWssRenderingModeBannerUpdated extends RunWeb {
    def test() {

        setup('vdiachuk', 'Unit test for Wss Rendering Mode Banner after Update(Removed content Selector)| Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test mode  rendering sf cf coldfusion storefront update',
                 "tfsTcIds: 265471",
                 'logLevel: info'])
        WssRenderingModeBanner banner = new WssRenderingModeBanner()


        tryLoad("https://www.dev.webstaurantstore.com/nor-lake-klf68-c-kold-locker-6-x-8-x-6-7-indoor-walk-in-freezer/596KLF68.html")

        assert "SF" == banner.getCurrentMode()
        //  assert "noOverride" == banner.getCurrentSelectorMode()
        log "Current Mode:" + banner.getCurrentMode()
        // log "Current Selector Mode:" + banner.getCurrentSelectorMode()

        assert banner.setMode("SF") == true
        assert banner.getCurrentMode() == "SF"
        assert banner.setMode("CF") == true
        assert banner.getCurrentMode() == "CF"
        assert banner.setMode("noOverride") == true


        log "=="
        tryLoad("https://www.dev.webstaurantstore.com/")
        assert "CF" == banner.getCurrentMode()
        //  assert "noOverride" == banner.getCurrentSelectorMode()
        log "Current Mode:" + banner.getCurrentMode()
        // log "Current Selector Mode:" + banner.getCurrentSelectorMode()

        assert banner.setMode("SF") == false // this page onlyin coldfusion now
      //  assert banner.getCurrentMode() == "storefront"
        assert banner.setMode("CF") == true
        assert banner.getCurrentMode() == "CF"
        assert banner.setMode("noOverride") == true

        log "=="
        // page with no Storefront banner
        tryLoad("https://www.dev.webstaurantstore.com/nor-lake-klf68-c-kold-locker-6-x-8-x-6-7-indoor-walk-in-freezer/596KLF68.html")

        assert "SF" == banner.getCurrentMode()
        //    assert null == banner.getCurrentSelectorMode()
        log "Current Mode:" + banner.getCurrentMode()
        //   log "Current Selector Mode:" + banner.getCurrentSelectorMode()

        assert banner.setMode("SF") == true
        assert banner.getCurrentMode() == "SF"
        assert banner.setMode("CF") == true
        assert banner.getCurrentMode() == "CF"
        assert banner.setMode("noOverride") == true

        log "=="
        assert banner.setMode("FAKE") == false


    }
}

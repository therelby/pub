package framework.wss.pages.productdetail.vg

import above.RunWeb
import wss.pages.productdetail.PDPVirtualGrouping

class UtPDPVirtualGroupingNumberFromUrl extends RunWeb {
    def test() {

        setup('vdiachuk', 'PDPVirtual Grouping Page Number from URL unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test pdp product detail page vg virtual grouping number url',
                 "PBI:0",
                 'logLevel:info'])

        String urlRight = "https://www.dev.webstaurantstore.com/g/158/sr-max-srm2660-denali-men-s-brown-waterproof-composite-toe-non-slip-hiker-boot"
        String urlNotRight1 = "https://www.dev.webstaurantstore.com/158/sr-max-srm2660-denali-men-s-brown-waterproof-composite-toe-non-slip-hiker-boot"
        String urlNotRight2 = "https://www.dev.webstaurantstore.com/g/158roof-composite-toe-non-slip-hiker-boot"

        // "158^g"
        assert PDPVirtualGrouping.getVGNumberFromUrl(urlRight) == "158^g"
        log PDPVirtualGrouping.getVGNumberFromUrl(urlNotRight1)
        log PDPVirtualGrouping.getVGNumberFromUrl(urlNotRight2)

    }
}
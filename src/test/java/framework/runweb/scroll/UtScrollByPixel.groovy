package framework.runweb.scroll

import above.RunWeb
import wss.pages.productdetail.PDPStickyHeader

class UtScrollByPixel extends RunWeb {

    def test() {

        setup('vdiachuk', 'Actions Unit test for Scroll by Pixels| Framework Self Testing Tool',
                ['product: wss',
                 'tfsProject: Webstaurant.StoreFront',
                 'keywords: unit test actions action pixel scroll',
                 'PBI: 0',
                 'logLevel:info'])


        tryLoad("https://www.dev.webstaurantstore.com/regency-16-gauge-type-304-stainless-steel-pass-through-shelf-with-overshelf-72-x-24/600PTSD2472.html")
        // Checking success scrolling by Sticky Header
        PDPStickyHeader pdpStickyHeader = new PDPStickyHeader()
        assert !pdpStickyHeader.isStickyHeader()
        scrollByPixels(0,1000)
        assert pdpStickyHeader.isStickyHeader()
    }
}

package framework.wss.item.itembox

import above.RunWeb
import wss.item.itembox.ProductListingItemBox

class UtProductListingItemBox extends RunWeb {
    def test() {

        setup('vdiachuk', ' Product Listing Item Box unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test Product Listing Item Box',
                 "tfsTcIds:265471", 'logLevel:info'])

        //

        tryLoad("https://www.dev.webstaurantstore.com/search/plastic.html?forcecacheupdate=1")
        ProductListingItemBox productListingItemBox2 = new ProductListingItemBox("346wkfsnspm", false)
        log  productListingItemBox2.toString()
        log productListingItemBox2.focus()
        log productListingItemBox2.toString()

        log "--"
        //suffix item with spaces
        tryLoad("https://www.dev.webstaurantstore.com/search/lft.html")
        productListingItemBox2 = new ProductListingItemBox("596KDB77810 lft", true)

        log  productListingItemBox2.toString()
        assert productListingItemBox2.focus()
        log productListingItemBox2.toString()


        log "--"
        //suffix item with no spaces inside
        tryLoad("https://www.dev.webstaurantstore.com/search/384rcl3030pkwh.html")
        productListingItemBox2 = new ProductListingItemBox("384RCL3030PKWHN", true)
        log  productListingItemBox2.toString()
        assert productListingItemBox2.focus()
        log productListingItemBox2.toString()

        log "--"
////////////////testing lone suffix item 999100CSTN  250
        tryLoad("https://www.dev.webstaurantstore.com/search/999100cstn.html")

        productListingItemBox2 = new ProductListingItemBox("999100CSTN 250", false)//number from page[999100cstn  250]
        log productListingItemBox2.toString()
        assert productListingItemBox2.focus()
        log productListingItemBox2.toString()

        log "--"
        // 942MOD427TDM208/240
        tryLoad("https://www.dev.webstaurantstore.com/search/942mod427tdm208%2f24.html")

        productListingItemBox2 = new ProductListingItemBox("942MOD427TDM208/240", false)
        log productListingItemBox2.toString()
        assert productListingItemBox2.focus()
        log productListingItemBox2.toString()


    }
}

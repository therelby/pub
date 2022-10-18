package framework.wss.item.itembox

import above.RunWeb
import wss.item.itembox.ProductListingItemBox

class UtProductListingItemBoxSecondConstructor extends RunWeb {

    static void main(String[] args) {
        new UtProductListingItemBoxSecondConstructor().testExecute([
                browser      : 'chrome',//'safari',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }
    def test() {

        setup('vdiachuk', ' Product Listing Item Box unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test Product Listing Item Box constructor',
                 "tfsTcIds:265471", 'logLevel:info'])

        ProductListingItemBox productListingItemBox2


        tryLoad("https://www.dev.webstaurantstore.com/search/plastic.html?forcecacheupdate=1")
         productListingItemBox2 = new ProductListingItemBox("346wkfsnspm")
        log  productListingItemBox2.toString()
        assert productListingItemBox2.focus()
        log productListingItemBox2.toString()

        log "--"
//        //suffix item with spaces
//        tryLoad("https://www.dev.webstaurantstore.com/search/lft.html")
//        productListingItemBox2 = new ProductListingItemBox("596KDB77810")
//
//        log  productListingItemBox2.toString()
//        assert productListingItemBox2.focus()
//        log productListingItemBox2.toString()


        log "--"
        //suffix item with no spaces inside
        tryLoad("/search/384rcl3030pkpi.html")
        productListingItemBox2 = new ProductListingItemBox("384RCL3030PKPIW")
        log  productListingItemBox2.toString()
        assert productListingItemBox2.focus()
        log productListingItemBox2.toString()

        log "--"
/////////////////testing lone suffix item 999100CSTN  250
        tryLoad("/search/600b3101413.html")

        productListingItemBox2 = new ProductListingItemBox("600b3101413 lft")//number from page[999100cstn  250]
        log productListingItemBox2.toString()
        assert productListingItemBox2.focus()
        log productListingItemBox2.toString()


    }
}

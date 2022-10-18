package framework.wss.item.itembox

import above.RunWeb
import wss.item.itembox.ProductListingItemBox
import wss.item.productlisting.ProductListingPage

class UtProductListingItemBoxCategory extends RunWeb {
    def test() {

        setup('vdiachuk', ' Product Listing Item Box unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test Product Listing Item Box constructor',
                 "tfsTcIds:265471", 'logLevel:info'])

        ProductListingItemBox productListingItemBox


        tryLoad("https://www.dev.webstaurantstore.com/52711/reach-in-freezers.html?withinval=178A19FHC")
        productListingItemBox = new ProductListingItemBox("178A19FHC")
        log productListingItemBox.toString()
        setLogLevel("debug")
        assert productListingItemBox.focus()
        log productListingItemBox.toString()
        assert click(productListingItemBox.getXpath("addToCartButton"))

    /*    productListingItemBox = new ProductListingItemBox("178A19FHC", false)
        log productListingItemBox.toString()
        assert productListingItemBox.focus()
        assert click(productListingItemBox.getXpath("addToCartButton"))


        //// category with suffix items
        tryLoad("https://www.dev.webstaurantstore.com/13703/walk-in-coolers-refrigerators.html")
        scrollToBottom()
        productListingItemBox = new ProductListingItemBox("596KLB741014")
        log productListingItemBox.toString()
        assert productListingItemBox.focus()
        log productListingItemBox.toString()
        assert click(productListingItemBox.getXpath("addToCartButton"))


        tryLoad("https://www.dev.webstaurantstore.com/13703/walk-in-coolers-refrigerators.html")
        productListingItemBox = new ProductListingItemBox("596klb741012lft", true)
        scrollToBottom()
        log productListingItemBox.toString()
        assert productListingItemBox.focus()
        log productListingItemBox.toString()
        click(productListingItemBox.getXpath("addToCartButton"))
        log getText(productListingItemBox.getXpath("uom"))
        log getText(productListingItemBox.getXpath("price"))
        log getText(productListingItemBox.getXpath("itemNumber"))
        log getText(productListingItemBox.getXpath("title"))

        tryLoad("https://www.dev.webstaurantstore.com/13703/walk-in-coolers-refrigerators.html")
        productListingItemBox = new ProductListingItemBox("596klb74810 lft", true)
        scrollToBottom()
        log productListingItemBox.toString()
        assert productListingItemBox.focus()
        log productListingItemBox.toString()
        click(productListingItemBox.getXpath("addToCartButton"))
*/

    }
}

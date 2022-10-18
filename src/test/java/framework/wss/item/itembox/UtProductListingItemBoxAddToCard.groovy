package framework.wss.item.itembox

import above.RunWeb
import wss.item.itembox.ProductListingItemBox

class UtProductListingItemBoxAddToCart extends RunWeb {
    def test() {

        setup('vdiachuk', ' Product Listing Item Box Add to cart methods unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test Product Listing Item Box add to cart',
                 "tfsTcIds:265471", 'logLevel:info'])

        ProductListingItemBox productListingItemBox


        tryLoad("https://www.dev.webstaurantstore.com/search/922pc15sc.html")
        productListingItemBox = new ProductListingItemBox("922PC15SCC")
        log productListingItemBox.toString()
        assert productListingItemBox.focus()
        log productListingItemBox.toString()

        def options = productListingItemBox.getMinMustBuyOptions()
        log options
        assert productListingItemBox.selectMinDropdownQuantity()
        assert productListingItemBox.selectMinDropdownQuantity("24")
        //
        assert productListingItemBox.selectMinDropdownQuantity("Other")
        // after selecting "other" dropdown disappeared - not possible to check
        assert !productListingItemBox.selectMinDropdownQuantity("12")

        //item with no Min Quantity dropdown
        log "=="
        tryLoad("https://www.dev.webstaurantstore.com/search/460ecpc.html")
        productListingItemBox = new ProductListingItemBox("460ECPC86")
        log productListingItemBox.toString()
        assert productListingItemBox.focus()
        log productListingItemBox.toString()

        options = productListingItemBox.getMinMustBuyOptions()
        log options
        assert !productListingItemBox.selectMinDropdownQuantity()
        assert !productListingItemBox.selectMinDropdownQuantity("24")

        // Adding to cart item with Dropdown quantity
        /*    tryLoad("https://www.dev.webstaurantstore.com/search/922pc15sc.html")
            productListingItemBox = new ProductListingItemBox("922PC15SCC")
            assert productListingItemBox.focus()
            assert productListingItemBox.addItemToCard()*/

        // Adding to cart item with Dropdown quantity
        log "--"
        tryLoad("https://www.dev.webstaurantstore.com/search/922pc15.html")
        productListingItemBox = new ProductListingItemBox("922PC15SCC")
        assert productListingItemBox.focus()
        log productListingItemBox.getMinMustBuyOptions()
        assert productListingItemBox.addItemToCard(60)

        // Adding to cart item with Dropdown quantity
        log "--"
        tryLoad("https://www.dev.webstaurantstore.com/search/922pc15.html")
        productListingItemBox = new ProductListingItemBox("922PC15PN")
        assert productListingItemBox.focus()
        log productListingItemBox.getMinMustBuyOptions()
        assert productListingItemBox.addItemToCard(64)

        //item with no Min Quantity dropdown
        log "=="
        tryLoad("https://www.dev.webstaurantstore.com/search/460ecpc.html")
        productListingItemBox = new ProductListingItemBox("460ECPC86")

        assert productListingItemBox.setItem()

        assert productListingItemBox.addItemToCard(64)

    }
}

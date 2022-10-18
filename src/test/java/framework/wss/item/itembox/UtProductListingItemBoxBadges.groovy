package framework.wss.item.itembox

import above.RunWeb
import wss.item.itembox.ProductListingItemBox

class UtProductListingItemBoxBadges extends RunWeb {
    def test() {

        setup('vdiachuk', ' Product Listing Item Box Badges text and image unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test Product Listing Item Box badge badges text image',
                 "tfsTcIds:265471", 'logLevel:info'])

        ProductListingItemBox productListingItemBox


        tryLoad("https://www.dev.webstaurantstore.com/search/avantco-fryer.html?withinval=177FF300N")
        productListingItemBox = new ProductListingItemBox("177FF300N")
        log productListingItemBox.toString()
        assert productListingItemBox.focus()

        def badgeImageXpath = productListingItemBox.getXpath('badgeImage')
        def badgeTextXpath = productListingItemBox.getXpath('badgeText')
        assert getText(badgeTextXpath) == "NATURAL GAS"
        //<img class="variation__icon" src="//cdnimg.webstaurantstore.com/uploads/design/2016/12/blueflame.png" alt="Natural Gas">
        assert getAttribute(badgeImageXpath, 'src').contains("blueflame.png")

        tryLoad("https://www.dev.webstaurantstore.com/search/avantco-fryer.html?withinval=177F202")
        productListingItemBox = new ProductListingItemBox("177F202")
        log productListingItemBox.toString()
        assert productListingItemBox.focus()

        badgeImageXpath = productListingItemBox.getXpath('badgeImage')
        badgeTextXpath = productListingItemBox.getXpath('badgeText')
        assert getText(badgeTextXpath) == "208/240 Volts".toUpperCase()
        //<img class="variation__icon" src="//cdnimg.webstaurantstore.com/uploads/design/2016/12/blueflame.png" alt="Natural Gas">
        assert getAttribute(badgeImageXpath, 'src').contains("bolt.png")


    }
}

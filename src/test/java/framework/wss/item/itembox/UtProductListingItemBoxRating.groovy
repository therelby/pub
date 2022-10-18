package framework.wss.item.itembox

import above.RunWeb
import wss.item.itembox.ProductListingItemBox

class UtProductListingItemBoxRating extends RunWeb {
    def test() {

        setup('vdiachuk', ' Product Listing Item Box Rating unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test Product Listing Item Box rating star',
                 "tfsTcIds:265471", 'logLevel:info'])

        ProductListingItemBox productListingItemBox


        tryLoad("https://www.dev.webstaurantstore.com/search/avantco-fryer.html")
        productListingItemBox = new ProductListingItemBox("177FF300N")
        log productListingItemBox.toString()
        assert productListingItemBox.focus()

        log getAttribute(productListingItemBox.getXpath("ratingMain"), "aria-label")
        //getting Rating from Attribute
        Integer rating = (getAttribute(productListingItemBox.getXpath("ratingMain"), "aria-label")?.split(" ")?.getAt(1) as Integer)
        log "attribute Rating: " + rating
        assert rating == 5
        //getting Rating from Stars Quantity
        Integer starRating = productListingItemBox.getRatingStars()
        log "Rating Stars stars Quantity: " + starRating
        assert starRating == 5


        log "--"
        productListingItemBox = new ProductListingItemBox("177EF40C")
        log productListingItemBox.toString()
        assert productListingItemBox.focus()

        log getAttribute(productListingItemBox.getXpath("ratingMain"), "aria-label")
        //getting Rating from Attribute
        rating = (getAttribute(productListingItemBox.getXpath("ratingMain"), "aria-label")?.split(" ")?.getAt(1) as Integer)
        log "Rating: " + rating
        assert rating == null
        //getting Rating from Stars Quantity
        starRating = productListingItemBox.getRatingStars()
        log "Rating Stars: " + starRating
        assert starRating == null


        log "--"
        productListingItemBox = new ProductListingItemBox("177FF400L")
        log productListingItemBox.toString()
        assert productListingItemBox.focus()

        log getAttribute(productListingItemBox.getXpath("ratingMain"), "aria-label")
        //getting Rating from Attribute
        rating = (getAttribute(productListingItemBox.getXpath("ratingMain"), "aria-label")?.split(" ")?.getAt(1) as Integer)
        log "Rating: " + rating
        assert rating == 4
        //getting Rating from Stars Quantity
        starRating = productListingItemBox.getRatingStars()
        log "Rating Stars: " + starRating
        assert starRating == 4


    }

}

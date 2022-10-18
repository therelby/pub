package framework.wss.pages.element.topmenu

import above.RunWeb
import wss.pages.productdetail.modal.ProductAccessoriesModal
import wss.pages.productlisting.ListingPage

class UtCartTopElement extends RunWeb {

    def test() {

        setup('vdiachuk', 'Cart Top Element unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test cart top menu count element',
                 'PBI: 0',
                 'logLevel:info'])


        tryLoad('https://www.dev.webstaurantstore.com/feature/14235/commercial-ice-machines/')

        ListingPage listingPage = new ListingPage()
        log "result listingPage.addRandomItemToCart(): " + listingPage.addRandomItemToCart()
        ProductAccessoriesModal productAccessoriesModal = new ProductAccessoriesModal()
        log "accessories: " + productAccessoriesModal.isProductAccessories()
        log "accessories: click: " + productAccessoriesModal.addToCartIfPresent()


    }
}

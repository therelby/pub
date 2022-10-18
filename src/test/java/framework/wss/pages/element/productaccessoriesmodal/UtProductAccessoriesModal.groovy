package framework.wss.pages.element.productaccessoriesmodal

import above.RunWeb
import wss.item.itembox.ProductListingItemBox
import wss.pages.productdetail.modal.ProductAccessoriesModal

class UtProductAccessoriesModal extends RunWeb{
    def test() {

        setup('vdiachuk', 'Product Accessories Modal unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test add cart product accessories modal',
                 'PBI: 0',
                 'logLevel:info'])


        tryLoad('https://www.dev.webstaurantstore.com/feature/14235/commercial-ice-machines/')

        ProductListingItemBox productListingItemBox = new ProductListingItemBox("720HID312A1")
        productListingItemBox.focus()
        String addToCartXpath = productListingItemBox.getXpath("addToCartButton")
        ProductAccessoriesModal productAccessoriesModal = new ProductAccessoriesModal()
        assert productAccessoriesModal.isProductAccessories() == false
        assert productAccessoriesModal.addToCartIfPresent() == true
        click(addToCartXpath)
        sleep(500)


        assert productAccessoriesModal.isProductAccessories()
        assert productAccessoriesModal.addToCartIfPresent()




    }
}

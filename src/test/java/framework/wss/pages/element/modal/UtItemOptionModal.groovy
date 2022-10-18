package framework.wss.pages.element.modal

import above.RunWeb
import wss.item.itembox.ProductListingItemBox
import wss.pages.element.modal.ItemOptionModal

class UtItemOptionModal extends RunWeb {
    static void main(String[] args) {
        new UtItemOptionModal().testExecute([

                browser      : 'chrome',
                remoteBrowser: false,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])
    }

    def test() {
        final int PBI = 0
        setup([
                author  : 'vdiachuk',
                title   : 'Product/Item Option Modal | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'item option product modal  unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        tryLoad("/search/75638140105.html")
        ProductListingItemBox productListingItemBox = new ProductListingItemBox("756M3814017M")
        ItemOptionModal itemOptionModal = new ItemOptionModal()
        assert !itemOptionModal.isModal()
        assert itemOptionModal.setRandomAllOptionsAndAddToCart() == false
        log "--"
        log "Setting and adding to cart(modal exists): " + productListingItemBox.setDetailsAndAddToCart()
        sleep(1000)

        assert itemOptionModal.isModal()
        assert itemOptionModal.setRandomAllOptionsAndAddToCart()


    }
}

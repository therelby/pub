package framework.wss.pages.productlisting.modal

import above.RunWeb
import wss.pages.element.modal.ItemAccessoriesModal

class UtPLPAccessoriesModal extends RunWeb {

    static void main(String[] args) {
        new UtPLPAccessoriesModal().testExecute([

                browser      : 'chrome',//'chrome',
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
        final int PBI = 563917
        setup([
                author  : 'vdiachuk',
                title   : 'Accessories Modal on PLP| Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'item product plp accessories accessory modal listing page',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        ItemAccessoriesModal plpAccessoriesModal = new ItemAccessoriesModal()

        tryLoad('/search/132as36vn3.html')

        String addToCartPLPXpath = "//input[@data-testid='itemAddCart']"
        assert !plpAccessoriesModal.isModalPresent()
        assert !plpAccessoriesModal.setRandomAllAccessoriesAndAddToCart()
        assert plpAccessoriesModal.getData() == []
        log "Click add to cart plp: " + click(addToCartPLPXpath)
        sleep(1000)
        assert plpAccessoriesModal.isModalPresent()
        //  def modalData = plpAccessoriesModal.getData()
        //  log modalData
        //   assert modalData.size() > 3
        log "--"
        assert plpAccessoriesModal.setRandomAllAccessories()

        log "--"
        tryLoad("/search/132ad3.html")
        log "Click add to cart plp: " + click(addToCartPLPXpath)
        assert plpAccessoriesModal.setRandomAllAccessoriesAndAddToCart()
    }
}
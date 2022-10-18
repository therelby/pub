package framework.wss.pages.element.modal

import above.RunWeb
import wss.pages.element.modal.ItemWarningNoteModal
import wss.pages.productdetail.PDPPriceTile

class UtProductWarningsNoteModal extends RunWeb {

    static void main(String[] args) {
        new UtProductWarningsNoteModal().testExecute([

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
        final int PBI = 561599
        setup([
                author  : 'ikomarov',
                title   : 'PDP, Product Warnings Note modal unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' pdp product detail page product warnings note modal unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        ItemWarningNoteModal productWarningsNoteModal = new ItemWarningNoteModal()

        String pageWithNoteModal = "https://www.dev.webstaurantstore.com/manitowoc-d570-ice-storage-bin-532-lb/499D570.html"

        tryLoad(pageWithNoteModal)
        assert !productWarningsNoteModal.isProductWarningsNote()
        click(PDPPriceTile.addToCartButtonXpath)
        assert productWarningsNoteModal.isProductWarningsNote()
        closeBrowser()
        tryLoad()
        assert !productWarningsNoteModal.isProductWarningsNote()
    }
}

package framework.wss.pages.element.modal.itemaddtocartcontroller

import above.RunWeb
import wss.pages.element.modal.ItemModalController
import wss.item.itembox.ProductListingItemBox
import wss.pages.element.topmenu.CartTopElement

class UtItemAddToCartController extends RunWeb {

    static void main(String[] args) {
        new UtItemAddToCartController().testExecute([

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
                title   : 'Item Add to cart Controller(Modals) unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'item add cart controller modal plp unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        tryLoad("/search/132as36vn3.html")
        ProductListingItemBox productListingItemBox = new ProductListingItemBox("132AS36VN34")
        productListingItemBox.focus()
        assert productListingItemBox.setDetailsAndAddToCart()
        //  sleep(1000)

        //    setLogLevel("debug")
        log "--"
        ItemModalController itemAddToCartController = new ItemModalController()
        assert itemAddToCartController.findAllModalAndSolve()


        tryLoad("/search/75638140105.html")
        ProductListingItemBox productListingItemBox1 = new ProductListingItemBox("75638140105M")
        log productListingItemBox1.focus()
        assert productListingItemBox1.setDetailsAndAddToCart()
        //  sleep(1000)
        assert itemAddToCartController.findAllModalAndSolve()


        tryLoad("/13253/water-cooled-ice-machines.html")
        ProductListingItemBox productListingItemBox2 = new ProductListingItemBox("720NS0422W1")
        log productListingItemBox2.focus()
        assert productListingItemBox2.setDetailsAndAddToCart()
        //  sleep(1000)
        assert itemAddToCartController.findAllModalAndSolve()

        CartTopElement cartTopElement = new CartTopElement()
        assert cartTopElement.getCartCount() == 3

    }
}

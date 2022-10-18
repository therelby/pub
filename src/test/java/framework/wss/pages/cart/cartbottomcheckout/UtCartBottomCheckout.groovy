package framework.wss.pages.cart.cartbottomcheckout

import above.RunWeb
import wss.pages.cart.CartBottomCheckout
import wss.pages.cart.CartItemBox
import wss.pages.cart.CartSuggestedItemBox
import wss.pages.cart.ViewCartPage

class UtCartBottomCheckout extends RunWeb{

    static void main(String[] args) {
        new UtCartBottomCheckout().testExecute([

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
        final int PBI = 530427
        setup([
                author  : 'vdiachuk',
                title   : 'Cart Bottom Subtotal Checkout unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' cart bottom subtotal checkout unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        tryLoad()
        ViewCartPage viewCartPage = new ViewCartPage()
        viewCartPage.addItemToCart("905SM40G", 1)
        CartBottomCheckout cartBottomCheckout = new CartBottomCheckout()
        assert cartBottomCheckout.isBlockPresent()
        assert cartBottomCheckout.getSubtotalSum()>0
        log new ViewCartPage().getCardID()

        tryLoad()
        assert cartBottomCheckout.isBlockPresent() == false
        assert cartBottomCheckout.getSubtotalSum() == null



    }
}

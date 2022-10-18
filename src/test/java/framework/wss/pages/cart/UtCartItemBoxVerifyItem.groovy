package framework.wss.pages.cart

import above.RunWeb
import wss.pages.cart.CartItemBox
import wss.pages.cart.ViewCartPage

class UtCartItemBoxVerifyItem extends RunWeb {
    static void main(String[] args) {
        new UtCartItemBoxVerifyItem().testExecute([

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
        final int PBI = 559832
        
        setup([
                author  : 'vdiachuk',
                title   : 'Cart Item Box - Verify Item in Cart unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' cart item box verify present unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        tryLoad()
        ViewCartPage viewCartPage = new ViewCartPage()
        String itemNoSpaces = "905SM40G"
        viewCartPage.addItemToCart(itemNoSpaces, 1)
        CartItemBox cartItemBox = new CartItemBox(itemNoSpaces)
        assert cartItemBox.verifyItemInCart()
        assert cartItemBox.removeItemFromCart()
        sleep(1000)
        assert !cartItemBox.verifyItemInCart()

       // String itemWithSpaces = "872cviwst   28".toUpperCase()

    }
}
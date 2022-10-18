package framework.wss.pages.cart.itemlisting.itemremoval

import above.RunWeb
import framework.wss.pages.cart.itemlisting.HpUtCartMinMustBuy
import wss.pages.cart.CartItemBox
import wss.pages.cart.ViewCartPage

class UtCartStandardItemRemoval extends RunWeb{

    static String loneProduct = '100BTWINEBAL'
    static String loneSuffixProduct = '100TWINE    SM'
    static String virtualGroupingProduct = '124COL24HCBK'

    static void main(String[] args) {
        new UtCartStandardItemRemoval().testExecute([

                browser      : 'chrome',
                remoteBrowser: false,
//                browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])
    }

    def test() {

        setup('mwestacott', 'Cart - Standard Item Removal | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart standard item removal',
                 "tfsTcIds:0", 'logLevel:info'])

        assert openAndTryLoad('cart')

        ViewCartPage viewCartPage = new ViewCartPage()
        assert viewCartPage.isCartEmpty()

        removeItemFromCart(loneProduct)
        removeItemFromCart(loneSuffixProduct)
        removeItemFromCart(virtualGroupingProduct)

        assert closeBrowser()
    }

    void removeItemFromCart(String itemNumber){
        ViewCartPage viewCartPage = new ViewCartPage()
        assert viewCartPage.addItemToCart(itemNumber, 1)

        CartItemBox cartItemBox = new CartItemBox(itemNumber)
        assert cartItemBox.removeItemFromCart()
    }
}

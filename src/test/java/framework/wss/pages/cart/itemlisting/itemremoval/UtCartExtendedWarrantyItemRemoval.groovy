package framework.wss.pages.cart.itemlisting.itemremoval

import above.RunWeb
import wss.pages.cart.CartItemBox
import wss.pages.cart.ViewCartPage
import wss.pages.productdetail.PDPPriceTile
import wss.pages.productdetail.PDPage

class UtCartExtendedWarrantyItemRemoval extends RunWeb{

    static String externalWarrantyProduct = '720C1848MR32'
    static String internalWarrantyProduct = '177CPO16TS'

    static void main(String[] args) {
        new UtCartExtendedWarrantyItemRemoval().testExecute([

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

        setup('mwestacott', 'Cart - Extended Warranty Item Removal | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart extended warranty item removal',
                 "tfsTcIds:0", 'logLevel:info'])

        testingByUser('guest')
        testingByUser('commercial', 989)
        testingByUser('residential', 655)
        assert closeBrowser()
    }

    void testingByUser(String userType, Integer userId = null){
        String initialUrl = (userType=='guest') ? "https://www.dev.webstaurantstore.com/myaccount/?logout=Y" : "https://www.dev.webstaurantstore.com/?login_as_user=$userId"
        assert tryLoad(initialUrl)

        removeItemFromCart(externalWarrantyProduct)
        removeItemFromCart(internalWarrantyProduct)
    }

    void removeItemFromCart(String itemNumber){
        PDPage pdPage = new PDPage()
        assert pdPage.navigateToPDPWithItemNumber(itemNumber)

        PDPPriceTile pdpPriceTile = new PDPPriceTile()
        assert pdpPriceTile.addToCart(1, true)

        ViewCartPage viewCartPage = new ViewCartPage()
        assert viewCartPage.navigate()

        CartItemBox cartItemBox = new CartItemBox(itemNumber)
        assert cartItemBox.removeItemFromCart()
    }
}

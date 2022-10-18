package framework.wss.pages.cart.savedforlater.negative

import above.RunWeb
import wss.pages.cart.CartItemBox
import wss.pages.cart.ViewCartPage
import wsstest.cart.saveforlater.nonnumericalproducts.TcCartSavedForLaterNonNumericalProducts

class UtCartItemStartsNoNumberSavedForLater extends RunWeb{

    def firstItemNumber = 'HPINGSK1070'
    def secondItemNumber = 'AP851300'
    def thirdItemNumber = 'HP2900906'

    static void main(String[] args) {
        new UtCartItemStartsNoNumberSavedForLater().testExecute([

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

        setup('mwestacott', 'Cart - Saved For Later - Item Number does not start with number Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart saved for later item number does not start with number',
                 "tfsTcIds:0", 'logLevel:info'])

        assert TcCartSavedForLaterNonNumericalProducts.getNonSaveForLaterItems(5, false).size() == 5
        assert TcCartSavedForLaterNonNumericalProducts.getNonSaveForLaterItems(5, true).size() == 0

        assert openAndTryLoad('cart')

        testingProduct(firstItemNumber)
        testingProduct(secondItemNumber)
        testingProduct(thirdItemNumber)

        closeBrowser()
    }

    void testingProduct(String itemNumber){
        ViewCartPage viewCartPage = new ViewCartPage()

        boolean wasAbleToEmptyCart = viewCartPage.emptyCart()
        assert wasAbleToEmptyCart
        assert viewCartPage.addItemToCart(itemNumber)

        CartItemBox cartItemBox = new CartItemBox(itemNumber)
        assert cartItemBox.verifyItemInCart(itemNumber)
        assert cartItemBox.verifyNoSaveForLater()

        assert viewCartPage.emptyCart()
    }
}

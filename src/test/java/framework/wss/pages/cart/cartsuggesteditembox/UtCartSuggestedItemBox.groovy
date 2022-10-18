package framework.wss.pages.cart.cartsuggesteditembox

import above.RunWeb
import wss.pages.cart.CartItemBox
import wss.pages.cart.CartSuggestedItemBox
import wss.pages.cart.ViewCartPage
import wss.pages.productdetail.PDPAccessory

class UtCartSuggestedItemBox extends RunWeb {

    static void main(String[] args) {
        new UtCartSuggestedItemBox().testExecute([

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
                title   : 'Cart Suggested Item Box unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' cart item box suggested unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        tryLoad()
        ViewCartPage viewCartPage = new ViewCartPage()
        viewCartPage.addItemToCart("905SM40G", 1)
        CartItemBox cartItemBox = new CartItemBox('905SM40G')
        assert cartItemBox.getSuggestedItemNumber() == "905MM40R"
        assert cartItemBox.verifyCrossSellItem()
// not present
        CartItemBox cartItemBoxFake = new CartItemBox('FAKE905SM40G')
        assert cartItemBoxFake.getSuggestedItemNumber() == ''
        assert cartItemBoxFake.verifyCrossSellItem() == false
        log "--"
        CartSuggestedItemBox cartSuggestedItemBox = new CartSuggestedItemBox('905MM40R')
        assert cartSuggestedItemBox.verifySuggestedItemInCart()

        assert getTextSafe(cartSuggestedItemBox.getXpath('title')) == 'Unger MM40R SmartColor MicroMop 15.0 16" Red Wet / Dry Mop Pad'
        assert getTextSafe(cartSuggestedItemBox.getXpath('header')) == CartSuggestedItemBox.blockHeaderText
        log "cartSuggestedItemBox.addToCart() "+cartSuggestedItemBox.addToCart()
        // not present
        log "--"
//        CartSuggestedItemBox cartSuggestedItemBoxFake = new CartSuggestedItemBox('905MM40RFAKE')
//        assert cartSuggestedItemBoxFake.verifySuggestedItemInCart() == false
    }
}

package framework.wss.pages.category

import above.RunWeb
import wss.cart.Cart
import wss.item.ItemUtil
import wss.pages.category.LeafCategory

class UtLeafPage extends RunWeb {
    def test() {

        setup('vdiachuk', 'Leaf Category unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test leafcategory leaf category',
                 "tfsTcIds:265471",
                 'logLevel:info'])

        /*    tryLoad("homepage")
            UserQuickLogin.loginUser()
            Cart.emptyCart(true)
            Cart.addItemToCart("220KWOYS4")

            Cart.addItemToCart("6901980420PL")
            setLogLevel("debug")
            CartItemBox cartItem = new CartItemBox("220KWOYS4")
            assert cartItem.incrementQuantity()
            assert cartItem.removeItemFromCart()*/


        openAndTryLoad(getUrl("homepage"))
        LeafCategory lc = new LeafCategory("43905")
        lc.navigateToLeafCategory()
        log "Real Category URL: $lc.realCategoryUrl"


    }
}

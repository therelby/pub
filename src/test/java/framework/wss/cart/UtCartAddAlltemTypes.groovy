package framework.wss.cart

import above.RunWeb
import wss.cart.Cart
import wss.item.ItemUtil

class UtCartAddAlltemTypes extends RunWeb {
    def test() {

        setup('vdiachuk', 'Cart SECOND unit test CartItemBox | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart add empty cartitembox',
                 "tfsTcIds:265471", 'logLevel:info'])

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
        //inum = inum.replace('  '. ' ').replace(' '. '%20')
        String itemNumber = "128E1003    1M"
        String count = 1

        String itemNumberUrl = java.net.URLEncoder.encode(itemNumber.trim(), "UTF-8").replace("+", "%20")
        //
        log "1"
        log "additemtocart.cfm?item_number=${itemNumberUrl}&quantity=${count}"


        Integer suffixQuantity = 10
        def suffixItems = ItemUtil.getItemByType("suffix", suffixQuantity)

        Cart.emptyCart()
        log "suffixItems:" + suffixItems
        for (def suffix : suffixItems) {
            String suffixItemNumber = suffix.get("Item_Number")
            log "========Testing Suffix item:" + suffixItemNumber
            assert Cart.addItemToCart(suffixItemNumber)
            log "Items in the cart:"+ Cart.getItemsInCart()
            assert Cart.verifyItemInCart(suffixItemNumber)
            assert Cart.emptyCart()
            log "Quantity items in the Cart:"+ getText(Cart.numberOfCartItems).trim()
            assert Cart.isCartEmpty()
        }

        Integer loneSuffixQuantity = 10
        def loneSuffixItems = ItemUtil.getItemByType("loneSuffix", loneSuffixQuantity)
        Cart.emptyCart()
        log "LoneSuffixItems:" + loneSuffixItems
        for (def item : loneSuffixItems) {
            itemNumber = item.get("Item_Number")
            log "========Testing Suffix item:" + itemNumber
            assert Cart.addItemToCart(itemNumber)
            log "Items in the cart:"+ Cart.getItemsInCart()
            assert Cart.verifyItemInCart(itemNumber)
            assert Cart.emptyCart()
            log "Quantity items in the Cart:"+ getText(Cart.numberOfCartItems).trim()
            assert Cart.isCartEmpty()
        }

        Integer loneQuantity = 10
        def loneItems = ItemUtil.getItemByType("lone", loneQuantity)
        Cart.emptyCart()
        log "LoneSItems:" + loneItems
        for (def item : loneItems) {
            itemNumber = item.get("Item_Number")
            log "========Testing Suffix item:" + itemNumber
            assert Cart.addItemToCart(itemNumber)
            log "Items in the cart:"+ Cart.getItemsInCart()
            assert Cart.verifyItemInCart(itemNumber)
            assert Cart.emptyCart()
            log "Quantity items in the Cart:"+ getText(Cart.numberOfCartItems).trim()
            assert Cart.isCartEmpty()
        }



    }
}

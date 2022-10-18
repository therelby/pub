package framework.wss.cart

import above.RunWeb
import wss.cart.Cart
import wss.pages.cart.CartItemBox
import wss.user.UserQuickLogin

class UtCartUnitTest extends RunWeb {
    def test() {

        setup('vdiachuk', 'Cart unit test | Framework Self ' +
                'Testing Tool',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart add empty',
               "tfsTcIds:265471", 'logLevel:info'])

        tryLoad("homepage")
        UserQuickLogin.loginUser()
        log "Cart Url:" + getUrl("cart")
        def itemNumber ="185HBR231GHC"
        Cart.addItemToCart(itemNumber)
        def items = Cart.getItemsInCart()
        def itemPrice = new CartItemBox(itemNumber).getPrice()
        log "item price:" + itemPrice
        assert itemPrice > 0
        assert items.any() { it -> it == "185HBR231GHC" }
        assert Cart.verifyItemInCart("185HBR231GHC")
        Cart.emptyCart()
        assert Cart.isCartEmpty()
        assert Cart.getItemsInCart().size() == 0
        def itemsToAdd = [["itemNumber": "185FB231G", "quantity": 1],
                          ["itemNumber": "947SY110D", "quantity": 2],
                          ["itemNumber": "947SU2H38P", "quantity": 1],
        ]
        Cart.addMultipleItemsToCart(itemsToAdd)
        items = Cart.getItemsInCart()
        assert items.size() == 3
        assert itemsToAdd.collect() { it.get('itemNumber') }.containsAll(items)
        waitForElement(Cart.buttonShippingZipChange, 10)
        jsClick(Cart.buttonShippingZipChange)
        Cart.setAddressTypeAndZipCode("Business", "32792")
        assert Cart.readCartId().size()>2


    }
}

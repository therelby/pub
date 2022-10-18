package framework.wss.cart

import above.RunWeb
import wss.pages.cart.CartItemBox
import wss.pages.cart.ViewCartPage

class UtCartItemBox extends RunWeb {
    static void main(String[] args) {
        new UtCartItemBox().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'kyilmaz',
                title   : 'UtCartItemBox',
                PBI     : 0,
                product : 'wss',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])
        testBasicFunctions()
        addSimilarItems()
        addMultipleItemsViaUrl()
        testLoneSuffixWithSpaces()
        testMinMustBuyQuantity()
    }
    void testMinMustBuyQuantity() {
        def item = '347RP6CL'
        def cart = new ViewCartPage()
        cart.addItemToCart(item, 6)
        cart.navigate()
        def cartItemBox = new CartItemBox(item)
        assert cartItemBox.getQuantity() == 6
    }
    void testLoneSuffixWithSpaces() {
        def item = '128E1002B   1M'
        def cart = new ViewCartPage()
        cart.addItemToCart(item, 1)
        cart.navigate()
        def cartItemBox = new CartItemBox(item)
        assert cartItemBox.getQuantity() == 1
    }
    void addMultipleItemsViaUrl() {
        def items = ['999SCUMBGONE', '5537104',  '12215X1ST']
        def cart = new ViewCartPage()
        cart.addMultipleItemsToCart(items, 1)
        cart.navigate()
        for (item in items) {
            def cartItemBox = new CartItemBox(item)
            assert cartItemBox.getQuantity() == 1
        }
    }
    List addSimilarItems() {
        def items = ['800X8', '800X830']
        def cart = new ViewCartPage()
        cart.addMultipleItemsToCart(items, 1)
        cart.navigate()
        def cartItemBox8 = new CartItemBox(items[0])
        def price8 = cartItemBox8.getPrice()

        def cartItemBox830 = new CartItemBox(items[1])
        def price830 = cartItemBox830.getPrice()

        log price8
        log price830
        assert price8
        assert price830
        assert price8 != price830
        return items
    }
    void testBasicFunctions() {
        def cart = new ViewCartPage()
        cart.navigate()
        cart.emptyCart()
        def item = '800X8'
        cart.addMultipleItemsToCart(['800X8', '800X830'])
        CartItemBox box = new CartItemBox(item)
        testGetDescription(box)
        testGetImageUrl(box)
        testGetUrl(box)
        testGetItemUom(box)
        testGetTotalPrice(box)
        testGetPrice(box)
        testGetPriceAsString(box)
        testGetPrice(box)
        testGetQuantity(box)
        testSetQuantity(box)
        testIncrementQuantity(box)
        testDecrementQuantity(box)
        // Needs login, don't want to add to this test until there are more verifications
        // testVerifySaveForLater(box)
        testRemoveItemFromCart(box)
        cart.emptyCart()
    }
    void testGetDescription(CartItemBox box) {
        assert box.getDescription()
    }
    void testGetImageUrl(CartItemBox box) {
        assert box.getImageUrl()
    }
    void testGetUrl(CartItemBox box) {
        assert box.getUrl()
    }
    void testGetItemUom(CartItemBox box) {
        assert box.getItemUom()
    }
    void testGetTotalPrice(CartItemBox box) {
        assert box.getTotalPrice()
    }
    void testGetPriceAsString(CartItemBox box) {
        assert box.getPriceAsString()
    }
    void testGetPrice(CartItemBox box) {
        assert box.getPrice()
    }
    void testGetQuantity(CartItemBox box) {
        assert box.getQuantity()
    }
    void testSetQuantity(CartItemBox box) {
        assert box.setQuantity(1)
    }
    void testIncrementQuantity(CartItemBox box) {
        def prevQ = box.getQuantity()
        box.incrementQuantity()
        def afterQ = box.getQuantity()
        assert afterQ == prevQ + 1
    }
    void testDecrementQuantity(CartItemBox box) {
        def prevQ = box.getQuantity()
        box.decrementQuantity()
        def afterQ = box.getQuantity()
        assert afterQ == prevQ - 1
    }
    void testRemoveItemFromCart(CartItemBox box) {
        assert box.removeItemFromCart()
        waitForPage()
        sleep(1000)
        assert !box.verifyItemInCart()
    }
    void testVerifySaveForLater(CartItemBox box) {
        assert box.verifySaveForLater()
    }
}

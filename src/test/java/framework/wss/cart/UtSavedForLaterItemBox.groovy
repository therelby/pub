package framework.wss.cart

import above.RunWeb
import wss.pages.cart.CartItemBox
import wss.pages.cart.ViewCartPage
import wss.pages.cart.savedforlater.SavedForLaterItemBox
import wss.user.UserType
import wss.user.userurllogin.UserUrlLogin

class UtSavedForLaterItemBox extends RunWeb {
    static void main(String[] args) {
        new UtSavedForLaterItemBox().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'kyilmaz',
                title   : 'UtSaveForLaterItemBox',
                PBI     : 0,
                product : 'wss',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])
        testLotDiscountQtyMap()
    }

    void testLotDiscountQtyMap() {
        new UserUrlLogin().loginNewUser(UserType.REGULAR_USER, 0)
        def itemNumber = '328VER49809'
        ViewCartPage cart = new ViewCartPage()
        cart.navigate()
        cart.emptyCart()
        cart.addItemToCart(itemNumber, 6)
        CartItemBox cb = new CartItemBox(itemNumber)
        cb.clickSaveForLater()
        assert SavedForLaterItemBox.verifyItemInSavedForLater(itemNumber)
        SavedForLaterItemBox sb = new SavedForLaterItemBox(itemNumber)
        assert sb.getQuantity() == 6
        assert sb.getSplitQuantityMap()
    }
}

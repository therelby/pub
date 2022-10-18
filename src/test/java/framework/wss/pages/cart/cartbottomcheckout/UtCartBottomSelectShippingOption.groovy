package framework.wss.pages.cart.cartbottomcheckout

import above.RunWeb
import wss.pages.cart.CartBottomCheckout
import wss.pages.cart.ViewCartPage
import wss.user.UserType
import wss.user.userurllogin.UserUrlLogin

class UtCartBottomSelectShippingOption extends RunWeb {
    static void main(String[] args) {
        new UtCartBottomSelectShippingOption().testExecute([
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

    int pbi = 0

    def test() {
        setup([
                author  : 'ikomarov',
                title   : 'Select shipping option by name | Cart',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'select shipping option by name  unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        UserUrlLogin userUrlLogin = new UserUrlLogin()
        ViewCartPage viewCartPage = new ViewCartPage()
        CartBottomCheckout cartBottomCheckout = new CartBottomCheckout()

        List items = []
        List shippingOptionNames = []

        userUrlLogin.loginNewUser(UserType.REGULAR_USER, 0)

         //"Ground", "Second Day", "Next Day"
        items = ["HP0085464600"]
        viewCartPage.addMultipleItemsToCart(items)

        waitForElementVisible(CartBottomCheckout.shippingOptionsXpath)
        waitForElementVisible(CartBottomCheckout.shipOptionTypeXpath)
        shippingOptionNames = cartBottomCheckout.getShippingOptionsData().collect { it?.name }

        for (option in shippingOptionNames) {
            assert cartBottomCheckout.clickShippingByName(option)
        }

        for (option in shippingOptionNames) {
            assert cartBottomCheckout.selectShippingAndVerifyByName(option)
        }

        //"Common Carrier", "Common Carrier W/ Liftgate"
        items = ["178A49RHC"]
        viewCartPage.addMultipleItemsToCart(items)

        waitForPage()
        waitForElementVisible(CartBottomCheckout.shippingOptionsXpath)
        waitForElementVisible(CartBottomCheckout.shipOptionTypeXpath)

        shippingOptionNames = cartBottomCheckout.getShippingOptionsData()?.collect { it?.name }

        for (option in shippingOptionNames) {
            assert cartBottomCheckout.clickShippingByName(option)
        }
        for (option in shippingOptionNames) {
            assert cartBottomCheckout.selectShippingAndVerifyByName(option)
        }

        tryLoad()
        for (option in shippingOptionNames) {
            assert !cartBottomCheckout.clickShippingByName(option)
        }

        for (option in shippingOptionNames) {
            assert !cartBottomCheckout.selectShippingAndVerifyByName(option)
        }
    }
}

package framework.wss.pages.cart.cartbottomcheckout

import above.RunWeb
import wss.pages.cart.CartBottomCheckout
import wss.pages.cart.ViewCartPage
import wss.user.UserType
import wss.user.userurllogin.UserUrlLogin

class UtCartBottomCheckoutShippingOptions extends RunWeb {

    static void main(String[] args) {
        new UtCartBottomCheckoutShippingOptions().testExecute([

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
        final int PBI = 598902
        // https://tfs.clarkinc.biz/DefaultCollection/Automation%20Projects/_sprints/backlog/Storefront%20Automation/Automation%20Projects/Automation%20Sprint%2035?workitem=598902
        setup([
                author  : 'vdiachuk',
                title   : 'Cart Bottom Checkout, Shipping Options Data unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' cart bottom checkout shipping options method data unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        UserUrlLogin userUrlLogin = new UserUrlLogin()
        ViewCartPage viewCartPage = new ViewCartPage()
        CartBottomCheckout cartBottomCheckout = new CartBottomCheckout()

        log userUrlLogin.loginNewUser(UserType.REGULAR_USER, PBI)

        viewCartPage.addMultipleItemsToCart(["131WC22541", "124SBB4", "124SQFBCN10"], 1)

        assert cartBottomCheckout.isBlockPresent()
        log waitForElement(CartBottomCheckout.shippingOptionsXpath)
        def optionsData = cartBottomCheckout.getShippingOptionsData()
        assert optionsData.any() { it['name'] == 'Ground' }
        assert optionsData.any() { it['discountedPrice']?.contains('$') }

        closeBrowser()

        log "--"
        log userUrlLogin.loginNewUser(UserType.REGULAR_USER, PBI)

        viewCartPage.addMultipleItemsToCart(["9091933101", "708VF40510", "271C32P1 "], 1)

        assert cartBottomCheckout.isBlockPresent()
        log waitForElement(CartBottomCheckout.shippingOptionsXpath)
        def optionsData2 = cartBottomCheckout.getShippingOptionsData()
        log ""+optionsData2

    }
}

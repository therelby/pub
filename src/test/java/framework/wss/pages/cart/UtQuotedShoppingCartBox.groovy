package framework.wss.pages.cart

import above.RunWeb
import all.Money
import wss.pages.account.YourQuotesPage
import wss.pages.cart.CartCouponCode
import wss.pages.cart.CartQuickCheckout
import wss.pages.cart.QuotedShoppingCartBox
import wss.pages.cart.ViewCartPage
import wss.user.UserType
import wss.user.userurllogin.UserUrlLogin

class UtQuotedShoppingCartBox extends RunWeb {

    static void main(String[] args) {
        new UtQuotedShoppingCartBox().testExecute([

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
        final int PBI = 617648
        setup([
                author  : 'vdiachuk',
                title   : 'Cart Quoted Shopping Cart Box unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' cart quoted cart quote box unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        //
        // PreSteps
        //
        tryLoad()
        UserUrlLogin userUrlLogin = new UserUrlLogin()
        if (!userUrlLogin.loginNewUser(UserType.REGULAR_USER, PBI)) {
            return
        }
        ViewCartPage viewCartPage = new ViewCartPage()
        def itemNumbers = ["124BLSMA510W", "566S8952", "124BLSMAV4TR"]
        viewCartPage.addMultipleItemsToCart(itemNumbers, 2)
        CartCouponCode cartCouponCode = new CartCouponCode()
        log 'Coupon Message: ' + cartCouponCode.addCouponAndGetMessage(CartCouponCode.quoteCoupon)
        sleep(2000)
        YourQuotesPage yourQuotesPage = new YourQuotesPage()
        log "Navigate your quotes: " + yourQuotesPage.navigate()
        def quotesData = yourQuotesPage.getCurrentQuotes()
        log " " + quotesData
        log quotesData?.getAt(0)?.getAt('cartLink')
        def cartId = getParamValueFromUrl(quotesData?.getAt(0)?.getAt('cartLink'), "quote")
        log "Click firs quote: " + yourQuotesPage.addToCartQuoteAndVerifyCartByIndex(0)

        log "--"
        //
        // Test
        //
        log "cartId: " + cartId
        QuotedShoppingCartBox quotedShoppingCartBox = new QuotedShoppingCartBox(cartId)
        log quotedShoppingCartBox.verifyBoxPresent()
        assert verifyElement(quotedShoppingCartBox.imageXpath)
        assert verifyElement(quotedShoppingCartBox.titleXpath)
        assert verifyElement(quotedShoppingCartBox.subTitleXpath)
        assert verifyElement(quotedShoppingCartBox.plusRibbonXpath)
        assert verifyElement(quotedShoppingCartBox.innerItemXpath)
        def innerItemsData = quotedShoppingCartBox.getInnerItemsData()

        // log innerItemsData
        assert innerItemsData.size() == 3
        assert innerItemsData.getAt(0)?.getAt('title') == "American Metalcraft BLSMA510WT Securit All-Purpose Small Tip White Chalk Marker - 2/Pack"
        assert quotedShoppingCartBox.deleteQuotedBoxFromCartAndVerify()
    }
}

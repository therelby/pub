package framework.wss.pages.cart.quickcheckout

import above.RunWeb
import all.Money
import wss.pages.cart.CartItemBox
import wss.pages.cart.CartQuickCheckout
import wss.pages.cart.CartSuggestedItemBox
import wss.pages.cart.ViewCartPage
import wss.user.userurllogin.UserUrlLogin

class UtCartQuickCheckout extends RunWeb {

    static void main(String[] args) {
        new UtCartQuickCheckout().testExecute([

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
                title   : 'Cart Quick Checkout unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' cart item checkout quick unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        tryLoad()
        UserUrlLogin userUrlLogin = new UserUrlLogin()
        userUrlLogin.loginAs('29575861')

        ViewCartPage viewCartPage = new ViewCartPage()
        viewCartPage.emptyCart()
        tryLoad()
        viewCartPage.addItemToCart('600LEGALV360', 1)
        CartQuickCheckout cartQuickCheckout = new CartQuickCheckout()
        log "--"
        cartQuickCheckout.waitForOrderNowButtonEnabled(15 )
        assert cartQuickCheckout.isPresent()

        assert cartQuickCheckout.getActiveBillingAddress().size() > 3

        def totalSum = cartQuickCheckout.getTotalSum()
        log totalSum
        assert totalSum > 0
        log "-- shippingTypesData"

        def shippingTypesData = cartQuickCheckout.getShippingTypes()
        log shippingTypesData
        assert shippingTypesData.size() > 0
        assert shippingTypesData.any() { it['isSelected'] }

        log "--"
        def shippingAddressesData = cartQuickCheckout.getShippingAddresses()
        assert shippingAddressesData.size() > 1
        assert shippingAddressesData.any() { it['isSelected'] }
        log shippingAddressesData

        log "-- Wait For Button"
        assert cartQuickCheckout.waitForOrderNowButtonEnabled() == true

        log "-- Credit Cards Data"
        assert cartQuickCheckout.getCreditCards().size() > 0

        assert cartQuickCheckout.waitForOrderNowButtonEnabled()
        log "-- TOOLTIP data"
        def toolipdata = cartQuickCheckout.getTotalTooltipData()
        assert toolipdata.size()>1
        log toolipdata

        log "-- Setting Address to secodn"
        selectByValue(CartQuickCheckout.shippingAddressSelectXpath, '35573108')
        log "-- Wait For Button"
        def timeStart = System.currentTimeMillis()
        assert cartQuickCheckout.waitForOrderNowButtonEnabled(30) == false
        assert System.currentTimeMillis() - timeStart > 30000

        log "--"
        tryLoad()
        assert cartQuickCheckout.isPresent() == false
        assert cartQuickCheckout.getActiveBillingAddress() == ''
        assert cartQuickCheckout.getTotalSum() == new Money(0)

    }
}

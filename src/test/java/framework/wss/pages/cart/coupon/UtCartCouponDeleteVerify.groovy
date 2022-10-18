package framework.wss.pages.cart.coupon

import above.RunWeb
import wss.pages.cart.CartCouponCode
import wss.pages.cart.ViewCartPage
import wss.pages.element.modal.LoggedInAsModal
import wss.user.UserType
import wss.user.userurllogin.UserUrlLogin

class UtCartCouponDeleteVerify extends RunWeb{

    static void main(String[] args) {
        new UtCartCouponDeleteVerify().testExecute([

                browser      : 'chrome',
                remoteBrowser: true,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])

    }

    def test() {
        final int PBI = 581378

        setup([
                author  : 'vdiachuk',
                title   : 'Cart Coupon Block Delete && Verify unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' cart coupon add delete mass unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        UserUrlLogin userUrlLogin = new UserUrlLogin()
        ViewCartPage viewCartPage = new ViewCartPage()
        LoggedInAsModal loggedInAsModal = new LoggedInAsModal()
        CartCouponCode cartCouponCode = new CartCouponCode()
        userUrlLogin.loginNewUser(UserType.REGULAR_USER, PBI)
        viewCartPage.addItemToCart('59166092')

        assert cartCouponCode.isBlockPresent()
       for (i in 0..19) {
           log "-- $i Attempt"
            loggedInAsModal.deleteModal()
            assert cartCouponCode.addCouponAndVerifyApplied('XBCXQI')
            refresh()
            sleep(1000)
            log cartCouponCode.getCouponData()
            loggedInAsModal.deleteModal()
            assert cartCouponCode.deleteCouponAndVerify('XBCXQI')
        }

    }
}

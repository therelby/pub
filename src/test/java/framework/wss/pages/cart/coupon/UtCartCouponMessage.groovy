package framework.wss.pages.cart.coupon

import above.RunWeb
import wss.pages.cart.CartCouponCode
import wss.pages.cart.ViewCartPage
import wss.user.UserType
import wss.user.userurllogin.UserUrlLogin

class UtCartCouponMessage extends RunWeb {

    static void main(String[] args) {
        new UtCartCouponMessage().testExecute([

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
        final int PBI = 581378

        setup([
                author  : 'vdiachuk',
                title   : 'Cart Coupon Block Messages unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' cart coupon message error success unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        UserUrlLogin userUrlLogin = new UserUrlLogin()
        ViewCartPage viewCartPage = new ViewCartPage()
        CartCouponCode cartCouponCode = new CartCouponCode()
        userUrlLogin.loginNewUser(UserType.REGULAR_USER, PBI)
        viewCartPage.addItemToCart('59166092')

        assert   cartCouponCode.isBlockPresent()
        def messageErrorData =  cartCouponCode.addCouponAndGetMessage('FAKECODE')
        assert messageErrorData['type'] == "error"
        assert messageErrorData['text'] == "Error: discount code entered is invalid or expired"
        def messageData =  cartCouponCode.addCouponAndGetMessage('XBCXQI')
        assert messageData['type'] == "success"
        assert messageData['text'] == "Discount applied"


        assert cartCouponCode.addCouponAndVerifyApplied("SPET2N")

    }

/* ex. of codes on 59166092  167400CHVCBK
   //todo  XBCXQI: #11: $10 off $100 -Check no coupon after applied in the box
   //todo  XBCXQI: #11: $10 off $100 -Check deleting Coupon - empty cart?:)

    ×YME1NQ: #2661: $10 off $100*/
}

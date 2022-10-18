package framework.wss.pages.cart.coupon

import above.RunWeb
import wss.pages.cart.CartCouponCode
import wss.pages.cart.ViewCartPage
import wss.user.UserType
import wss.user.userurllogin.UserUrlLogin

class UtCartCoupon extends RunWeb {

    static void main(String[] args) {
        new UtCartCoupon().testExecute([

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
                title   : 'Cart Coupon Block unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' cart coupon unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        UserUrlLogin userUrlLogin = new UserUrlLogin()
        ViewCartPage viewCartPage = new ViewCartPage()
        CartCouponCode cartCouponCode = new CartCouponCode()
        userUrlLogin.loginNewUser(UserType.REGULAR_USER, PBI)
        viewCartPage.addItemToCart('59166092')

        assert cartCouponCode.isBlockPresent()
        assert cartCouponCode.addCoupon('XBCXQI')
        assert cartCouponCode.addCoupon('YME1NQ')
        sleep(3000)
        def couponData = cartCouponCode.getCouponData()
        log couponData
        assert couponData.size() == 2

        log "--"
        sleep(2000)
        log cartCouponCode.deleteCouponByCode('XBCXQI')
        sleep(1000)
        log cartCouponCode.getCouponData()
//        log "=="
//        log cartCouponCode.deleteCouponByCode('YME1NQ')
//        sleep(1000)
//        log couponData

        // String itemWithSpaces = "872cviwst   28".toUpperCase()

    }

/* ex. of codes on 59166092  167400CHVCBK
   //todo  XBCXQI: #11: $10 off $100 -Check no coupon after applied in the box
   //todo  XBCXQI: #11: $10 off $100 -Check deleting Coupon - empty cart?:)

    ×YME1NQ: #2661: $10 off $100*/
}

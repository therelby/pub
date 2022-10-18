package framework.wss.pages.cart.coupon

import above.RunWeb
import wss.pages.cart.CartCouponCode

class UtCouponMessageColor extends RunWeb {

    static void main(String[] args) {
        new UtCouponMessageColor().testExecute([

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
        final int PBI = 0

        setup([
                author  : 'vdiachuk',
                title   : 'Cart Coupon Message Color unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' cart coupon message background color unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        CartCouponCode cartCouponCode = new CartCouponCode()
        //  log CartCouponCode.SUCCESS_COLORS.contains('rgb(223, 240, 216)')
        assert cartCouponCode.isErrorMessageColor('rgb(248, 208, 200)')
        assert !cartCouponCode.isSuccessMessageColor('rgb(248, 208, 200)')

        assert !cartCouponCode.isErrorMessageColor('rgba(223, 240, 216, 1)')
        assert cartCouponCode.isSuccessMessageColor('rgba(223, 240, 216, 1)')
        log cartCouponCode.isErrorMessageColor("rgba(248, 208, 200, 1)")
    }

}
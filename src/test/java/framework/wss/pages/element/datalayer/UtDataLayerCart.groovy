package framework.wss.pages.element.datalayer

import above.RunWeb
import wss.checkout.coupon.CouponUtil
import wss.pages.cart.CartCouponCode
import wss.pages.cart.CartItemBox
import wss.pages.cart.ViewCartPage
import wss.pages.element.DataLayerElement

class UtDataLayerCart extends RunWeb {

    static void main(String[] args) {
        new UtDataLayerCart().testExecute([

                browser      : 'chrome',// 'remotechrome-lt',//'chrome',//'edge',//'chrome',//'safari'
                remoteBrowser: false,//true,//false,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                //   parallelThreads: 1,
                //  runType: 'Regular' ,
                //  browserVersionOffset: -1
        ])
    }

    def test() {

        setup('vdiachuk', 'Data Layer on PDP Element unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: datalayer data layer unit test element pdp  ',
                 'PBI: 626603',
                 'logLevel:info'])

        tryLoad()

        ViewCartPage viewCartPage = new ViewCartPage()
        viewCartPage.addMultipleItemsToCart(['100AHSC1', '100CRAY3PKCS', '100BKTWINEBL '], 1)

        viewCartPage.navigate()
        CartItemBox cartItemBox0 = new CartItemBox('100AHSC1')
        log "REMOVE: " + cartItemBox0.removeItemFromCart()


        //
        // COUPOn
        //
        def validCoupon = CouponUtil.getCouponWithCodeForSubtotal()

        sleep(4000)
        CartCouponCode cartCouponCode = new CartCouponCode()
        Boolean couponApplyResult = cartCouponCode.addCouponAndVerifyApplied(validCoupon)
        assert couponApplyResult
        log "Coupon [$validCoupon] applied: " + couponApplyResult


        // wait until events worked and stored
        sleep(3000)
        waitForPage()

        DataLayerElement dataLayerElement = new DataLayerElement()

        def dataLayer = dataLayerElement.getDataLayerEventsData()
        dataLayer.each { println it }

        assert dataLayer.any() { it?.keySet()?.contains('ecommerce') }

        def dataEcommerce = dataLayerElement.getEcommerceEventsData()
        log "--"
        //   log dataEcommerce
        def couponsAppliedEvent = dataEcommerce.findAll() { it?.ecommerce?.getAt('coupon') }
        assert couponsAppliedEvent.size() > 0
        log couponsAppliedEvent

        assert couponsAppliedEvent?.collect() { it?.ecommerce?.coupon }?.contains(validCoupon)
    }
}
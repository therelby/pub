package framework.wss.pages.cart.coupon

import above.RunWeb
import all.Money
import wss.pages.cart.CartBottomCheckout
import wss.pages.cart.ViewCartPage
import wss.user.userurllogin.UserUrlLogin
import wss.checkout.coupon.CouponUtil
import wss.pages.cart.CartCouponCode
import wss.pages.cart.CartTopCheckout

class UtCartCouponSubtotal extends RunWeb{

    def itemNumberForTesting = ['177FF300L', '$999.00']

    static void main(String[] args) {
        new UtCartCouponSubtotal().testExecute([

                browser      : 'chrome',
                remoteBrowser: false,
//                browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])

    }

    def test() {
        final int PBI = 586777

        setup([
                author  : 'mwestacott',
                title   : 'Cart Coupon Subtotal Discount unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' cart coupon subtotal discount unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        runningTestsForSubtotalDiscountCode("guest", "", itemNumberForTesting, false)
        runningTestsForSubtotalDiscountCode("guest", "", itemNumberForTesting, true)
        runningTestsForSubtotalDiscountCode("regular", "8613901", itemNumberForTesting, false)
        runningTestsForSubtotalDiscountCode("regular", "8613901", itemNumberForTesting, true)

        closeBrowser()

    }

    void runningTestsForSubtotalDiscountCode(String userTypeUnderTest, String userIndexUnderTest, def itemNumberUnderTest, boolean isCouponAmountGreaterThanSubtotal){
        UserUrlLogin userUrlLogin = new UserUrlLogin()
        assert (userTypeUnderTest == 'guest') ? userUrlLogin.logOut() : userUrlLogin.loginAs(userIndexUnderTest)

        ViewCartPage viewCartPage = new ViewCartPage()
        assert viewCartPage.navigate()

        CartCouponCode cartCouponCode = new CartCouponCode()
        assert !verifyElement(cartCouponCode.appliedCouponsBlock)
        assert viewCartPage.emptyCart()

        String itemNumber = itemNumberForTesting[0]
        assert viewCartPage.addItemToCart(itemNumber)
        assert applyCouponCodeAndVerifyApplied(userTypeUnderTest, itemNumberUnderTest, isCouponAmountGreaterThanSubtotal)

        assert verifyElement(CartBottomCheckout.subtotalSavingsRowXpath)
        assert getTextSafe(CartBottomCheckout.subtotalSavingsLabelXpath) == CartBottomCheckout.subtotalSavingsLabelText

        Money expectedSavings = new Money(itemNumberForTesting[1])
        String actualSavingsBase = getTextSafe(CartBottomCheckout.subtotalSavingsSumXpath)
        Money actualSavings = new Money(actualSavingsBase)

        assert actualSavings == expectedSavings

        assert verifyElement(CartBottomCheckout.subtotalSavingsUnusedCreditWarningXpath) == isCouponAmountGreaterThanSubtotal
        if(isCouponAmountGreaterThanSubtotal){
            assert getTextSafe(CartBottomCheckout.subtotalSavingsUnusedCreditWarningXpath) == CartBottomCheckout.subtotalSavingsUnusedCreditWarningText
        }

        assert verifyElement(CartTopCheckout.cartDiscountAppliedRow)

        String expectedCartDiscountAppliedLabelText = CartTopCheckout.cartDiscountLabelText
        String actualCartDiscountAppliedLabelText = getTextSafe(CartTopCheckout.cartDiscountLabelXpath)

        assert actualCartDiscountAppliedLabelText == expectedCartDiscountAppliedLabelText

        String actualCartDiscountAppliedSubtotalTopCheckoutBase = getTextSafe(CartTopCheckout.cartDiscountDiscountXpath)
        Money actualCartDiscountAppliedSubtotalTopCheckout = new Money(actualCartDiscountAppliedSubtotalTopCheckoutBase)

        assert actualCartDiscountAppliedSubtotalTopCheckout == expectedSavings

        String couponCode = getCouponCode(userTypeUnderTest, isCouponAmountGreaterThanSubtotal)
        assert cartCouponCode.deleteCouponByCode(couponCode)

    }

    private String getCouponCode(String userTypeUnderTest, boolean isCouponAmountGreaterThanSubtotal){
        String userTypeUnderTestAbbreviation = (userTypeUnderTest == 'platinumPlus') ? "PP" : userTypeUnderTest.substring(0, 1).capitalize()
        String browserTypeAbbreviation = testBrowser.minus('remote').substring(0, 1).capitalize()
        String browserOffsetAbsoluteValue = testBrowserVersionOffset.abs()
        String couponCode = "SUBDSC"+ (isCouponAmountGreaterThanSubtotal ? "G" : "L") + userTypeUnderTestAbbreviation + browserTypeAbbreviation + browserOffsetAbsoluteValue
        return couponCode
    }

    boolean applyCouponCodeAndVerifyApplied(String userTypeUnderTest, def itemNumberUnderTest, boolean isCouponAmountGreaterThanSubtotal){
        String couponCode = getCouponCode(userTypeUnderTest, isCouponAmountGreaterThanSubtotal)

        String itemNumber = itemNumberUnderTest[0]
        def mainHeading = "Subtotal Deduction $userTypeUnderTest $itemNumber: ${isCouponAmountGreaterThanSubtotal ? "Greater than subtotal" : "Less than subtotal"}"
        def subtotalDiscountAmount = new Money(itemNumberUnderTest[1])
        if(isCouponAmountGreaterThanSubtotal){
            subtotalDiscountAmount = subtotalDiscountAmount.plus(0.01)
        }

        Integer saveCoupon = CouponUtil.saveSubtotalDiscountCoupon(couponCode, mainHeading, itemNumber, subtotalDiscountAmount)
        sleep(3000)
        CartCouponCode cartCouponCode = new CartCouponCode()

        log("Coupon code created is $couponCode.")

        boolean wasCouponCodeApplied = false

        for(int i = 1; i <= 5; i++) {
            wasCouponCodeApplied = cartCouponCode.addCouponAndVerifyApplied(couponCode)
            if(wasCouponCodeApplied){
                log("Coupon code $couponCode applied on appliance $i.")
                break
            }
        }

        return wasCouponCodeApplied
    }
}

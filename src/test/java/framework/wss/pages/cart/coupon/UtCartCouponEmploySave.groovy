package framework.wss.pages.cart.coupon

import above.RunWeb
import all.Money
import wss.item.ItemUtil
import wss.pages.cart.CartCouponCode
import wss.pages.cart.CartItemBox
import wss.pages.cart.ViewCartPage

class UtCartCouponEmploySave extends RunWeb{

    def loneProductStandardProduct = ['538013B16021', 1, 367.641]
    def loneProductNoP7Product = ['265HJ5311318', 1, 0]

    def loneProductLotDiscountNone = ['470M18604PU', 1, 9.8910]
    def loneProductLotDiscountApplied = ['470M18604PU', 6, 9.8910]

    def loneSuffixProductStardardProduct = ['415DCM751BWH120', 1, 8540.10]
    def loneSuffixProductMapProduct = ['131ITM272DLX120/1', 1, 9147.006]
    def loneSuffixProductQuantityDiscountProductFirstTier = ['128YL2532   COMBO240', 1, 91.23]
    def loneSuffixProductQuantityDiscountProductSecondTier = ['128YL2532   COMBO240', 3, 91.23]

    def virtualGroupingProductStandardProduct = ['413GRBW48TK', 1, 1223.145]

    static void main(String[] args) {
        new UtCartCouponEmploySave().testExecute([

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
        final int PBI = 577592

        setup([
                author  : 'mwestacott',
                title   : 'Cart Coupon Employ Save unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' cart coupon employ save unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        assert ItemUtil.getProductsEmploySave(5, 'Lone', false).size() == 5
        assert ItemUtil.getProductsEmploySave(5, 'Lone Suffix', false).size() == 0
        assert ItemUtil.getProductsEmploySave(5, 'Virtual Grouping', false).size() == 0

        assert ItemUtil.getProductsEmploySave(5, 'Lone', true).size() == 5
        assert ItemUtil.getProductsEmploySave(5, 'Lone Suffix', true).size() == 5
        assert ItemUtil.getProductsEmploySave(5, 'Virtual Grouping', true).size() == 5

        assert openAndTryLoad('cart')

        testingProduct(loneProductStandardProduct)
        testingProduct(loneProductNoP7Product)

        testingProduct(loneProductLotDiscountNone)
        testingProduct(loneProductLotDiscountApplied)

        testingProduct(loneSuffixProductStardardProduct)
        testingProduct(loneSuffixProductMapProduct)
        testingProduct(loneSuffixProductQuantityDiscountProductFirstTier)
        testingProduct(loneSuffixProductQuantityDiscountProductSecondTier)

        testingProduct(virtualGroupingProductStandardProduct)

        closeBrowser()
    }

    void testingProduct(def product){
        String itemNumber = product[0]
        int itemQuantity = product[1]
        Money itemP7 = new Money(product[2])

        ViewCartPage viewCartPage = new ViewCartPage()
        CartCouponCode cartCouponCode = new CartCouponCode()

        boolean wasAbleToEmptyCart = viewCartPage.emptyCart()
        assert wasAbleToEmptyCart
        assert viewCartPage.addItemToCart(itemNumber, itemQuantity)

        CartItemBox cartItemBox = new CartItemBox(itemNumber)
        def currentItemPrice = cartItemBox.getTotalPrice()

        if(!cartCouponCode.addEmploySaveDiscountCode()){
            if(!handlingCouponCodeApplicationFailure()){
                log("Failed to apply coupon code after five refreshes. Aborting the test.")
                return
            }
        }

        boolean isProductP7Zero = itemP7 == 0

        Money expectedItemPrice = isProductP7Zero ? currentItemPrice : itemP7.multiply(itemQuantity)
        Money actualItemPrice = cartItemBox.getTotalPrice()

        boolean doPricesMatch = (actualItemPrice == expectedItemPrice)
        assert doPricesMatch

        boolean deleteEmploySaveAndVerify = cartCouponCode.deleteCouponAndVerify("EMPLOYSAVE")
        assert deleteEmploySaveAndVerify
    }

    boolean handlingCouponCodeApplicationFailure(){
        CartCouponCode cartCouponCode = new CartCouponCode()
        boolean didRefreshCauseCouponCodeToAppear = false
        for(int i = 1; i <= 5; i++) {
            if(!refresh()){
                return false
            }
            didRefreshCauseCouponCodeToAppear = (cartCouponCode.getCouponData().size() == 1)
            if(didRefreshCauseCouponCodeToAppear){
                log("EMPLOYSAVE was applied on loop $i")
                break
            }
        }
        return didRefreshCauseCouponCodeToAppear
    }
}

package framework.wss.pages.cart.map

import above.RunWeb
import all.Money
import all.util.StringUtil
import wss.checkout.coupon.CouponUtil
import wss.pages.cart.CartCouponCode
import wss.pages.cart.CartItemBox
import wss.pages.cart.ViewCartPage
import wss.pages.productdetail.PDPMap
import wss.pages.productdetail.PDPPriceTile
import wss.pages.productdetail.PDPage
import wsstest.cart.map.HpMapCartTesting
import wsstest.cart.map.HpMapCartTestingQueries
import wss.item.ItemUtil
import wss.api.user.UserApiBase

class UtMapCartProductsWithCouponCodes extends RunWeb{

    static void main(String[] args) {
        new UtMapCartProductsWithCouponCodes().testExecute([

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

    static String mapStyleANoOverrides = '369PRG50TN'
    static String mapStyleENoOverrides = '999104421'
    static String mapStyleINoOverrides = '305CT1221'
    static String mapStyleJNoOverrides = '882UPT328L'
    static String mapStyleKNoOverrides = '629ES700'
    static String mapStylePNoOverrides = '649CL50ND'
    static String mapStyleQNoOverrides = '345ECGD50'
    static String mapStyleTNoOverrides = '987SM300LP'
    static String mapStyleWNoOverrides = '882RAC372'
    static String mapStyleXNoOverrides = '787BPM40ECPP'

    static String mapStyleAMapOverride = '4611830NK3'
    static String mapStyleEMapOverride = '415KM45M30SF'
    static String mapStyleIMapOverride = '480413BS'
    static String mapStyleJMapOverride = '46127UPK3'
    static String mapStyleKMapOverride = '383US24VG91'
    static String mapStyleMMapOverride = '467EFXSX'
    static String mapStylePMapOverride = '185SE27BHLA'
    static String mapStyleQMapOverride = '422MIRMIST'
    static String mapStyleTMapOverride = '253CGV'
    static String mapStyleWMapOverride = '740CO5324RCH'
    static String mapStyleXMapOverride = '386SS324806'

    static String mapStyleEPlatinumOverride = '109WB2GL'
    static String mapStyleIPlatinumOverride = '76534SAB2N'
    static String mapStyleJPlatinumOverride = '10993354'
    static String mapStyleKPlatinumOverride = '109SAG3011'
    static String mapStylePPlatinumOverride = '109DI130'
    static String mapStyleTPlatinumOverride = '109BS304'
    static String mapStyleWPlatinumOverride = '109PRB1953C'
    static String mapStyleXPlatinumOverride = '109PRWC2418D'

    static String mapStyleABothOverrides = '372GD36GFFN'
    static String mapStyleEBothOverrides = '369SCFHD46TL'
    static String mapStyleIBothOverrides = '9361600216FL'
    static String mapStyleJBothOverrides = '372GTOG488L'
    static String mapStyleKBothOverrides = '369PH155DL'
    static String mapStylePBothOverrides = '372S686E'
    static String mapStyleQBothOverrides = '385CDE37J'
    static String mapStyleTBothOverrides = '372UT60G06CL'
    static String mapStyleWBothOverrides = '372E23656GMC'
    static String mapStyleXBothOverrides = '372E2111D'

    static String mapStyleTNoOverridesQuantityDiscount = '3532018142'

    static String mapStyleJMapOverrideQuantityDiscount = '461DC56EC'
    static String mapStyleQMapOverrideQuantityDiscount = '239BH425'
    static String mapStyleTMapOverrideQuantityDiscount = '136XLSBECOAH'
    static String mapStyleXMapOverrideQuantityDiscount = '166LPC40L'

    static String mapStyleQBothOverridesQuantityDiscount = '5519136'

    static String[] testProducts =
            [
                    mapStyleANoOverrides
//                    , mapStyleENoOverrides
                    , mapStyleINoOverrides
                    , mapStyleJNoOverrides
                    , mapStyleKNoOverrides
                    , mapStylePNoOverrides
                    , mapStyleQNoOverrides
                    , mapStyleTNoOverrides
                    , mapStyleWNoOverrides
                    , mapStyleXNoOverrides
                    , mapStyleAMapOverride
                    , mapStyleEMapOverride
                    , mapStyleIMapOverride
                    , mapStyleJMapOverride
                    , mapStyleKMapOverride
                    , mapStyleMMapOverride
                    , mapStylePMapOverride
                    , mapStyleQMapOverride
                    , mapStyleTMapOverride
                    , mapStyleWMapOverride
                    , mapStyleXMapOverride
                    , mapStyleEPlatinumOverride
                    , mapStyleIPlatinumOverride
                    , mapStyleJPlatinumOverride
                    , mapStyleKPlatinumOverride
                    , mapStylePPlatinumOverride
                    , mapStyleTPlatinumOverride
                    , mapStyleWPlatinumOverride
                    , mapStyleXPlatinumOverride
                    , mapStyleABothOverrides
                    , mapStyleEBothOverrides
                    , mapStyleIBothOverrides
                    , mapStyleJBothOverrides
                    , mapStyleKBothOverrides
                    , mapStylePBothOverrides
                    , mapStyleQBothOverrides
                    , mapStyleTBothOverrides
                    , mapStyleWBothOverrides
                    , mapStyleXBothOverrides
                    , mapStyleTNoOverridesQuantityDiscount
                    , mapStyleJMapOverrideQuantityDiscount
                    , mapStyleQMapOverrideQuantityDiscount
                    , mapStyleTMapOverrideQuantityDiscount
                    , mapStyleXMapOverrideQuantityDiscount
                    , mapStyleQBothOverridesQuantityDiscount
            ]

    def test() {

        setup('mwestacott', 'Cart - MAP - Products with coupon codes | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart map products with coupon codes',
                 "tfsTcIds:0", 'logLevel:info'])

        assert openAndTryLoad('hp')
        for(testProduct in testProducts){
            def itemNumberForTesting = ItemUtil.getIndividualMapProduct(testProduct)
            runTest(itemNumberForTesting)
        }
    }

    void runTest(def itemNumberForTesting){
        String testProduct = itemNumberForTesting['Item_Number']
        String mapStyle = itemNumberForTesting['Call_For_Pricing']

        ViewCartPage viewCartPage = new ViewCartPage()
        PDPage pdPage = new PDPage()
        PDPPriceTile pdpPriceTile = new PDPPriceTile()
        CouponUtil couponUtil = new CouponUtil()

        assert viewCartPage.emptyCart(true)
        assert pdPage.navigateToPDPWithItemNumber(testProduct)

        String emailAddressFieldXpath = PDPMap.getXpathForMAPAspect(mapStyle, "Email", "emailAddressField")
        String emailAddressSubmitButtonXpath = PDPMap.getXpathForMAPAspect(mapStyle, "Email", "emailAddressSubmitButton")

        String emailInput = UserApiBase.getUniqueId() + "@webstaurantstore.com"

        assert setText(emailAddressFieldXpath, emailInput)
        assert jsClick(emailAddressSubmitButtonXpath)

        sleep(5000)

        String mapDiscountCode = couponUtil.getMapDiscountCode(emailInput)
        assert mapDiscountCode.length() > 0

        assert pdpPriceTile.addToCart(1, false)

        assert viewCartPage.navigate()
        assert viewCartPage.verifyItemInCart(testProduct)

        boolean ableToApplyCouponCode = new CartCouponCode().addCouponAndVerifyApplied(mapDiscountCode)
        if(!ableToApplyCouponCode){
            refresh()
            assert waitForElement(CartCouponCode.eachAppliedCouponBox)
        }
        assert new CartCouponCode().addCouponAndVerifyApplied(mapDiscountCode)
        CartItemBox cartItemBox = new CartItemBox(testProduct)

        Money expectedPrice = new Money(itemNumberForTesting["MAP_Price"])
        Money actualPrice = cartItemBox.getPrice()

        assert (actualPrice == expectedPrice)

    }
}

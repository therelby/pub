package framework.wss.pages.productdetail.stickyheader

import above.RunWeb
import wss.pages.productdetail.PDPStickyHeader
import wss.pages.productdetail.PDPage

class UtPDPStickyHeaderPlatPlusOverride extends RunWeb{

    String nonQuantityDiscountProductPageNoMapOverrideUrl = "https://www.dev.webstaurantstore.com/delfield-cab4-500et-even-temp-mobile-enclosed-four-stack-heated-dish-dispenser-warmer-for-3-to-5-dishes-208v/305CAB4500E.html"
    String nonQuantityDiscountProductPageNoMapOverridePriceAndUom = '$3,302.17/Each'

    String nonQuantityDiscountProductPageMapOverrideUrl = "https://www.dev.webstaurantstore.com/anets-gpc-14d-liquid-propane-pasta-cooker-with-digital-controls-111-000-btu/119GPC14DLP.html"
    String nonQuantityDiscountProductPageMapOverridePriceAndUom = '$4,624.00/Each'

    String quantityDiscountProductPageNoMapOverrideUrl = "https://www.dev.webstaurantstore.com/cambro-gbd181412110-insulated-black-jumbo-delivery-gobag-18-x-14-x-12/214GBD1814BK.html"
    String quantityDiscountProductPageNoMapOverridePriceAndUom = '$45.10/Each'

    String quantityDiscountProductPageMapOverrideUrl = "https://www.dev.webstaurantstore.com/grindmaster-gtd3-fot-3-narrow-gallon-stainless-steel-iced-tea-dispenser-with-black-valve/385GTD3FOT.html"
    String quantityDiscountProductPageMapOverridePriceAndUom = '$80.02/Each'

    String nonQuantityDiscountProductPageNoMapOverrideMapLUrl = "https://www.dev.webstaurantstore.com/waring-wsg60-3-cup-commercial-spice-grinder-120v/929WSG60.html"
    String nonQuantityDiscountProductPageNoMapOverrideMapLPriceAndUom = '$229.99/Each'

    String nonQuantityDiscountProductPageMapOverrideMapLUrl = "https://www.dev.webstaurantstore.com/nemco-6151-36-d-36-dual-infrared-strip-warmer-with-infinite-controls-208v-1700w/591615136DB.html"
    String nonQuantityDiscountProductPageMapOverrideMapLPriceAndUom = '$454.49/Each'

    String quantityDiscountProductPageMapOverrideMapLUrl = "https://www.dev.webstaurantstore.com/hamilton-beach-iron-120v-1700w-gray-ret/41213102.html"
    String quantityDiscountProductPageMapOverrideMapLPriceAndUom = '$95.90/Each'

    def test() {

        setup('mwestacott', 'PDPage Product Detail Page Sticky Header Plat Plus Override unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test pdpage pdp product detail page sticky header plat plus override',
                 "PBI:0",
                 'logLevel:info'])

        testsForIndividualUser(nonQuantityDiscountProductPageNoMapOverrideUrl, nonQuantityDiscountProductPageNoMapOverridePriceAndUom, false, false)
        testsForIndividualUser(nonQuantityDiscountProductPageNoMapOverrideMapLUrl, nonQuantityDiscountProductPageNoMapOverrideMapLPriceAndUom, false, false)

        testsForIndividualUser(nonQuantityDiscountProductPageMapOverrideUrl, nonQuantityDiscountProductPageMapOverridePriceAndUom, false, true)
        testsForIndividualUser(nonQuantityDiscountProductPageMapOverrideMapLUrl, nonQuantityDiscountProductPageMapOverrideMapLPriceAndUom, false, true)

        testsForIndividualUser(quantityDiscountProductPageNoMapOverrideUrl, quantityDiscountProductPageNoMapOverridePriceAndUom, true, false)

        testsForIndividualUser(quantityDiscountProductPageMapOverrideUrl, quantityDiscountProductPageMapOverridePriceAndUom, true, true)
        testsForIndividualUser(quantityDiscountProductPageMapOverrideMapLUrl, quantityDiscountProductPageMapOverrideMapLPriceAndUom, true, true)
        closeBrowser()
    }

    private void testsForIndividualUser(String productPageUrl, String priceAndUom, Boolean hasQuantityDiscount, Boolean hasMapOverride){
        testingPlatPlusOverride(productPageUrl, priceAndUom, "guest", hasQuantityDiscount, hasMapOverride)
        testingPlatPlusOverride(productPageUrl, priceAndUom, "regular", hasQuantityDiscount, hasMapOverride, 5732051, false, "Ryan Gabers")
        testingPlatPlusOverride(productPageUrl, priceAndUom, "regular", hasQuantityDiscount, hasMapOverride, 8613901, true, "Michael Westacott", "WSS")
        testingPlatPlusOverride(productPageUrl, priceAndUom, "webplus", hasQuantityDiscount, hasMapOverride, 13002743, false, "Joey")
        testingPlatPlusOverride(productPageUrl, priceAndUom, "webplus", hasQuantityDiscount, hasMapOverride, 25909953, true, "Kylah", "Webstaurant Store")
        testingPlatPlusOverride(productPageUrl, priceAndUom, "platinum", hasQuantityDiscount, hasMapOverride, 17868837, false, "Max Condren")
        testingPlatPlusOverride(productPageUrl, priceAndUom, "platinum", hasQuantityDiscount, hasMapOverride, 25035591, true, "Jacci Jones", "Legacy Management and Investment Group LLC")
        testingPlatPlusOverride(productPageUrl, priceAndUom, "platinumplus", hasQuantityDiscount, hasMapOverride, 241217, true, "Nathan Horchem", "Pizza Resources Corp")
    }

    private void testingPlatPlusOverride(String productPageUrl, String priceAndUom, String userType, Boolean hasQuantityDiscount, Boolean hasMapOverride, Integer userId = null, Boolean hasCompany = null, String userName = null, String userCompany = null){
        boolean isUserGuest = (userType == 'guest')
        
        if(isUserGuest){
            assert tryLoad("https://www.dev.webstaurantstore.com/myaccount/?logout=Y")
        }
        
        String url = productPageUrl + (isUserGuest ? "" : "?login_as_user=$userId")
        boolean isUserExpectedToSeePricing = (userType in ['webplus', 'platinum', 'platinumplus'] || (userType=='regular' && hasMapOverride))

        assert tryLoad(url)
        assert new PDPage().verifyItemType("Lone")

        PDPStickyHeader pdpStickyHeader = new PDPStickyHeader()
        log "before: " + pdpStickyHeader.isStickyHeader()
        assert !pdpStickyHeader.isStickyHeader()

        scrollToBottom()

        log "after: " + pdpStickyHeader.isStickyHeader()
        assert pdpStickyHeader.isStickyHeader()
        assert pdpStickyHeader.isShown()
        assert !pdpStickyHeader.isHidden()

        assert verifyElement(pdpStickyHeader.backToTopButtonXpath) == isUserGuest
        assert verifyElement(pdpStickyHeader.pageNavigationButtonXpath)
        assert verifyElement(pdpStickyHeader.pricingBlockXpath) == isUserExpectedToSeePricing

        if (isUserExpectedToSeePricing) {
            String expectedCustomQuote = getExpectedFromQuote(userType, hasCompany, userName, userCompany)
            assert getTextSafe(pdpStickyHeader.pricingFromXpath) == expectedCustomQuote

            String actualPriceAndUomBase = getTextSafe(pdpStickyHeader.priceXpath)
            int actualPriceAndUomIndexOfLineBreak = actualPriceAndUomBase.indexOf('\n')
            String actualPriceAndUom = actualPriceAndUomBase.substring(actualPriceAndUomIndexOfLineBreak + 1)
            assert (actualPriceAndUom == priceAndUom)
        }
    }

    private String getExpectedFromQuote(String userType, Boolean hasCompany = null, String userName = null, String userCompany = null){
        switch(userType){
            case 'regular':
                String userInfo = hasCompany ? userCompany : userName
                return "Custom Quote for $userInfo"
            case 'webplus':
                return 'Plus Member Price'
                break
            default:
                return 'Platinum Member Price'
                break
        }
    }
}

package framework.wss.pages.productdetail.map

import above.RunWeb
import all.Money
import wss.pages.element.modal.LoginModal
import wss.pages.productdetail.PDPMap
import wss.pages.productdetail.PDPage
import java.text.NumberFormat

class hUtPDPMap extends RunWeb{

    protected String mapStyleAXpath = PDPMap.getMapXpath('A')

    protected String itemNumberALoneHasOverride = "350PIZ3GLA"
    protected String itemNumberALoneHasOverrideP1 = '$15,535.85'
    protected String itemNumberALoneHasOverrideP4 = '$13,558.56'
    protected String itemNumberALoneHasOverrideUom = "Each"
    protected String itemNumberALoneHasOverrideUrl = "https://www.dev.webstaurantstore.com/doyon-piz3g-liquid-propane-triple-deck-pizza-oven-120v-70-000-btu/350PIZ3GLA.html"

    protected String itemNumberDLoneNoOverride = "900HP120SS"
    protected String itemNumberDLoneNoOverrideP1 = '$1,814.00'
    protected String itemNumberDLoneNoOverrideP4 = '$1,779.00'
    protected String itemNumberDLoneNoOverrideUrl = "https://www.dev.webstaurantstore.com/mytee-hp120-grand-prix-10-gallon-automotive-heated-detail-extractor-with-stainless-steel-upholstery-tool-100-cfm-120-psi-115v/900HP120SS.html"

    protected String itemNumberGLoneHasOverride = '445MBSDR8L'
    protected String itemNumberGLoneHasOverrideP1 = '$246.99'
    protected String itemNumberGLoneHasOverrideP4 = '$240.00'
    protected String itemNumberGLoneHasOverrideUom = 'Each'
    protected String itemNumberGLoneHasOverrideUrl = 'https://www.dev.webstaurantstore.com/luxor-mbs-dr-8l-mobile-bin-storage-unit-8-large-bin-capacity/445MBSDR8L.html'

    protected String itemNumberJLoneNoOverride = '372SS686C'
    protected String itemNumberJLoneNoOverrideP1 = '$6,710.00'
    protected String itemNumberJLoneNoOverrideP4 = '$4,959.00'
    protected String itemNumberJLoneNoOverrideUom = 'Each'
    protected String itemNumberJLoneNoOverrideUrl = 'https://www.dev.webstaurantstore.com/garland-ss686-sentry-series-6-sealed-burner-electric-restaurant-range-with-standard-oven-208v-3-phase-19-kw/372SS686C.html'

    protected String itemNumberJLoneHasOverride = '4611430NFS'
    protected String itemNumberJLoneHasOverrideP1 = '$154.00'
    protected String itemNumberJLoneHasOverrideP4 = '$148.49'
    protected String itemNumberJLoneHasOverrideUom = 'Each'
    protected String itemNumberJLoneHasOverrideUrl = 'https://www.dev.webstaurantstore.com/metro-1430nfs-super-erecta-14-x-30-autoclave-solid-stainless-steel-shelf/4611430NFS.html'

    protected String mapStyleKXpath = PDPMap.getMapXpath('K')

    protected String itemNumberKLoneNoOverride = '383US004599'
    protected String itemNumberKLoneNoOverrideP1 = '$129.50'
    protected String itemNumberKLoneNoOverrideUom = 'Each'
    protected String itemNumberKLoneNoOverrideUrl = 'https://www.dev.webstaurantstore.com/grosfillex-us004599-sunset-24-x-40-fusion-bronze-aluminum-cocktail-table/383US004599.html'

    protected String itemNumberLLoneNoOverride = '519KHBC420OB'
    protected String itemNumberLLoneNoOverrideP1 = '$749.00'
    protected String itemNumberLLoneNoOverrideUom = 'Each'
    protected String itemNumberLLoneNoOverrideUrl = 'https://www.dev.webstaurantstore.com/kitchenaid-khbc420ob-400-series-variable-speed-onyx-black-immersion-blender-with-20-blending-arm-120v-750w/519KHBC420OB.html'

    protected String itemNumberNLoneNoOverride = "995ZC16NHCR"
    protected String itemNumberNLoneNoOverrideP1 = '$1,204.87'
    protected String itemNumberNLoneNoOverrideP4 = '$589.00'
    protected String itemNumberNLoneNoOverrideUrl = "https://www.dev.webstaurantstore.com/zurn-zc100-6nh-c-r-15-cast-iron-roof-drain-with-low-silhouette-cast-iron-dome-roof-sump-receiver-underdeck-clamp-and-6-no-hub-outlet/995ZC16NHCR.html"

    protected String mapStyleOXpath = PDPMap.getMapXpath('O')

    protected String itemNumberOLoneNoOverride = '929WCM3'
    protected String itemNumberOLoneNoOverrideP1 = '$439.00'
    protected String itemNumberOLoneNoOverrideP4 = '$382.49'
    protected String itemNumberOLoneNoOverrideUom = 'Each'
    protected String itemNumberOLoneNoOverrideUrl = 'https://www.dev.webstaurantstore.com/waring-wcm3-3-kg-chocolate-melter/929WCM3.html'

    protected String itemNumberOLoneHasOverride = '195BLCT6EHE'
    protected String itemNumberOLoneHasOverrideP1 = '$13,122.00'
    protected String itemNumberOLoneHasOverrideP4 = '$11,722.32'
    protected String itemNumberOLoneHasOverrideUom = 'Each'
    protected String itemNumberOLoneHasOverrideUrl = 'https://www.dev.webstaurantstore.com/blodgett-combi-blct-6e-h-electric-boiler-free-5-pan-mini-combi-oven-with-touchscreen-controls-and-hoodini-ventless-hood-240v-3-phase/195BLCT6EHE.html'

    protected String mapStyleQXpath = PDPMap.getMapXpath('Q')

    protected String itemNumberQLoneNoOverride = '922FC4SH3027'
    protected String itemNumberQLoneNoOverrideP1 = '$397.60'
    protected String itemNumberQLoneNoOverrideUom = 'Each'
    protected String itemNumberQLoneNoOverrideUrl = 'https://www.dev.webstaurantstore.com/30-inch-208-volt-low-profile-heat-strip/922FC4SH3027.html'

    protected String itemNumberQLoneHasOverride = '422PT42LBDT1'
    protected String itemNumberQLoneHasOverrideP1 = '$284.00'
    protected String itemNumberQLoneHasOverrideP4 = '$248.99'
    protected String itemNumberQLoneHasOverrideUom = 'Each'
    protected String itemNumberQLoneHasOverrideUrl = 'https://www.dev.webstaurantstore.com/holland-bar-stool-l211b4228detred-28-round-detroit-red-wings-bar-height-pub-table/422PT42LBDT1.html'

    protected String itemNumberTLoneNoOverride = "232STS1300"
    protected String itemNumberTLoneNoOverrideP1 = '$1,542.80'
    protected String itemNumberTLoneNoOverrideUom = 'Each'
    protected String itemNumberTLoneNoOverrideP4 = '$1,339.00'
    protected String itemNumberTLoneNoOverrideUrl = "https://www.dev.webstaurantstore.com/astra-sts1300-pro-standard-semi-automatic-pourover-milk-and-beverage-steamer-110v/232STS1300.html"

    protected String itemNumberVLoneNoOverride = '519KHBC414OB'
    protected String itemNumberVLoneNoOverrideP1 = '$599.00'
    protected String itemNumberVLoneNoOverrideUom = 'Each'
    protected String itemNumberVLoneNoOverrideUrl = 'https://www.dev.webstaurantstore.com/kitchenaid-khbc414ob-400-series-immersion-blender-with-14-blending-arm-750w/519KHBC414OB.html'

    protected String itemNumberXLoneNoOverride = "787DEE440CD"
    protected String itemNumberXLoneNoOverrideP1 = '$20,020.00'
    protected String itemNumberXLoneNoOverrideUom = 'Each'
    protected String itemNumberXLoneNoOverrideP4 = '$16,729.00'
    protected String itemNumberXLoneNoOverrideUrl = "https://www.dev.webstaurantstore.com/groen-dee-4-40c-240-1-stainless-steel-40-gallon-steam-jacketed-tilting-electric-floor-mounted-kettle-240v-1-phase-24-kw/787DEE440CD.html"

    protected String itemNumberZLoneNoOverride = "5915045SXNZ"
    protected String itemNumberZLoneNoOverrideP1 = '$1,386.00'
//    protected String itemNumberZLoneNoOverrideP4 = '$1,159.00'
    protected String itemNumberZLoneNoOverrideUom = "Each"
    protected String itemNumberZLoneNoOverrideUrl = "https://www.dev.webstaurantstore.com/nemco-8045sxn-220-narrow-hot-dog-roller-grill-with-gripsit-non-stick-coating-45-hot-dog-capacity-220v/5915045SXNZ.html"

    protected String itemNumberZLoneHasOverride = "943073101WA"
    protected String itemNumberZLoneHasOverrideP1 = '$124.99'
    protected String itemNumberZLoneHasOverrideP4 = '$104.99'
    protected String itemNumberZLoneHasOverrideUom = "Each"
    protected String itemNumberZLoneHasOverrideUrl = "https://www.dev.webstaurantstore.com/weston-07-3101-w-a-manual-meat-tenderizer-with-two-legs/943073101WA.html"

    protected String userIndexNoCompany = "99"
    protected String userIndexName = "Jodi Galvin"
    protected String userIndexNoCompanyEmail = "jgalvin@bestlaw.com"
    protected String userIndexWithCompany = "134"
    protected String userIndexCompanyName = "Pinnacle Marble And Granite"

    protected static String[] googleUrls = [
            "?utm_source=google"
            , "?utm_campaign=google"
            , "?utm_medium=google&utm_source=onlineshopping"
            , "?utm_content=google&utm_source=onlineshopping"
    ]

    protected String getXpathForLoginLink(String mapStyle){
        return PDPMap.getXpathForMAPAspect(mapStyle, "Login Functionality", "loginLink")
    }

    protected String getXpathForCustomQuoteOverrideLabel(String mapStyle){
        return PDPMap.getXpathForMAPAspect(mapStyle, "Custom Quote Override", "customQuoteLabel")
    }

    protected String getXpathForCustomQuoteOverridePrice(String mapStyle){
        return PDPMap.getXpathForMAPAspect(mapStyle, "Custom Quote Override", "customQuotePrice")
    }

    protected void testingAddToCart(String mapStyle, String p1, String p4, boolean hasLogin, boolean isGoogleView = false, String userIdUnderTest = null){

        boolean isDiscountSectionSupposedToAppear = (mapStyle == 'H' || (mapStyle == 'S' && isGoogleView && userIdUnderTest != null) || (mapStyle == 'S' && !isGoogleView))

        String doesDiscountSectionAppearXpath = PDPMap.getXpathForMAPAspect(mapStyle, "Add to cart for pricing", "addToCartDiscount")
        boolean doesDiscountSectionAppear = verifyElement(doesDiscountSectionAppearXpath)

        assert(doesDiscountSectionAppear == isDiscountSectionSupposedToAppear)

        if(isDiscountSectionSupposedToAppear){
            String expectedDiscountBase = getExpectedDiscount(p1, p4)
            String expectedDiscount
            switch(mapStyle){
                case "H":
                    expectedDiscount = expectedDiscountBase + " lower"
                    break
                case "S":
                    expectedDiscount = "Save " + expectedDiscountBase
                    break
                default:
                    break
            }
            String actualDiscount = getTextSafe(doesDiscountSectionAppearXpath)
            assert (actualDiscount == expectedDiscount)
        }

        String expectedAddToCartText = hasLogin ? "Login or add to cart to see our price!" : "Add to cart to see our price!"
        String actualAddToCartTextXpath = PDPMap.getXpathForMAPAspect(mapStyle, "Add to cart for pricing", "addToCartDescription")
        String actualAddToCartText = getText(actualAddToCartTextXpath)

        assert(actualAddToCartText == expectedAddToCartText)

        boolean isAddToCartPriceInXFormatExpectedToAppear = (mapStyle in ['D', 'N']) || (mapStyle == 'G' && isGoogleView) || (mapStyle == 'S' && isGoogleView && userIdUnderTest == null)
        String doesAddToCartPriceInXFormatAppearXpath = PDPMap.getXpathForMAPAspect(mapStyle, "Add to cart for pricing", "addToCartPricing")
        boolean doesAddToCartPriceInXFormatAppear = verifyElement(doesAddToCartPriceInXFormatAppearXpath)

        assert(doesAddToCartPriceInXFormatAppear == isAddToCartPriceInXFormatExpectedToAppear)

        if(doesAddToCartPriceInXFormatAppear) {
            String expectedAddToCartPrice = p4.replaceAll("[0-9]", "X")
            String actualAddToCartPrice = getText(doesAddToCartPriceInXFormatAppearXpath)
            assert(actualAddToCartPrice == expectedAddToCartPrice)
        }
    }

    private Money getExpectedDiscount(String p1, String p4){
        Money expectedP1 = new Money(p1)
        Money expectedP4 = new Money(p4)
        return expectedP1 - expectedP4
    }


    protected void testLoginForDetailsLinkAndHyperlink(String mapStyle, boolean shouldSectionAppear) {
        String loginForDetailsTextAndHyperlinkXpath = PDPMap.getXpathForMAPAspect(mapStyle, "Login for Details text and hyperlink", "loginForDetailsTextAndHyperlink")

        assert verifyElement(loginForDetailsTextAndHyperlinkXpath) == shouldSectionAppear

        if (shouldSectionAppear) {
            String expectedText = "Login for details"
            String actualText = getTextSafe(loginForDetailsTextAndHyperlinkXpath)

            assert (actualText == expectedText)
        }
    }

}

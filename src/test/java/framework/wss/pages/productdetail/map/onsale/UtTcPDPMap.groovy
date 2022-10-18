package framework.wss.pages.productdetail.map.onsale

import above.RunWeb
import all.Money
import wss.pages.element.PopoverTemplate
import wss.pages.productdetail.PDPMap
import wss.pages.productdetail.PDPPriceTile
import wss.pages.productdetail.PDPStickyHeader
import wsstest.product.productdetailpage.mapRedo.salesflag.HpPDPMapTestSalesFlag
import wsstest.product.productdetailpage.mapRedo.shippingwithplusmessage.HpPDPMapTestShippingWithPlusMessages
import wsstest.product.productdetailpage.mapRedo.showrewardscard.HpPDPMapTestShowRewardsCard
import wsstest.product.productdetailpage.mapRedo.HpPDPMapTestStickyHeader
import wsstest.product.productdetailpage.mapRedo.whywedothis.HpPDPMapTestWhyWeDoThis

class UtTcPDPMap extends RunWeb{
    static void testingWhyDoWeDoThis(String mapStyle, String userTypeUnderTest, boolean isMapBubbleSupposedToAppear, boolean isGoogleView){
        RunWeb r = run()
        assert HpPDPMapTestWhyWeDoThis.doesWhyWeDoThisSectionAppear(mapStyle, userTypeUnderTest, isMapBubbleSupposedToAppear, isGoogleView)
            if(HpPDPMapTestWhyWeDoThis.isMAPAndGoogleCombinationExpectedToHaveWhyDoWeDoThisSection(mapStyle, userTypeUnderTest, isMapBubbleSupposedToAppear, isGoogleView)) {
                assert HpPDPMapTestWhyWeDoThis.doesWhyWeDoThisQuestionMarkButtonAppear()
                assert r.jsScrollTo(PDPMap.whyDoWeDoThisQuestionMark)
                assert r.jsMouseOver(PDPMap.whyDoWeDoThisQuestionMark)
                assert r.waitForElementVisible(PopoverTemplate.popover)
                assert HpPDPMapTestWhyWeDoThis.doesExpectedPopoverTitleAppear()
                assert r.mouseOver(PDPMap.wssBanner)
            }
    }

    static void testingSalesFlag(def itemNumberUnderTest){
        RunWeb r = run()
        boolean isSaleFlagSupposedToAppear = itemNumberUnderTest["on_sale_with_map_price"] || itemNumberUnderTest['has_sale_price']
        boolean doesSaleWithMapPriceFlagAppear = r.verifyElement(PDPMap.saleFlag)
        boolean doesSaleWithMapPriceFlagAppearAppearAsExpected = (doesSaleWithMapPriceFlagAppear == isSaleFlagSupposedToAppear)
        assert doesSaleWithMapPriceFlagAppearAppearAsExpected
        if(isSaleFlagSupposedToAppear){
            assert r.verifyElement(PDPMap.saleFlagText)
        }
    }

    static void testingShippingWithPlusMessage(def itemNumberUnderTest){
        RunWeb r = run()
        boolean isShipsFreeWithPlusSupposedToAppear = itemNumberUnderTest['ships_free_with_plus']
        boolean isDiscountedShippingWithPlusMessageSupposedToAppear = itemNumberUnderTest['has_discounted_shipping_with_plus_message']

        if(isShipsFreeWithPlusSupposedToAppear && isDiscountedShippingWithPlusMessageSupposedToAppear){
            r.report("You have input an invalid test combination of testing both shipping with Plus messages.")
        }
        else if(isShipsFreeWithPlusSupposedToAppear && !isDiscountedShippingWithPlusMessageSupposedToAppear){
            testingShipsFreeWithPlus()
        }
        else if(!isShipsFreeWithPlusSupposedToAppear && isDiscountedShippingWithPlusMessageSupposedToAppear){
            testingDiscountedShippingWithPlus()
        }
        else{
            assert !r.verifyElement(PDPMap.basePlusText)
        }
    }

    private static void testingPlusSections(String plusSectionXpath, String expectedText){
        RunWeb r = run()
        assert r.verifyElement(plusSectionXpath)

        String actualText = r.getTextSafe(PDPMap.basePlusText)
        assert (actualText == expectedText)

        String expectedUrl = r.getUrl('hp') + "/plus/"
        String actualUrl = r.getAttribute(plusSectionXpath, "href")
        assert (actualUrl == expectedUrl)

        assert r.verifyElement(PDPMap.basePlusTextFlag)
    }

    static void testingShipsFreeWithPlus(){
        testingPlusSections(PDPMap.shipsFreeWithPlus, "Ships free with Plus")
    }

    static void testingDiscountedShippingWithPlus(){
        testingPlusSections(PDPMap.discountedShippingWithPlus, "Discounted shipping with Plus")
    }

    static void testingStickyHeader(def itemNumberUnderTest, String mapStyle, String userTypeUnderTest, Integer userIdUnderTest, Boolean hasCompany, boolean isQualified, boolean isGoogleView){
        RunWeb r = run()

        PDPStickyHeader pdpStickyHeader = new PDPStickyHeader()

        boolean doesItemHaveCustomization = itemNumberUnderTest['has_customization']
        boolean doesItemHaveRequiredAccessory = itemNumberUnderTest['has_required_accessory']
        boolean hasSalePrice = itemNumberUnderTest['has_sale_price']

        assert !pdpStickyHeader.isStickyHeader()
        assert r.scrollToBottom()
        assert r.waitForElementVisible(PDPStickyHeader.stickyHeaderDivXpath)

        assert HpPDPMapTestStickyHeader.doesBackToTopOrConfigureButtonAppearAsExpected(mapStyle, isQualified, isGoogleView, doesItemHaveCustomization, doesItemHaveRequiredAccessory)
        assert HpPDPMapTestStickyHeader.doesDetailsButtonAppear()
        assert HpPDPMapTestStickyHeader.doesPricingSectionAppearAsExpected(isQualified, hasSalePrice, mapStyle, isGoogleView)

        boolean isPricingSectionSupposedToAppear = HpPDPMapTestStickyHeader.isPriceSectionExpectedToShow(isQualified, mapStyle, isGoogleView, hasSalePrice)

        if (isPricingSectionSupposedToAppear){
            assert HpPDPMapTestStickyHeader.doesStickyLabelHeaderAppear(itemNumberUnderTest, isQualified, mapStyle)

            boolean onSaleWithMapPrice = itemNumberUnderTest["on_sale_with_map_price"]
            boolean isSaleExpectedToBeInEffect = !onSaleWithMapPrice && hasSalePrice

            boolean shouldLabelAppear = (isQualified && !isSaleExpectedToBeInEffect && mapStyle != 'G')

            if(shouldLabelAppear){
                Boolean hasPlatinumPlusOverride = itemNumberUnderTest["show_platinum_plus_price"]
                assert HpPDPMapTestStickyHeader.doesStickyLabelHeaderHaveCorrectText(userTypeUnderTest, userIdUnderTest, hasPlatinumPlusOverride, hasCompany)
            }

            if(isSaleExpectedToBeInEffect){
                assert HpPDPMapTestStickyHeader.testingStickyHeaderPricingOnSale(itemNumberUnderTest)
            }
            else{
                assert HpPDPMapTestStickyHeader.testingStickyHeaderPricingRegular(itemNumberUnderTest)
            }
        }
    }

    static void showWebRewardsCardAdNegativeTesting(def itemNumberUnderTest){
        RunWeb r = run()
        assert !r.verifyElement(PDPPriceTile.webstaurantRewardsVisaCreditCardBlockXpath)
    }

    static void showWebRewardsCardAdPositiveTesting(def itemNumberUnderTest, boolean isQualified){
        RunWeb r = run()
        assert r.verifyElement(PDPPriceTile.webstaurantRewardsVisaCreditCardBlockXpath)
        assert HpPDPMapTestShowRewardsCard.doesCorrectRewardsCreditCartPriceAppear(itemNumberUnderTest, itemNumberUnderTest['Call_For_Pricing'], isQualified)

        String actualPriceBase = r.getTextSafe(PDPPriceTile.webstaurantRewardsVisaCreditCardTextXpath)
        Integer actualPricePriceBeginningLocation = actualPriceBase.indexOf('$')
        Integer actualPricePriceEndLocation = actualPriceBase.indexOf(' back')
        String actualPriceSubstring = actualPriceBase.substring(actualPricePriceBeginningLocation, actualPricePriceEndLocation)
        Money actualPrice = new Money(actualPriceSubstring)

        String expectedPoints = actualPrice.multiply(100).toString().replace('$', '').replace('.00', '')
        String expectedMessage = 'Earn up to ' + actualPrice + ' back (' + expectedPoints + ' points)' +
                'with a Webstaurant Rewards Visa® Credit Card'
        String actualMessage = r.getTextSafe(PDPPriceTile.webstaurantRewardsVisaCreditCardTextXpath).replace('\n', '')
        assert (actualMessage == expectedMessage)

        String expectedHyperlink = r.getUrl('hp') + "/rewards/"
        String actualHyperlink = r.getAttribute(PDPPriceTile.webstaurantRewardsVisaCreditCardTextHyperlinkXpath, "href")

        assert (actualHyperlink == expectedHyperlink)
    }

    static void standardTesting(def itemNumberUnderTest, String userTypeUnderTest, Integer userIdUnderTest, boolean isMapBubbleSupposedToAppear, boolean isGoogleView, boolean isQualified, Boolean hasCompany){
        testingWhyDoWeDoThis(itemNumberUnderTest['Call_For_Pricing'], userTypeUnderTest, isMapBubbleSupposedToAppear, isGoogleView)
        testingSalesFlag(itemNumberUnderTest)
        testingShippingWithPlusMessage(itemNumberUnderTest)
//        testingStickyHeader(itemNumberUnderTest, itemNumberUnderTest['Call_For_Pricing'], userTypeUnderTest, userIdUnderTest, hasCompany, isQualified, isGoogleView)

        boolean isShowWebRewardsCardExpectedToAppear = itemNumberUnderTest['show_web_rewards_card_ad'] && !(userTypeUnderTest in ['regularRewards', 'webPlusRewards', 'platinumRewards', 'platinumPlusRewards'])
        isShowWebRewardsCardExpectedToAppear ? showWebRewardsCardAdPositiveTesting(itemNumberUnderTest, isQualified) : showWebRewardsCardAdNegativeTesting(itemNumberUnderTest)
        //show rewards card test will go here
    }

    static void standardTestingNonUnitTest(def itemNumberUnderTest, String userTypeUnderTest, Integer userIdUnderTest, boolean isMapBubbleSupposedToAppear, boolean isGoogleView, boolean isQualified, Boolean hasCompany){
        HpPDPMapTestWhyWeDoThis.testingWhyDoWeDoThis(itemNumberUnderTest['Call_For_Pricing'], userTypeUnderTest, isMapBubbleSupposedToAppear, isGoogleView)

        Boolean isSaleFlagSupposedToAppear = itemNumberUnderTest["on_sale_with_map_price"] || itemNumberUnderTest['has_sale_price']
        HpPDPMapTestSalesFlag.testingSaleFlag(isSaleFlagSupposedToAppear)

        HpPDPMapTestShippingWithPlusMessages.testingShippingWithPlusMessage(itemNumberUnderTest)
        HpPDPMapTestStickyHeader.testingStickyHeader(itemNumberUnderTest, itemNumberUnderTest['Call_For_Pricing'], userTypeUnderTest, userIdUnderTest, hasCompany, isQualified, isGoogleView)

        boolean isShowWebRewardsCardExpectedToAppear = itemNumberUnderTest['show_web_rewards_card_ad'] && !(userTypeUnderTest in ['regularRewards', 'webPlusRewards', 'platinumRewards', 'platinumPlusRewards'])
        isShowWebRewardsCardExpectedToAppear ? HpPDPMapTestShowRewardsCard.showWebRewardsCardAdPositiveTesting(itemNumberUnderTest, isQualified) : HpPDPMapTestShowRewardsCard.showWebRewardsCardAdNegativeTesting(itemNumberUnderTest)

    }
}

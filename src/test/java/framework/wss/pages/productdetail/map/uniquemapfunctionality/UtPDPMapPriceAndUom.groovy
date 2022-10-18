package framework.wss.pages.productdetail.map.uniquemapfunctionality

import framework.wss.pages.productdetail.map.hUtPDPMap
import wss.actions.WssRenderingModeBanner
import wss.pages.productdetail.PDPMap
import wss.user.UserQuickLogin
import wss.user.userurllogin.UserUrlLogin

class UtPDPMapPriceAndUom extends hUtPDPMap{
    def test() {
        setup('mwestacott', 'UtPDPMapPriceAndUom',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test map pdp', 'logLevel:debug'])

        assert openAndTryLoad(getUrl('hp') + "/plus/")
        assert new WssRenderingModeBanner().setMode("SF")

        assert UserQuickLogin.logOut()

        //Logged out user, MAP Style K, No Override
        //Checking to see that it uses P1 and UOM, and that it has a strikethrough.
        assert tryLoad(itemNumberKLoneNoOverrideUrl)
        runTestPriceAndUom("K", itemNumberKLoneNoOverrideP1, itemNumberKLoneNoOverrideUom,  true, true, true)

        //Logged out user, MAP Style O, No override
        //Checking to see that the pricing field doesn't appear at all
        assert tryLoad(itemNumberOLoneNoOverrideUrl)
        runTestPriceAndUom("O", "", itemNumberOLoneNoOverrideUom, false, false, false)

        //Looged out, MAP Style ), no override, Google View
        //Checking to see that P1 displays by itself without a UOM. Strikethrough doesn't appear
        assert tryLoad(itemNumberOLoneNoOverrideUrl + googleUrls[0])
        runTestPriceAndUom("O", itemNumberOLoneNoOverrideP1, itemNumberOLoneNoOverrideUom, true, false, false)

        //Looged in as regular user, MAP Style ), no override
        //Checking to see that it uses P4 and UOM, and that it has no strikethrough.
        assert new UserUrlLogin().loginAs(userIndexNoCompany) != null
        runTestPriceAndUom("O", itemNumberOLoneNoOverrideP4, itemNumberOLoneNoOverrideUom, true, false, true)


        closeBrowser()
    }

    protected void runTestPriceAndUom(String mapStyle, String price, String uom, Boolean expectedToHavePricing = true, Boolean hasStrikethrough = false, Boolean hasUom = true){

        String mapAspect = hasStrikethrough ? "priceAndUomStrikethrough" : "priceAndUom"
        String actuallyHasPricingXpath = PDPMap.getXpathForMAPAspect(mapStyle, "Price and UOM", mapAspect)

        assert(verifyElement(actuallyHasPricingXpath) == expectedToHavePricing)

        if(expectedToHavePricing) {
            if(hasUom) {
                String expectedPriceAndUom = "$price/$uom"
                String actualPriceAndUom = getTextSafe(actuallyHasPricingXpath)
                assert (actualPriceAndUom == expectedPriceAndUom)
            }
            else{
                String actualPrice = getTextSafe(actuallyHasPricingXpath)
                assert(actualPrice == price)
            }
        }
    }
}

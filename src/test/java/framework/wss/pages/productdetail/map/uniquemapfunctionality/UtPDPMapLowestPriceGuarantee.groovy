package framework.wss.pages.productdetail.map.uniquemapfunctionality

import framework.wss.pages.productdetail.map.hUtPDPMap
import wss.actions.WssRenderingModeBanner
import wss.pages.productdetail.PDPMap
import wss.user.UserQuickLogin
import wss.webadmin.WebAdmin
import wss.webadmin.headermanagement.WebAdminHeaderManagement

class UtPDPMapLowestPriceGuarantee extends hUtPDPMap{

    def test() {
        setup('mwestacott', 'UtPDPMapLowestPriceGuarantee',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test map pdp', 'logLevel:debug'])

        assert openAndTryLoad(getUrl('hp') + "/plus/")
        assert new WssRenderingModeBanner().setMode("SF")

        assert UserQuickLogin.logOut()

        setUpUserForMapLTesting(false)
        assert tryLoad(itemNumberLLoneNoOverrideUrl)
        testingLowestPriceGuarantee(false)

        setUpUserForMapLTesting(true)
        assert tryLoad(itemNumberLLoneNoOverrideUrl)
        testingLowestPriceGuarantee(true)

        closeBrowser()
    }

    protected void setUpUserForMapLTesting(boolean isChatOnline){
        WebAdmin.open()
        WebAdminHeaderManagement.removeBanners()
        if(!isChatOnline){
            assert WebAdminHeaderManagement.addBanner(isChatOnline)
        }
    }

    //Basis for testing MAP style L
    protected void testingLowestPriceGuarantee(boolean isChatOnline){
        String lowestPriceGuaranteeLabelXpath = PDPMap.getXpathForMAPAspect("L", "Lowest Price Guarantee", "lowestPriceGuaranteeLabel")
        String lowestPriceGuaranteeSmallTextXpath = PDPMap.getXpathForMAPAspect("L", "Lowest Price Guarantee", "lowestPriceGuaranteeSmallText")
        String lowestPriceGuaranteeSmallTextArrow = PDPMap.getXpathForMAPAspect("L", "Lowest Price Guarantee", "lowestPriceGuaranteeSmallTextArrow")

        assert verifyElement(lowestPriceGuaranteeLabelXpath)
        assert (getTextSafe(lowestPriceGuaranteeLabelXpath) == 'Lowest Price Guarantee')
        assert verifyElement(lowestPriceGuaranteeSmallTextArrow)

        String expectedSmallText = isChatOnline ?  "Chat Now " : "Request Quote "
        assert (getTextSafe(lowestPriceGuaranteeSmallTextXpath) == expectedSmallText)

        if(isChatOnline){
            String testEnvironment = (webProject.testEnv == 'prod' ? '' : webProject.testEnv)
            String expectedChatUrlBeginning = "https://www." + testEnvironment + ".webstaurantstore.com/chat/?referringURL="
            String actualChatUrl = getAttribute(PDPMap.lowestPriceGuarantee, "href")

            assert actualChatUrl.startsWith(expectedChatUrlBeginning)
        }
        else {
            String lowestPriceGuaranteedRequestQuoteModalXpath = PDPMap.getXpathForMAPAspect("L", "Lowest Price Guarantee", "lowestPriceGuaranteeRequestQuoteModal")

            assert !verifyElement(lowestPriceGuaranteedRequestQuoteModalXpath)
            assert tryClick(PDPMap.lowestPriceGuarantee)
            assert waitForElement(lowestPriceGuaranteedRequestQuoteModalXpath)

            String lowestPriceGuaranteeRequestQuoteModalCloseButtonXpath = PDPMap.getXpathForMAPAspect("L", "Lowest Price Guarantee", "lowestPriceGuaranteeRequestQuoteModalCloseButton")
            sleep(1000)
            assert (tryClick(lowestPriceGuaranteeRequestQuoteModalCloseButtonXpath))
            sleep(1000)
            assert !verifyElement(lowestPriceGuaranteedRequestQuoteModalXpath)
        }
    }
}

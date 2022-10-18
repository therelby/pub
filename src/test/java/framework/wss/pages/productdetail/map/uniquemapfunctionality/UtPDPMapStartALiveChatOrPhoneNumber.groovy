package framework.wss.pages.productdetail.map.uniquemapfunctionality

import framework.wss.pages.productdetail.map.hUtPDPMap
import wss.actions.WssRenderingModeBanner
import wss.pages.productdetail.PDPMap
import wss.user.UserQuickLogin

//Testing to see if Start a Live Chat or Phone Number appears between certain MAP Styles
class UtPDPMapStartALiveChatOrPhoneNumber extends hUtPDPMap {
    def test() {
        setup('mwestacott', 'UtPDPMapStartALiveChatOrPhoneNumber',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test map pdp', 'logLevel:debug'])

        assert openAndTryLoad(getUrl('hp') + "/plus/")
        assert new WssRenderingModeBanner().setMode("SF")

        assert UserQuickLogin.logOut()

        //MAP Style T: Has Start A Live Chat, but not a phone number
        assert tryLoad(itemNumberTLoneNoOverrideUrl)
        testingStartALiveChat(mapStyle, true)
        testingPhoneNumber(mapStyle, false)

        //MAP Style O: Has phone number, but not Start a Live Chat
        assert tryLoad(itemNumberOLoneNoOverrideUrl)
        testingStartALiveChat(mapStyle, false)
        testingPhoneNumber(mapStyle, true)

        closeBrowser()
    }

    protected void testingStartALiveChat(String mapStyle, boolean expectedToHaveStartALiveChat){
        String actuallyHasStartALiveChatXpath = PDPMap.getXpathForMAPAspect(mapStyle, "Contact us", "startALiveChatLink")
        boolean actuallyHasStartALiveChat = verifyElement(actuallyHasStartALiveChatXpath)

        assert(actuallyHasStartALiveChat == expectedToHaveStartALiveChat)

        if(expectedToHaveStartALiveChat){

            String testEnvironment = (webProject.testEnv == 'prod' ? '' : webProject.testEnv)
            String expectedChatUrlBeginning = "https://www." + testEnvironment + ".webstaurantstore.com/chat/?referringURL="
            String actualChatUrl = getAttribute(actuallyHasStartALiveChatXpath, "href")

            assert(actualChatUrl.startsWith(expectedChatUrlBeginning))
        }
    }

    protected void testingPhoneNumber(String mapStyle, boolean expectedToHavePhoneNumber){
        String actuallyHasPhoneNumberXpath = PDPMap.getXpathForMAPAspect(mapStyle, "Contact us", "hasPhoneNumber")
        boolean actuallyHasPhoneNumber = verifyElement(actuallyHasPhoneNumberXpath)

        assert(actuallyHasPhoneNumber == expectedToHavePhoneNumber)
    }
}

package framework.wss.pages.productdetail.map.uniquemapfunctionality

import framework.wss.pages.productdetail.map.hUtPDPMap
import wss.actions.WssRenderingModeBanner
import wss.pages.productdetail.PDPMap
import wss.user.UserQuickLogin
import wss.user.userurllogin.UserUrlLogin

//These test cases check the Enter Your Email functionality.
//These all check that the Start A Live Chat exists and is accurate
class UtPDPMapEnterYourEmail extends hUtPDPMap{

    def test() {
        setup('mwestacott', 'UtPDPMapEnterYourEmail',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test map pdp', 'logLevel:debug'])

        assert openAndTryLoad(getUrl('hp') + "/plus/")
        assert new WssRenderingModeBanner().setMode("SF")

        assert UserQuickLogin.logOut()

        //The following tests are for logged out users

        //MAP Style K checks for the Each Only label, 'to be instantly sent the price' as the enter your email reason
        // no login indication, and the normal start a live chat text.
        assert(tryLoad(itemNumberKLoneNoOverrideUrl))
        testingEnterYourEmail('K', false)

        //MAP Style Q checks that the Each Only label doesn't appear, 'to be instantly sent the price' as the enter your email reason
        // login indication, and a slightly different start a live chat text.
        assert(tryLoad(itemNumberQLoneHasOverrideUrl))
        testingEnterYourEmail('Q', true)

        //MAP Style V checks that the Each Only label doesn't appear, 'for current pricing' as the enter your email reason
        // no login indication, and the normal start a live chat text.
        assert(tryLoad(itemNumberVLoneNoOverrideUrl))
        testingEnterYourEmail('V', false)

        closeBrowser()
    }

    protected void testingEnterYourEmail(String mapStyle, boolean hasLogin){

        String actuallyHasEachOnlyLabelXpath = PDPMap.getXpathForMAPAspect(mapStyle, "Enter your email", "labelEachOnly")

        assert (verifyElement(actuallyHasEachOnlyLabelXpath) == (mapStyle == 'K'))

        if(mapStyle == 'K'){
            assert (getTextSafe(actuallyHasEachOnlyLabelXpath)=="Each Only")
        }

        String expectedEnterYourEmailReason = (mapStyle == 'V' ? "for current pricing." : "to be instantly sent the price!")
        String expectedEnterYourEmailText = hasLogin ? "Login or enter your email $expectedEnterYourEmailReason" : "Enter your email $expectedEnterYourEmailReason"
        String actualEnterYourEmailTextXPath = PDPMap.getXpathForMAPAspect(mapStyle, "Enter your email", "enterYourEmailText")

        assert (getTextSafe(actualEnterYourEmailTextXPath) == expectedEnterYourEmailText)

        String expectedStartALiveChatText = "Start a Live Chat with a Customer Service rep to receive the price!\n" +
                "Mon-Thur 24 Hours, Fri 12AM-8PM EST\n" +
                "Sat & Sun: 9AM-4PM EST" +
                (mapStyle == "Q" ? "." : "")

        String actualStartALiveChatTextXpath = PDPMap.getXpathForMAPAspect(mapStyle, "Enter your email", "startALiveChatText")

        assert (getTextSafe(actualStartALiveChatTextXpath) == expectedStartALiveChatText)

        String startALiveChatLinkXpath = PDPMap.getXpathForMAPAspect(mapStyle, "Enter your email", "startALiveChatLink")

        String testEnvironment = (webProject.testEnv == 'prod' ? '' : webProject.testEnv)
        String expectedChatUrlBeginning = "https://www." + testEnvironment + ".webstaurantstore.com/chat/?referringURL="
        String actualChatUrl = getAttribute(startALiveChatLinkXpath, "href")

        assert actualChatUrl.startsWith(expectedChatUrlBeginning)
    }
}

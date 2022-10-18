package framework.wss.pages.productdetail.map.uniquemapfunctionality

import above.RunWeb
import framework.wss.pages.productdetail.map.hUtPDPMap
import wss.actions.WssRenderingModeBanner
import wss.pages.productdetail.PDPMap
import wss.user.UserQuickLogin
import wss.user.userurllogin.UserUrlLogin

class UtPDPMapContactUsCommonFunctionality extends hUtPDPMap{

    def test() {
        setup('mwestacott', 'UtPDPMapContactUsCommonFunctionality',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test map pdp', 'logLevel:debug'])

        assert openAndTryLoad(getUrl('hp') + "/plus/")
        assert new WssRenderingModeBanner().setMode("SF")

        assert UserQuickLogin.logOut()

        assert(tryLoad(itemNumberJLoneNoOverrideUrl))
        testingContactUsCommonFunctionality('J', false)

        assert(tryLoad(itemNumberJLoneHasOverrideUrl))
        testingContactUsCommonFunctionality('J', true)

        assert(tryLoad(itemNumberOLoneHasOverrideUrl))
        testingContactUsCommonFunctionality('O', true)

        assert(tryLoad(itemNumberXLoneNoOverrideUrl))
        testingContactUsCommonFunctionality('X', false)

        closeBrowser()
    }

    def testingContactUsCommonFunctionality(String mapStyle, boolean hasLogin, boolean hideEmailForExternalUsers = true){

        String emailAddressFieldXpath = PDPMap.getXpathForMAPAspect(mapStyle, "Email", "emailAddressField")

        if(mapStyle=='O'){
            assert waitForNoElement(emailAddressFieldXpath)
        }
        else {
            assert verifyElement(emailAddressFieldXpath)
            assert setCookie("SF_EXTERNAL_OVERRIDE", "1")
            assert refresh()

            assert verifyCookie("SF_EXTERNAL_OVERRIDE")
            assert (waitForNoElement(emailAddressFieldXpath) == hideEmailForExternalUsers)

            assert removeCookie("SF_EXTERNAL_OVERRIDE")
            assert refresh()

            assert !verifyCookie("SF_EXTERNAL_OVERRIDE")
            assert waitForElement(emailAddressFieldXpath)
        }

        String contactUsWord = (mapStyle in ['E', 'I', 'J', 'O'] ? "details" : "pricing")
        String expectedContactUsText = (hasLogin && mapStyle != 'J') ? "Contact us or login for $contactUsWord" : "Contact us for $contactUsWord"
        String actualContactUsTextXPath = PDPMap.getXpathForMAPAspect(mapStyle, "Contact us", "contactUsText")
        String actualContactUsText = getTextSafe(actualContactUsTextXPath)

        assert (actualContactUsText == expectedContactUsText)

        String expectedHourlyInfoText = "(Mon-Thur 24 Hours, Fri 12AM-8PM EST\nSat & Sun: 9AM-4PM EST)"
        String actualHourlyInfoTextXpath = PDPMap.getXpathForMAPAspect(mapStyle, "Contact us", "hourlyInfo")
        String actualHourlyInfoText = getTextSafe(actualHourlyInfoTextXpath)

        assert (actualHourlyInfoText == expectedHourlyInfoText)
    }
}

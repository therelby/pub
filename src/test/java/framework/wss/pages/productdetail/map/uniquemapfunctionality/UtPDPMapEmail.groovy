package framework.wss.pages.productdetail.map.uniquemapfunctionality

import framework.wss.pages.productdetail.map.hUtPDPMap
import wss.actions.WssRenderingModeBanner
import wss.pages.productdetail.PDPMap
import wss.user.UserQuickLogin
import wss.user.userurllogin.UserUrlLogin

class UtPDPMapEmail extends hUtPDPMap{

    private String emailAddressFieldXpath = PDPMap.getXpathForMAPAspect('Q', "Email", "emailAddressField")
    private String emailAddressFieldSpaceXpath = PDPMap.getXpathForMAPAspect('Q', "Email", "emailAddressFieldSpace")
    private String emailAddressSubmitButtonXpath = PDPMap.getXpathForMAPAspect('Q', "Email", "emailAddressSubmitButton")
    private String emailAddressFormErrorXpath = PDPMap.getXpathForMAPAspect('Q', "Email", "formError")
    private String emailAddressFormSuccessXpath = PDPMap.getXpathForMAPAspect('Q', "Email", "formSuccess")

    def test() {
        setup('mwestacott', 'UtPDPMapEmail',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test map pdp', 'logLevel:debug'])

        assert openAndTryLoad(getUrl('hp') + "/plus/")
        assert new WssRenderingModeBanner().setMode("SF")

        assert UserQuickLogin.logOut()

        assert(tryLoad(itemNumberQLoneNoOverrideUrl))
        testingEmail(false, '')

        assert(tryLoad(itemNumberQLoneHasOverrideUrl))
        testingEmail(false, '')

        assert new UserUrlLogin().loginAs(userIndexNoCompany) != null

        assert(tryLoad(itemNumberQLoneNoOverrideUrl))
        testingEmail(true, userIndexNoCompanyEmail)

        closeBrowser()
    }

    //If you're logged in, there are times when the email field will populate.
    //Bad email inputs should trigger failure messages.
    //Good email inputs will trigger the success message.
    //There is a known error where sometimes the error message generates and looks like the success message.
    protected void testingEmail(boolean isLoggedIn, String expectedDefaultEmail){
        if (isLoggedIn) {
            String actualDefaultEmail = getAttributeSafe(emailAddressFieldXpath, "value")
            assert(actualDefaultEmail == expectedDefaultEmail)
            setText(emailAddressFieldXpath, " ")
            assert(waitForElement(emailAddressFieldSpaceXpath))
        }

        testingBadEmailInputs("")
        testingBadEmailInputs("test")
        testingBadEmailInputs("@webstaurantstore.com")
        testingBadEmailInputs("@webstaurantstore.test.com")
        testingBadEmailInputs("test@webstaurantstore")
        testingBadEmailInputs("test@webstaurantstore.test.com")
        testingGoodEmailInput("guest@automationWss.com")
    }

    private void testingBadEmailInputs(String emailInput){
        setText(emailAddressFieldXpath, emailInput)
        sleep(1000)

        assert(tryClick(emailAddressSubmitButtonXpath))
        sleep(1000)

        assert(verifyElement(emailAddressFieldXpath))
        assert(verifyElement(emailAddressSubmitButtonXpath))
        assert(!verifyElement(emailAddressFormSuccessXpath))
        assert(verifyElement(emailAddressFormErrorXpath))

        assert(getTextSafe(emailAddressFormErrorXpath) == 'Please enter a valid email address.')
    }

    private void testingGoodEmailInput(String emailInput){
        setText(emailAddressFieldXpath, emailInput)
        sleep(1000)

        assert(tryClick(emailAddressSubmitButtonXpath))
        sleep(1000)

        assert(!verifyElement(emailAddressFieldXpath))
        assert(!verifyElement(emailAddressSubmitButtonXpath))
        assert(!verifyElement(emailAddressFormErrorXpath))
        assert(verifyElement(emailAddressFormSuccessXpath))

        String expectedText = 'Thank you! A message has been sent to the contact information you provided.'
        String actualText = getTextSafe(emailAddressFormSuccessXpath)
        assert(actualText == expectedText)
    }
}

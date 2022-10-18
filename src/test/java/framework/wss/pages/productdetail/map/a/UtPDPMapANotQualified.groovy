package framework.wss.pages.productdetail.map.a

import wss.actions.WssRenderingModeBanner
import wss.pages.element.PopoverTemplate
import wss.pages.productdetail.PDPMap
import wss.user.UserQuickLogin
class UtPDPMapANotQualified extends hUtPDPMapA {

    def test() {

        setup('mwestacott', 'MAP PDP A Not qualified unit test',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test, pdp, map style A, not qualified ',
                 "PBI: 349771",
                 'logLevel:info'])
        assert openAndTryLoad(getUrl('hp') + "/plus/")
        assert new WssRenderingModeBanner().setMode("SF")

        assert UserQuickLogin.logOut()

        assert tryLoad(itemNumberALoneHasOverrideUrl)

        String expectedLoginOrGetPriceByText = "Login or get price by text!"
        String actualLoginOrGetPriceByTextXpath = PDPMap.getXpathForMAPAspect("A", "Phone number", "getPriceByText")
        String actualLoginOrGetPriceByText = getTextSafe(actualLoginOrGetPriceByTextXpath)

        assert (actualLoginOrGetPriceByText == expectedLoginOrGetPriceByText)

        String loginOrGetPriceByTextIconXpath = PDPMap.getXpathForMAPAspect("A", "Phone number", "getPriceByTextIcon")

        mouseOver(loginOrGetPriceByTextIconXpath)
        waitForElement(PopoverTemplate.popover)

        assert (getTextSafe(PopoverTemplate.popoverTitle) == "SMS Text Messages")
        assert (getTextSafe(PopoverTemplate.popoverContent) == "Phone number must be able to receive text messages\nMessage and data rates may apply. You may opt-out at any time by sending STOP to +17172887303. (Note: if you texted STOP but would like to restart, text START, and make sure your number below is correct.)")

        mouseOver(actualLoginOrGetPriceByTextXpath)

        String toSeePriceTextXpath = PDPMap.getXpathForMAPAspect("A", "Phone number", "toSeePriceText")
        assert (getTextSafe(toSeePriceTextXpath) == "Or call 717-392-7472\n(Mon-Thur 24 Hours, Fri 12AM-8PM EST\nSat & Sun: 9AM-4PM EST)")

        String actualPhoneNumberFieldXpath = PDPMap.getXpathForMAPAspect("A", "Phone number", "phoneNumberField")
        assert (getAttribute(actualPhoneNumberFieldXpath, 'value')=='')

/*        testingBadPhoneNumberInput("")
        testingBadPhoneNumberInput("7")
        testingBadPhoneNumberInput("71")
        testingBadPhoneNumberInput("717")
        testingBadPhoneNumberInput("7170")
        testingBadPhoneNumberInput("71700")
        testingBadPhoneNumberInput("717000")
        testingBadPhoneNumberInput("7170000")
        testingBadPhoneNumberInput("71700000")
        testingBadPhoneNumberInput("717000000")*/

        closeBrowser()
    }

//code for testing phone numbers when the bug is fixed
/*    void testingBadPhoneNumberInput(String phoneNumberInput){
        String phoneNumberFieldXpath = PDPMap.getXpathForMAPAspect("A", "Phone number", "phoneNumberField")
        setText(phoneNumberFieldXpath, phoneNumberInput)

        String phoneNumberSubmitButtonXpath = PDPMap.getXpathForMAPAspect("A", "Phone number", "phoneNumberSubmitButton")
        tryClick(phoneNumberSubmitButtonXpath)

        String errorMessageXpath = PDPMap.getXpathForMAPAspect("A", "Phone number", "formError")
        assert waitForElement(errorMessageXpath)
        assert getTextSafe(errorMessageXpath) == 'Please enter a valid phone number in the format 111-222-3333 or 1112223333'
    }*/
}

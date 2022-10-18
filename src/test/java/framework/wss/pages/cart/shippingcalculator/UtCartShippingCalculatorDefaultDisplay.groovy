package framework.wss.pages.cart.shippingcalculator

import above.RunWeb
import org.openqa.selenium.WebElement
import wss.pages.cart.CartBottomCheckout
import wss.pages.cart.ViewCartPage
import wss.user.userurllogin.UserUrlLogin

class UtCartShippingCalculatorDefaultDisplay extends RunWeb{

    static int pbiNumber = 647742

    static String groundItemNumber = "100317001P"
    static String commonCarrierItemNumber = "10918K556"
    
    static void main(String[] args) {
        new UtCartShippingCalculatorDefaultDisplay().testExecute([

                browser      : 'chrome',
                remoteBrowser: false,
//                browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])
    }

    def test() {

        setup('mwestacott', 'Cart - Shipping Calculator - Default Display',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart shipping calculator default display',
                 "tfsTcIds:0", 'logLevel:info'])

        assert openAndTryLoad('hp')

        testByUser('guest')
        testByUser('Regular User')

        assert closeBrowser()
    }

    void testByUser(String userTypeUnderTest){
        assert ableToSetUpTestUser(userTypeUnderTest)

        testByItemNumberAndLiftgate(groundItemNumber, false)
        testByItemNumberAndLiftgate(commonCarrierItemNumber, true)
    }

    void testByItemNumberAndLiftgate(String itemNumber, boolean isLiftgateExpected){
        ViewCartPage viewCartPage = new ViewCartPage()
        assert viewCartPage.navigate()
        assert viewCartPage.emptyCart(true)
        assert viewCartPage.addItemToCart(itemNumber)

        testingUI()
        testingShippingCalculator(false, isLiftgateExpected)
        testingShippingCalculator(true, isLiftgateExpected)
    }

    static boolean ableToSetUpTestUser(String userTypeUnderTest){
        def user = (userTypeUnderTest == 'guest') ? new UserUrlLogin().logOut() : new UserUrlLogin().loginReusableUser(userTypeUnderTest, pbiNumber)
        return user as boolean
    }

    static void testingUI(){
        RunWeb r = run()

        sleep(5000)

        if (r.elementVisible(CartBottomCheckout.changeZipButtonXpath)) {
            assert r.jsClick(CartBottomCheckout.changeZipButtonXpath)
        }

        String shipsToDropdownXpath = CartBottomCheckout.addressTypeSelectXpath
        assert r.waitForElement(shipsToDropdownXpath)

        List<WebElement> optionList = r.selectGetOptions(shipsToDropdownXpath)
        assert optionList.size() == 2
        assert optionList[0].getText() == 'Business'
        assert optionList[1].getText() == 'Residential'

        assert r.verifyElement(CartBottomCheckout.postalCodeXpath)
        Integer actualMaxLength = r.getAttributeSafe(CartBottomCheckout.postalCodeXpath, "maxlength").toInteger()
        assert actualMaxLength == 7

        assert r.verifyElement(CartBottomCheckout.calculateButtonXpath)
    }

    static void testingShippingCalculator(boolean isBusinessAddress, boolean isLiftgateExpected){
        RunWeb r = run()
        CartBottomCheckout cartBottomCheckout = new CartBottomCheckout()
        String shipsToDropdownXpath = CartBottomCheckout.addressTypeSelectXpath

        assert cartBottomCheckout.calculateShipping(isBusinessAddress, "34688")
        assert r.waitForNoElement(shipsToDropdownXpath)

        testingShippingMethodsHaveRadioButtonsAndAreClickable(isLiftgateExpected)

        String expectedShippingMethod = isLiftgateExpected ? "Common Carrier W/ Liftgate" : cartBottomCheckout.getCheapestShippingOption()
        String actualShippingMethod = getSelectedShippingMethod()
        assert (actualShippingMethod == expectedShippingMethod)

        assert cartBottomCheckout.clickChangeButton()
        assert r.waitForElement(shipsToDropdownXpath)

        assert r.isElementContainsText(isBusinessAddress ? "Business" : "Residential", CartBottomCheckout.addressTypeSelectXpath)
        assert r.getAttributeSafe(CartBottomCheckout.postalCodeXpath, "value") == "34688"
    }

    static void testingShippingMethodsHaveRadioButtonsAndAreClickable(boolean isLiftgate){
        RunWeb r = run()
        String radioXpath = "[.//input[@type='radio']]"
        int numberOfShippingOptions = r.findElements(CartBottomCheckout.shippingOptionXpath).size()
        if(isLiftgate){
            assert r.verifyElement(CartBottomCheckout.commonCarrierLiftgateShippingOptionXpath)
            assert r.elementClickable(CartBottomCheckout.commonCarrierLiftgateShippingOptionXpath)
            assert r.verifyElement(CartBottomCheckout.commonCarrierLiftgateShippingOptionXpath + radioXpath)
            numberOfShippingOptions--
        }
        for(int i = 1; i <= numberOfShippingOptions; i++) {
            String individualShippingOptionXpath = "$CartBottomCheckout.shippingOptionXpath[$i]"
            assert r.verifyElement(individualShippingOptionXpath)
            assert r.elementClickable(individualShippingOptionXpath)
            assert r.verifyElement(individualShippingOptionXpath + radioXpath)
        }
    }

    private static String getSelectedShippingMethod(){
        CartBottomCheckout cartBottomCheckout = new CartBottomCheckout()
        List shippingOptionsData = cartBottomCheckout.getShippingOptionsData()
        for(shippingOptionData in shippingOptionsData){
            if(shippingOptionData['isSelected']){
                return shippingOptionData['name']
            }
        }
        return ""
    }
}

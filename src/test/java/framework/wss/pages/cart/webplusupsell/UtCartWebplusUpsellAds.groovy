package framework.wss.pages.cart.webplusupsell

import above.RunWeb
import all.util.Addresses
import wss.pages.cart.CartBottomCheckout
import wss.pages.cart.ViewCartPage
import wss.user.userurllogin.UserUrlLogin

class UtCartWebplusUpsellAds extends RunWeb{
    static void main(String[] args) {
        new UtCartWebplusUpsellAds().testExecute([

                browser      : 'chrome',
                remoteBrowser: false,
//                 browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])

    }
    final int PBI = 604214

    String unitTestProduct = "10207380"

    Addresses alaskaAddress = Addresses.usCommercialAddressAK
    Addresses hawaiiAddress = Addresses.usCommercialAddressHI
    Addresses nonAlaskaHawaiiAddress = Addresses.usCommercialAddressFL

    def test() {

        setup([
                author  : 'mwestacott',
                title   : 'Cart Webplus Upsell Ads | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' cart webplus upsell ads unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        assert openAndTryLoad('hp')
        testingAppearanceOfWebplusUpsellAds(alaskaAddress)
        testingAppearanceOfWebplusUpsellAds(hawaiiAddress)
        testingAppearanceOfWebplusUpsellAds(nonAlaskaHawaiiAddress)
        assert closeBrowser()
    }

    void testingAppearanceOfWebplusUpsellAds(Addresses address){
        String testState = address.getState()
        String uniqueIdentifier = getUniqueIdentifier(testState)
        assert new UserUrlLogin().loginReusableUser("Regular User", PBI, address, null, uniqueIdentifier)

        ViewCartPage viewCartPage = new ViewCartPage()
        assert viewCartPage.emptyCart(true)
        assert viewCartPage.addItemToCart(unitTestProduct, 1)
        assert doesNonPlusUpsellAdAppearAsExpected(testState)
        assert viewCartPage.emptyCart()
    }

    private boolean isStateAlaskaOrHawaii(String testState){
        return testState in [ "Alaska", "AK", "Hawaii", "HI"]
    }

    String getUniqueIdentifier(String testState){
        if (!isStateAlaskaOrHawaii(testState))
        {
            return "_non_alaska_hawaii_address"
        }
        else{
            return "_$testState"
        }
    }

    boolean doesNonPlusUpsellAdAppearAsExpected(String testState){
        RunWeb r = run()
        r.sleep(5000)

        boolean isNonPlusUpsellAdExpectedToAppear = !isStateAlaskaOrHawaii(testState)
        boolean doesNonPlusUpsellAdAppear = r.verifyElement(CartBottomCheckout.nonPlusUpsellAdXpath)
        return (doesNonPlusUpsellAdAppear == isNonPlusUpsellAdExpectedToAppear)
    }
}

package framework.wss.pages.cart.webplusupsell

import above.RunWeb
import all.util.Addresses
import wss.pages.cart.CartBottomCheckout
import wss.pages.cart.ViewCartPage
import wss.user.userurllogin.UserUrlLogin
import wss.api.user.UserCreationApi
import wsstest.cart.webplusupsell.HpCartWebplusUpsellAds

class UtCartWebplusUpsellAdsZipCodeChange extends RunWeb{
    static void main(String[] args) {
        new UtCartWebplusUpsellAdsZipCodeChange().testExecute([

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

    String alaskaAddressZipCode = "99503"
    String hawaiiAddressZipCode = "96814"

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

        String baseZipCode = UserCreationApi.defaultAddress.getZip().substring(0, 5)

        assert HpCartWebplusUpsellAds.getCartWebplusUpsellAdsAlaskaHawaiiZipCodes(5, 'AK')
        assert HpCartWebplusUpsellAds.getCartWebplusUpsellAdsAlaskaHawaiiZipCodes(5, 'HI')

        assert new UserUrlLogin().loginReusableUser("Regular User", PBI, UserCreationApi.defaultAddress, null, "_zip_code_change")

        ViewCartPage viewCartPage = new ViewCartPage()
        assert viewCartPage.emptyCart(true)
        assert viewCartPage.addItemToCart(unitTestProduct, 1)

        testingAppearanceOfWebplusUpsellAds(alaskaAddressZipCode, false)
        testingAppearanceOfWebplusUpsellAds(hawaiiAddressZipCode, false)

        def nonAlaskaHawaiiZipCodes = HpCartWebplusUpsellAds.getCartWebplusUpsellAdsNonAlaskaHawaiiZipCodes(baseZipCode)

        for(nonAlaskaHawaiiZipCode in nonAlaskaHawaiiZipCodes){
            testingAppearanceOfWebplusUpsellAds(nonAlaskaHawaiiZipCode['zipcode'], true)
        }


        assert viewCartPage.emptyCart()

        assert closeBrowser()
    }

    void testingAppearanceOfWebplusUpsellAds(String zipCode, boolean isNonPlusUpsellAdExpectedToAppear){
        RunWeb r = run()
        if(!wasAbleToGetZipCodeShipping(true, zipCode)){
            report("Skipping test for zipcode $zipCode due to failure to calculate shipping.")
        }
        assert r.verifyElement(CartBottomCheckout.nonPlusUpsellAdXpath) == isNonPlusUpsellAdExpectedToAppear
    }

    boolean wasAbleToGetZipCodeShipping(Boolean isBusinessAddress, String zipCode){
        RunWeb r = run()
        CartBottomCheckout cartBottomCheckout = new CartBottomCheckout()

        if(!r.waitForElement(CartBottomCheckout.changeZipButtonXpath)){
            return false
        }

        if(!r.jsClick(CartBottomCheckout.changeZipButtonXpath)){
            return false
        }
        return cartBottomCheckout.calculateShipping(isBusinessAddress, zipCode)
    }
}

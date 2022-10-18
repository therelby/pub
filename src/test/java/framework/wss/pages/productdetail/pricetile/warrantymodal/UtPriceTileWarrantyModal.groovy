package framework.wss.pages.productdetail.pricetile.warrantymodal

import above.RunWeb
import wss.cart.Cart
import wss.pages.productdetail.PDPage
import wss.user.userurllogin.UserUrlLogin
import wsstest.product.productdetailpage.pricetile.warranty.helper.hPDPWarrantyTest

class UtPriceTileWarrantyModal extends RunWeb{
    //These item numbers are used in the event that the cart needs to be checked for expected products

    String rkwLoneProductWithNoWarning = "177WB19L"
    String rkwLoneSuffixProductWithNoWarning = "554C3H830   LP"
    String rkwVirtualGroupingProductWithNoWarning = "177BMFW4"

    String rkwLoneProductWithWarning = "177EG36N"

    String safewareLoneProductWithNoWarning = "622IBM600A"
    String safewareLoneSuffixProductWithNoWarning = "276IMD30015 ASPB"
    String safewareVirtualGroupingProductWithNoWarning = "109DIRCP5"

    String safewareLoneProductWithWarning = "276CNM0630RH"
    String safewareLoneSuffixProductWithWarning = "415DCM500BWH120"
    String safewareVirtualGroupingProductWithWarning = "415DM200BA"

    //The below product URLs are for loading the test pages for the various warranty modal scenarios

    String rkwLoneProductWithNoWarningUrl = "https://www.dev.webstaurantstore.com/avantco-wb19l-5-gallon-80-cup-19-liter-water-boiler-120v-1500w/177WB19L.html"
    String rkwLoneSuffixProductWithNoWarningUrl = "https://www.dev.webstaurantstore.com/backyard-pro-c3h830-30-stainless-steel-outdoor-grill/554C3H830%20%20%20LP.html"
    String rkwVirtualGroupingProductWithNoWarningUrl = "https://www.dev.webstaurantstore.com/avantco-bmfw4-46-electric-bain-marie-buffet-countertop-food-warmer-with-4-half-size-wells-1500w-120v/177BMFW4.html"

    String rkwLoneProductWithWarningUrl = "https://www.dev.webstaurantstore.com/avantco-eg36n-36-electric-countertop-griddle-208-240v-7488w-10080w/177EG36N.html"

    String safewareLoneProductWithNoWarningUrl = "https://www.dev.webstaurantstore.com/polar-temp-ibm600-600-lb-clear-ice-block-maker-120v-9-2-cu-ft/622IBM600A.html"
    String safewareLoneSuffixProductWithNoWarningUrl = "https://www.dev.webstaurantstore.com/cornelius-imd-300-15-aspb-13-lb-capacity-air-cooled-ice-maker-dispenser-push-button-controls/276IMD30015%20ASPB.html"
    String safewareVirtualGroupingProductWithNoWarningUrl = "https://www.dev.webstaurantstore.com/advance-tabco-dircp-5-stainless-steel-five-pan-drop-in-refrigerated-cold-pan-unit/109DIRCP5.html"

    String safewareLoneProductWithWarningUrl = "https://www.dev.webstaurantstore.com/cornelius-cnm0630rh6a-nordic-series-30-remote-condenser-half-size-cube-ice-machine-615-lb/276CNM0630RH.html"
    String safewareLoneSuffixProductWithWarningUrl = "https://www.dev.webstaurantstore.com/hoshizaki-dcm-500bwh-countertop-ice-maker-and-water-dispenser-40-lb-storage-water-cooled/415DCM500BWH120.html"
    String safewareVirtualGroupingProductWithWarningUrl = "https://www.dev.webstaurantstore.com/hoshizaki-dm-200b-manual-fill-modular-countertop-ice-and-water-dispenser-200-lb-115v/415DM200BA.html"

    //constants throughout the testing process
    UserUrlLogin userUrlLogin
    def baseWarrantyModal
    String modalWarrantyXpath
    def warrantyModal

    //Method that gets the test user for each user type
    protected Integer getUserIdBasedOnUserType(String userType){
        switch(userType){
            case "residential":
                return 655
                break
            case "commercial":
                return 989
                break
            case "guest":
                return null
                break
        }
    }

    //Method for determining if user is supposed to be seeing the commercial user modal during testing.
    protected boolean isCommercialUseModalExpectedToAppear(Integer userId, boolean isWarrantyGoingToBeSelected, boolean isSafeware){
        boolean isUserCommercial = (userId==989)
        boolean isUserResidentialAndIsProductSafeware = (userId==655 && isSafeware)
        return isWarrantyGoingToBeSelected && (!isUserCommercial || isUserResidentialAndIsProductSafeware)
    }

    protected void setup(String url, Integer userId, String itemType, boolean isSafeware){
        assert openAndTryLoad("https://www.dev.webstaurantstore.com/plus/")
        userUrlLogin = new UserUrlLogin()
        baseWarrantyModal = new hPDPWarrantyTest(){}
        modalWarrantyXpath = isSafeware ? baseWarrantyModal.externalModalWarranty : baseWarrantyModal.internalModalWarranty

        if(userId == null){
            assert userUrlLogin.logOut()
        }
        else{
            userUrlLogin.loginAs(userId.toString())
            Cart.emptyCart()
        }

        assert tryLoad(url)
        assert new PDPage().verifyItemType(itemType)
    }
}

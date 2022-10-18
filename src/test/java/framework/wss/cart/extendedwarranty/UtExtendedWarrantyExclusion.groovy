package framework.wss.cart.extendedwarranty

import above.RunWeb
import wss.pages.cart.CartItemBox
import wss.pages.cart.CartSuggestedItemBox
import wss.pages.cart.ViewCartPage
import wss.pages.productdetail.PDPPriceTile
import wss.pages.productdetail.PDPage
import wsstest.product.productdetailpage.pricetile.warranty.helper.hPDPWarrantyTest

class UtExtendedWarrantyExclusion extends RunWeb{

    int alaskaUserId = 29403247
    int arizonaUserId = 19396309
    int floridaUserId = 27963077
    int hawaiiUserId = 28553007
    int montanaUserId = 29416011

    int virginIslesUserId = 29215957

    int australiaUserId = 24140575
    int canadaUserId = 29239097
    int mexicoUserId = 28546517
    int spainUserId = 29298701

    static void main(String[] args) {
        new UtExtendedWarrantyExclusion().testExecute([

                browser      : 'chrome',
                remoteBrowser: false,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])
    }

    def test() {
        final int PBI = 567904
        setup([
                author  : 'mwestacott',
                title   : 'Extended Warranty exclusion unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' extended warranty exclusion unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        tryLoad("plus")
        
        testingUsers(arizonaUserId, false)
        testingUsers(floridaUserId, false)
        testingUsers(montanaUserId, false)

        testingUsers(alaskaUserId, true)
        testingUsers(hawaiiUserId, true)

        testingUsers(virginIslesUserId, true)

        testingUsers(australiaUserId, true)
        testingUsers(canadaUserId, true)
        testingUsers(mexicoUserId, true)
        testingUsers(spainUserId, true)

        closeBrowser()
    }

    private void addingProductToCart(){
        PDPage pdPage = new PDPage()
        pdPage.navigateToPDPWithItemNumber("342WRC165DZ")
        assert tryClick(PDPPriceTile.addToCartButtonXpath)

        def baseWarrantyModal = new hPDPWarrantyTest(){}

        String isWarrantyGoingToBeSelectedBaseXpath = baseWarrantyModal.inhouseWarrantyModal.planOptions
        String isWarrantyGoingToBeSelectedContainsNoAdditionalProtectionBaseXpath = "contains(text(), 'No Additional Protection')"
        String isWarrantyGoingToBeSelectedNotHandling = "[not($isWarrantyGoingToBeSelectedContainsNoAdditionalProtectionBaseXpath)]"
        String isWarrantyGoingToBeSelectedXpath = "$isWarrantyGoingToBeSelectedBaseXpath$isWarrantyGoingToBeSelectedNotHandling/input"

        assert tryClick(isWarrantyGoingToBeSelectedXpath)
        assert tryClick(baseWarrantyModal.inhouseWarrantyModal.buttonUpdateCoverage)

        sleep(3000)
        if(verifyElement(baseWarrantyModal.commercialUseOnlyYesCommercialUseOnly)){
            assert tryClick(baseWarrantyModal.commercialUseOnlyYesCommercialUseOnly)
        }
    }

    private void testingUsers(int userId, boolean isMessageExpectedToAppear){
        assert tryLoad("https://www.dev.webstaurantstore.com/?login_as_user=$userId")
        
        ViewCartPage viewCartPage = new ViewCartPage()
        viewCartPage.emptyCart()
        
        addingProductToCart()

        assert viewCartPage.navigate()

        CartItemBox cartItem = new CartItemBox("342WRC165DZ")

        assert cartItem.verifyItemInCart("342WRC165DZ")
        assert isMessageExpectedToAppear ? !cartItem.verifyAccessoryItem() : cartItem.verifyAccessoryItem()
        assert isMessageExpectedToAppear ? doesExtendedWarrantyRemovalMessageAppear() : !doesExtendedWarrantyRemovalMessageAppear()

        viewCartPage.emptyCart()
    }

    private boolean doesExtendedWarrantyRemovalMessageAppear(){
        List messages = new ViewCartPage().getAboveMessagesData()
        for(message in messages){
            String actualMessage = message['message']
            if(actualMessage == ViewCartPage.extendedWarrantyRemovalText){
                return true
            }
        }
        return false
    }

}

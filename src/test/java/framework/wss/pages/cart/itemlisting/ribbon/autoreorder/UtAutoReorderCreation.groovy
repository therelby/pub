package framework.wss.pages.cart.itemlisting.ribbon.autoreorder

import above.RunWeb
import wss.api.user.UserCreationApi
import wss.checkout.Checkout
import wss.pages.account.AutoReorderDetailsPage
import wss.pages.cart.CartItemBox
import wss.pages.cart.ViewCartPage
import wss.user.userurllogin.UserUrlLogin
import wsstest.checkout.confirmorder.autoreorder.AutoReorderUtils
import wss.user.UserType

class UtAutoReorderCreation extends RunWeb{

    static Integer pbiNumber = 605943

    static String currentRunType = 'Debug'
    def parametersForTest
    Map variableForTest = [numberOfDays: 3]

    static void main(String[] args) {
        new UtAutoReorderCreation().testExecute([

                browser      : 'edge',
                remoteBrowser: false,
//                browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : currentRunType,//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])

    }

    def test() {
        parametersForTest = (currentRunType == 'Debug' ? variableForTest : runParams)
        int numberOfDays = parametersForTest['numberOfDays']

        setup('mwestacott', "Cart - Auto Reorder ribbon - ${numberOfDays} days - Active Auto Reorder | Framework Self Testing Tool",
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', "keywords:unit test cart auto reorder ribbon active auto reorder $numberOfDays days",
                 "tfsTcIds:$pbiNumber", 'logLevel:info'])

        runningAutoReorderCreationTest(numberOfDays)
        testingAutoReorderRibbonByDate(numberOfDays)
        closeBrowser()
    }

    def runningAutoReorderCreationTest(int days) {

        def user = new UserUrlLogin().loginReusableUser(UserType.REGULAR_USER, pbiNumber, UserCreationApi.defaultAddress, null, "${days}_day_test")

        if(user == null){
            report("Failed to generate test user regular. Aborting the test.")
            closeBrowser()
            return
        }

        assert wasItemSetupSuccessful(user)
        closeBrowser()
    }

    boolean wasItemSetupSuccessful(def user){

        ViewCartPage viewCartPage = new ViewCartPage()
        if(!viewCartPage.navigate()){
            report(pbiNumber, "Skipping remaining tests due to failure to navigate to cart page.")
            return false
        }
        if(!viewCartPage.emptyCart()){
            report(pbiNumber, "Skipping remaining tests due to failure to empty cart.")
            return false
        }

        return wasItemSetupSuccessfulBasedOnActiveScenario(user)
    }

    boolean wasItemSetupSuccessfulBasedOnActiveScenario(def user){
        Integer userIndex = user['detail']['index'] as Integer
        def testItems = AutoReorderUtils.getAutoReorderedItemsForUser(userIndex)
        if(testItems.size() == 0){
            log("This user doesn't have an active auto-reorder set up. Proceeding with setup.")
            return settingUpAutoReorder()
        }
        else if(testItems.size() == 3){
            for(testItem in testItems){
                String itemNumber = testItem['item_number']
                def designatedTestItems = [HpUtAutoReorderCreation.loneProductForActiveAutoReorder, HpUtAutoReorderCreation.loneSuffixProductForActiveAutoReorder, HpUtAutoReorderCreation.virtualGroupingProductForActiveAutoReorder]

                if(!itemNumber in designatedTestItems){
                    report(pbiNumber,"$itemNumber isn't one of the three designated testing numbers for active auto reorders. Aborting the test.")
                    return false
                }
            }
            log("This user has all of the designated testing item numbers in the active auto reorder. Proceeding to testing.")
            return true
        }
        else if(testItems.size() < 3 && testItems.size() > 0){ //user for whatever reason didn't add all of the items to their cart
            report(pbiNumber,"This user is missing some of the testing item numbers. " +
                    "Item Number #1: ${testItems[0]['item_number']}." +
                    "Item Number #2: ${testItems[1]['item_number']}." +
                    "Item Number #3: ${testItems[2]['item_number']}.")
            return false
        }
        else //edge case in which for whatever reason the user has extra items
        {
            report(pbiNumber,"This user has more than 3 items. Please investigate and make sure any excess items are removed.")
            return false
        }
    }

    boolean settingUpAutoReorder(){
        if(!HpUtAutoReorderCreation.settingUpAutoReorder()){
            report(pbiNumber,"Skipping remaining tests due to failure to add test products to cart")
            return false
        }

        if(!tryLoad("cart")) {
            report(pbiNumber,"Skipping remaining tests due to failure to navigate to cart page.")
            return false
        }

        if(!Checkout.checkoutFromCart()){
            report(pbiNumber,"Skipping remaining tests due to failure to complete the auto-reorder checkout.")
            return false
        }

        return true
    }

    void testingAutoReorderRibbonByDate(int days){
        AutoReorderDetailsPage autoReorderDetailsPage = new AutoReorderDetailsPage()
        def user = new UserUrlLogin().loginReusableUser(UserType.REGULAR_USER, pbiNumber, UserCreationApi.defaultAddress, null, "${days}_day_test")

        Integer userIndex = user['detail']['index'] as Integer
        int autoReorderId = AutoReorderUtils.getAutoReorderedItemsForUser(userIndex)[0]['autoReorderId'] as int
        assert autoReorderDetailsPage.ableToLoadAutoReorderDetailsPage(autoReorderId)
        assert autoReorderDetailsPage.ableToLoadShipmentScheduleModal()

        Date newShippingDate = new Date().plus(days)
        assert autoReorderDetailsPage.ableToSetShippingDateForAutoReorder(newShippingDate)

        testingAutoReorderRibbonByProducts(HpUtAutoReorderCreation.loneProductForActiveAutoReorder, days)
        testingAutoReorderRibbonByProducts(HpUtAutoReorderCreation.loneSuffixProductForActiveAutoReorder, days)
        testingAutoReorderRibbonByProducts(HpUtAutoReorderCreation.virtualGroupingProductForActiveAutoReorder, days)
    }

    void testingAutoReorderRibbonByProducts(String itemNumber, int days){
        ViewCartPage viewCartPage = new ViewCartPage()
        assert viewCartPage.emptyCart(true)
        assert viewCartPage.addItemToCart(itemNumber)

        int currentHour = Calendar.HOUR_OF_DAY

        CartItemBox cartItemBox = new CartItemBox(itemNumber)
        boolean isAutoReorderRibbonExpectedToShowUp = (days == 1 || (days == 2 && (currentHour >= 4)))
        boolean doesAutoReorderRibbonAppearAsExpected = (cartItemBox.verifyAutoReorderRibbon() == isAutoReorderRibbonExpectedToShowUp)

        if(!doesAutoReorderRibbonAppearAsExpected){
            println(takeScreenshot())
            println("Test")
        }

        assert doesAutoReorderRibbonAppearAsExpected
    }

}

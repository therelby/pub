package framework.wss.pages.cart.savedforlater

import above.RunWeb
import all.Money
import wss.pages.cart.CartItemBox
import wss.pages.cart.ViewCartPage
import wss.pages.cart.savedforlater.SavedForLaterItemBox
import wss.pages.cart.savedforlater.SavedForLaterSection
import wsstest.cart.saveforlater.map.HpMapCartSavedForLaterTesting
import wsstest.cart.saveforlater.map.HpMapCartSavedForLaterTestingQueries

class HpUtCartMapSavedForLater extends RunWeb{

    def regularUserWithoutOrderHistory = ['regular', 26126723, false, 0, 0]
    def regularUserWithOrderHistory = ['regular', 387645, true, 1, 0]
    def regularUserWithOrderHistoryOver1000 = ['regular', 12184033, true, 1, 1000.00]
    def platinumUser = ['platinum', 29588089, false, 0, 0]
    def platinumPlusUser = ['platinumPlus', 241217, false, 0, 0]
    def plusUser = ['webplus', 29820173, false, 0, 0]

    def testingWithSpecificProduct(def userForTesting, def mapStyleLItemNumberForTesting, boolean showMapPrice, boolean showPlatinumPlusPrice){
        ViewCartPage viewCartPage = new ViewCartPage()
        SavedForLaterSection savedForLater = new SavedForLaterSection()

        assert viewCartPage.navigate()
        assert viewCartPage.emptyCart()
        assert savedForLater.removeAllItems()

        String itemNumber = mapStyleLItemNumberForTesting[0]

        if(!viewCartPage.addItemToCart(itemNumber)){
            report(0,"Skipping remaining tests due to failure to add product to cart.")
            return
        }

        CartItemBox cartItemBox = new CartItemBox(itemNumber)

        assert cartItemBox.verifySaveForLater()
        if(!cartItemBox.clickSaveForLater()){
            report(0, "Skipping remaining tests due to failure to move product to Save for Later.")
            return
        }

        sleep(3000)

        SavedForLaterSection savedForLaterSection = new SavedForLaterSection()
        int numberOfSavedForLaterProducts = savedForLaterSection.getSavedForLaterItemNumbers().size()
        if(numberOfSavedForLaterProducts != 1){
            report(0, "Skipping remaining tests due to failure to test product by itself.")
            return
        }

        SavedForLaterItemBox savedForLaterItemBox = new SavedForLaterItemBox(itemNumber)
        HpMapCartSavedForLaterTesting hpMapCartSavedForLaterTesting = new HpMapCartSavedForLaterTesting()
        def mapProductForTesting = HpMapCartSavedForLaterTestingQueries.getSpecificMapProductForSavedForLaterSectionTesting(itemNumber)[0]
        Boolean hasOrderHistory = userForTesting[2]
        int numberOfOrders = userForTesting[3]
        double totalAmountSpent = userForTesting[4]

        Money expectedPrice = hpMapCartSavedForLaterTesting.getExpectedPrice(mapProductForTesting, userForTesting[0], showMapPrice, showPlatinumPlusPrice, false, false, hasOrderHistory, totalAmountSpent, numberOfOrders)
        Money actualPrice = savedForLaterItemBox.getPrice()

        assert actualPrice == expectedPrice

        assert viewCartPage.emptyCart()
        assert savedForLater.removeAllItems()

    }
}

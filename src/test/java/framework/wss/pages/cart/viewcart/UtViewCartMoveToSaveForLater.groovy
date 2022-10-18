package framework.wss.pages.cart.viewcart

import above.RunWeb
import wss.pages.cart.ViewCartPage
import wss.user.UserType
import wss.user.userurllogin.UserUrlLogin

class UtViewCartMoveToSaveForLater extends RunWeb {

    static void main(String[] args) {
        new UtViewCartMoveToSaveForLater().testExecute([

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
        final int PBI = 0

        setup([
                author  : 'vdiachuk',
                title   : 'Cart move to save for later | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' cart view save for later item box unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        int quantityOfItems = 3
        def itemNumbers
//        def itemsDB = AutoReorderUtils.getAutoReorderItemAfterApiCheck(quantityOfItems, 'lone')
//        itemNumbers = itemsDB.collect() { it['itemNumber'] }
//        log itemNumbers
        itemNumbers = [
                "124BLSMAV4CO",
                "24525183WBTP",
                "5255667"
        ]
        UserUrlLogin userUrlLogin = new UserUrlLogin()
        userUrlLogin.loginNewUser(UserType.REGULAR_USER, PBI)

        ViewCartPage viewCartPage = new ViewCartPage()
        log viewCartPage.addMultipleItemsToCart(itemNumbers, 2)
        refresh()
        def itemsMovedToSafeForLater = viewCartPage.moveItemsToSaveForLater(itemNumbers)
        assert itemNumbers as Set == itemsMovedToSafeForLater as Set
        closeBrowser()
        //
        // All items method
        //
        userUrlLogin.loginNewUser(UserType.REGULAR_USER, PBI)
        log viewCartPage.addMultipleItemsToCart(itemNumbers, 2)
        refresh()
        def itemsMovedToSafeForLater2 = viewCartPage.moveAllItemsToSaveForLater()
        assert itemNumbers as Set == itemsMovedToSafeForLater2 as Set


    }
}
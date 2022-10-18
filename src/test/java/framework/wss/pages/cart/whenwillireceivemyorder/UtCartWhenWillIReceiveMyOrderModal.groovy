package framework.wss.pages.cart.whenwillireceivemyorder


import above.RunWeb
import wss.pages.cart.CartWhenWillIReceiveMyOrderModal
import wss.pages.cart.ViewCartPage
import wss.user.userurllogin.UserUrlLogin


class UtCartWhenWillIReceiveMyOrderModal extends RunWeb {

    String residentialTestUser = "655"

    def listOfItems = ['267780803', '176PCSCOOP64']
    def listOfItemsName = ['Choice 64 oz. Clear Plastic Utility Scoop', 'Acopa Harmony 7 1/4" 18/8 Stainless Steel Extra Heavy Weight Oval Bowl Dinner / Dessert Spoon - 12/Case']




    static void main(String[] args) {
        new UtCartWhenWillIReceiveMyOrderModal().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    def test() {

        setup(
                [author  : 'nsahin',
                 title   : 'Cart - When Will I Receive My Order Modal',
                 product : 'wss',
                 PBI     : 0,
                 project : 'Webstaurant.StoreFront',
                 keywords: 'Unit test cart when will I receive my order'

                ])

        //login
        openAndTryLoad('hp')
        tryLoad('myaccount')
        UserUrlLogin userUrlLogin = new UserUrlLogin()
        userUrlLogin.loginAs(residentialTestUser)


        log('listOfItemsDB: ' + listOfItems)

        ViewCartPage viewCartPage = new ViewCartPage()
        viewCartPage.emptyCart(true)

        //loop to get to each item to add in the cart
        for (i in 0..<listOfItems.size()) {
            def itemNumber = listOfItems.get(i)
            def addItemToCart = viewCartPage.addItemToCart(itemNumber)
        }

        def whenWillIReceiveMyOrderLinkXpath = ViewCartPage.whenWillIReceiveMyOrderLinkXpath
        waitForElementVisible(whenWillIReceiveMyOrderLinkXpath)
        click(whenWillIReceiveMyOrderLinkXpath)


        CartWhenWillIReceiveMyOrderModal cartWhenWillIReceiveMyOrderModal = new CartWhenWillIReceiveMyOrderModal()

        def actualListOfTabsDataMap = cartWhenWillIReceiveMyOrderModal.getTabsData()
        assert (actualListOfTabsDataMap.size() == 3)
        assert (actualListOfTabsDataMap.get(0).size() == 4)
        assert (actualListOfTabsDataMap.get(1).size() == 4)
        assert (actualListOfTabsDataMap.get(2).size() == 4)


        assert (cartWhenWillIReceiveMyOrderModal.clickTabByName('Ground'))
        assert (cartWhenWillIReceiveMyOrderModal.clickTabByName('Second Day'))
        assert (cartWhenWillIReceiveMyOrderModal.clickTabByName('Next Day'))


        def groundTabListOfMap = cartWhenWillIReceiveMyOrderModal.getTabDataByName('Ground')
        def secondDayTabListOfMap = cartWhenWillIReceiveMyOrderModal.getTabDataByName('Second Day')
        def nextDayTabListOfMap = cartWhenWillIReceiveMyOrderModal.getTabDataByName('Next Day')

        println('groundTabListOfMap:' + groundTabListOfMap)
        println('secondDayTabListOfMap:' + secondDayTabListOfMap)
        println('nextDayTabListOfMap:' + nextDayTabListOfMap)

        def listOfGroundItems = groundTabListOfMap.collect{ it->it.get('itemDescription')}
        def listOfSecondDayItems = secondDayTabListOfMap.collect{ it->it.get('itemDescription')}
        def listOfNextDayItems = nextDayTabListOfMap.collect{ it->it.get('itemDescription')}

        assert (listOfItemsName == listOfGroundItems)
        assert (listOfItemsName == listOfSecondDayItems)
        assert (listOfItemsName == listOfNextDayItems)

        closeBrowser()

    }


}


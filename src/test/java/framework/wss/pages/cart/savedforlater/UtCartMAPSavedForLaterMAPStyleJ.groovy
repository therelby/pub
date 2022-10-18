package framework.wss.pages.cart.savedforlater

import wss.user.userurllogin.UserUrlLogin

class UtCartMAPSavedForLaterMAPStyleJ extends HpUtCartMapSavedForLater{

    def mapStyleJMapOverrideItemNumberForTesting = ['123ABB60']
    def mapStyleJNoOverridesItemNumberForTesting = ['4254031']

    static void main(String[] args) {
        new UtCartMAPSavedForLaterMAPStyleJ().testExecute([

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

        setup('mwestacott', 'Cart - MAP - Saved For Later - MAP Style J Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart map saved for later map style j',
                 "tfsTcIds:0", 'logLevel:info'])

        assert openAndTryLoad('plus')

        testingWithSpecificUser(regularUserWithoutOrderHistory)
        testingWithSpecificUser(regularUserWithOrderHistory)
        testingWithSpecificUser(regularUserWithOrderHistoryOver1000)
        testingWithSpecificUser(platinumUser)
        testingWithSpecificUser(platinumPlusUser)
        testingWithSpecificUser(plusUser)

        closeBrowser()
    }

    def testingWithSpecificUser(def userForTesting){
        assert new UserUrlLogin().loginAs(userForTesting[1].toString())

        testingWithSpecificProduct(userForTesting, mapStyleJMapOverrideItemNumberForTesting, true, false)
        testingWithSpecificProduct(userForTesting, mapStyleJNoOverridesItemNumberForTesting, false, false)
    }
}

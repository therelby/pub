package framework.wss.pages.cart.savedforlater

import wss.user.userurllogin.UserUrlLogin

class UtCartMAPSavedForLaterMAPStyleL extends HpUtCartMapSavedForLater{

    def mapStyleLBothOverridesItemNumberForTesting = ['333DGF160FF']
    def mapStyleLMapOverrideItemNumberForTesting = ['1311000B296A']
    def mapStyleLNoOverridesItemNumberForTesting = ['519KCM4212SX']
    def mapStyleLPlatinumOverrideItemNumberForTesting = ['9296001C']

    static void main(String[] args) {
        new UtCartMAPSavedForLaterMAPStyleL().testExecute([

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

        setup('mwestacott', 'Cart - MAP - Saved For Later - MAP Style L Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart map saved for later map style l',
                 "tfsTcIds:0", 'logLevel:info'])

        assert openAndTryLoad('plus')

        testingWithSpecificUser(regularUserWithoutOrderHistory)
        testingWithSpecificUser(regularUserWithOrderHistory)
        testingWithSpecificUser(platinumUser)
        testingWithSpecificUser(platinumPlusUser)
        testingWithSpecificUser(plusUser)

        closeBrowser()
    }

    def testingWithSpecificUser(def userForTesting){
        assert new UserUrlLogin().loginAs(userForTesting[1].toString())

        testingWithSpecificProduct(userForTesting, mapStyleLBothOverridesItemNumberForTesting, true, true)
        testingWithSpecificProduct(userForTesting, mapStyleLMapOverrideItemNumberForTesting, true, false)
        testingWithSpecificProduct(userForTesting, mapStyleLNoOverridesItemNumberForTesting, false, false)
        testingWithSpecificProduct(userForTesting, mapStyleLPlatinumOverrideItemNumberForTesting, false, true)
    }
}

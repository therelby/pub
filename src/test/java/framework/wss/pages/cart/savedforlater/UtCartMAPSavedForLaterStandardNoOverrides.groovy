package framework.wss.pages.cart.savedforlater

import wss.user.userurllogin.UserUrlLogin

class UtCartMAPSavedForLaterStandardNoOverrides extends HpUtCartMapSavedForLater{

    def mapStyleDNoOverridesItemNumberForTesting = ['464104577928']
    def mapStyleGNoOverridesItemNumberForTesting = ['597OSD30']
    def mapStyleINoOverridesItemNumberForTesting = ['415KM80BAJA']
    def mapStyleNNoOverridesItemNumberForTesting = ['6456070107']
    def mapStyleONoOverridesItemNumberForTesting = ['19510481L']
    def mapStylePNoOverridesItemNumberForTesting = ['345CD3628']
    def mapStyleQNoOverridesItemNumberForTesting = ['91607943']
    def mapStyleTNoOverridesItemNumberForTesting = ['232A2000']
    def mapStyleXNoOverridesItemNumberForTesting = ['7872SSB5EFB']

    static void main(String[] args) {
        new UtCartMAPSavedForLaterStandardNoOverrides().testExecute([

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

        setup('mwestacott', 'Cart - MAP - Saved For Later - Standard - No Overrides Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart map saved for later standard no overrides',
                 "tfsTcIds:0", 'logLevel:info'])

        assert openAndTryLoad('plus')

        testingWithSpecificUser(regularUserWithoutOrderHistory)
        testingWithSpecificUser(platinumUser)
        testingWithSpecificUser(platinumPlusUser)
        testingWithSpecificUser(plusUser)

        closeBrowser()
    }

    def testingWithSpecificUser(def userForTesting){
        assert new UserUrlLogin().loginAs(userForTesting[1].toString())

        testingWithSpecificProduct(userForTesting, mapStyleDNoOverridesItemNumberForTesting, false, false)
        testingWithSpecificProduct(userForTesting, mapStyleGNoOverridesItemNumberForTesting, false, false)
        testingWithSpecificProduct(userForTesting, mapStyleINoOverridesItemNumberForTesting, false, false)
        testingWithSpecificProduct(userForTesting, mapStyleNNoOverridesItemNumberForTesting, false, false)
        testingWithSpecificProduct(userForTesting, mapStyleONoOverridesItemNumberForTesting, false, false)
        testingWithSpecificProduct(userForTesting, mapStylePNoOverridesItemNumberForTesting, false, false)
        testingWithSpecificProduct(userForTesting, mapStyleQNoOverridesItemNumberForTesting, false, false)
        testingWithSpecificProduct(userForTesting, mapStyleTNoOverridesItemNumberForTesting, false, false)
        testingWithSpecificProduct(userForTesting, mapStyleXNoOverridesItemNumberForTesting, false, false)
    }
}

package framework.wss.pages.cart.savedforlater

import wss.user.userurllogin.UserUrlLogin

class UtCartMAPSavedForLaterQuantityDiscountMapOverride extends HpUtCartMapSavedForLater{

    def mapStyleDMapOverrideQuantityDiscountItemNumberForTesting = ['196100280']
    def mapStyleHMapOverrideQuantityDiscountItemNumberForTesting = ['479G5SEMI']
    def mapStyleNMapOverrideQuantityDiscountItemNumberForTesting = ['742GD2']
    def mapStyleQMapOverrideQuantityDiscountItemNumberForTesting = ['239BH4212']
    def mapStyleTMapOverrideQuantityDiscountItemNumberForTesting = ['136TASBA']

    static void main(String[] args) {
        new UtCartMAPSavedForLaterQuantityDiscountMapOverride().testExecute([

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

        testingWithSpecificProduct(userForTesting, mapStyleDMapOverrideQuantityDiscountItemNumberForTesting, true, false)
        testingWithSpecificProduct(userForTesting, mapStyleHMapOverrideQuantityDiscountItemNumberForTesting, true, false)
        testingWithSpecificProduct(userForTesting, mapStyleNMapOverrideQuantityDiscountItemNumberForTesting, true, false)
        testingWithSpecificProduct(userForTesting, mapStyleQMapOverrideQuantityDiscountItemNumberForTesting, true, false)
        testingWithSpecificProduct(userForTesting, mapStyleTMapOverrideQuantityDiscountItemNumberForTesting, true, false)
    }
}

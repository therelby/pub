package framework.wss.pages.cart.savedforlater

import wss.user.userurllogin.UserUrlLogin

class UtCartMAPSavedForLaterQuantityDiscountNoOverrides extends HpUtCartMapSavedForLater{

    def mapStyleDNoOverridesQuantityDiscountItemNumberForTesting = ['8008DHC25']
    def mapStyleTNoOverridesQuantityDiscountItemNumberForTesting = ['353MASH0302']

    static void main(String[] args) {
        new UtCartMAPSavedForLaterQuantityDiscountNoOverrides().testExecute([

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

        testingWithSpecificProduct(userForTesting, mapStyleDNoOverridesQuantityDiscountItemNumberForTesting, false, false)
        testingWithSpecificProduct(userForTesting, mapStyleTNoOverridesQuantityDiscountItemNumberForTesting, false, false)
    }
}

package framework.wss.pages.cart.savedforlater

import wss.user.userurllogin.UserUrlLogin

class UtCartMAPSavedForLaterStandardPlatinumOverride extends HpUtCartMapSavedForLater{

    def mapStyleIPlatinumOverrideItemNumberForTesting = ['36911814ECC']
    def mapStyleZPlatinumOverrideItemNumberForTesting = ['2141000LCDCB']

    static void main(String[] args) {
        new UtCartMAPSavedForLaterStandardPlatinumOverride().testExecute([

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

        setup('mwestacott', 'Cart - MAP - Saved For Later - Standard - Both Overrides Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart map saved for later standard both overrides',
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

        testingWithSpecificProduct(userForTesting, mapStyleIPlatinumOverrideItemNumberForTesting, false, true)
        testingWithSpecificProduct(userForTesting, mapStyleZPlatinumOverrideItemNumberForTesting, false, true)
    }
}

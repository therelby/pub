package framework.wss.pages.cart.savedforlater

import wss.user.userurllogin.UserUrlLogin

class UtCartMAPSavedForLaterStandardMapOverride extends HpUtCartMapSavedForLater{

    def mapStyleBMapOverrideItemNumberForTesting = ['119GPC14DLP']
    def mapStyleCMapOverrideItemNumberForTesting = ['338ECS10WHBK']
    def mapStyleDMapOverrideItemNumberForTesting = ['140ACE14V']
    def mapStyleHMapOverrideItemNumberForTesting = ['654602524']
    def mapStyleKMapOverrideItemNumberForTesting = ['383US003599']
    def mapStyleMMapOverrideItemNumberForTesting = ['467EFLLC']
    def mapStyleNMapOverrideItemNumberForTesting = ['1151ND400']
    def mapStylePMapOverrideItemNumberForTesting = ['185406068A']
    def mapStyleQMapOverrideItemNumberForTesting = ['120FFB1250']
    def mapStyleTMapOverrideItemNumberForTesting = ['133J162A3']
    def mapStyleWMapOverrideItemNumberForTesting = ['740B3424E3A']
    def mapStyleXMapOverrideItemNumberForTesting = ['386111PLSRW']
    def mapStyleZMapOverrideItemNumberForTesting = ['943070701']

    static void main(String[] args) {
        new UtCartMAPSavedForLaterStandardMapOverride().testExecute([

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

        testingWithSpecificProduct(userForTesting, mapStyleBMapOverrideItemNumberForTesting, true, false)
        testingWithSpecificProduct(userForTesting, mapStyleCMapOverrideItemNumberForTesting, true, false)
        testingWithSpecificProduct(userForTesting, mapStyleDMapOverrideItemNumberForTesting, true, false)
        testingWithSpecificProduct(userForTesting, mapStyleHMapOverrideItemNumberForTesting, true, false)
        testingWithSpecificProduct(userForTesting, mapStyleKMapOverrideItemNumberForTesting, true, false)
        testingWithSpecificProduct(userForTesting, mapStyleMMapOverrideItemNumberForTesting, true, false)
        testingWithSpecificProduct(userForTesting, mapStyleNMapOverrideItemNumberForTesting, true, false)
        testingWithSpecificProduct(userForTesting, mapStylePMapOverrideItemNumberForTesting, true, false)
        testingWithSpecificProduct(userForTesting, mapStyleQMapOverrideItemNumberForTesting, true, false)
        testingWithSpecificProduct(userForTesting, mapStyleTMapOverrideItemNumberForTesting, true, false)
        testingWithSpecificProduct(userForTesting, mapStyleWMapOverrideItemNumberForTesting, true, false)
        testingWithSpecificProduct(userForTesting, mapStyleXMapOverrideItemNumberForTesting, true, false)
        testingWithSpecificProduct(userForTesting, mapStyleZMapOverrideItemNumberForTesting, true, false)
    }
}

package framework.wss.pages.cart.savedforlater

import above.RunWeb
import wss.item.ItemUtil
import wsstest.cart.saveforlater.map.HpMapCartSavedForLaterTestingQueries

class UtCartMAPSavedForLaterQuery extends RunWeb{

    static void main(String[] args) {
        new UtCartMAPSavedForLaterQuery().testExecute([

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

        setup('mwestacott', 'Cart - MAP Products - Save for Later - Missing product check',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart map saved for later missing product check',
                 "tfsTcIds:0", 'logLevel:info'])

        def inactiveProducts = HpMapCartSavedForLaterTestingQueries.getMapProductsForSavedForLaterSectionTesting("Lone", false, false, false, false, false)
        def activeProducts = HpMapCartSavedForLaterTestingQueries.getMapProductsForSavedForLaterSectionTesting("Lone", false, false, false, false, true)

        assert inactiveProducts
        assert activeProducts
    }
}

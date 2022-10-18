package framework.wss.pages.cart.availability

import above.RunWeb
import wss.item.ItemUtil

class UtCartAvailabilityQuery extends RunWeb{

    static void main(String[] args) {
        new UtCartAvailabilityQuery().testExecute([

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

        setup('mwestacott', 'Cart - Product Availability Query',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart product availability query',
                 "tfsTcIds:0", 'logLevel:info'])

        assert ItemUtil.getProductsBasedOnAvailability(5, 'Lone', false).size() == 5
        assert ItemUtil.getProductsBasedOnAvailability(5, 'Lone Suffix', false).size() == 5
        assert ItemUtil.getProductsBasedOnAvailability(5, 'Virtual Grouping', false).size() == 5

        assert ItemUtil.getProductsBasedOnAvailability(5, 'Lone', true).size() == 5
        assert ItemUtil.getProductsBasedOnAvailability(5, 'Lone Suffix', true).size() == 5
        assert ItemUtil.getProductsBasedOnAvailability(5, 'Virtual Grouping', true).size() == 5
    }
}

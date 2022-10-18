package framework.wss.pages.cart.itemlisting

import above.RunWeb
import wss.item.ItemUtil

class UtCartMinMustBuy extends RunWeb{
    static void main(String[] args) {
        new UtCartMinMustBuy().testExecute([

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

        setup('mwestacott', 'Cart - MAP - Min Must Buy | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart map min must buy',
                 "tfsTcIds:0", 'logLevel:info'])

        //assert openAndTryLoad('plus')

        assert ItemUtil.getProductsMinMustBuy(5, "Lone", false, false, false).size() == 5
        assert ItemUtil.getProductsMinMustBuy(5, "Lone", false, true, false).size() == 5
        assert ItemUtil.getProductsMinMustBuy(5, "Lone", true, false, false).size() == 5
        assert ItemUtil.getProductsMinMustBuy(5, "Lone", true, true, false).size() == 5

        assert ItemUtil.getProductsMinMustBuy(1, "Lone Suffix", false, false, false).size() == 1
        assert ItemUtil.getProductsMinMustBuy(1, "Lone Suffix", false, true, false).size() == 1
        assert ItemUtil.getProductsMinMustBuy(1, "Lone Suffix", true, false, false).size() == 1
        assert ItemUtil.getProductsMinMustBuy(1, "Lone Suffix", true, true, false).size() == 1

        assert ItemUtil.getProductsMinMustBuy(1, "Virtual Grouping", false, false, false).size() == 1
        assert ItemUtil.getProductsMinMustBuy(1, "Virtual Grouping", false, true, false).size() == 1
        assert ItemUtil.getProductsMinMustBuy(1, "Virtual Grouping", true, false, false).size() == 0
        assert ItemUtil.getProductsMinMustBuy(1, "Virtual Grouping", true, true, false).size() == 1

        //closeBrowser()
    }
}

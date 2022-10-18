package framework.wss.pages.cart.itemlisting

import above.RunWeb
import wss.item.ItemUtil
import wss.pages.cart.ViewCartPage

class UtCartNeitherMinNorMustBuy extends RunWeb{
    static void main(String[] args) {
        new UtCartNeitherMinNorMustBuy().testExecute([

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

        assert openAndTryLoad('cart')

        ViewCartPage viewCartPage = new ViewCartPage()
        HpUtCartMinMustBuy hpUtCartMinMustBuy = new HpUtCartMinMustBuy()

        assert viewCartPage.isCartEmpty()

        hpUtCartMinMustBuy.testingAnItemNumber("155P48BLD")
        hpUtCartMinMustBuy.testingAnItemNumber("131ED2SY72PL208/240")
        hpUtCartMinMustBuy.testingAnItemNumber("495NO66LRLC")

        closeBrowser()
    }
}

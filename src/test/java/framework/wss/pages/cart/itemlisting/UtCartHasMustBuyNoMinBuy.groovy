package framework.wss.pages.cart.itemlisting

import above.RunWeb
import wss.pages.cart.ViewCartPage

class UtCartHasMustBuyNoMinBuy extends RunWeb{
    static void main(String[] args) {
        new UtCartHasMustBuyNoMinBuy().testExecute([

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

        hpUtCartMinMustBuy.testingItemNumberWithMinOrMustBuy("32641610", 1, 12)
        hpUtCartMinMustBuy.testingItemNumberWithMinOrMustBuy("128NC8168   COMBO150", 1, 15)
        hpUtCartMinMustBuy.testingItemNumberWithMinOrMustBuy("87917042", 1, 6)

        closeBrowser()
    }
}

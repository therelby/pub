package framework.wss.pages.cart.itemlisting

import above.RunWeb
import wss.pages.cart.ViewCartPage

class UtCartBothMinAndMustBuy extends RunWeb{
    static void main(String[] args) {
        new UtCartBothMinAndMustBuy().testExecute([

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

        hpUtCartMinMustBuy.testingItemNumberWithMinOrMustBuy("8441451R3GY", 20, 20)
        hpUtCartMinMustBuy.testingItemNumberWithMinOrMustBuy("8081073R", 9, 3)
        hpUtCartMinMustBuy.testingItemNumberWithMinOrMustBuy("92290083", 252, 3)
        hpUtCartMinMustBuy.testingItemNumberWithMinOrMustBuy("130SVFFKS50SKIT", 10, 10)
        hpUtCartMinMustBuy.testingItemNumberWithMinOrMustBuy("755M95408W", 13, 13)

        closeBrowser()
    }
}

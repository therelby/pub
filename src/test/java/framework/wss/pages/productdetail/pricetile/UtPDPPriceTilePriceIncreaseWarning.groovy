package framework.wss.pages.productdetail.pricetile

import above.RunWeb
import wss.pages.productdetail.PDPPriceTile
import wss.pages.productdetail.PDPage

class UtPDPPriceTilePriceIncreaseWarning extends RunWeb {
    static void main(String[] args) {
        new UtPDPPriceTilePriceIncreaseWarning().testExecute([
                browser      : 'chrome',
                remoteBrowser: true,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'evdovina',
                title   : 'Price Tile Price Increase Warning Block | PDP Unit Test',
                PBI     : 711392,
                product : 'wss|dev',
                project : 'Webstaurant.StoreFront',
                keywords: 'PDP Price Increase Warning Price Tile',
                logLevel: 'info'
        ])

        String itemNumberWithPriceIncreaseWarning = "670CD45ALM"
        testCondition1(itemNumberWithPriceIncreaseWarning)

        String itemNumberWithoutPriceIncreaseWarning = "200909096"
        testCondition2(itemNumberWithoutPriceIncreaseWarning)
    }

    void testCondition1(String itemNumberWithPriceIncreaseWarning) {
        PDPage pdPage = new PDPage()
        pdPage.navigateToPDPWithItemNumber(itemNumberWithPriceIncreaseWarning)

        PDPPriceTile pdpPriceTile = new PDPPriceTile()
        assert pdpPriceTile.isPriceIncreaseWarningBlockPresent()
        assert pdpPriceTile.isPriceIncreaseWarningIconPresent()
        assert pdpPriceTile.isPriceIncreaseWarningMessagePresent()
        assert pdpPriceTile.getPriceIncreaseWarningData().size() == 4
    }

    void testCondition2(String itemNumberWithoutPriceIncreaseWarning) {
        PDPage pdPage = new PDPage()
        pdPage.navigateToPDPWithItemNumber(itemNumberWithoutPriceIncreaseWarning)

        PDPPriceTile pdpPriceTile = new PDPPriceTile()
        assert !pdpPriceTile.isPriceIncreaseWarningBlockPresent()
        assert !pdpPriceTile.isPriceIncreaseWarningIconPresent()
        assert !pdpPriceTile.isPriceIncreaseWarningMessagePresent()
        assert pdpPriceTile.getPriceIncreaseWarningData()?.getAt('StartPercentIncrease') == null
    }
}
package framework.wss.pages.productdetail.pricetile

import above.RunWeb
import wss.pages.productdetail.PDPPriceTile
import wss.pages.productdetail.PDPage

class UtPDPPriceTileDividePrice extends RunWeb {
    static void main(String[] args) {
        new UtPDPPriceTileDividePrice().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'evdovina',
                title   : 'Price Tile Divide Price | PDP Unit Test',
                PBI     : 538385,
                product : 'wss|dev',
                project : 'Webstaurant.StoreFront',
                keywords: 'PDP Divide Price Price Tile',
                logLevel: 'info'
        ])

        String itemNumberWithDividePrice = "18315301476"
        testCondition1(itemNumberWithDividePrice)

        String itemNumberWithoutDividePrice = "15412204"
        testCondition2(itemNumberWithoutDividePrice)

    }

    void testCondition1(String itemNumberWithDividePrice) {
        PDPage pdPage = new PDPage()
        pdPage.navigateToPDPWithItemNumber(itemNumberWithDividePrice)

        PDPPriceTile pdpPriceTile = new PDPPriceTile()
        def dividePriceData = pdpPriceTile.getDividePriceData()
        assert dividePriceData.size() == 3

        def bestDividePriceData =  pdpPriceTile.getBestDividePriceData()
        assert bestDividePriceData.size() == 1
    }

    void testCondition2(String itemNumberWithoutDividePrice) {
        PDPage pdPage = new PDPage()
        pdPage.navigateToPDPWithItemNumber(itemNumberWithoutDividePrice)

        PDPPriceTile pdpPriceTile = new PDPPriceTile()
        def dividePriceData = pdpPriceTile.getDividePriceData()
        assert dividePriceData.size() == 0

        def bestDividePriceData =  pdpPriceTile.getBestDividePriceData()
        assert bestDividePriceData.size() == 1
    }
}
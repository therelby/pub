package framework.wss.pages.productdetail.pricetile

import above.RunWeb
import wss.pages.productdetail.PDPPriceTile

class UtPriceTileGetMinBuyData extends RunWeb {

    static void main(String[] args) {
        new UtPriceTileGetMinBuyData().testExecute([

                browser      : 'chrome',
                remoteBrowser: false,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])
    }

    def test() {
        final int PBI = 523214
        setup([
                author  : 'vdiachuk',
                title   : 'PDP, Price Tile Get Min Buy selector data unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' pdp product detail page tile min must buy selector data unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        tryLoad("/american-tables-seating-as-42t-wall-button-tufted-back-wall-bench-42-high/132AS42TW.html")

        PDPPriceTile pdpPriceTile = new PDPPriceTile()
        def minBuySelectData = pdpPriceTile.getMinBuyData()

        assert minBuySelectData.size() > 3
        def minBuyActive = minBuySelectData.findAll() { it['disabled'] == false && it['selected'] == false }
        log minBuyActive
        def option = minBuyActive.shuffled()?.getAt(0)
        log option['index']

        // log   selectByIndex(PDPPriceTile.selectMinBuyQuantityXpath, option['index'])
        assert pdpPriceTile.setMinBuyQuantityByIndex(0)
        assert pdpPriceTile.setMinBuyQuantityByIndex(option['index'])
        assert pdpPriceTile.setMinBuyQuantityRandom()

        tryLoad("/wabash-valley-cm230q-camino-42-round-square-perforated-top-surface-mount-plastisol-coated-aluminum-outdoor-ada-accessible-umbrella-table-with-3-attached-chairs/947CM230Q.html")
        assert !pdpPriceTile.setMinBuyQuantityByIndex(0)
        assert pdpPriceTile.setMinBuyQuantityRandom()
    }
}

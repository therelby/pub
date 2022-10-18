package framework.wss.pages.productdetail.pricetile

import above.RunWeb
import wss.pages.productdetail.PDPPriceTile

class UtPriceTileWssCredit extends RunWeb {

    static void main(String[] args) {
        new UtPriceTileWssCredit().testExecute([

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
                title   : 'PDP, Price Tile Wss Credit Card data unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' pdp product detail page tile Wss Credit Card unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        PDPPriceTile pdpPriceTile = new PDPPriceTile()
        // with wss credit card
        tryLoad("/aarco-adc3624iba-36-x-24-enclosed-hinged-locking-1-door-bronze-anodized-aluminum-indoor-lighted-message-center-with-black-letter-board/116ADC3624IZ.html")
        assert pdpPriceTile.getWssCreditBackDollarValue() > 0
        assert pdpPriceTile.getWssCreditPointValue() > 0

        // no wss credit card
        tryLoad()
        assert pdpPriceTile.getWssCreditBackDollarValue() == null
        log pdpPriceTile.getWssCreditPointValue()
    }
}

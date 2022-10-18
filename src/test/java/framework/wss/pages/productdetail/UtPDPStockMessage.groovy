package framework.wss.pages.productdetail

import above.RunWeb
import wss.pages.productdetail.PDPage

class UtPDPStockMessage extends RunWeb {
    static void main(String[] args) {
        new UtPDPStockMessage().testExecute([

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
        setup([
                author  : 'vdiachuk',
                title   : 'PDP get Stock Message on  Product Detail Page unit test | Framework Self Testing Tool',
                PBI     : 0,
                product : 'wss|test,dev',
                project : 'Webstaurant.StoreFront',
                keywords: ' pdp product detail page vg virtual grouping stock message unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        PDPage pdPage = new PDPage()
        // in highlights
        tryLoad("https://www.dev.webstaurantstore.com/belt-dress-leather-cowhid/169750046.html")
        assert pdPage.getStockMessage('highlights') == 'Usually Ships in 1-2 Business Days'
        assert pdPage.getStockMessage() == 'Usually Ships in 1-2 Business Days'
        //    getNodeText(PDPage.stockMessageNoQuickShippingXpath)
        // with Quick shipping
        tryLoad('https://www.dev.webstaurantstore.com/mercer-culinary-unisex-black-millenia-chef-pants-small/47060050BKS.html')
        assert pdPage.getStockMessage('quick shipping')== 'Usually ships in 1 business day'
        assert pdPage.getStockMessage("fakeLocation") == null
        //  log getTextSafe(PDPage.stockMessageQuickShippingXpath)
    }
}



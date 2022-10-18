package framework.wss.pages.productdetail

import above.RunWeb
import wss.pages.productdetail.PDPCompatibleModels

class UtPDPCompatibleModels extends RunWeb {
    static void main(String[] args) {
        new UtPDPCompatibleModels().testExecute([

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
        final int PBI = 546005
        setup([
                author  : 'vdiachuk',
                title   : 'PDP, Compatible models block unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' pdp product detail page compatible model unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        // with block
        tryLoad("/choice-6-deep-full-size-stainless-steel-spillage-pan/176SSPILLFL.html")

        PDPCompatibleModels pdpCompatibleModels = new PDPCompatibleModels()
        assert pdpCompatibleModels.isCompatibleModels()
        assert pdpCompatibleModels.getTooltipText() == PDPCompatibleModels.tooltipText
        assert pdpCompatibleModels.getTooltipHeaderText() == PDPCompatibleModels.tooltipHeader
        log pdpCompatibleModels.getItemsData()

        //items with no links
        tryLoad('/beverage-air-502-434d-switch/HP502434D.html')
        log pdpCompatibleModels.getItemsData()
        // without block
        tryLoad()
        assert !pdpCompatibleModels.isCompatibleModels()
        assert pdpCompatibleModels.getTooltipText() == ""
        assert pdpCompatibleModels.getTooltipHeaderText() == ""
    }
}

package framework.wss.pages.productdetail.qualityrating

import above.RunWeb
import wss.pages.productdetail.PDPQualityRatingBlock


class UtPDPQualityRatingBlock extends RunWeb {

    static void main(String[] args) {
        new UtPDPQualityRatingBlock().testExecute([

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
        final int PBI = 711933
        setup([
                author  : 'ikomarov',
                title   : 'PDP, Quality Rating Block unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' pdp product detail page quality rating block unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        PDPQualityRatingBlock pdpQualityRatingBlock = new PDPQualityRatingBlock()

        tryLoad("https://www.dev.webstaurantstore.com/hatco-c-39-compact-booster-water-heater-208v-1-phase-39-kw/413C39B.html")
        assert pdpQualityRatingBlock.isQualityRatingBlock()
        assert pdpQualityRatingBlock.getQualityRatingTitle() == PDPQualityRatingBlock.qualityRatingMessage
        assert pdpQualityRatingBlock.getGBBRibbonText() == "Better"
        closeBrowser()
    }
}

package framework.wss.pages.productdetail.review

import above.RunWeb
import wss.pages.productdetail.PDPReview

class UtPDPReviewGetData extends RunWeb {
    static void main(String[] args) {
        new UtPDPReviewGetData().testExecute([

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

        setup('vdiachuk', 'PDPReview on  Product Detail Page, Get Reviews Data  unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test review rating pdpage pdp product detail page ',
                 "tfsTcIds:265471",
                 'logLevel:info'])

        tryLoad("https://www.dev.webstaurantstore.com/avantco-a-19r-hc-29-solid-door-reach-in-refrigerator/178A19RHC.html")
        PDPReview pdpReview = new PDPReview()
        def data = pdpReview.getPresentReviewsData()
        assert data.size() > 4
        //    log data

        //     log data.findAll(){it["rating"]==1}.collect(){it["author"]}


        log "--"
        tryLoad("https://www.dev.webstaurantstore.com/ecochoice-8-oz-smooth-double-wall-kraft-compostable-paper-hot-cup-case/5008DWPLAKR.html")
        assert pdpReview.getPresentReviewsData() == []

        log "--"
        tryLoad("homepage")
        log pdpReview.getPresentReviewsData()
        assert pdpReview.getPresentReviewsData() == []

        // read more link
        tryLoad("https://www.dev.webstaurantstore.com/vollrath-55064-replacement-1-2-dicing-blade-assembly-for-55002-redco-instacut-5-0-fruit-and-vegetable-dicer/92255064.html")
        data = pdpReview.getPresentReviewsData()
        log data

        assert data[0].getAt("textAfterReadMore").contains("While the quality of Vollrath's product")
        assert data[0].getAt("readMorePresent") == true

        assert data[2].getAt("readMorePresent") == false
        assert data[2].getAt("textAfterReadMore") == null


    }
}

package framework.wss.pages.productdetail.review

import above.RunWeb
import wss.pages.productdetail.PDPReview

class UtPDPRating extends RunWeb {
    static void main(String[] args) {
        new UtPDPRating().testExecute([

                browser      : 'chrome',// 'remotechrome-lt',//'chrome',//'edge',//'chrome',//'safari'
                remoteBrowser: false,//true,//false,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                //   parallelThreads: 1,
                //  runType: 'Regular' ,
                //  browserVersionOffset: -1
        ])
    }

    def test() {

        setup('vdiachuk', 'PDPReview RATING Block on  Product Detail Page  unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test review rating pdpage pdp product detail page ',
                 "tfsTcIds:265471",
                 'logLevel:info'])

        // PDP - with Rating module
        PDPReview pdpReview = new PDPReview()

        tryLoad("https://www.dev.webstaurantstore.com/avantco-a-19r-hc-29-solid-door-reach-in-refrigerator/178A19RHC.html")

        // log pdpReview.getReviewsQuantityRatingBlock()
        assert pdpReview.getReviewsQuantityRatingBlock() > 0
        assert pdpReview.getRatingRatingBlockText() == 3.5
        assert pdpReview.getKeywordTagsRatingBlock().size() > 4
        assert pdpReview.getRatingStarsRatingBlock() == 4
        def centralPartData = pdpReview.getRatingBlockCentralPartData()
        log "" + centralPartData
        assert centralPartData.size() == 5
        assert centralPartData.each { it.containsKey('stars') }
        assert centralPartData.each { it.containsKey('emptyStars') }
        assert centralPartData.each { it.containsKey('isPbar') }
        assert centralPartData.each { it.containsKey('pbarPercent') }
        assert centralPartData.each { it.containsKey('reviewsQuantity') }


        log "=="
        // PDP - with Rating module
        tryLoad("/choice-12-oz-white-poly-paper-hot-cup-case/50012W.html")
        // log pdpReview.getReviewsQuantityRatingBlock()
        assert pdpReview.getReviewsQuantityRatingBlock() == 319
        assert pdpReview.getRatingRatingBlockText() == 4.7
        assert pdpReview.getKeywordTagsRatingBlock().size() > 4
        assert pdpReview.getRatingStarsRatingBlock() == 5
        def centralPartData2 = pdpReview.getRatingBlockCentralPartData()
        log "2" + centralPartData2
        assert centralPartData2.size() == 5
        assert centralPartData2.each { it.containsKey('stars') }
        assert centralPartData2.each { it.containsKey('emptyStars') }
        assert centralPartData2.each { it.containsKey('isPbar') }
        assert centralPartData2.each { it.containsKey('pbarPercent') }
        assert centralPartData2.each { it.containsKey('reviewsQuantity') }


        log "=="
        // PDP - no Rating module

        tryLoad("https://www.dev.webstaurantstore.com/ecochoice-8-oz-smooth-double-wall-kraft-compostable-paper-hot-cup-case/5008DWPLAKR.html")
        // log pdpReview.getReviewsQuantityRatingBlock()
        assert pdpReview.getReviewsQuantityRatingBlock() == null
        assert pdpReview.getRatingRatingBlockText() == 0
        assert pdpReview.getKeywordTagsRatingBlock().size() == 0
        assert pdpReview.getRatingStarsRatingBlock() == 0
        def centralPartData3 = pdpReview.getRatingBlockCentralPartData()
        log "3: " + centralPartData3
        assert centralPartData3.size() == 0

        log "=="

        // Home page - no Rating module
        tryLoad("homepage")
        // log pdpReview.getReviewsQuantityRatingBlock()
        assert pdpReview.getReviewsQuantityRatingBlock() == null
        assert pdpReview.getRatingRatingBlockText() == 0
        assert pdpReview.getKeywordTagsRatingBlock().size() == 0
        assert pdpReview.getRatingStarsRatingBlock() == 0

        def centralPartData4 = pdpReview.getRatingBlockCentralPartData()
        log "4: " + centralPartData4
        assert centralPartData4.size() == 0

    }
}

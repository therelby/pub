package framework.wss.pages.productdetail.review

import above.RunWeb
import wss.pages.productdetail.PDPReview

class UtPDPReviewsSorting extends RunWeb {
    static void main(String[] args) {
        new UtPDPReviewsSorting().testExecute([

                browser      : 'safari',
                remoteBrowser: true,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])
    }
    def test() {

        setup('vdiachuk', 'PDPReview on  Product Detail Page, Sorting Reviews  unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test review sort sorting select rating pdpage pdp product detail page ',
                 "tfsTcIds:265471",
                 'logLevel:info'])

        tryLoad("https://www.dev.webstaurantstore.com/avantco-a-19r-hc-29-solid-door-reach-in-refrigerator/178A19RHC.html")
        PDPReview pdpReview = new PDPReview()
        def sortingParams = ["Most Helpful", "Highest Rating", "Lowest Rating", "Date"]
        sortingParams.each {
            boolean selectingResult = pdpReview.selectSortByReviews(it)
            log "Selecting: [$it] " + selectingResult
            assert pdpReview.getSortByReviewsSelectedOption() == it
            assert selectingResult
        }

        def sortingValueParams = ["most_helpful", "highest_rated", "lowest_rated", "date"]
        sortingValueParams.each {paramValue->
            boolean selectingResult = pdpReview.selectSortByReviews(paramValue)
            log "Selecting: [$paramValue] " + selectingResult
            assert pdpReview.getSortByReviewsSelectedOption() == PDPReview.sortingValues.find { it.value == paramValue }?.key
            assert selectingResult
        }
        // Selecting with wrong parameters
        assert pdpReview.selectSortByReviews('FAKESORTING') == false
        assert pdpReview.selectSortByReviews(null) == false
        log "--"
        // Page with NO reviews
        tryLoad()
        sortingParams.each {
            boolean selectingResult = pdpReview.selectSortByReviews(it)
            log "Selecting: [$it] " + selectingResult
            assert pdpReview.getSortByReviewsSelectedOption() == ''
            assert selectingResult == false
        }


    }
}

package framework.wss.pages.productdetail.review

import above.RunWeb
import wss.pages.productdetail.PDPReview

class UtPDPReviewNameConverter extends RunWeb {


    static void main(String[] args) {
        new UtPDPReviewNameConverter().testExecute([

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
        int pbi = 539267
        setup('vdiachuk', 'PDPReview Name Convertor unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test name converter conversion customer hide review pdpage pdp product detail page ',
                 "PBI:$pbi",
                 'logLevel:info'])

        PDPReview pdpReview = new PDPReview()

        assert pdpReview.reviewsNameConverter('Michael Price') == 'Michael P.'
        assert pdpReview.reviewsNameConverter("Phil    Schneider") == "Phil S."
        assert pdpReview.reviewsNameConverter("  Danica Abejon") == 'Danica A.'
        assert pdpReview.reviewsNameConverter("  Danica   Abejon  ") == 'Danica A.'
        assert pdpReview.reviewsNameConverter("William") == "William"
        assert pdpReview.reviewsNameConverter("William ") == "William"
        assert pdpReview.reviewsNameConverter(" William    ") == "William"
        assert pdpReview.reviewsNameConverter("Nicolas A Arcila") == "Nicolas A."
        assert pdpReview.reviewsNameConverter("Nicolas A B C D") == "Nicolas A."
        assert pdpReview.reviewsNameConverter("nicolas a b c D") == "Nicolas A."

        assert pdpReview.reviewsNameConverter('PETER GABRIEL') == 'Peter G.'
        assert pdpReview.reviewsNameConverter('PETER gabriel') == 'Peter G.'


        log "--"
        assert pdpReview.reviewsNameConverter(" ") == ''
        assert pdpReview.reviewsNameConverter("") == ''
        assert pdpReview.reviewsNameConverter(null) == null
    }
}

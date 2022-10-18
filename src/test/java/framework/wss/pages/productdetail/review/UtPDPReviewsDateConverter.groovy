package framework.wss.pages.productdetail.review

import above.RunWeb
import wss.pages.productdetail.PDPReview

class UtPDPReviewsDateConverter extends RunWeb {
    def test() {

        setup('vdiachuk', 'PDPReview Date Convertor unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test date converter conversion customer review pdpage pdp product detail page',
                 "PBI:0",
                 'logLevel:info'])

        PDPReview pdpReview = new PDPReview()
        assert pdpReview.reviewsDateConverter("2021-01-08T00:00:00") == "01/08/2021"
        assert pdpReview.reviewsDateConverter("2020-12-27T00:00:00") == "12/27/2020"
        assert pdpReview.reviewsDateConverter("fake") == null
        assert pdpReview.reviewsDateConverter("") == null
        assert pdpReview.reviewsDateConverter(null) == null

    }
}

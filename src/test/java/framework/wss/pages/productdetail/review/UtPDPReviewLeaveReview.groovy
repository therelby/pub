package framework.wss.pages.productdetail.review

import above.RunWeb
import wss.pages.productdetail.PDPReview

class UtPDPReviewLeaveReview extends RunWeb {
    def test() {

        setup('vdiachuk', 'PDPReview Leave Review on  Product Detail Page unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test review leave pdpage pdp product detail page ',
                 "tfsTcIds:265471",
                 'logLevel:info'])

        PDPReview pdpReview = new PDPReview()

        tryLoad("https://www.dev.webstaurantstore.com/avantco-udd-1-hc-triple-tap-kegerator-beer-dispenser-black-1-1-2-keg-capacity/178UDD13.html")
        assert pdpReview.isLeaveReviewModule()==true
        assert pdpReview.isLeaveReviewWide()==true

        tryLoad("https://www.dev.webstaurantstore.com/g/104/shoes-for-crews-26819-pearl-women-s-medium-width-black-water-resistant-soft-toe-non-slip-athletic-shoe")
        assert pdpReview.isLeaveReviewModule()==true
        assert pdpReview.isLeaveReviewWide()==false

        tryLoad("https://www.dev.webstaurantstore.com/55215/men-s-shoes.html")
        assert pdpReview.isLeaveReviewModule()==false
        assert pdpReview.isLeaveReviewWide()==null

    }
}

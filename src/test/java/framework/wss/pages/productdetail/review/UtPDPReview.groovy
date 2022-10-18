package framework.wss.pages.productdetail.review

import above.RunWeb
import wss.api.catalog.product.Products
import wss.pages.productdetail.PDPReview
import wss.pages.productdetail.PDPage

class UtPDPReview extends RunWeb {

    def test() {

        setup('vdiachuk', 'PDPReview on  Product Detail Page  unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test review rating pdpage pdp product detail page ',
                 "tfsTcIds:265471",
                 'logLevel:info'])

        tryLoad("https://www.dev.webstaurantstore.com/avantco-a-19r-hc-29-solid-door-reach-in-refrigerator/178A19RHC.html")
        PDPReview pdpReview = new PDPReview()
        assert pdpReview.getReviewsQuantitySubhead() == 27
        assert pdpReview.getRatingSubHeaderLink() == 4
        assert pdpReview.getRatingSubHeaderStars() == 4
        assert pdpReview.checkSubHeaderReviewText()
        assert pdpReview.getSeeAllXXXReviewsQuantity() == null

        tryLoad("https://www.dev.webstaurantstore.com/ecochoice-8-oz-smooth-double-wall-kraft-compostable-paper-hot-cup-case/5008DWPLAKR.html")
        assert pdpReview.getReviewsQuantitySubhead() == 0
        assert pdpReview.getRatingSubHeaderLink() == 0
        assert pdpReview.getRatingSubHeaderStars() == 0
        assert pdpReview.checkSubHeaderReviewText()
        assert pdpReview.getSeeAllXXXReviewsQuantity() == null


        tryLoad("homepage")
        assert pdpReview.getReviewsQuantitySubhead() == null
        assert pdpReview.getRatingSubHeaderLink() == null
        assert pdpReview.getRatingSubHeaderStars() == null
        assert pdpReview.checkSubHeaderReviewText() == false
        assert pdpReview.getSeeAllXXXReviewsQuantity() == null

        // 588 reviews
        tryLoad("https://www.dev.webstaurantstore.com/choice-economy-8-qt-full-size-stainless-steel-chafer-with-folding-frame/100FOLDCHAFE.html")
        assert pdpReview.getSeeAllXXXReviewsQuantity() == 588

    }
}

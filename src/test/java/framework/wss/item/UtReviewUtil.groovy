package framework.wss.item

import above.RunWeb
import wss.item.ReviewUtil

class UtReviewUtil extends RunWeb {

    def test() {

        setup('vdiachuk', 'ReviewUtil test | Framework Self  Testing Tool',
                ['product:wss|dev,test',
                 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test user review util db',
                 "PBI: 485911", 'logLevel:info'])

        ReviewUtil reviewUtil = new ReviewUtil()
//        assert reviewUtil.getItemsWithReviewsByItemType(-1, "loneSuffix", true) == []
//        assert reviewUtil.getItemsWithReviewsByItemType(5, "fakeItemType", true) == []
//        assert reviewUtil.getItemsWithReviewsByItemType(5, "loneSuffix", true).size() == 5
//        assert reviewUtil.getItemsWithReviewsByItemType(15, "lone", false).size() == 15
//        assert reviewUtil.getItemsWithReviewsByItemType(15, "lone", false).every() { it['reviewCount'] == 0 }
//        assert reviewUtil.getItemsWithReviewsByItemType(10, "lone", true, 'newid()').size() == 10
//
//        assert reviewUtil.getItemsWithTextReviews(10, false).size() == 10
//        assert reviewUtil.getItemsWithTextReviews(0, false) == []
//        assert reviewUtil.getItemsWithTextReviews(10, false, 'fakeSorting') == []
//        assert reviewUtil.getItemsWithTextReviews(10, true, 'quantityOfReviews desc').size() == 10
//
        assert reviewUtil.getItemsWithPhotoReviews(10, false).size() == 10
//        assert reviewUtil.getItemsWithPhotoReviews(0, false) == []
//        assert reviewUtil.getItemsWithPhotoReviews(10, false, 'fakeSorting') == []
//        assert reviewUtil.getItemsWithPhotoReviews(10, true, 'quantityOfReviews desc').size() == 10

//        assert reviewUtil.getItemsWithVideoReviews(10, false).size() == 10
//        assert reviewUtil.getItemsWithVideoReviews(0, false) == []
//        assert reviewUtil.getItemsWithVideoReviews(10, false, 'fakeSorting') == []
//        assert reviewUtil.getItemsWithVideoReviews(10, true, 'quantityOfReviews desc').size() == 10

       log ""+reviewUtil.getItemsWithVideoReviews(10, true)

    }
}

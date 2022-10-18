package framework.wss.pages.productdetail.advertisement.cutdownadvertisement

import above.RunWeb
import wss.item.ItemUtil
import wss.pages.productdetail.PDPage
import wss.pages.productdetail.advertisement.PDPAdvertisement

class UtCutDownAdvertisement extends RunWeb{

    def productWithNoExpandedDescriptionAndIsInStock = ["600WTS24X60B", false, false]
    def productWithNoExpandedDescriptionAndIsOutOfStock = ["600WTS30120B", false, true]
    def productWithExpandedDescriptionAndIsInStock = ["600TSS3696S", true, false]
    def productWithExpandedDescriptionAndIsOutOfStock = ["600TA3060S", true, true]

    def test() {

        setup('mwestacott', 'PDPage Cut Down advertisement unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test pdpage related cut down advertisement ',
                 "PBI: 0",
                 'logLevel:info'])

        assert ItemUtil.getItemsWithPDPAds(5, false, false, false, 148).size() > 0
        assert ItemUtil.getItemsWithPDPAds(5, false, false, true, 148).size() > 0
        assert ItemUtil.getItemsWithPDPAds(5, false, true, false, 148).size() == 0
        assert ItemUtil.getItemsWithPDPAds(5, false, true, true, 148).size() == 0
        assert ItemUtil.getItemsWithPDPAds(5, true, false, false, 148).size() > 0
        assert ItemUtil.getItemsWithPDPAds(5, true, false, true, 148).size() > 0
        assert ItemUtil.getItemsWithPDPAds(5, true, true, false, 148).size() == 0
        assert ItemUtil.getItemsWithPDPAds(5, true, true, true, 148).size() == 0

        testingPDPAd(productWithNoExpandedDescriptionAndIsInStock)
        testingPDPAd(productWithNoExpandedDescriptionAndIsOutOfStock)
        testingPDPAd(productWithExpandedDescriptionAndIsInStock)
        testingPDPAd(productWithExpandedDescriptionAndIsOutOfStock)

        closeBrowser()
    }

    void testingPDPAd(def product){
        String itemNumber = product[0]
        PDPage pdPage = new PDPage()
        Boolean wasAbleToNavigateToPDP = pdPage.navigateToPDPWithItemNumber(itemNumber)
        if(wasAbleToNavigateToPDP) {
            boolean isExpandedDescription = product[1]
            boolean isOutOfStock = product[2]

            boolean isRegularDescriptionCutDownAdExpectedToAppear = (!isExpandedDescription && !isOutOfStock)
            boolean doesRegularDescriptionCutDownAdAppear = verifyElement(PDPAdvertisement.regularDescriptionCutDownAdXpath)
            assert (doesRegularDescriptionCutDownAdAppear == isRegularDescriptionCutDownAdExpectedToAppear)

            boolean isExpandedDescriptionCutDownAdExpectedToAppear = (isExpandedDescription && !isOutOfStock)
            boolean doesExpandedDescriptionCutDownAdAppear = verifyElement(PDPAdvertisement.expandedDescriptionCutDownAdXpath)
            assert (doesExpandedDescriptionCutDownAdAppear == isExpandedDescriptionCutDownAdExpectedToAppear)
        }
    }
}

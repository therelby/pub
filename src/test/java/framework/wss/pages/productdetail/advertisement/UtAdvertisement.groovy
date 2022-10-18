package framework.wss.pages.productdetail.advertisement

import above.RunWeb
import wss.item.ItemUtil
import wss.pages.productdetail.PDPage
import wss.pages.productdetail.advertisement.PDPAdvertisement

class UtAdvertisement extends RunWeb{

    def productWithNoExpandedDescriptionAndIsInStock = ["600WTS24X60B", false, false]
    def productWithExpandedDescriptionAndIsInStock = ["600TSS3696S", true, false]

    def test() {

        setup('mwestacott', 'PDPage advertisement unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test pdpage advertisement ',
                 "PBI: 0",
                 'logLevel:info'])

        testingPDPAd(productWithNoExpandedDescriptionAndIsInStock)
        testingPDPAd(productWithExpandedDescriptionAndIsInStock)

        closeBrowser()
    }

    void testingPDPAd(def product){
        String itemNumber = product[0]
        PDPage pdPage = new PDPage()
        Boolean wasAbleToNavigateToPDP = pdPage.navigateToPDPWithItemNumber(itemNumber)
        if(wasAbleToNavigateToPDP) {
            boolean isExpandedDescription = product[1]

            boolean isRegularDescriptionCutDownAdExpectedToAppear = (!isExpandedDescription)
            String regularDescriptionCutDownAdXpath = PDPAdvertisement.getRegularDescriptionAdXpathByIndex(1)
            boolean doesRegularDescriptionCutDownAdAppear = verifyElement(regularDescriptionCutDownAdXpath)
            assert (doesRegularDescriptionCutDownAdAppear == isRegularDescriptionCutDownAdExpectedToAppear)

            boolean isExpandedDescriptionCutDownAdExpectedToAppear = (isExpandedDescription)
            String expandedDescriptionCutDownAdXpath = PDPAdvertisement.getExpandedDescriptionAdXpathByIndex(1)
            boolean doesExpandedDescriptionCutDownAdAppear = verifyElement(expandedDescriptionCutDownAdXpath)
            assert (doesExpandedDescriptionCutDownAdAppear == isExpandedDescriptionCutDownAdExpectedToAppear)
        }
    }
}

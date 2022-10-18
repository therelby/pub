package framework.wss.item

import above.RunWeb
import wss.item.AutoReorderItemUtil
import wss.pages.productdetail.PDPPriceTile
import wss.pages.productdetail.PDPage
import wss.user.userurllogin.UserUrlLogin
import wsstest.checkout.confirmorder.autoreorder.AutoReorderUtils

class UtAutoReorderItemQuery extends RunWeb{

    static Integer pbiNumber = 605943

    static def itemTypes = ["Lone", "Lone Suffix", "Virtual Grouping"]

    static void main(String[] args) {
        new UtAutoReorderItemQuery().testExecute([

                browser      : 'edge',
                remoteBrowser: false,
//                browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])

    }

    def test() {

        setup('mwestacott', "Auto Reorder Item Query | Framework Self Testing Tool",
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', "keywords:unit test auto reorder item query",
                 "tfsTcIds:$pbiNumber", 'logLevel:info'])

        assert new UserUrlLogin().loginAs("8613901")

        for(itemType in itemTypes) {
            testingQueryByItemType(itemType)
            testingProductsByItemType(itemType)
        }

        closeBrowser()
    }

    void testingQueryByItemType(String itemType){
        assert AutoReorderItemUtil.getQuantityDiscountMinMustBuyProducts(itemType).size() == 5
    }

    void testingProductsByItemType(String itemType){
        def productsForTesting = AutoReorderItemUtil.getQuantityDiscountMinMustBuyProducts(itemType, 5)['item_number']
        PDPage pdPage = new PDPage()

        for(productForTesting in productsForTesting) {
            assert pdPage.navigateToPDPWithItemNumber(productForTesting)

            boolean isVirtualGroupingDropdownExpectedToAppear = (itemType == "Virtual Grouping")
            boolean doesVirtualGroupingDropdownAppear = verifyElement(PDPage.virtualGroupingDropdown)
            assert (doesVirtualGroupingDropdownAppear == isVirtualGroupingDropdownExpectedToAppear)

            assert verifyElement(PDPPriceTile.addToCartQuantityXpath)
            assert verifyElement(PDPPriceTile.addToCartButtonXpath)
            assert AutoReorderUtils.currentItemIsAutoReorderEligible()
        }
    }
}

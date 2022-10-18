package framework.wss.pages.productdetail.relateditems

import above.RunWeb
import wss.pages.productdetail.PDPVariationBlock
import wss.pages.productdetail.PDPage

class UtPDPItemVariations extends RunWeb {
    static void main(String[] args) {
        new UtPDPItemVariations().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'evdovina',
                title   : 'PDP Item Variations Unit Test',
                PBI     : 689016,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'PDP Item Variations Variation Block',
                logLevel: 'info'
        ])

        String itemNumberWithVariationBlock = "100317001P"
        testCondition1(itemNumberWithVariationBlock)

        String itemNumberWithoutVariationBlock = "334RSB50SS"
        testCondition2(itemNumberWithoutVariationBlock)

    }

    void testCondition1(String itemNumberWithVariationBlock) {
        PDPage pdPage = new PDPage()
        pdPage.navigateToPDPWithItemNumber(itemNumberWithVariationBlock)
        PDPVariationBlock pdpVariationBlock = new PDPVariationBlock()

        assert pdpVariationBlock.isVariationBlockPresent()

        def variationsData = pdpVariationBlock.getVariationsData()
        assert variationsData.size() == 2

        def visibleText = variationsData.find(){it['isSelected'] == false}?.getAt('text')
        assert pdpVariationBlock.selectByVisibleText(visibleText)
    }

    void testCondition2(String itemNumberWithoutVariationBlock) {
        PDPage pdPage = new PDPage()
        pdPage.navigateToPDPWithItemNumber(itemNumberWithoutVariationBlock)
        PDPVariationBlock pdpVariationBlock = new PDPVariationBlock()

        assert !pdpVariationBlock.isVariationBlockPresent()

        def variationsData = pdpVariationBlock.getVariationsData()
        assert !variationsData

        def visibleText = variationsData.find(){it['isSelected'] == false}?.getAt('text')
        assert !pdpVariationBlock.selectByVisibleText(visibleText)
    }
}
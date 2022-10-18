package framework.wss.pages.productdetail.relateditems

import above.RunWeb
import wss.pages.productdetail.PDPImageVariationBlock
import wss.pages.productdetail.PDPage

class UtPDPItemVariationsImages extends RunWeb {
    static void main(String[] args) {
        new UtPDPItemVariationsImages().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'evdovina',
                title   : 'PDP Item Variations With Images Unit Test',
                PBI     : 689016,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'PDP Item Variations Variation Block With Images',
                logLevel: 'info'
        ])

        String itemNumberWithImageVariationBlock = '164BWDMVBKKD'
        testCondition1(itemNumberWithImageVariationBlock)

        String itemNumberWithoutImageVariationBlock = '334RSB50SS'
        testCondition2(itemNumberWithoutImageVariationBlock)
    }

    void testCondition1(String itemNumberWithImageVariationBlock) {
        PDPage pdPage = new PDPage()
        pdPage.navigateToPDPWithItemNumber(itemNumberWithImageVariationBlock)
        PDPImageVariationBlock pdpImageVariationBlock = new PDPImageVariationBlock()

        assert pdpImageVariationBlock.isVariationBlockPresent()

        def variationsData = pdpImageVariationBlock.getVariationsData()
        assert variationsData.size() == 13

        def title = variationsData.find(){it['isSelected'] == false}?.getAt('title')
        assert pdpImageVariationBlock.selectByTitle(title)
    }

    void testCondition2(itemNumberWithoutImageVariationBlock) {
        PDPage pdPage = new PDPage()
        pdPage.navigateToPDPWithItemNumber(itemNumberWithoutImageVariationBlock)
        PDPImageVariationBlock pdpImageVariationBlock = new PDPImageVariationBlock()

        assert !pdpImageVariationBlock.isVariationBlockPresent()

        def variationsData = pdpImageVariationBlock.getVariationsData()
        assert !variationsData.size()

        def title = variationsData.find(){it['isSelected'] == false}?.getAt('title')
        assert !pdpImageVariationBlock.selectByTitle(title)
    }
}
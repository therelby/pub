package framework.wss.pages.productdetail.banner

import above.RunWeb
import wss.pages.productdetail.PDPage
import wss.pages.productdetail.banner.PDPPartsAndAccessoriesAd

class UtPDPPartsAndAccessories extends RunWeb {
    static void main(String[] args) {
        new UtPDPPartsAndAccessories().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'evdovina',
                title   : 'Replacement Parts & Accessories Ad | PDP Unit Test',
                PBI     : 708977,
                product : 'wss|dev',
                project : 'Webstaurant.StoreFront',
                keywords: 'PDP Replacement Parts & Accessories Ad',
                logLevel: 'info'
        ])

        String itemNumberWithPartsAndAccessories = "882UHT60LL"
        testCondition1(itemNumberWithPartsAndAccessories)

        String itemNumberWithoutPartsAndAccessories = "0181327900"
        testCondition2(itemNumberWithoutPartsAndAccessories)

    }

    void testCondition1(String itemNumberWithPartsAndAccessories) {
        PDPage pdPage = new PDPage()
        pdPage.navigateToPDPWithItemNumber(itemNumberWithPartsAndAccessories)

        PDPPartsAndAccessoriesAd pdpPartsAndAccessories = new PDPPartsAndAccessoriesAd()
        assert pdpPartsAndAccessories.isPartsAndAccessoriesAdPresent()
        assert pdpPartsAndAccessories.partsAndAccessoriesAdData().size() == 2
        assert pdpPartsAndAccessories.clickPartsAndAccessoriesAd()
    }

    void testCondition2(String itemNumberWithoutPartsAndAccessories) {
        PDPage pdPage = new PDPage()
        pdPage.navigateToPDPWithItemNumber(itemNumberWithoutPartsAndAccessories)

        PDPPartsAndAccessoriesAd pdpPartsAndAccessories = new PDPPartsAndAccessoriesAd()
        assert !pdpPartsAndAccessories.isPartsAndAccessoriesAdPresent()
        assert pdpPartsAndAccessories.partsAndAccessoriesAdData().size() == 2
        assert pdpPartsAndAccessories.partsAndAccessoriesAdData()?.getAt('partsAndAccessoriesAdText') == ''
        assert !pdpPartsAndAccessories.clickPartsAndAccessoriesAd()
    }
}
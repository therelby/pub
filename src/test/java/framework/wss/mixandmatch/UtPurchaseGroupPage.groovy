package framework.wss.purchasinggroup

import above.RunWeb
import wss.purchasinggroup.PurchasingGroupBuilder
import wss.purchasinggroup.PurchasingGroupUtil

class UtPurchaseGroupPage extends RunWeb {

    def test() {

        //inherited from micurtis
		setup('kyilmaz', 'UtPurchaseGroupPage',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:purchase group, mix and match, unit test', 'logLevel:debug'])

        def items = PurchasingGroupUtil.getItemsWithMatchingMinBuy(100)

        PurchasingGroupBuilder npgp = new PurchasingGroupBuilder(items['itemNumber'], items['minBuy'], 2)

        assert npgp.navToCreationPage()
        assert npgp.fillNewPageForm()
        assert npgp.extractPageIdFromUrl()
        assert npgp.createNewPageUrl()
        assert openAndTryLoad(npgp.createNewPageUrl())
        assert npgp.createNewPageUrl() instanceof String
//        Thread.sleep(5000)

    }
}

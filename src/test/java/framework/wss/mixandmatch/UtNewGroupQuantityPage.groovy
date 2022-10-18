package framework.wss.purchasinggroup

import above.RunWeb
import wss.purchasinggroup.groupquantity.CreateNewGroupQuantityDiscountPage
import wss.purchasinggroup.sql.PurchasingGroupQuery
import wsstest.mixmatchpage.testtools.ItemGeneratorMixMatchPage

class UtNewGroupQuantityPage extends RunWeb {


    // Test
    def test() {

        //inherited from micurtis
		setup('kyilmaz', 'UtNewGroupQuantityPage',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test, group quantity discount, mix and match page', 'logLevel:info'])

        log 'Testing...'

        int testItemQty = 3
        PurchasingGroupQuery mmq = new PurchasingGroupQuery()
        ItemGeneratorMixMatchPage iGen = new ItemGeneratorMixMatchPage(mmq.getItemWithStartQuantityQuery(), testItemQty)
        CreateNewGroupQuantityDiscountPage newPage = new CreateNewGroupQuantityDiscountPage(iGen.getItemNumbers(), iGen.getStartQty())

        assert newPage.navToCreationPage()
        assert newPage.fillNewPageForm()
        assert newPage.getPageId() != null
        assert newPage.updateApiCalls()
        assert tryLoad(newPage.getNewPageUrl())

    }
}

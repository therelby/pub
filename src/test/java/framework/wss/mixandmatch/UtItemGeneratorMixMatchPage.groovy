package framework.wss.purchasinggroup

import above.RunWeb
import wss.purchasinggroup.sql.PurchasingGroupQuery
import wsstest.mixmatchpage.testtools.ItemGeneratorMixMatchPage

class UtItemGeneratorMixMatchPage extends RunWeb {

    def test() {

        //inherited from micurtis
		setup('kyilmaz', 'UtItemGeneratorMixMatchPage',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:mix and match, item generator, unit test', 'logLevel:debug'])

        log 'Testing...'

        int testItemQty = 3
        PurchasingGroupQuery mmpq = new PurchasingGroupQuery()
        ItemGeneratorMixMatchPage test = new ItemGeneratorMixMatchPage(mmpq.getItemWithStartQuantityQuery(), 3)

        assert test.getItemNumbers().size() == testItemQty
        assert test.getStartQty() >= 0
    }
}

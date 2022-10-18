package framework.wss.purchasinggroup

import above.RunWeb
import wss.purchasinggroup.PurchasingGroupItemBox

class UtPurchasingGroupItemBox extends RunWeb {


    def test() {

        //inherited from micurtis
		setup('kyilmaz', 'UtPurchasingGroupItemBox',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test, item box, purchasing group', 'logLevel:debug'])

        log 'Testing...'

        openAndTryLoad("https://www.dev.webstaurantstore.com/purchasing-group.cfm?i=12684041")

        PurchasingGroupItemBox ibox = new PurchasingGroupItemBox("10200002")

        assert ibox.verifyItemBoxPresent()
        assert ibox.setQuantityField(5)
        assert ibox.readQuantityField() == 5
        assert ibox.clickIncrementButton()
        assert ibox.clickDecrementButton()

    }

}

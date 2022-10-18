package framework.wss.pages.productdetail.vg

import above.RunWeb
import wss.api.catalog.product.Products
import wss.item.ItemUtil
import wss.pages.productdetail.PDPVirtualGrouping

class UtPDPVGData extends RunWeb {

    def test() {

        setup('vdiachuk', 'PDPVirtual Grouping Page DATA unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test pdp product detail page vg virtual grouping data',
                 "tfsTcIds:265471",
                 'logLevel:info'])



        PDPVirtualGrouping pdpVirtualGrouping = new PDPVirtualGrouping()
        assert pdpVirtualGrouping.getVGIdUsingAPI("756M7674585M") == "68^g"
        assert pdpVirtualGrouping.getVGIdUsingAPI("68^g") == "68^g"
//        assert pdpVirtualGrouping.getVGIdUsingAPI("109TSFG302") == "1182^g"
        assert pdpVirtualGrouping.getVGIdUsingAPI("756M7674585MFAKE") == null

        //    assert pdpVirtualGrouping.getItemsWithSameVG("756M7674585M").size() == 28
    //    assert pdpVirtualGrouping.getItemsWithSameVG("109TSFG302").size() == 3
        assert pdpVirtualGrouping.getItemsWithSameVG("68^g").size() == 28
//        assert pdpVirtualGrouping.getItemsWithSameVG("1182^g").size() == 3
        assert pdpVirtualGrouping.getItemsWithSameVG("158^g").size()==  26
        assert pdpVirtualGrouping.getItemsWithSameVG("1182^gFAKE") == null

PDPVirtualGrouping
    }

}

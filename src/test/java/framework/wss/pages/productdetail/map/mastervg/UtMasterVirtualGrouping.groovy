package framework.wss.pages.productdetail.map.mastervg

import above.RunWeb
import wss.item.ItemUtil

class UtMasterVirtualGrouping extends RunWeb{
    def test() {

        setup('mwestacott', 'PDPage MAP Master Virtual Grouping | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test pdpage map master virtual grouping ',
                 "tfsTcIds:0",
                 'logLevel:info'])

        assert ItemUtil.getVirtualGroupingMasterMapProducts(5, false).size() == 5
        assert ItemUtil.getVirtualGroupingMasterMapProducts(5, true).size() == 5
    }
}

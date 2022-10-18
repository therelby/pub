package framework.wss.pages.productdetail.relateditems

import above.RunWeb
import wss.item.ItemUtil

class UtPDPRelatedItemsQueries extends RunWeb{


    def test() {

        setup('mwestacott', 'PDPage Related Items Queries unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test pdpage related items queries ',
                 "PBI: 0",
                 'logLevel:info'])

        assert ItemUtil.getEverlivingProducts(5, "Lone", false).size() == 5
        assert ItemUtil.getEverlivingProducts(5, "Lone", true).size() == 5

        assert ItemUtil.getEverlivingProducts(5, "Lone Suffix", false).size() == 5
        assert ItemUtil.getEverlivingProducts(5, "Lone Suffix", true).size() == 5

        assert ItemUtil.getEverlivingProducts(5, "Virtual Grouping", false).size() == 5
        assert ItemUtil.getEverlivingProducts(5, "Virtual Grouping", true).size() == 5

        assert ItemUtil.getVirtualGroupingMasterProducts(5).size() == 5

    }
}

package framework.wss.item

import above.RunWeb
import wss.item.ItemUtil

class ItemUtilUnitTest extends RunWeb {
    def test() {

        setup('vdiachuk', 'ItemUtil Unit test | Framework Self Testing Tool',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test ItemUtil item getItems items',
               "tfsTcIds:265471", 'logLevel:info'])


        //   log("items noMin:" + ItemUtil.getItemsNoMinBuy(2))
        assert ItemUtil.getItemsNoMinBuy(2).size() == 2
        //  log("lone items noMin, noMust:" + ItemUtil.getLoneItemsNoMinNoMust(2))
        assert ItemUtil.getLoneItemsNoMinNoMust(2).size() == 2
        //   log("Lone Items By type:" + ItemUtil.getItemByType("lone", 2))
        //   log("Suffix Items By type:" + ItemUtil.getItemByType("suffix", 2))
        //  log("loneSuffix Items By type:" + ItemUtil.getItemByType("loneSuffix", 2))
        assert ItemUtil.getItemByType("lone", 2).size() == 2
        assert ItemUtil.getItemByType("suffix", 2).size() == 2
        assert ItemUtil.getItemByType("loneSuffix", 3).size() == 3
        // log "Customizable items:" + ItemUtil.getdCustomizedItem(3)
        assert ItemUtil.getCustomizedItem(3).size() == 3


        // can return 0 Outlet items - can not assert
        log "Lone Outlet items:" + ItemUtil.getOutletItem(2, "Lone")
        log "Suffix Outlet items:" + ItemUtil.getOutletItem(2, "Suffix")
        log "Lone Suffix Outlet items:" + ItemUtil.getOutletItem(2, "Lone Suffix")


        //   log "All Points items:" +  ItemUtil.getdAllPointsItem(2)
        assert ItemUtil.getAllPointsItem(2).size() == 2

        def groundItems =  ItemUtil.getGroundItem(3)

        log "Ground items:" + groundItems
        log "Ground item number:"+ ItemUtil.getGroundItem(3).get(0).get("item_number")
        assert  groundItems.size() ==3

        def commonCarrierItems = ItemUtil.getCommonCarrierItem(1)
        log "Common Carrier items:" + commonCarrierItems
        log "Common Carrier item number:" + ItemUtil.getCommonCarrierItem(3).get(0).get("item_number")
        assert  commonCarrierItems.size() ==1

        testGetLeafCategoryLinksForItem()
    }

    def testGetLeafCategoryLinksForItem() {
        def cat1 = ItemUtil.getLeafCategoryLinksForItem('500TCLL20WN')
        def cat2 = ItemUtil.getLeafCategoryLinksForItem('12212105    CASE')
        def cat3 = ItemUtil.getLeafCategoryLinksForItem('500TCLL20RED')
        assert cat1.endsWith('/755/disposable-table-covers-skirting.html')
        assert cat2.endsWith('/427/aluminum-foil-food-wrap.html')
        assert cat3.endsWith('/56719/red-party-supplies.html')
    }
}

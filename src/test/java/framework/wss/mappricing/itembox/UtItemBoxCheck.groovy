package framework.wss.mappricing.itembox

import above.RunWeb
import wss.price.map.MapPricingCustomItems
import wss.price.map.MapPricingItemBox
import wss.webadmin.WebAdmin
import wss.webadmin.itemdetail.WebAdminItemDetail

class UtItemBoxCheck extends RunWeb {
    def test() {

        setup('vdiachuk', ' Item Box unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test credit card generator',
                 "tfsTcIds:265471", 'logLevel:info'])

        tryLoad("https://www.dev.webstaurantstore.com/specializedpage.cfm?index=20620&forcecacheupdate=1")


        def itemNumbers = [ "885RM55RNAT", "186CVB912", "422PT42LBMS2"]

        def itemsWithDetails= [[[itemNumber:"885RM55RNAT"],[suffix:true]],
                              [[itemNumber:"885RM55RNAT"],[suffix:true]],
                              [[itemNumber:"885RM55RNAT"],[suffix:true]],
        ]
        itemsWithDetails.add([[itemNumber:"885RM55RNAT"],[suffix:true]])

        def items = new MapPricingCustomItems()
        for(def item in itemsWithDetails){

            def itemNumber = item.get("itemNumber")
            def suffix = item.get("suffix")
            items.addItemNumber(itemNumber, false)//true/false suffix/nonsuffix
        }

        items.saveItemsAndSp('vdiachuk-sp-try-temp-2')
        def ibox = new MapPricingItemBox(items.items)
        log "tryLoad(items.spUrl):" + tryLoad(items.spUrl)

        log "ibox.setCurrent('885RM55RNAT'): " + ibox.setCurrent("885RM55RNAT")
        verifyElement(ibox.getXpath("addButton"))
        String xpathAddButton = ibox.getXpath("addButton")
        log "xpathAddButton: " + xpathAddButton

    }
}

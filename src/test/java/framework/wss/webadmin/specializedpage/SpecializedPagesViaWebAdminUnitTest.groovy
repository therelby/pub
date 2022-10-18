package framework.wss.webadmin.specializedpage

import above.RunWeb
import wss.item.ItemUtil
import wss.webadmin.WebAdmin
import wss.webadmin.specializedpage.SpecializedPage

class SpecializedPagesViaWebAdminUnitTest extends RunWeb {

    def test() {

        setup('vdiachuk', 'SpecializedPagesViaWebAdmin unit test | Framework Self ' +
                'Testing Tool',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test webadmin admin Specialized ' +
                      'Pages Via SpecializedPagesViaWebAdmin ' +
                      'settings system manage ',
               "tfsTcIds:265471", 'logLevel:info'])

         def items = ItemUtil.getItemByType("lone",5)
          log "Lone items:$items"
          def itemNumbers = []
          itemNumbers = items.collect(){item-> item.get("Item_Number")}
          log "Item Numbers:$itemNumbers"
          WebAdmin.open()
          def spUrl = SpecializedPage.createNew(itemNumbers.join(","), 1 , 'dev')
          log "SP url:$spUrl"
        //  SpecializedPagesViaWebAdmin.updateSpecializedPade(19238)



    }
}

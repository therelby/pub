package framework.wss.item

import above.RunWeb
import wss.item.ItemUtil
import wss.item.productdetail.ProductDetailsPage

class ProductDetailsPageUnitTest extends RunWeb {

    def test() {

        setup('vdiachuk', 'Product Details Page Unit test | Framework Self Testing Tool',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test product detail page item',
               "tfsTcIds:265471", 'logLevel:info'])

        String itemLoneNoMinNoMust = ItemUtil.getLoneItemsNoMinNoMust(1).get(0).get("Item_Number")
        log "itemLoneNoMinNoMust:$itemLoneNoMinNoMust"
        String itemUrl = ProductDetailsPage.getItemUrl(itemLoneNoMinNoMust)
        log "item url:$itemUrl"
        assert tryLoad(itemUrl)
        assert ProductDetailsPage.isProductPageLoaded()
        assert ProductDetailsPage.getItemNumber() == itemLoneNoMinNoMust

    }
}

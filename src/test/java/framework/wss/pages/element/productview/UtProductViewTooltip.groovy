package framework.wss.pages.element.productview

import above.RunWeb
import wss.pages.element.ProductView

class UtProductViewTooltip extends  RunWeb{

    def test() {

        setup('vdiachuk', 'ProductView Element Tooltip unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test ProductView tooltip grid list view product item element',
                 "tfsTcIds:265471",
                 'logLevel:info'])

        log "======Category Page testing"
        String urlCategory = 'https://www.dev.webstaurantstore.com/42541/chair-carts-trucks-and-dollies.html'
        tryLoad(urlCategory)
        ProductView productView = new ProductView()



        assert productView.getGridTooltip() == ProductView.expectedGridTooltip
        assert productView.getListTooltip() == ProductView.expectedListTooltip

        mouseOver("//button[@value=\"Search\"]")
        assert !productView.isTooltip()
        mouseOver(ProductView.gridButtonXpath)

        assert productView.isTooltip()


    }
}

package framework.wss.pages.element.topproductmodule

import above.RunWeb
import wss.pages.element.TopProductsModule

class UtTopProductsModule extends RunWeb {
    def test() {

        setup('vdiachuk', 'Top Products Module unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test top product module items carousel',
                 'PBI: 0',
                 'logLevel:info'])


        TopProductsModule topProductsModule = new TopProductsModule()

         tryLoad('https://www.dev.webstaurantstore.com/restaurant-storage-transport.html')
         assert topProductsModule.getItemsQuantity() > 3

         log topProductsModule.isNextHidden()
         log topProductsModule.isPrevHidden()
         scrollTo(TopProductsModule.moduleDivXpath)
         assert topProductsModule.checkPrevButtonFunction()

         assert topProductsModule.checkNextButtonFunction()

         assert topProductsModule.checkPrevButtonFunction()

           tryLoad("https://www.dev.webstaurantstore.com/3897/flatware.html")
           assert topProductsModule.getItemsQuantity() > 3
           scrollTo(TopProductsModule.moduleDivXpath)
           assert topProductsModule.checkPrevButtonFunction()
           assert topProductsModule.checkNextButtonFunction()
           assert topProductsModule.checkPrevButtonFunction()

        tryLoad("homepage")
        assert topProductsModule.getItemsQuantity() == 0
        scrollTo(TopProductsModule.moduleDivXpath)
        assert !topProductsModule.checkPrevButtonFunction()
        assert !topProductsModule.checkNextButtonFunction()
        assert !topProductsModule.checkPrevButtonFunction()


    }
}

package framework.wss.pages.category

import above.RunWeb
import wss.pages.category.ShopAllCategory

class UtShopAllCategory extends RunWeb {
    def test() {

        setup('vdiachuk', 'Shop All(top category) Category unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test shop all top category category',
                 "tfsTcIds:265471",
                 'logLevel:info'])

        tryLoad("https://www.dev.webstaurantstore.com/restaurant-smallwares.html")
        scrollToBottom()
        ShopAllCategory shopAllCategory = new ShopAllCategory()
//        log verifyElement(ShopAllCategory.bannerDixXpath)
//       log verifyElement(ShopAllCategory.bannerH1Xpaht)
//        log verifyElement(ShopAllCategory.categoryTileDivXpath)
        assert shopAllCategory.isShopAllPage()
        assert shopAllCategory.getCategoryTileQuantity()>3
        assert shopAllCategory.getCategoryTilesData().size() == shopAllCategory.getCategoryTileQuantity()
        assert shopAllCategory.getFeatureCategoryModulesQuantity()>0
        log shopAllCategory.getFeatureCategoryModulesData()
//
//        //NOT a ShopAll page
//        tryLoad("homepage")
//        scrollToBottom()
//        assert !shopAllCategory.isShopAllPage()
//        assert shopAllCategory.getCategoryTileQuantity() == 0
//        assert shopAllCategory.getCategoryTilesData().size() == 0
//        assert shopAllCategory.getQuantityFeatureCategoryModules() == 0

    }
}
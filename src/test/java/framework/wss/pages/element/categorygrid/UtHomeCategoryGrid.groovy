package framework.wss.pages.element.categorygrid

import above.RunWeb
import wss.pages.element.Breadcrumb
import wss.pages.element.categorygrid.HomeCategoryGrid


class UtHomeCategoryGrid extends RunWeb {
    def test() {

        setup('vdiachuk', 'Home Category Grid Element  unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test home category grid element ',
                 "tfsTcIds:265471",
                 'logLevel:info'])
        tryLoad("https://www.dev.webstaurantstore.com/search/gsgsasdf.html")

        HomeCategoryGrid homeCategoryGrid = new HomeCategoryGrid()
        assert homeCategoryGrid.isHomeCategoryGrid()
        assert homeCategoryGrid.isAllCategoriesWithImage()
        assert homeCategoryGrid.getCategoryTitles().contains("Restaurant Equipment")
        log homeCategoryGrid.getCategoryTitles()
        assert homeCategoryGrid.getCategoryTitles().size()>10

        //negative testing on page with NO Home Category Grid Element
       tryLoad("https://www.dev.webstaurantstore.com/restaurant-smallwares.html")
        assert !homeCategoryGrid.isHomeCategoryGrid()
        assert !homeCategoryGrid.isAllCategoriesWithImage()
        assert homeCategoryGrid.getCategoryTitles()==null



    }
}

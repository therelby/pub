package framework.wss.pages.element.categorygrid

import above.RunWeb
import wss.pages.element.categorygrid.CategoryGrid
import wss.pages.element.categorygrid.HomeCategoryGrid

class UtCategoryGrid extends RunWeb {

    def test() {

        setup('vdiachuk', 'Category Grid Element unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test category grid element ',
                 "tfsTcIds:265471",
                 'logLevel:info'])
        CategoryGrid categoryGrid = new CategoryGrid()

        // Category Grid but no view all categories button
        tryLoad("https://www.dev.webstaurantstore.com/search/food.html")
        assert categoryGrid.isCategoryGrid()
        assert !categoryGrid.isViewAllCategories()
        assert categoryGrid.clickViewAllCategories() == false
        assert categoryGrid.isViewAllCategoriesOpen() == null
        assert categoryGrid.isViewAllCategoriesClose() == null
        assert categoryGrid.isResources()
        assert categoryGrid.getResources().size()>0

        //with view all categories + Category Grid
        tryLoad("https://www.dev.webstaurantstore.com/search/top.html")
        assert categoryGrid.isViewAllCategories()
        assert categoryGrid.isResources()
        assert categoryGrid.isViewAllCategoriesClose()
        assert categoryGrid.isViewAllCategoriesOpen() == false
        assert categoryGrid.isCategoryGrid()
        assert categoryGrid.clickViewAllCategories()
        assert categoryGrid.isViewAllCategoriesOpen()
        assert categoryGrid.isViewAllCategoriesClose() == false
        assert categoryGrid.clickViewAllCategories()
        assert categoryGrid.isViewAllCategoriesOpen() == false
        assert categoryGrid.isViewAllCategoriesClose()
        assert categoryGrid.getResources().size()>0





       //no Category grid
        tryLoad("https://www.dev.webstaurantstore.com/search/fosdfsadfod.html")
        assert !categoryGrid.isCategoryGrid()
        assert !categoryGrid.isViewAllCategories()
        assert !categoryGrid.isResources()
        assert categoryGrid.getResources().size()==0

        //
        // Getting Categories Check
        //

        // Category Grid but no view all categories button
        tryLoad("https://www.dev.webstaurantstore.com/search/food.html")
        def categories = categoryGrid.getCategories()
        assert categories.size()>0 &&  categories.size()<10
        assert categories[0].link.contains("http")
        assert categories[0]['isImage'] == true

        // Category Grid + view all categories button
        tryLoad("https://www.dev.webstaurantstore.com/search/top.html")
         categories = categoryGrid.getCategories()
        assert categories.size()>10 && categories.size()<100
        assert categories[0]['link'].contains("http")
        assert categories[0]['isImage'] === true


        // NO Category Grid element
        tryLoad("https://www.dev.webstaurantstore.com/search/tosdfasdfsadfp.html")
        categories = categoryGrid.getCategories()
        assert categories == []
        assert categories[0]?.link == null



    }
}

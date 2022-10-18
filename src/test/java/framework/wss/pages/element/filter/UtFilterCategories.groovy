package framework.wss.pages.element.filter

import above.RunWeb
import wss.pages.element.Filter

class UtFilterCategories extends RunWeb {
    def test() {

        setup('vdiachuk', 'Filter Element Categories unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test filter element category categories headers ',
                 "tfsTcIds:265471",
                 'logLevel:info'])

        Filter filter = new Filter()

        //many categories - no selected category
        tryLoad("https://www.webstaurantstore.com/search/cutter.html")
        log " filter.getCategories(): " + filter.getCategories()
        assert filter.getCategoryHeaders().size() > 0
        assert filter.applyRandomCategory()
        //one category - and it is selected
        tryLoad("https://www.webstaurantstore.com/search/cutter.html?category=13765")
        log " filter.getCategories(): " + filter.getCategories()
        log filter.getCategoryHeaders()
        assert filter.getCategoryHeaders().size() == 1
        assert !filter.applyRandomCategory()
        assert filter.applyRandomCategory(true)

        //no categories
        tryLoad("https://www.dev.webstaurantstore.com/25887/commercial-gas-ranges.html")
        log " filter.getCategories(): " + filter.getCategories()
        log "filter.getCategoryHeaders(): " + filter.getCategoryHeaders()
        assert filter.getCategoryHeaders().size() == 0
        assert !filter.applyRandomCategory()
        assert !filter.applyRandomCategory(true)


        tryLoad("https://www.dev.webstaurantstore.com/specializedpage.cfm?index=18529&forcecacheupdate=1")
        Filter filterCat = new Filter()
        def catData = filterCat.getCategories()
        assert catData.size()==6
        assert catData.findAll(){it.header=="Tabletop"}.size()==4


    }
}

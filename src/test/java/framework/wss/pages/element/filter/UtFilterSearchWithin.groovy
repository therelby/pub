package framework.wss.pages.element.filter

import above.RunWeb
import wss.pages.element.Filter

class UtFilterSearchWithin extends RunWeb {
    def test() {

        setup('vdiachuk', 'Filter Element SearchWithin unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test filter element search within ',
                 "tfsTcIds:265471",
                 'logLevel:info'])


        Filter filter = new Filter()
        //Checking URl with Filters
        String url = "https://www.dev.webstaurantstore.com/search/cookie-cutter.html?category=2405"
        tryLoad(url)
        assert filter.isSearchWithin()
        assert filter.searchWithin("steel")


        //Checking URl with no Filter
        String urlNoFilter = "https://www.dev.webstaurantstore.com/"
        tryLoad(urlNoFilter)
        assert filter.isSearchWithin() == false
        assert filter.searchWithin("steel") == false


    }
}

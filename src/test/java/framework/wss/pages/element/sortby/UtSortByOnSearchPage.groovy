package framework.wss.pages.element.sortby

import above.RunWeb
import wss.pages.element.SortBy

class UtSortByOnSearchPage extends RunWeb {
    def test() {

        setup('vdiachuk', 'SortBy Element on Search Pages unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test sortBy sort by element search page',
                 "tfsTcIds:265471",
                 'logLevel:info'])


        // check sorting on search page
        String url = 'https://www.dev.webstaurantstore.com/search/table.html'
        tryLoad(url)
        SortBy sortOnSearch = new SortBy()
        log "sortOnSearch.selectSort('popular')" + sortOnSearch.selectSort('popular')
        assert sortOnSearch.selectSort('popular')
        assert sortOnSearch.getSortingOptionFromUrl() == 'popular'
        assert !sortOnSearch.selectSort('fake')

        String urlSortParamInside = 'https://www.dev.webstaurantstore.com/search/table.html?category=3787&order=relevancy_desc&parts=y'
        tryLoad(urlSortParamInside)
        SortBy sortParamInside = new SortBy()
        assert sortParamInside.selectSort('relevancy_desc')


    }

}

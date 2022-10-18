package framework.wss.pages.search

import above.RunWeb
import wss.pages.search.SearchForm
import wss.user.UserQuickLogin

class UtSearchFormDirectSearch extends RunWeb {

    def test() {

        setup('vdiachuk', 'SearchForm direct search Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test searchForm search form direct search',
                 "tfsTcIds:265471", 'logLevel:info'])

        tryLoad('homepage')
        SearchForm searchForm = new SearchForm()
        assert searchForm.directSearch("samplesearch")
        assert getCurrentUrl() == "https://www.dev.webstaurantstore.com/search/samplesearch.html"
    }
}
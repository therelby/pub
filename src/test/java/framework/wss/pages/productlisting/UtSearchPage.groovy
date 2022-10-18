package framework.wss.pages.productlisting

import above.RunWeb
import wss.pages.productlisting.SearchPage

class UtSearchPage extends RunWeb {


    def test() {

        setup('vdiachuk', 'Search Product Page unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test search product related module page',
                 'PBI: 0',
                 'logLevel:info'])


        String testUrl = "https://www.dev.webstaurantstore.com/search/food.html"
        openAndTryLoad(testUrl)

        SearchPage searchPage = new SearchPage()
        assert searchPage.isRelatedSearch2()
        assert getTextSafe(SearchPage.relatedSearch2HeaderXpath) == SearchPage.relatedSearch2HeaderText
        assert searchPage.getRelatedSearch2Tags().size() >1

        log "=="

        tryLoad("homepage")
        assert !searchPage.isRelatedSearch2()
        assert searchPage.getRelatedSearch2Tags().size() == 0


    }
}

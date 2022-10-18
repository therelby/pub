package framework.wss.pages.element.pagination

import above.RunWeb
import wss.pages.element.Pagination

class UtPaginationClickNextPrevious extends RunWeb {

    static void main(String[] args) {
        new UtPaginationClickNextPrevious().testExecute([

                browser      : 'chrome',// 'remotechrome-lt',//'chrome',//'edge',//'chrome',//'safari'
                remoteBrowser: false,//true,//false,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                //   parallelThreads: 1,
                //  runType: 'Regular' ,
                //  browserVersionOffset: -1
        ])
    }

    // Test
    def test() {

        setup('vdiachuk', 'Pagination Element Click Next Previous Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords: unit test pagination page click previous next ',
                 "tfsTcIds:265471", 'logLevel:info'])

        tryLoad("https://www.dev.webstaurantstore.com/43905/dishwasher-parts-and-dishwashing-components.html?page=9")
        Pagination pagination = new Pagination()
        assert pagination.clickNextPage()
        assert pagination.getCurrentPageNumberFromUrl() == 10
        assert pagination.clickPreviousPage()
        assert pagination.getCurrentPageNumberFromUrl() == 9

        tryLoad("https://www.dev.webstaurantstore.com/43905/dishwasher-parts-and-dishwashing-components.html")
        assert pagination.clickPreviousPage() == false
        assert pagination.clickNextPage()
        assert pagination.getCurrentPageNumberFromUrl() == 2
        tryLoad("https://www.dev.webstaurantstore.com/43905/dishwasher-parts-and-dishwashing-components.html?page=51")
        assert pagination.clickNextPage() ==  false
        assert pagination.clickPreviousPage()
        assert pagination.getCurrentPageNumberFromUrl() == 50
        //no pagination
        tryLoad("https://www.dev.webstaurantstore.com/43905/dishwasher-parts-and-dishwashing-components.html?filter=type:bottle-racks")
        assert pagination.clickNextPage() == false
        assert pagination.clickPreviousPage() == false
    }
}

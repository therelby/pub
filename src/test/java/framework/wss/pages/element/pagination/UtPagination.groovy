package framework.wss.pages.element.pagination

import above.RunWeb
import wss.pages.element.Pagination

class UtPagination extends RunWeb {
    static void main(String[] args) {
        new UtPagination().testExecute([

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
    def test() {

        //inherited from micurtis
		setup('kyilmaz', 'UtPagination',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test pagination page',
                 "tfsTcIds:265471", 'logLevel:info'])

        log 'Testing...'

        openAndTryLoad('/25887/commercial-gas-ranges.html')
        Pagination pagination = new Pagination()
        scrollToBottom()
        assert pagination.verifyLeftArrowDisabled()
        assert pagination.verifyPaginationBarPresent()
        setLogLevel('debug')
        assert pagination.clickNextPage()
        assert pagination.clickPreviousPage()
        assert pagination.clickOnLastPage()
        assert pagination.verifyRightArrowDisabled()
        assert pagination.goToPageNumberUrl(8)

    }
}

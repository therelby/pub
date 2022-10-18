package framework.wss.pages.element.pagination

import above.RunWeb
import wss.pages.element.Pagination
import wss.user.UserQuickLogin

class UtPagination2 extends RunWeb {


    static void main(String[] args) {
        new UtPagination2().testExecute([

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

        setup('vdiachuk', 'Pagination Element 2nd Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test pagination page',
                 "tfsTcIds:265471", 'logLevel:info'])

        tryLoad("/43905/dishwasher-parts-and-dishwashing-components.html?page=9")
        scrollToBottom()
        Pagination pagination = new Pagination()
        assert pagination.getCurrentPageNumberFromUrl() == 9
        assert pagination.getCurrentPageNumber() == 9
        assert pagination.getIndexOfLeftArrow() == 0
        assert pagination.getIndexOfRightArrow() == 10
        assert pagination.getIndexesOfEllipses() == [2, 8]
        assert pagination.getIndexOfCurrentPage() == 5


        assert pagination.getIndexOfPageByNumber(1) == 1
        assert pagination.getIndexOfPageByNumber(2) == -1
        assert pagination.getIndexOfPageByNumber(233) == -1
        assert pagination.getIndexOfPageByNumber(pagination.getNumberOfPages()) == 9

        tryLoad("https://www.dev.webstaurantstore.com/43905/dishwasher-parts-and-dishwashing-components.html")
        assert pagination.getCurrentPageNumberFromUrl() == 1
        assert pagination.getCurrentPageNumber() == 1
        assert pagination.getIndexOfLeftArrow() == 0
        // Left arrow not present if not necessary(before was greyed out)
        assert pagination.getIndexOfRightArrow() == 7
        assert pagination.getIndexesOfEllipses() == [5]
        assert pagination.getIndexOfCurrentPage() == 0


        assert pagination.getIndexOfPageByNumber(1) == 0
        assert pagination.getIndexOfPageByNumber(2) == 1
        assert pagination.getIndexOfPageByNumber(233) == -1
        assert pagination.getIndexOfPageByNumber(pagination.getNumberOfPages()) == 6

        tryLoad("/43905/dishwasher-parts-and-dishwashing-components.html?vendor=Aerowerks")
        assert pagination.getCurrentPageNumberFromUrl() == 1
        assert pagination.getCurrentPageNumber() == 1
        assert pagination.getIndexOfLeftArrow() == -1
        assert pagination.getIndexOfRightArrow() == -1
        assert pagination.getIndexesOfEllipses() == []
        assert pagination.getIndexOfCurrentPage() == -1
        assert pagination.getIndexOfPageByNumber(1) == -1
        assert pagination.getIndexOfPageByNumber(2) == -1
        assert pagination.getIndexOfPageByNumber(233) == -1
        assert pagination.getIndexOfPageByNumber(pagination.getNumberOfPages()) == -1

        // page without product listing but with pagination
        //https://www.dev.webstaurantstore.com/food-service-resources/restaurant-management/

      /*  log "pagination.getIndexOfPageByNumber(1): " + pagination.getIndexOfPageByNumber(1)
        log "pagination.getIndexOfPageByNumber(2): " + pagination.getIndexOfPageByNumber(2)
        log "pagination.getIndexOfPageByNumber(233): " + pagination.getIndexOfPageByNumber(233)
        log "pagination.getIndexOfPageByNumber(pagination.getNumberOfPages())" + pagination.getIndexOfPageByNumber(pagination.getNumberOfPages())
*/
        /*  log "pagination.getCurrentPageNumberFromUrl(): " + pagination.getCurrentPageNumberFromUrl()
          log "pagination.getIndexOfLeftArrow(): " + pagination.getIndexOfLeftArrow()
          log "pagination.getIndexOfRightArrow(): " + pagination.getIndexOfRightArrow()
          log "pagination.getIndexesOfEllipses(): " + pagination.getIndexesOfEllipses()
          log "pagination.getIndexOfCurrentPage(): " + pagination.getIndexOfCurrentPage()*/


    }
}

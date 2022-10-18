package framework.runweb.scroll

import above.RunWeb

class UtFuncActionScrollToFooter extends RunWeb {
    static void main(String[] args) {
        new UtFuncActionScrollToFooter().testExecute([

                browser      : 'chrome',//'safari' edge
                remoteBrowser: false,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])
    }

    def test() {

        setup('vdiachuk', 'Actions Unit test for Scroll to footer| Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test actions action scrolltofooter scroll',
                 "tfsTcIds:265471", 'logLevel:info'])

        //
        // Checking Search Page
        //
        String urlWithItems = "https://www.dev.webstaurantstore.com/search/table.html"
      //  String itemOnTheBottomOfThePageXpath = "//div[@data-item-number='3492430antnt']"
        String itemOnTheBottomOfThePageXpath =  "//*[text()='384y728kt12']"
        String itemXpath = "//div[@data-item-number]"
        tryLoad(urlWithItems)

        //check we can not find item without scroll
      //  assert !find(itemOnTheBottomOfThePageXpath)
        log "before scroll: " + findElements(itemXpath).size()
        assert scrollToBottom()

        //verify element can be found after scrolling to footer
        assert find(itemOnTheBottomOfThePageXpath)
        log "after scroll: " + findElements(itemXpath).size()
        assert findElements(itemXpath).size() == 60

        //
        // Checking Category Page
        //
        String urlCategoryPageWithManyItems = "https://www.dev.webstaurantstore.com/4081/napkin-dispensers.html"
        String item2OnTheBottomOfThePageXpath = "//div[@data-item-number='922652028']"

        tryLoad(urlCategoryPageWithManyItems)

        //check we can not find item without scroll
//        assert !find(item2OnTheBottomOfThePageXpath)
        log "before scroll: " + findElements(itemXpath).size()

        assert scrollToBottom()

        //verify element can be found after scrolling to footer
        assert find(item2OnTheBottomOfThePageXpath)
        log "after scroll: " + findElements(itemXpath).size()
        assert findElements(itemXpath).size() == 100


    }
}

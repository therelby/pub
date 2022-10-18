package framework.wss.pages.element.helptile

import above.RunWeb
import wss.pages.element.HelpTile

class UtHelpTileWaitAnyElementPresent extends RunWeb {

    static void main(String[] args) {
        new UtHelpTileWaitAnyElementPresent().testExecute([

                browser      : 'chrome',
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
        final int PBI = 530427
        setup([
                author  : 'vdiachuk',
                title   : 'Help Tile, wait for any element from List by xpath appears on the page unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'chat help tile wait element any unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        tryLoad()
        def xpaths = ["//input[@id='searchvalFAKE']",
                      "//fake[@id='searchval']"]
        HelpTile helpTile = new HelpTile()
        long startTime = System.currentTimeMillis()
        assert helpTile.waitForAnyElement(xpaths, 3) == false
        long endTime = System.currentTimeMillis()
        log "Timeout aprox: ${(endTime - startTime)/1000}/sec"

        def onePresentXpath = [
                "//input[@id='searchvalFAKE']",
                "//fake[@id='searchval']",
                "//input[@id='searchval']"]
        assert helpTile.waitForAnyElement(onePresentXpath, 0) == true
        assert helpTile.waitForAnyElement([]) == false
        assert helpTile.waitForAnyElement(["//div[@data-testid='hotkeys']"]) == true

    }
}

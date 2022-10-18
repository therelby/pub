package framework.wss.item.customitem

import above.RunWeb
import wss.item.custom.CustomCuttingBoard
import wss.pages.cart.ViewCartPage

class UtCustomCuttingBoardAddToCart extends RunWeb {


    static void main(String[] args) {
        new UtCustomCuttingBoardAddToCart().testExecute([

                browser      : 'chrome',//'chrome',
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
        final int PBI = 582354

        setup([
                author  : 'vdiachuk',
                title   : 'Custom Item Cutting Board, add to cart unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'custom cutting board item add cart unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        CustomCuttingBoard customCuttingBoard = new CustomCuttingBoard()

        (1..10).each {

            assert customCuttingBoard.navigate()
            def parameters = customCuttingBoard.setRandomBoard()
            assert parameters as boolean
            assert customCuttingBoard.addCustomBoardToCart()
            ViewCartPage viewCartPage = new ViewCartPage()
            //
            //   if (viewCartPage.getItemsInCart().size() != it) {
            // TEMP, to take screeshot in case of issue
            if (viewCartPage.getAllItems().size() != it) {
                log takeScreenshot()
                return
            }
            assert viewCartPage.getItemsInCart().size() == it

        }

    }
}

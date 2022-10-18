package framework.wss.item.customitem

import above.RunWeb
import wss.item.custom.CustomCuttingBoard

class UtCustomCuttingBoard extends RunWeb {

    static void main(String[] args) {
        new UtCustomCuttingBoard().testExecute([

                browser      : 'edge',//'edge', //'chrome',
                remoteBrowser: true,
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
                title   : 'Custom Item Cutting Board unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'custom cutting board item unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        CustomCuttingBoard customCuttingBoard = new CustomCuttingBoard()
        log customCuttingBoard.navigate()
        assert customCuttingBoard.getAvailableBoardColors().size() > 2
        //   log  customCuttingBoard.setAllParameters(10,'1"',3,3,null,true,3,true,true)


        (0..5).each {
            log "--"
            log "$it Iteration"
            if(!customCuttingBoard.setAllParameters(null, null, null, null, "Brown", true, 3, true)){
                takeScreenshot()
                return
            }
          //  assert customCuttingBoard.setAllParameters(null, null, null, null, "Brown", true, 3, true)
            assert customCuttingBoard.setAllParameters(null, null, null, null, "Tuff-Cut", false, 3, false)
        }
        assert customCuttingBoard.getTotalSum() == null
        assert customCuttingBoard.getTotalQuantity() == 0
        assert customCuttingBoard.getTotalPrice() == null
        assert customCuttingBoard.setColorByName("Tuff-Cut")
        assert customCuttingBoard.getSelectedColorName() == "Tuff-Cut"
        assert customCuttingBoard.setColorByName('Yellow')
        assert customCuttingBoard.getSelectedColorName() == 'Yellow'
        Random random = new Random()
        (0..100).each {
            log "--"
            int quantityToSet = random.nextInt(10) + 1
            def parameters = customCuttingBoard.setRandomBoard(quantityToSet)
            log parameters
            assert parameters as boolean
            assert customCuttingBoard.isBoardReadyAddToCart()
            assert customCuttingBoard.getAvailableBoardColors()
            def totalSum = customCuttingBoard.getTotalSum()
            def totalQuantity = customCuttingBoard.getTotalQuantity()
            def totalPriceEach = customCuttingBoard.getTotalPrice()

            log 'totalSum: ' + totalSum
            log "totalQuantity: " + totalQuantity
            log "totalPriceEach: " + totalPriceEach
            assert totalQuantity == quantityToSet
            if( totalSum < 1){
                takeScreenshot()
                return
            }
            assert totalSum > 1
            assert totalPriceEach > 1
            refresh()
        }

    }
}

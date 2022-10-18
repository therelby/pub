package framework.wss.pages.compareproducts

import above.RunWeb
import wss.pages.compareproducts.CompareProductItemBox

class UtCompareProductItemBoxAddToCart extends RunWeb {
    static void main(String[] args) {
        new UtCompareProductItemBoxAddToCart().testExecute([
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
        final int PBI = 610767
        setup([
                author  : 'vdiachuk',
                title   : 'Compare Products Item Box Add to Cartunit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'compare item product box itembox add cart unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        def compareUrl = "/compareproducts/?items=303bwcop16%2c303bwcop11%2c303bwcop13"
        tryLoad(compareUrl)
        CompareProductItemBox compareProductItemBox = new CompareProductItemBox('303bwcop11')
        assert compareProductItemBox.clickAddToCart()

        assert compareProductItemBox.addToCartWithConfirmationCheck(10)
        assert !compareProductItemBox.addToCartWithConfirmationCheck(-10)


//        tryLoad()
//        CompareProductItemBox compareProductItemBox2 = new CompareProductItemBox('303bwcop11')
//        assert compareProductItemBox2.clickAddToCart() == false


    }
}

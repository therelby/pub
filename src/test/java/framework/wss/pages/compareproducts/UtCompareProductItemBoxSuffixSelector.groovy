package framework.wss.pages.compareproducts

import above.RunWeb
import wss.pages.compareproducts.CompareProductItemBox

class UtCompareProductItemBoxSuffixSelector extends RunWeb {

    static void main(String[] args) {
        new UtCompareProductItemBoxSuffixSelector().testExecute([
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
                title   : 'Compare Products Item Box unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'compare item product box itembox suffix dropdown unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        tryLoad("/compareproducts/?items=600ddt48%20%20%20%20lft,600smfss23l")
        CompareProductItemBox compareProductItemBox = new CompareProductItemBox("600ddt48    LFT",false)
       assert compareProductItemBox.addToCartWithConfirmationCheck() == false
       assert compareProductItemBox.setSuffixSelector() == true
       assert compareProductItemBox.addToCartWithConfirmationCheck() == true

        closeBrowser()
        tryLoad("/compareproducts/?items=600ddt48%20%20%20%20lft,600smfss23l")
        CompareProductItemBox compareProductItemBox2 = new CompareProductItemBox("600ddt48    LFT",true)
        assert compareProductItemBox2.addToCartWithConfirmationCheck() == true
        assert compareProductItemBox2.setSuffixSelector() == true
        assert compareProductItemBox2.addToCartWithConfirmationCheck(2) == true

        CompareProductItemBox compareProductItemBox1 = new CompareProductItemBox('600smfss23l')
        assert compareProductItemBox1.addToCartWithConfirmationCheck() == true
        assert compareProductItemBox1.setSuffixSelector() == true
        assert compareProductItemBox1.addToCartWithConfirmationCheck(2) == true
    }
}

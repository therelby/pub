package framework.wss.pages.compareproducts

import above.RunWeb
import wss.pages.compareproducts.CompareProductsPage

class UtCompareProductPageGetRowHeaders extends RunWeb {

    static void main(String[] args) {
        new UtCompareProductPageGetRowHeaders().testExecute([
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
        final int PBI = 0
        setup([
                author  : 'vdiachuk',
                title   : 'Compare Products Page get row headers unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'compare item product row header unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        def compareUrl = "/compareproducts/?items=303bwcop16%2c303bwcop11%2c303bwcop13"
        tryLoad(compareUrl)
        CompareProductsPage compareProductsPage = new CompareProductsPage()
        def data = compareProductsPage.getRowHeadersData()
        //log data
        assert data.size() == 15
        log "--"

        tryLoad()
        assert compareProductsPage.getRowHeadersData() == []
    }
}

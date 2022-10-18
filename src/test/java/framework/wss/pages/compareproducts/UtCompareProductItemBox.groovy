package framework.wss.pages.compareproducts

import above.RunWeb
import wss.pages.compareproducts.CompareProductItemBox

class UtCompareProductItemBox extends RunWeb {
    static void main(String[] args) {
        new UtCompareProductItemBox().testExecute([
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
                keywords: 'compare item product box itembox unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        def compareUrl = "/compareproducts/?items=303bwcop16%2c303bwcop11%2c303bwcop13"
        tryLoad(compareUrl)
        CompareProductItemBox compareProductItemBox = new CompareProductItemBox('303bwcop11')
        assert compareProductItemBox.verifyItemBoxPresent()
        String itemNumberStringXpath = compareProductItemBox.getXpath('itemNumber')
        log itemNumberStringXpath
        log verifyElement(itemNumberStringXpath)
        def headerData1 = compareProductItemBox.getProductHeaderData()
        log 'headerData1' + headerData1
        assert headerData1 == ['itemNumber': 'Item #: 303bwcop11', 'productImage': 'https://www.dev.webstaurantstore.com/images/products/medium/274135/1516286.jpg', 'productTitle': 'Acopa 11 1/4" Round Bright White Coupe Stoneware Plate - 12/Case', 'plusIcon': 'https://www.dev.webstaurantstore.com/plus/', 'productQuantity': '1', 'isAddToCartButton': true, 'yellowStarRating': 4, 'brandLink': 'https://www.dev.webstaurantstore.com/vendor/acopasy.html', 'brandImg': 'https://www.dev.webstaurantstore.com/images/vendor/medium/20200608/acopa_original_logo_hr.jpg']


        def rowData1 = compareProductItemBox.getProductCompareRowsData()

        log "rowData1:" + rowData1
        assert rowData1.find() { it['headerName'] == 'Top Diameter' }.getAt('text') == "11 1/4 Inches"
        int quantity = 8
        assert compareProductItemBox.setQuantity(quantity.toString())
        assert getAttributeSafe(compareProductItemBox.getXpath('productQuantity'), 'value') == quantity.toString()

        log "--"

        tryLoad("/compareproducts/?items=600ddt48%20%20%20%20lft,600smfss23l")
        CompareProductItemBox compareProductItemBoxWithSpace = new CompareProductItemBox("600ddt48    lft")
        assert compareProductItemBoxWithSpace.verifyItemBoxPresent()

        assert compareProductItemBoxWithSpace.getXpath('itemNumber').contains(CompareProductItemBox.boxElements['itemNumber'])

        log compareProductItemBoxWithSpace.getProductHeaderData()
        assert compareProductItemBoxWithSpace.getProductHeaderData() == [
                "itemNumber"       : "Item #: 600ddt48    lft",
                "productImage"     : "https://www.dev.webstaurantstore.com/images/products/medium/46355/2136186.jpg",
                "productTitle"     : "Regency 16 Gauge 4' Soiled / Dirty Dish Table - Left",
                "plusIcon"         : "https://www.dev.webstaurantstore.com/plus/",
                "productQuantity"  : "1",
                "isAddToCartButton": true,
                "yellowStarRating" : 5,
                "brandLink"        : "https://www.dev.webstaurantstore.com/vendor/regency-tables-sinks.html",
                "brandImg"         : "https://www.dev.webstaurantstore.com/images/vendor/medium/20180703/regency_tables_and_sinks_logo_hr.jpg"
        ]
        assert compareProductItemBoxWithSpace.getProductCompareRowsData().find() { it['headerName'] == 'Material' }.getAt('text') == "Stainless Steel "
        assert compareProductItemBoxWithSpace.setQuantity(quantity.toString())
        assert getAttributeSafe(compareProductItemBoxWithSpace.getXpath('productQuantity'), 'value') == quantity.toString()

        log "--"

        tryLoad()
        CompareProductItemBox compareProductItemBoxNoItem = new CompareProductItemBox('303bwcop11')
        assert !compareProductItemBoxNoItem.verifyItemBoxPresent()
        assert compareProductItemBoxNoItem.getProductCompareRowsData() == []
        assert compareProductItemBoxNoItem.getProductHeaderData() == [:]
        assert !compareProductItemBoxNoItem.setQuantity(quantity.toString())

    }
}

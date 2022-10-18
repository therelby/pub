package framework.wss.pages.productdetail.accessory

import above.RunWeb
import wss.pages.productdetail.PDPAccessory

class UtPDPAccessory extends RunWeb {

    static void main(String[] args) {
        new UtPDPAccessory().testExecute([

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
                title   : 'PDP Accessory unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' pdp product detail page accessory accessories options unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        //tryLoad("/american-tables-seating-as-42tb-3-w-biltmore-standard-seat-three-button-pullover-back-wall-bench-42-high/132AS42TB3W.html")
        tryLoad("/wabash-valley-cm230q-camino-42-round-square-perforated-top-surface-mount-plastisol-coated-aluminum-outdoor-ada-accessible-umbrella-table-with-3-attached-chairs/947CM230Q.html")
        PDPAccessory pdpAccessory = new PDPAccessory()

        assert pdpAccessory.isAccessory()
        assert pdpAccessory.getAccessoryHeaderText() == PDPAccessory.accessoryHeaderText

        def accessoryData = pdpAccessory.getData()
        assert accessoryData.size() > 1
        assert pdpAccessory.getAccessories() == accessoryData.collect() { it['accessoryName'] }.unique().grep()

        log accessoryData

        log "--"
        // NO accessories
        tryLoad("https://www.dev.webstaurantstore.com/g/68/ace-76745-providence-men-s-black-water-resistant-steel-toe-non-slip-work-boot")

        assert !pdpAccessory.isAccessory()
        assert pdpAccessory.getAccessoryHeaderText() == ""
        assert pdpAccessory.getData() == []
        assert pdpAccessory.getAccessories() == []
    }
}

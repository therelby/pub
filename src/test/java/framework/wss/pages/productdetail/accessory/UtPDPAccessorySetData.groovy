package framework.wss.pages.productdetail.accessory

import above.RunWeb
import wss.pages.productdetail.PDPAccessory

class UtPDPAccessorySetData extends RunWeb {

    static void main(String[] args) {
        new UtPDPAccessorySetData().testExecute([

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
                title   : 'PDP Accessory set data unit test | Framework Self Testing Tool ',
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
        Map settings0 = ['Table Top Color': '4689922', 'Leg Color': '4689954', 'Seat Color': '4689982']
        //    Map settings0 = ['Table Top Color':'fdfdfdf','Leg Color': '4689954','Seat Color':'4689982']
        assert pdpAccessory.setData(settings0, 'value')

        closeBrowser()
        tryLoad("/wabash-valley-cm230q-camino-42-round-square-perforated-top-surface-mount-plastisol-coated-aluminum-outdoor-ada-accessible-umbrella-table-with-3-attached-chairs/947CM230Q.html")
        settings0 = ['Table Top Color': 'FAKE', 'Leg Color': '4689954', 'Seat Color': '4689982']
        assert !pdpAccessory.setData(settings0, 'value')

        closeBrowser()
        tryLoad("/wabash-valley-cm230q-camino-42-round-square-perforated-top-surface-mount-plastisol-coated-aluminum-outdoor-ada-accessible-umbrella-table-with-3-attached-chairs/947CM230Q.html")
        Map settings1 = ['Leg Color': 'Dark Bronze - $0.00', 'Seat Color': 'Dark Bronze - $0.00']
        assert pdpAccessory.setData(settings1, 'text')

        closeBrowser()
        tryLoad("/wabash-valley-cm230q-camino-42-round-square-perforated-top-surface-mount-plastisol-coated-aluminum-outdoor-ada-accessible-umbrella-table-with-3-attached-chairs/947CM230Q.html")
        settings1 = ['Leg Color': 'Dark Bronze - $0.00', 'Seat Color': 'Dark Bronze - $0.00', 'Table Top Color': 'FAKE']
        assert !pdpAccessory.setData(settings1, 'text')

        closeBrowser()
        tryLoad("/wabash-valley-cm230q-camino-42-round-square-perforated-top-surface-mount-plastisol-coated-aluminum-outdoor-ada-accessible-umbrella-table-with-3-attached-chairs/947CM230Q.html")
        settings1 = ['FAKE': 'Dark Bronze - $0.00']
        assert ! pdpAccessory.setData(settings1, 'text')

    }
}

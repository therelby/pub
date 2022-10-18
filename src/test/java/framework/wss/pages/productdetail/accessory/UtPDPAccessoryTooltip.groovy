package framework.wss.pages.productdetail.accessory

import above.RunWeb
import wss.pages.productdetail.PDPAccessory

class UtPDPAccessoryTooltip extends RunWeb{

    static void main(String[] args) {
        new UtPDPAccessoryTooltip().testExecute([

                browser      : 'safari',
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
                title   : 'PDP Accessory tooltip unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' pdp product detail page accessory accessories options tooltip data unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        tryLoad("/american-tables-seating-as-36vn-3-4-3-4-circle-v-shape-back-corner-booth-36-high/132AS36VN34.html")
        PDPAccessory pdpAccessory = new PDPAccessory()
        def tooltipData = pdpAccessory.getTooltipData()
        log tooltipData
        assert tooltipData.find(){it['askIconPresent']&&it['tooltipPresent']}.getAt('tooltipHeader')=="6. Cal 133 Spec."

        tryLoad("/wabash-valley-cm230q-camino-42-round-square-perforated-top-surface-mount-plastisol-coated-aluminum-outdoor-ada-accessible-umbrella-table-with-3-attached-chairs/947CM230Q.html")
        assert pdpAccessory.getTooltipData().find(){it['askIconPresent']&&it['tooltipPresent']}.getAt('tooltipText')=="Refer to the Color Chart PDF below to see images of available colors. Please note: images are not precise representations of the end product color."

    }
}

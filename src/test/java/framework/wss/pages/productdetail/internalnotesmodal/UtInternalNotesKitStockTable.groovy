package framework.wss.pages.productdetail.internalnotesmodal

import above.RunWeb
import wss.pages.productdetail.PDPInternalNotesModal
import wss.pages.productdetail.PDPWhatWeOffer
import wss.user.UserQuickLogin

class UtInternalNotesKitStockTable extends RunWeb {

    static void main(String[] args) {
        new UtInternalNotesKitStockTable().testExecute([

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
        final int PBI = 523214
        setup([
                author  : 'vdiachuk',
                title   : 'PDP, Internal Notes Kit Stock Table get data | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' pdp product detail internal notes kit stock table unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        // page with Kit items
        // tryLoad('/curtron-pest-pro-bl100gldc-gold-combination-high-intensity-uv-flying-insect-control-light-and-trap-with-10-replacement-insect-trap-glue-boards-15w/517BL100GLDC.html')

        // page with Rules and Tooltip D
        tryLoad('/continental-countryside-charcuterie-collection/100CNTCNTRY.html')
        PDPInternalNotesModal pdpInternalNotesModal = new PDPInternalNotesModal()
        pdpInternalNotesModal.openModal()
        waitForElement(PDPInternalNotesModal.kitStockCountTableXpath)
        def kitData = pdpInternalNotesModal.getKitStockCountData()
        for (kitDetail in kitData) {
            def rules = kitDetail['Rules']
            def tooltip = kitDetail['tooltipRules']
            if (rules) {
                assert tooltip.size() > 2
                log "TEMPLOG: Checked:)"
            }
        }

    }

}

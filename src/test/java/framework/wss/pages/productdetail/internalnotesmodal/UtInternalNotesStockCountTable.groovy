package framework.wss.pages.productdetail.internalnotesmodal

import above.RunWeb
import wss.pages.productdetail.PDPInternalNotesModal

class UtInternalNotesStockCountTable extends RunWeb {
    def test() {

        setup('vdiachuk', 'Internal Notes Modal -  Stock Count Table unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test modal internal notes stock count table',
                 'PBI: 0',
                 'logLevel:info'])


        PDPInternalNotesModal pdpInternalNotesModal = new PDPInternalNotesModal()
        def stockCountData
        tryLoad("https://www.dev.webstaurantstore.com/rubbermaid-fgsr18epltbk-silhouettes-textured-black-steel-designer-rectangular-waste-receptacle-40-gallon/690SR18EPLBK.html")

        stockCountData = pdpInternalNotesModal.getStockCountsTable()
        assert stockCountData.size() == 0
        log "click: " + click(PDPInternalNotesModal.buttonLinkXpath)

        log "wait: " + waitForElement(PDPInternalNotesModal.modalItemHeaderDivXpath)
        // log "headerText: " + getTextSafe(PDPInternalNotesModal.modalItemHeaderDivXpath)
        log "--"
        stockCountData = pdpInternalNotesModal.getStockCountsTable()
        assert stockCountData.size() == 9
        log "" + stockCountData

        tryLoad("https://www.dev.webstaurantstore.com/regency-24-x-48-nsf-stainless-steel-solid-shelf/460SS2448.html")

        stockCountData = pdpInternalNotesModal.getStockCountsTable()
        assert stockCountData.size() == 0
        log "click: " + click(PDPInternalNotesModal.buttonLinkXpath)

        log "wait: " + waitForElement(PDPInternalNotesModal.modalItemHeaderDivXpath)
        // log "headerText: " + getTextSafe(PDPInternalNotesModal.modalItemHeaderDivXpath)
        log "--"
        stockCountData = pdpInternalNotesModal.getStockCountsTable()
        log  stockCountData
        assert stockCountData.find() { it['Rules'] == 'S' }.getAt("RulesTooltip") == "\"do not ship from 102 unless OOS everywhere else\" - Aaron Weber"
        assert stockCountData.find() { it['Rules'] == 'X' }.getAt("RulesTooltip") == "\"items in 105 are for Hometown customers only; do NOT ship WEB, Pro, or CNA from 105\" - Nate Shertzer"


    }
}

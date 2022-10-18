package framework.wss.pages.productdetail.stickyheader

import above.RunWeb
import wss.pages.productdetail.PDPStickyHeader

class UtPDPStickyHeader extends RunWeb {

    def test() {

        setup('vdiachuk', 'PDPage Product Detail Page Sticky Header unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test pdpage pdp product detail page sticky header ',
                 "PBI:0",
                 'logLevel:info'])

        tryLoad("https://www.dev.webstaurantstore.com/advance-tabco-tss-369-36-x-108-14-gauge-open-base-stainless-steel-commercial-work-table/109TSS369.html")
        PDPStickyHeader pdpStickyHeader = new PDPStickyHeader()
        log "before: " + pdpStickyHeader.isStickyHeader()
        assert !pdpStickyHeader.isStickyHeader()
        log pdpStickyHeader.getItemNumber()
        log "Close tooltip: "+ pdpStickyHeader.getCloseButtonTooltip()
        log "Show tooltip: "+ pdpStickyHeader.getShowButtonTooltip()
        assert pdpStickyHeader.getCloseButtonTooltip() == ''
        assert pdpStickyHeader.getShowButtonTooltip() == ''
        log "--"
        scrollToBottom()

        log "after: " + pdpStickyHeader.isStickyHeader()
        assert pdpStickyHeader.isStickyHeader()
        assert pdpStickyHeader.getItemNumber().size() > 4
        assert pdpStickyHeader.isShown()
        assert !pdpStickyHeader.isHidden()
        log "SH shown orig image: " + pdpStickyHeader.getProductImage()
        assert pdpStickyHeader.getProductImage().contains("https://")
        log "Close tooltip: "+ pdpStickyHeader.getCloseButtonTooltip()
        log "Show tooltip: "+ pdpStickyHeader.getShowButtonTooltip()
        assert pdpStickyHeader.getCloseButtonTooltip() == PDPStickyHeader.closeTooltipText
        assert pdpStickyHeader.getShowButtonTooltip() == ''

        log "--"

        // click to hide
        assert click(PDPStickyHeader.closeButtonXpath)
        assert !pdpStickyHeader.isShown()
        assert pdpStickyHeader.isHidden()
        log "SH hidden image: " + pdpStickyHeader.getProductImage()
        assert  pdpStickyHeader.getProductImage() ==''
        log "Close tooltip: "+ pdpStickyHeader.getCloseButtonTooltip()
        log "Show tooltip: "+ pdpStickyHeader.getShowButtonTooltip()
        assert pdpStickyHeader.getCloseButtonTooltip() == ""
        assert pdpStickyHeader.getShowButtonTooltip() == PDPStickyHeader.showTooltipText
        log "--"

        // click to show
        assert click(PDPStickyHeader.showButtonXpath)
        assert pdpStickyHeader.isShown()
        assert !pdpStickyHeader.isHidden()
        log "SH shown image: " + pdpStickyHeader.getProductImage()
        assert pdpStickyHeader.getProductImage().contains("https://")
        log "Close tooltip: "+ pdpStickyHeader.getCloseButtonTooltip()
        log "Show tooltip: "+ pdpStickyHeader.getShowButtonTooltip()
        assert pdpStickyHeader.getCloseButtonTooltip() ==  PDPStickyHeader.closeTooltipText
        assert pdpStickyHeader.getShowButtonTooltip() == ''


    }
}

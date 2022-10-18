package framework.wss.pages.productdetail.stickyheader

import above.RunWeb
import wss.pages.productdetail.PDPStickyHeader

class UtPDPStickyHeaderInputQuantity extends RunWeb {

    def test() {
        int pbi = 503011
        setup('vdiachuk', 'PDPage Product Detail Page Sticky Header get set input quantity unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test pdpage pdp product detail page sticky header get set quantity',
                 "PBI:$pbi",
                 'logLevel:info'])

        PDPStickyHeader pdpStickyHeader = new PDPStickyHeader()
        tryLoad("https://www.dev.webstaurantstore.com/extra-virgin-olive-oil-3-liter-tin/101OLIVEVIRG.html")
        assert pdpStickyHeader.getInputQuantity() == null
        assert pdpStickyHeader.getBaseData() == [:]

        scrollToBottom()
        assert pdpStickyHeader.getInputQuantity() == '1'

        log "--"

        tryLoad("https://www.dev.webstaurantstore.com/choice-economy-4-qt-half-size-stainless-steel-chafer/100ECONHALF.html")
        scrollToBottom()
        assert pdpStickyHeader.getInputQuantity() == '1'
        String quantityStr = '10'
        String quantityFake = "Fake"
        assert pdpStickyHeader.setInputQuantity(quantityStr)
        assert pdpStickyHeader.getInputQuantity() == quantityStr
        assert pdpStickyHeader.setInputQuantity(quantityFake)
        assert pdpStickyHeader.getInputQuantity() == ""


    }
}

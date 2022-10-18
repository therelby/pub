package framework.wss.pages.element.filter

import above.RunWeb
import wss.item.itembox.ProductListingItemBox
import wss.pages.element.Filter

class UtFilterClassificationTip extends RunWeb {
    def test() {

        setup('vdiachuk', 'Product Listing Filter Element Classification tooltip unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test Product Listing filter tooltip tip tips question classification',
                 "tfsTcIds:265471", 'logLevel:info'])

        tryLoad("https://www.dev.webstaurantstore.com/specializedpage.cfm?index=23312&forcecacheupdate=1&filter=webstaurantplus:eligible")
        Filter filter = new Filter()
        log "Induction Ready: " + filter.getClassificationTip("Induction Ready")
        assert filter.getClassificationTip("Induction Ready").contains("Instead of traditional cooking methods where the cook top generates heat that then passes")

        assert filter.getClassificationTip("Number of Fry Pots").contains("Also called tanks, this refers to how many separate frying areas the fryer has.")
        log "Number of Fry Pots: " + filter.getClassificationTip("Number of Fry Pots")
        assert filter.getClassificationTip("Width") == null
        log "Number of Fry Pots: " + filter.getClassificationTip("Width")


        List classificatinHeaders = filter.getAllClassifications()
        log "quantity: " + classificatinHeaders.size()
        for (def classificationHeader in classificatinHeaders) {
            log classificationHeader + " :   " + filter.getClassificationTip(classificationHeader)
        }


    }
}

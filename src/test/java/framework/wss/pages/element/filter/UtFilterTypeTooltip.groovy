package framework.wss.pages.element.filter

import above.RunWeb
import wss.pages.element.Filter

class UtFilterTypeTooltip extends RunWeb {
    def test() {

        setup('vdiachuk', 'Product Listing Filter Element Type(item) tooltip unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test Product Listing filter tip tooltip tips question type',
                 "tfsTcIds:265471", 'logLevel:info'])

        tryLoad("https://www.dev.webstaurantstore.com/specializedpage.cfm?index=23312&filter=webstaurantplus:eligible")
        Filter filter = new Filter()
        //    setLogLevel("debug")
        //Existing Type Element with tooltip
        //     log "Riveted: " + filter.getFilterTypeTooltip("Riveted")
        assert filter.getFilterTypeTooltip("Riveted").contains("A riveted handle is connected by small metal pieces (called rivets) that are crushed between the handle and the pan body to attach them together.")

        //Existing Type Element without tooltip
        //   log "Natural Gas: " + filter.getFilterTypeTooltip("Natural Gas")
        assert filter.getFilterTypeTooltip("Natural Gas") == null

        //non existing type element
        //   log "FAKE: " + filter.getFilterTypeTooltip("FAKE")
        assert filter.getFilterTypeTooltip("FAKE") == null

    }
}
package framework.wss.pages.element.filter

import above.RunWeb
import wss.pages.element.Filter

class UtFilterFoldExpandClassification extends RunWeb {
    def test() {

        setup('vdiachuk', 'Filter Element Fold/Expand unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test filter element expand fold ',
                 "tfsTcIds:265471",
                 'logLevel:info'])


        Filter filter = new Filter()

        String url = "https://www.dev.webstaurantstore.com/search/table.html?category=48633"
        tryLoad(url)
        def classificationHeaders = filter.getAllClassifications()
        //vendor expanded by default
        assert !filter.checkClassificationHeaderFolded("Vendor")
        //fold
        assert filter.clickClassificationHeaderByName("Vendor")
        assert filter.checkClassificationHeaderFolded("Vendor")
        //expand
        assert filter.clickClassificationHeaderByName("Vendor")
        assert !filter.checkClassificationHeaderFolded("Vendor")

        for (def heaeder in classificationHeaders) {
            log "header: $heaeder, is folded:" + filter.checkClassificationHeaderFolded(heaeder)
            log "click on header: " + filter.clickClassificationHeaderByName(heaeder)
            log "after click, header: $heaeder, is folded:" + filter.checkClassificationHeaderFolded(heaeder)
            log "=="
        }

    }
}
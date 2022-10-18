package framework.wss.pages.element.filter

import above.RunWeb
import wss.pages.element.Filter

class UtFilterUnfoldAllClassifications extends RunWeb {
    def test() {

        setup('vdiachuk', 'Product Listing Filter unfold expand all classifications unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test filter unfold expand element classification',
                 "tfsTcIds:265471", 'logLevel:info'])

        tryLoad("https://www.dev.webstaurantstore.com/specializedpage.cfm?index=23312&filter=webstaurantplus:eligible")
        Filter filter = new Filter()
        filter.clickClassificationHeaderByName("WebstaurantPlus")
        assert filter.checkClassificationHeaderFolded("WebstaurantPlus")
        List classificationNames = filter.getAllClassifications()

     //   classificationNames.each { log it + " : " + filter.checkClassificationHeaderFolded(it) }
        log "--"
        log "unfoldALL: "+ filter.unfoldAllClassifications()
        classificationNames.each {
            assert !filter.checkClassificationHeaderFolded(it)
        }


        /*tryLoad("https://www.dev.webstaurantstore.com/search/lft.html")
        log filter.getAllClassifications()
        assert filter.unfoldAllClassifications()*/

    }
}

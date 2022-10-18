package framework.wss.pages.productdetail

import above.RunWeb
import wss.api.catalog.product.Products
import wss.pages.productdetail.PDPage

class UtPDPage extends RunWeb {

    static void main(String[] args) {
        new UtPDPage().testExecute([:])
//        new UtPDPage().testExecute([
//                remoteBrowser: true,        // use remote browser
//                browser: 'edge',            // use specific browser
//                browserVersionOffset: -1,   // use specific browser version
//                environment: 'test',        // use specific environment
//                runType: 'Regular'          // Regular running (with stats saving)
//        ])
    }

    def test() {

        setup('vdiachuk', 'PDPage Product Detail Page  unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test pdpage pdp product detail page ',
                 "tfsTcIds:265471",
                 'logLevel:info'])

        //get images from API
        def productAPI = new Products("600ST3048   LFT", [allowGroupingProducts: true, ignoreVisibilityFilters: true, showHidden: true])
        def images = productAPI.getValues('images') // Should be list of maps*/
        log images
        PDPage pdPage2 = new PDPage()
        pdPage2.navigateToPDPWithItemNumber("600ST3048")
        assert pdPage2.isPDPage()
        assert pdPage2.getImageGalleryData().size() > 3
        log " pdPage2.getItemNumber(): [${pdPage2.getItemNumber()}]"
        assert '600ST3048 LFT' == pdPage2.getItemNumber()

        def highlightsData = pdPage2.getHighlightsData()
        log highlightsData
        assert highlightsData.size() == 5
        for (highlight in highlightsData) {
            assert highlight['icon']
            assert highlight['text']?.size() > 2
        }
    }

}

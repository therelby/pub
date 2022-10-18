package framework.wss.pages.element.filter

import above.RunWeb
import wss.pages.element.Filter

class UtFilter2Types extends RunWeb {
    def test() {

        setup('vdiachuk', 'Filter Element Second unit test, Types and options | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test filter element type option ',
                 "tfsTcIds:265471",
                 'logLevel:info'])


        Filter filter = new Filter()
        //Checking URl with Filters
        String url = "https://www.dev.webstaurantstore.com/search/cookie-cutter.html?category=2405"
        tryLoad(url)
        def data1 = filter.getFilterTypesByClassification("Vendor")
        assert data1.size() > 0
        data1 = filter.getFilterTypesByClassification("Color")
        assert data1.any { it -> it.type.contains('Blue') }
        assert filter.getFilterTypesByClassification("FaKeTYPE").size() == 0
        assert filter.applyRandomFilterType("Vendor")
        assert filter.applyRandomFilterType("FAKEClassification") == false
        assert filter.applyRandomFilterType()


        //Checking URl with no Filter - should be no Exceptions and not null.. only false
        String urlNoFilter = "https://www.dev.webstaurantstore.com/"
        tryLoad(urlNoFilter)
        assert filter.getFilterTypesByClassification("Vendor") == []
        assert filter.applyRandomFilterType() == false
        assert filter.applyRandomFilterType("Vendor") == false


    }

}

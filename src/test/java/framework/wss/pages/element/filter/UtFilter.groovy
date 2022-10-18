package framework.wss.pages.element.filter

import above.RunWeb
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import wss.pages.element.Breadcrumb
import wss.pages.element.Filter

class UtFilter extends RunWeb {
    def test() {

        setup('vdiachuk', 'Filter Element  unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test filter element ',
                 "tfsTcIds:265471",
                 'logLevel:info'])


        String urlCategory = 'https://www.dev.webstaurantstore.com/25887/commercial-gas-ranges.html?page=10'
        String urlColor = 'https://www.dev.webstaurantstore.com/search/paper.html?category=569'
        //next url in Prod because in Dev Filters not working correct
        String urlSearch = 'https://www.dev.webstaurantstore.com/search/cookie-cutter.html'
        String urlNoFilter = 'https://www.dev.webstaurantstore' +
                '.com/wolf-c48c-8bp-challenger-xl-series-liquid-propane-48-range-with-8-burners-and-convection-oven-275-000-btu/950C48C8BP.html'

        Filter filter = new Filter()

        tryLoad(urlCategory)

        List classificationsHeaders = filter.getAllClassifications()
        assert filter.isFilter()
        assert classificationsHeaders.contains('Vendor')
        assert classificationsHeaders.contains('WebstaurantPlus')


        log "--"
        //page with color Type
        tryLoad(urlColor)
        assert filter.isFilter()
        classificationsHeaders = filter.getAllClassifications()
        assert classificationsHeaders.contains('Color test')
        tryLoad(urlSearch)

        log "filter.getAllClassifications(): " + filter.getAllClassifications()
        log "filter.getData(): " + filter.getData()
        String urlWithCheckedFilters = "https://www.dev.webstaurantstore.com/search/cookie-cutter.html?vendor=Ateco&filter=color:blue:silver&multi=true&filter=edge-style:fluted"
        tryLoad(urlWithCheckedFilters)
        log "urlWithCheckedFilters filter.getAllClassifications(): " + filter.getAllClassifications()
        log "urlWithCheckedFilters filter.getData(): " + filter.getData()
        assert filter.isRemoveAllFilters()
        assert filter.removeAllFilters()
        assert !filter.isRemoveAllFilters()


    }
}

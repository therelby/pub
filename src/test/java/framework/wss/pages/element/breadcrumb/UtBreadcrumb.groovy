package framework.wss.pages.element.breadcrumb

import above.RunWeb
import wss.pages.element.Breadcrumb


class UtBreadcrumb extends RunWeb {
    def test() {

        setup('vdiachuk', 'Breadcrumb Element  unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test breadcrumb element ',
                 "tfsTcIds:265471",
                 'logLevel:info'])


        String url = 'https://www.dev.webstaurantstore.com/25887/commercial-gas-ranges.html?page=10'
        String urlProduct = 'https://www.dev.webstaurantstore.com/wolf-c48c-8bp-challenger-xl-series-liquid-propane-48-range-with-8-burners-and-convection-oven-275-000-btu/950C48C8BP.html'
        String url2levelBreadcrumb = "https://www.dev.webstaurantstore.com/restaurant-equipment.html"
        String urlNoBreadcrumb = 'https://www.dev.webstaurantstore.com/search/table.html'

        tryLoad(url2levelBreadcrumb)
        Breadcrumb breadcrumb = new Breadcrumb()

        assert breadcrumb.isBreadcrumb()
        log "2Level breadcrumb.getValues(): " + breadcrumb.getValues()
        log "2Level breadcrumb.getData(): " + breadcrumb.getData()
        log "2Level breadcrumb.getParentLink(): " + breadcrumb.getParentLink()
        log "2Level breadcrumb.getParentText(): " + breadcrumb.getParentText()
        assert 'https://www.dev.webstaurantstore.com/' == breadcrumb.getParentLink()
        assert 'WebstaurantStore' == breadcrumb.getParentText()
        tryLoad(url)

        assert breadcrumb.isBreadcrumb()
        log "Category breadcrumb.getValues(): " + breadcrumb.getValues()
        log "Category breadcrumb.getData(): " + breadcrumb.getData()
        log "Category breadcrumb.getParentLink(): " + breadcrumb.getParentLink()
        assert 'https://www.dev.webstaurantstore.com/15037/commercial-restaurant-ranges.html' == breadcrumb.getParentLink()
        assert 'Commercial Restaurant Ranges' == breadcrumb.getParentText()

        tryLoad(urlProduct)
        log "Product breadcrumb.getValues(): " + breadcrumb.getValues()
        log "Product breadcrumb.getData(): " + breadcrumb.getData()
        log "Product breadcrumb.getParentLink(): " + breadcrumb.getParentLink()
        assert breadcrumb.isBreadcrumb()

        tryLoad(urlNoBreadcrumb)
        log "NO BREADCRUMB breadcrumb.getValues(): " + breadcrumb.getValues()
        log "NO BREADCRUMB breadcrumb.getData(): " + breadcrumb.getData()
        log "NO BREADCRUMB breadcrumb.getParentLink(): " + breadcrumb.getParentLink()
        assert breadcrumb.getValues() == []
        assert !breadcrumb.isBreadcrumb()
        assert !breadcrumb.getData()
        assert breadcrumb.getParentLink() == null
        assert breadcrumb.getParentText() == null


    }
}

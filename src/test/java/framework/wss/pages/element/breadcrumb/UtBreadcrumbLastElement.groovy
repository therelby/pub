package framework.wss.pages.element.breadcrumb

import above.RunWeb
import wss.pages.element.Breadcrumb

class UtBreadcrumbLastElement extends RunWeb{

        def test() {

            setup('vdiachuk', 'Breadcrumb Element - Last Part  unit test  | Framework Self Testing Tool',
                    ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                     'keywords:unit test breadcrumb element last part ',
                     "tfsTcIds:265471",
                     'logLevel:info'])


            String url = 'https://www.dev.webstaurantstore.com/25887/commercial-gas-ranges.html?page=10'
            String urlProduct = 'https://www.dev.webstaurantstore.com/wolf-c48c-8bp-challenger-xl-series-liquid-propane-48-range-with-8-burners-and-convection-oven-275-000-btu/950C48C8BP.html'
            String url2levelBreadcrumb = "https://www.dev.webstaurantstore.com/restaurant-equipment.html"
            String urlNoBreadcrumb = 'https://www.dev.webstaurantstore.com/search/table.html'

            Breadcrumb breadcrumb = new Breadcrumb()

            tryLoad(url2levelBreadcrumb)

            assert breadcrumb.isBreadcrumb()
            log "2Level breadcrumb.getValues(): " + breadcrumb.getLastValue()
            assert breadcrumb.getLastValue() == "Restaurant Equipment"

            tryLoad(url)
            log "https://www.dev.webstaurantstore.com/25887/commercial-gas-ranges.html?page=10 breadcrumb.getValues(): " + breadcrumb.getLastValue()
            assert breadcrumb.getLastValue() == "Commercial Gas Ranges"

            tryLoad(urlProduct)
            log "product breadcrumb.getValues(): " + breadcrumb.getLastValue()
            assert breadcrumb.getLastValue() == "Wolf C48C-8BP Challenger XL Series Liquid Propane 48\" Range with 8 Burners and Convection Oven - 275,000 BTU"

            tryLoad(urlNoBreadcrumb)
            log "urlNoBreadcrumb breadcrumb.getValues(): " + breadcrumb.getLastValue()
            assert breadcrumb.getLastValue() == ''
        }
    }
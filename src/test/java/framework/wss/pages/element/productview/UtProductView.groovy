package framework.wss.pages.element.productview

import above.RunWeb
import wss.pages.element.ProductView
import wss.pages.element.SortBy

class UtProductView extends RunWeb {

    def test() {

        setup('vdiachuk', 'ProductView Element unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test ProductView grid list view product item element',
                 "tfsTcIds:265471",
                 'logLevel:info'])

        log "======Category Page testing"
        String urlCategory = 'https://www.dev.webstaurantstore.com/42541/chair-carts-trucks-and-dollies.html'
        tryLoad(urlCategory)
        ProductView productView = new ProductView()
        log "isProductView(): " + productView.isProductView()
        log "productView.getView(): " + productView.getView()
        log "productView.getViewProductListing(): " + productView.getViewProductListing()
        assert productView.isProductView()
        assert 'grid' == productView.getView()
        assert 'grid' == productView.getViewProductListing()

        assert productView.setViewToList()
        assert 'list' == productView.getView()
        assert 'list' == productView.getViewProductListing()
        log "grid tooltip:" + productView.getGridTooltip()
        log "list tooltip:" + productView.getListTooltip()
        assert  productView.getGridTooltip()=='Grid view'
        assert productView.getListTooltip()=='List view'
        assert productView.setViewToGrid()
        assert 'grid' == productView.getView()
        assert 'grid' == productView.getViewProductListing()


        log "grid tooltip:" + productView.getGridTooltip()
        log "list tooltip:" + productView.getListTooltip()
        assert  productView.getGridTooltip()=='Grid view'
        assert productView.getListTooltip()=='List view'



        log "======Search Page testing"
        String urlSearchSort = 'https://www.dev.webstaurantstore.com/search/table.html?category=3787&order=price_asc'
        tryLoad(urlSearchSort)
        ProductView productViewSearch = new ProductView()
        log "isProductView(): " + productViewSearch.isProductView()
        log "productView.getView(): " + productViewSearch.getView()
        assert productViewSearch.isProductView()
        assert 'grid' == productViewSearch.getView()
        assert 'grid' == productViewSearch.getViewProductListing()
        assert productViewSearch.setViewToList()
        assert 'list' == productViewSearch.getView()
        assert 'list' == productViewSearch.getViewProductListing()
        assert productViewSearch.setViewToGrid()
        assert 'grid' == productViewSearch.getView()
        assert 'grid' == productViewSearch.getViewProductListing()



        log "=====NO Product View Page testing"
        String urlNoProductView = 'https://www.dev.webstaurantstore.com/myaccount/'
        tryLoad(urlNoProductView)
        ProductView noView = new ProductView()
        log "isProductView(): " + noView.isProductView()
        log "productView.getView(): " + noView.getView()
        assert !productViewSearch.isProductView()
        assert !noView.getView()
        assert !noView.setViewToList()
        assert !noView.getView()
        assert !noView.setViewToGrid()
        assert !noView.getView()
        assert !noView.getViewProductListing()
        log "NO ELement grid tooltip:" + productView.getGridTooltip()
        log "NO ELement list tooltip:" + productView.getListTooltip()
        assert  productView.getGridTooltip()==null
        assert productView.getListTooltip()==null
        assert productView.getViewProductListing()==''


    }
}

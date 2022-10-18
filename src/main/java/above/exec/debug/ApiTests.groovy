package above.exec.debug

import above.RunWeb
import all.RestApi
import wss.api.catalog.product.Products

class ApiTests extends RunWeb {

    static void main(String[] args) {
        new ApiTests().testExecute()
    }

    void test() {

        setup([ author: 'akudin', title: 'Debug', PBI: 1, product: 'wss',
                project: 'Webstaurant.StoreFront', keywords: 'debug', logLevel: 'info' ])

        RestApi api = new RestApi()
        api.apiCall(
                'https://internalassets-api.dev.webstaurantstore.com/v3/directory?path=%2Fqa-technical-storage%2Fscreenshots%2F',
                'GET'
        )

        log 'Status code: ' + api.response.getStatusCode()
        log 'Result size: ' + api.apiResult?.size()
        log '--'


//        api.apiCall(
//                'https://qatools.dev.clarkinc.biz/ServerRunsApiQueue?type=manual',
//                'GET'
//        )
//
//        log 'Status code: ' + api.response.getStatusCode()
//        log 'Result size: ' + api.apiResult?.size()
//        log '--'

        def itemNumbers = ['349C22S3SEQ']
        def productAPI = new Products(itemNumbers, [allowGroupingProducts: true, ignoreVisibilityFilters: true, showHidden: true])
        def productData0 = productAPI.getValues('', itemNumbers[0])
        log 'Result size: ' + productData0?.size()

    }

}

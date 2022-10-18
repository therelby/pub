package framework.runweb.urltool

import above.RunWeb

class UtUrlTool extends RunWeb {

    static void main(String[] args) {
        new UtUrlTool().testExecute([

                browser      : 'chrome',// 'remotechrome-lt',//'chrome',//'edge',//'chrome',//'safari'
                remoteBrowser: true,//true,//false,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                //   parallelThreads: 1,
                //  runType: 'Regular' ,
                //  browserVersionOffset: -1
        ])
    }

    def test() {

        setup('vdiachuk', 'FuncUrlTool unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test url tool urltool ' +
                        ' handle ',
                 "tfsTcIds:265471", 'logLevel:info'])
        String url = 'https://www.dev.webstaurantstore.com/search/table.html?category=3681&order=rating_desc&filter=webstaurantplus:eligible&vendor=Choice,Solo&multi=true'
        //getParamValueFromUrl
        assert '3681' == getParamValueFromUrl(url, 'category')
        assert 'rating_desc' == getParamValueFromUrl(url, 'order')
        assert 'webstaurantplus:eligible' == getParamValueFromUrl(url, 'filter')
        assert 'Choice,Solo' == getParamValueFromUrl(url, 'vendor')
        assert 'true' == getParamValueFromUrl(url, 'multi')
        assert removeAllParamsFromUrl(url) == 'https://www.dev.webstaurantstore.com/search/table.html'
        assert removeAllParamsFromUrl('https://www.dev.webstaurantstore.com/search/table.html') == 'https://www.dev.webstaurantstore.com/search/table.html'
        assert removeAllParamsFromUrl('') == null
        assert removeAllParamsFromUrl(null) == null

        //getAllParamsFromUrl

        log "getAllParamsFromUrl url: " + getAllParamsFromUrl(url)

        String urlNoParameter = 'https://www.dev.webstaurantstore.com/search/table.html'

        //addparameters
        List parameters = ['rage=', 'order=rating_desc', 'filter=webstaurantplus:eligible', 'filter=webstaurantplus:eligible', 'vendor=Choice,Sol']
        String urlUpdated = addAllParamsToUrl(urlNoParameter, parameters)
        log "urlUpdated: $urlUpdated"
        assert parameters == getAllParamsFromUrl(urlUpdated)
        assert urlUpdated == 'https://www.dev.webstaurantstore.com/search/table' +
                '.html?rage=&order=rating_desc&filter=webstaurantplus:eligible&filter=webstaurantplus:eligible&vendor=Choice,Sol'

        parameters = getAllParamsFromUrl(urlNoParameter)
        log "getAllParamsFromUrl(urlNoParameter): " + parameters
        log "addParamsToUrl(urlNoParameter, parameters): " + addAllParamsToUrl(urlNoParameter, parameters)
        log "=="
        //    setLogLevel('debug')
        log "add parameter to url with empty parameter:" + addParamToUrl('https://www.dev.webstaurantstore' +
                '.com/search/table' +
                '.html?vendor=&multi=true&order=rating_asc', "order=price_desc")
        assert 'https://www.dev.webstaurantstore.com/search/table.html?vendor=&multi=true&order=price_desc' == addParamToUrl('https://www.dev.webstaurantstore.com/search/table.html?vendor=&multi=true', "order=price_desc")
        log "get all parameters from url with no parameters: " + getAllParamsFromUrl("https://www.dev.webstaurantstore.com/search/table.html")
        assert getAllParamsFromUrl("https://www.dev.webstaurantstore.com/search/table.html") == []
        log "Add parameter to url that does not have this parameter: " + addParamToUrl("https://dev" +
                ".webstaurantstore.com/43905/dishwasher-parts-and-dishwashing-components.html?vendor=Adamation,Regency-Space-Solutions&multi=true", "order=price_asc")
        assert addParamToUrl("https://dev.webstaurantstore" +
                ".com/43905/dishwasher-parts-and-dishwashing-components.html?vendor=Adamation," +
                "Regency-Space-Solutions&multi=true", "order=price_asc") == 'https://dev.webstaurantstore.com/43905/dishwasher-parts-and-dishwashing-components.html?vendor=Adamation,Regency-Space-Solutions&multi=true&order=price_asc'

        url = 'https://www.dev.webstaurantstore.com/search/table.html?category=3787&vendor=Choice,Tuxton&multi=true&order=rating_asc'
        log "remove parameter vendor: " + removeParamFromUrl(url, 'vendor')
        assert 'https://www.dev.webstaurantstore.com/search/table.html?category=3787&multi=true&order=rating_asc' ==
                removeParamFromUrl(url, 'vendor')
        log "remove parameter that not exists: " + removeParamFromUrl(url, 'fake')

        assert url == removeParamFromUrl(url, 'fake')
        assert 'https://www.dev.webstaurantstore.com/search/table.html' ==
                removeParamFromUrl('https://www.dev.webstaurantstore.com/search/table.html?category=3787', 'category')

        log 'remove parameter from url with only one parameter: ' + removeParamFromUrl('https://www.dev' +
                '.webstaurantstore.com/search/table.html?category=3787', "category")

        assert 'https://www.dev.webstaurantstore.com/search/table.html' == removeParamFromUrl('https://www.dev' +
                '.webstaurantstore.com/search/table.html?category=3787', "category")

        String urlWithFilter = "https://www.dev.webstaurantstore.com/search/table" +
                ".html?order=&price_asc&category=3787&filter=bottom-diameter:1~2*-inches:3~4*-inches&multi=true" +
                "&vendor=Pasabahce,Tuxton&filter=webstaurantplus:eligible"
        String urlFilterRemoved = removeParamFromUrl(urlWithFilter, 'filter')
        log "urlFilterRemoved: " + urlFilterRemoved
        assert getParamValueFromUrl(urlFilterRemoved, 'filter') == ''

        log "===== changeParamValueToUrl"
        String urlWithFilters = "https://www.dev.webstaurantstore.com/search/table.html?order=price_asc&category=3787&filter=bottom-diameter:1~2*-inches:3~4*-inches&multi=true&vendor=Pasabahce,Tuxton&filter=webstaurantplus:eligible"


        log "changeParamValueToUrl(urlWithFilters, 'category', '999'): " + changeParamValueToUrl(urlWithFilters,
                'category', '999')
        assert changeParamValueToUrl(urlWithFilters, 'category', '999') == "https://www.dev.webstaurantstore" +
                ".com/search/table.html?order=price_asc&category=999&filter=bottom-diameter:1~2*-inches:3~4*-inches&multi=true&vendor=Pasabahce,Tuxton&filter=webstaurantplus:eligible"
        assert getParamValueFromUrl(changeParamValueToUrl(urlWithFilters, 'category', '999'), 'category') == '999'
        log "changeParamValueToUrl(urlWithFilters, 'filter', 'firstFilterValue'): " + changeParamValueToUrl(urlWithFilters, 'filter', 'firstFilterValue')
        assert 'firstFilterValue' == getParamValueFromUrl(changeParamValueToUrl(urlWithFilters, 'filter', 'firstFilterValue'), 'filter')
        assert changeParamValueToUrl(urlWithFilters, 'filter', 'firstFilterValue') == 'https://www.dev.webstaurantstore.com/search/table.html?order=price_asc&category=3787&filter=firstFilterValue&multi=true&vendor=Pasabahce,Tuxton&filter=webstaurantplus:eligible'

        String urlNoParameters = "https://www.dev.webstaurantstore.com/search/table.html"
        assert changeOrAddParamToUrl(urlNoParameters, "order", "popular") == 'https://www.dev.webstaurantstore' +
                '.com/search/table.html?order=popular'
        assert changeOrAddParamToUrl(urlWithFilters, "order", "popular") == 'https://www.dev.webstaurantstore' +
                '.com/search/table.html?order=popular&category=3787&filter=bottom-diameter:1~2*-inches:3~4*-inches&multi=true&vendor=Pasabahce,Tuxton&filter=webstaurantplus:eligible'

        log "getParamValueFromUrlParam('filter=power-type:liquid-propane'): " + getParamValueFromUrlParam('filter=power-type:liquid-propane')
        log "getParamNameFromUrlParam('filter=power-type:liquid-propane'): " + getParamNameFromUrlParam('filter=power-type:liquid-propane')
        assert getParamValueFromUrlParam('filter=power-type:liquid-propane') == 'power-type:liquid-propane'
        assert getParamNameFromUrlParam('filter=power-type:liquid-propane') == 'filter'

        def urlWithManySameParams = "https://www.dev.webstaurantstore.com/myaccount?prevPage=%2Fmyaccount%2Faccount%2F#profileLink&login_as_user=9827067&login_as_user=9827067&login_as_user=9827067"
        List params = getAllParamsFromUrl(urlWithManySameParams)
        assert params.contains("prevPage=%2Fmyaccount%2Faccount%2F#profileLink")
        assert params.contains("login_as_user=9827067")
    }


}

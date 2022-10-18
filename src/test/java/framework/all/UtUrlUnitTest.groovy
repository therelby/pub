package framework.all

import above.RunWeb

class UtUrlUnitTest extends RunWeb {


    // Test
    def test() {

        setup('vdiachuk', 'all.Url Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test url all',
                 "tfsTcIds:265471", 'logLevel:info'])


        RunWeb r = run()


        String url = getUrl("homepage")
        String urlWithParameter = 'https://www.dev.webstaurantstore.com/?login_as_user=5180037'
        String parameterName = 'login_as_user'
        String parameterValue = '5180037'
        String parameter = 'login_as_user=5180037'
        log("HomePage:$url, Parameter:$parameter, UrlWithParameter:$urlWithParameter")

        assert addParamToUrl(url, parameter) == urlWithParameter
        assert getParamValueFromUrl(urlWithParameter, parameterName) == parameterValue


    }
}

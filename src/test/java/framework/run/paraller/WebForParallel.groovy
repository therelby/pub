package framework.run.paraller

import above.RunWeb
import wss.search.SearchForm

class WebForParallel extends RunWeb {

    def tcIds = [265465]

    // Test
    def test() {

        setup('akudin', 'Web For Parallel',
              ['product:wss|dev,test,prod', 'tfsProject:Webstaurant.StoreFront',
               'keywords:unit test', "tfsTcIds:${tcIds.join(',')}", 'logLevel:info'])

        log 'Testing...'

        List<String> urls = [
            getUrl(),
            getUrl('/viewcart.cfm'),
            getUrl('/trackorder.html'),
            getUrl('/aboutus.html'),
            getUrl('/policies.html'),
            getUrl('/ask.html')
        ]

        urls.each {
            tryLoad(it)
            assert getCurrentUrl() == it
            SearchForm.search('table')
            assert getPageSource().contains('table')
        }
    }

}

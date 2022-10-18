package framework.mobile

import above.RunWeb
import above.azure.AzureDevOpsTestcase
import all.DbTools
import wss.search.SearchForm

/**
 *    Mobile Browsers Debug
 */
class MobileDebug extends RunWeb {

    def tcIds = [265465]

    def test() {

        setup('akudin', 'Mobile Browsers Debugger',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront',
               'keywords:health', "tfsTcIds:${tcIds.join(',')}"])

        tryLoad()
        new SearchForm().search('table')
        assert getPageSource().contains('table')

    }

}

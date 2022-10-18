package framework.run.webissues

import above.RunWeb

class PageHandleIssues extends RunWeb {

    def tcIds = [265465]

    // Test
    def test() {

        setup('akudin', 'Page Handle Issues',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront',
               'keywords:unit test', "tfsTcIds:${tcIds.join(',')}", 'logLevel:info'])

        log 'Testing...'

        tryLoad()

        setText('//input[@id=\'searchval\']', 'table')
        click('//ssss/s/ssss') // issue
        click('//button[@value=\'Search\']')

        check(true, tcIds, 'Error!')

        def issues = issueTrackerList
        assert issues.size() > 0

    }

}

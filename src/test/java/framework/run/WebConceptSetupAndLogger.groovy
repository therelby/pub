package framework.run

import above.RunWeb

class WebConceptSetupAndLogger extends RunWeb {

    def tcId = 265465 // debugging testcase

    def test() {

        setup('akudin', 'Web Concept Unit Test | Minimal Setup and Logger',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test',
              "tfsTcIds:$tcId"])

        assert testProduct == 'wss'
        assert testTitle == 'Web Concept Unit Test | Minimal Setup and Logger'
        assert testClass == 'framework.run.WebConceptSetupAndLogger'
        assert testTfsProject == 'Webstaurant.StoreFront'
        assert testKeywords == 'unit test'
        assert testCases*.tcId.contains(tcId)

        log 'Testing...'
        assert runLog.contains('Testing...')

        logDebug 'Should Not Appear In Log'
        assert !runLog.contains('Should Not Appear In Log')

    }

}

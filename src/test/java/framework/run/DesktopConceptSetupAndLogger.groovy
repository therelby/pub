package framework.run

import above.RunDesktop

class DesktopConceptSetupAndLogger extends RunDesktop {

    def tcId = 265465 // debugging testcase

    def test() {

        setup('akudin', 'Desktop Concept Unit Test | Minimal Setup and Logger',
                ['product:wss-sps', 'tfsProject:SPS .NET Rewrite', 'keywords:unit test',
                 "tfsTcIds:$tcId"])

        assert testProduct == 'wss-sps'
        assert testTitle == 'Desktop Concept Unit Test | Minimal Setup and Logger'
        assert testClass == 'framework.run.DesktopConceptSetupAndLogger'
        assert testTfsProject == 'SPS%20.NET%20Rewrite'
        assert testKeywords == 'unit test'
        assert testCases*.tcId.contains(tcId)

        log 'Testing...'
        assert runLog.contains('Testing...')

        logDebug 'Should Not Appear In Log'
        assert !runLog.contains('Should Not Appear In Log')
    }

}

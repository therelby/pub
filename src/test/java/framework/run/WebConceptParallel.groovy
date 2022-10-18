package framework.run

import above.RunWeb
import above.azure.AzureDevOpsTestcase

class WebConceptParallel extends RunWeb {

    def tcId = 265465 // debugging testcase
    def tcss = [270071, 296881]

    def test() {

        setup('akudin', 'Web Concept Unit Test | Reports and Checks for Parallel', [
                'product:wss|dev,test,preflight,prod', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test',
                "tfsTcIds:$tcId,${tcss.join(',')}",
        ])

        log 'Forcing all testcases to ready'
        testCases.each { AzureDevOpsTestcase.updateStatus(it.tcId, 'Passed') }

        isTcReady(tcId) // ignoring
        tcStatusCache.each { assert it.status == 'Passed' }

        // report & check
        report(tcId, 'UT Report description 1')
        report(tcId, 'UT Report description 2', false)
        check(tcId, 'No error', true)
        check(tcId, 'Oh, no - error!', false)

        isTcReady(tcss) // ignoring for list of ids

        // report & check - 2
        report(tcss, 'ListOfIds UT Report description 1')
        report(tcss, 'ListOfIds UT Report description 2', false)
        check(tcss, 'ListOfIds No error', true)
        check(tcss, 'ListOfIds Oh, no - error!', false)

        tryLoad('homepage')
        assert getCurrentUrl() == getUrl('homepage')

        //closeBrowser()
    }

}

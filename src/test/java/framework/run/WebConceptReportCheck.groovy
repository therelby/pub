package framework.run

import above.RunWeb
import all.DbTools

class WebConceptReportCheck extends RunWeb {

    def tcId = 265465 // debugging testcase
    def tcss = [270071, 296881]

    def test() {

        setup('akudin', 'Web Concept Unit Test | Full Setup', [
                'product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test',
                "tfsTcIds:$tcId,${tcss.join(',')}",
                'note:Some notes',
                'logLevel:debug',
                'showTestResult',
        ])

        // setup
        assert runLog.contains('framework.run.WebConceptReportCheck.tes  logLevel = debug')
        assert testNotes.contains('Some notes')

        isTcReady(tcId) // ignoring

        // report & check
        report(tcId, 'UT Report description 1')
        report(tcId, 'UT Report description 2', false)
        check(tcId, 'No error', true)
        check(tcId, 'Oh, no - error!', false)

        def info = getInfo()
        assert info.testcases.tests.reports.msg*.contains('UT Report description 1')
        assert info.testcases.tests.reports.msg*.contains('UT Report description 2')
        assert info.testcases.tests.checks.msg*.contains('No error')
        assert info.testcases.tests.checks.msg*.contains('Oh, no - error!')

        // stats
        if (!runDebug) {
            def stat = DbTools.selectAll("SELECT * FROM [Stat_Test_Runs] WHERE run_id='$runId'", 'qa')
            assert stat.size() == 1
        }

        isTcReady(tcss) // ignoring for list of ids

        // report & check - 2
        report(tcss, 'ListOfIds UT Report description 1')
        report(tcss, 'ListOfIds UT Report description 2', false)
        check(tcss, 'ListOfIds No error', true)
        check(tcss, 'ListOfIds Oh, no - error!', false)

    }

}

package above.allrun.helpers

import above.Run

/**
 *      Regular Run Statistics - Final Update
 */

class RunStatsEnd {

    final Run r = run()

    private String qUpdateRun = """
        UPDATE Stat_Test_Runs 
        SET 
            test_info='%testInfo', 
            date_done=GETDATE(), 
            test_error=%testError, 
            test_log='%test_log',
            environment='%env', 
            browser='%browser', 
            browser_version='%version', 
            parallel = %parallel, 
            concept = '%concept',
            test_tfs_project = '%tfsProject',
            checks = %checks,
            passed = %passed,
            failed = %failed,
            reports = %reports,
            tfs_docs = %tfsDocs
        WHERE id=%id;"""

    private String qUpdateRunTime = "UPDATE Stat_Test_Runs SET exec_time_sec = Datediff(s, date_start, date_done) WHERE id=%id;"


    void finalStats() {

        if (!r.runRecordId) { return }

        Map info = r.getInfo()

        if (!r.isRunDebug()) {
            r.log 'STATISTICS: Final -- Saving screenshots...'
            new StorageScreenshots().saveAllScreenshots()
        }

        r.log 'STATISTICS: Final -- Updating run record in QA database...'
        String env = ''
        String bro = ''
        String ver = ''
        if (r.xTestConcept() == 'web') {
            env = r.webProject.testEnv
            bro = r.testBrowser.replaceFirst('remote', '')
            ver = r.testBrowserVersion
        }

        // checks and reports values for old and verify() approach
        int checks
        int passed
        int failed
        int reports
        if (!r.runVerifyBased) {
            checks = r.testCases*.checks.flatten().size()
            passed = r.testCases*.checks*.checkResult.flatten().findAll{it==true}.size()
            failed = r.testCases*.checks*.checkResult.flatten().findAll{it==false||it==null}.size()
            reports = r.testCases*.reports.flatten().size()
        } else {
            checks = r.runVerifyStats.failed + r.runVerifyStats.passed
            passed = r.runVerifyStats.passed
            failed = r.runVerifyStats.failed
            reports = r.runVerifyStats.reports
        }

        // updating current run record with final values
        r.dbInfoLoggingHide()
        r.dbExecute(qUpdateRun
                .replace('%testInfo', info.toPrettyString().replace("'", "''"))
                .replace('%testError', r.runFailed.toString().replace('true', '1').replace('false', '0'))
                .replace('%test_log', r.runLog.toPrettyString().replace("'", "''"))
                .replace('%env', env)
                .replace('%browser', bro)
                .replace('%version', ver)
                .replace('%parallel', r.runSuite.parallelThreads.toString())
                .replace('%concept', r.xTestConcept())
                .replace('%tfsProject', r.testTfsProject.toString().replace('null', 'none'))
                .replace('%checks', checks.toString())
                .replace('%passed', passed.toString())
                .replace('%failed', failed.toString())
                .replace('%reports', reports.toString())
                .replace('%tfsDocs', r.testCases.size().toString())
                .replace('%id', r.runRecordId.toString()),
                'qa'
        )
        r.dbExecute(qUpdateRunTime.replace('%id', r.runRecordId.toString()), 'qa')
        r.dbInfoLoggingNormal()

        r.log('==', r.console_color_yellow)
    }

}

package above.allrun

/**
 *      Framework Health Checker
 */

abstract class ZcRunHealth extends ZzRunVariables {

    private long lastHealthCheck = getRealTimeStamp()
    private long lastStopCheck = getRealTimeStamp()

    void xCheckRunHealth() {

        // setup() already called?
        if (!testAuthor) return

        // got stop request?
        if (isRunStopped())
            xStop(getStopDescription())

        long curTime = getRealTimeStamp()

        // external stop request -- on server only
        if (isServerRun() && curTime - lastStopCheck >= 30000) {
            String saveLogLevel = getLogLevel()
            setLogLevel('none', true)
            def stop = dbSelect("SELECT * FROM ServerRuns_Stop WHERE run_row_id = $runRecordId OR run_id = '$runId'", 'qa')
            if (stop) {
                dbExecute("DELETE FROM ServerRuns_Stop WHERE run_row_id = $runRecordId OR run_id = '$runId' OR dateadd(minute, 5, date_add) >= getdate()", 'qa')
                setLogLevel(saveLogLevel, true)
                xStop(stop[0].description)
            } else {
                setLogLevel(saveLogLevel, true)
            }
            lastStopCheck = curTime
        }

        // time blocker to avoid to often checking
        if ((curTime - lastHealthCheck) < 60000)
            return
        else
            System.gc()
        lastHealthCheck = curTime

        // too long test execution
        if (isServerRun() && xTestConcept() != 'base' && runStartTime && ((curTime - runStartTime) > 7200000)) { // 2 hours
            xTestStopRequest("RunHealth: the test got stopped because it is running more than 2 hours")
            log '--'
        }

        // web only
        if (xTestConcept() == 'web') {

            // WebDriver usage time
            if (isServerRun() && testBrowserDriverCreated && curTime - testBrowserDriverCreated >= 1800000) { // 30 minutes
                xTestStopRequest("RunHealth: the test has stopped because " +
                        "WebDriver for $testBrowser was created more than 30 minutes ago.\n" +
                        "Please use closeBrowser() after each browser use session to avoid memory and Grid issues.\n" +
                        "The doc https://tfs.clarkinc.biz/DefaultCollection/Automation%20Projects/_wiki/wikis/Automation-Projects.wiki/2580/01-Simple-Web-Test")
                log '--'
            }

        }

    }


    // Unit Test Intended
    void xSetLastHealthCheck(long value) {
        lastHealthCheck = value
    }
    void xSetLastStopCheck(long value) {
        lastStopCheck = value
    }

}

package above.smokecloud

import above.azure.AzureDevOpsApi
import all.Db
import all.DbTools

/**
 *      Smoke Cloud Run Object
 */

abstract class RunSmoke extends StepsController {

    Integer cbCount = 0

    final String smokeCloudVersion = '0.5.beta'
    String smokeStepRunId = null
    def currentRootScenario = null
    boolean smokeCloudStopTesting = false

    Map currentTaskScenario = null
    Integer smokeStepNumber = null
    Integer currentTaskNumber = 0


    String smokeCallBackHost = 'https://qatools.dev.clarkinc.biz/SmokeCloudCallback'
    Long smokeLastCallBackTimestamp = new Date().getTime()

    Integer smokePageLoadIssues = 0
    Integer smokePageLoadIssuesMax = 3


    /** Test status update */
    void smokeCloudTaskStatusUpdate(Integer taskId, Integer status) {
        // Status codes:
        // 0 - just created/waiting for claim/lost
        // 1 - claimed/running/lost
        // 2 - Java error
        // 3 - canceled by user
        // 4 - blocked by a side issue
        // 100 - well done
        //new Db('qa').updateQuery("UPDATE SmokeCloud_Tasks SET task_status = $status WHERE id = $taskId")
    }


    /** Smoke Test Session Starter */
    void smokeExecute() {

        return

        // getting smokeTestId and setting smoke test up
        if (!isRunDebug()) {
            testSmokeCloudTestId = runSuite?.smokeTestId
        } else {
            testSmokeCloudTestId = DebugData.testScenario.id
        }
        if (!testSmokeCloudTestId) {
            log "Wrong testSmokeCloudTestId = $testSmokeCloudTestId"
            return
        }
        //testSmokeCloud = true
        if (!isRunDebug()) {
            testRealtimeResults = true
        }

        log "Smoke Cloud version ${smokeCloudVersion}"
        log "https://qatools.dev.clarkinc.biz/SmokeCloudTest?id=$testSmokeCloudTestId"
        log('==', console_color_yellow)

        // claiming and running scenarios
        Map scenario = claimTask()
        while (scenario) {

            currentRootScenario = scenario

            log '--'
            log 'Test Root Scenario:'
            log scenario

            // running current task scenario
            if (runScenario(scenario)) {
                // NORMAL task scenario done
                log("TEST SCENARIO '${scenario.name}' DONE", console_color_green)
                smokeCloudTaskStatusUpdate(scenario.taskId, 100)
            } else {
                // CANCELED task scenario done
                log("TEST SCENARIO '${scenario.name}' STOPPED", console_color_yellow)
                smokeCloudTaskStatusUpdate(scenario.taskId, 3)
                break
            }

            if (isLocalRun()) {
                break // prevent deadlock for our debug scenario
            }

            // try to get next task scenario
            scenario = claimTask()
        }
    }


    /** Scenario Running -- RECURSION */
    Boolean runScenario(Map scenario) {

        return

        if (!smokeCloudCallBack()) {
            return false
        }

        currentTaskScenario = scenario

        if (!scenario) { throw new Exception("Empty scenario") }

        log '--'
        log("Current Scenario: ${scenario.id} / ${scenario.product} / ${scenario.name}", console_color_green)

        // executing steps
        smokeStepNumber = 0
        for (step in scenario.steps) {
            smokeStepNumber++
            if (step instanceof Integer) {

                // recursion run another scenario
                def sc = getScenario(step, scenario.taskId, scenario.level)
                if (!sc) {
                    log("(!) Cannot get scenario: $step", console_color_yellow)
                    return false
                }
                if (!sc.url) {
                    sc.url = scenario.url
                }
                if (!runScenario(sc)) {
                    // stop testing
                    log('(!) Test stopped', console_color_yellow)
                    return false
                }

            } else if (step instanceof String) {

                // execution final functionality
                log("\n-- ${scenario.product} / ${scenario.name}", console_color_green)
                log("-- step: $step", console_color_green)
                closeBrowser()
                if (!checkUrl(scenario.url, true)) {
                    log('(!) Smoke Cloud: STOPPING TEST because of a lot of page loading issues', console_color_yellow)
                    // stop testing
                    return false
                }
                stepExecute(scenario.product, scenario.url, step, [testLevel: scenario.level])
                if (!smokeCloudCallBack(true)) {
                    // stop testing
                    log('(!) Test canceled', console_color_yellow)
                    return false
                }

            } else {
                throw new Exception("Step type is ${step.getClass()} but must be String or Integer")
            }
        }

        if (smokeCloudStopTesting) {
            return false
        }
        return true
    }


    /** Check URL */
    Boolean checkUrl(String url, Boolean frameworkCall = false) {

        return

        // getting dynamic URLs
        if (!url.startsWith('http')) {
            url = getUrl(url)
        }

        // adjusting driver
        closeAllWindowsButOriginal()
        setImplicitTimeoutToDefault()

        // loading page when needed
        Boolean result
        if (!goOrStay(url)) {
            while(smokePageLoadIssues < smokePageLoadIssuesMax) {
                smokePageLoadIssues++
                sleep(3000)
                if (tryLoad(url)) {
                    smokePageLoadIssues = 0
                    result = true
                    break
                }
                closeBrowser()
            }
            lastIssue = "(!) FATAL: Smoke Cloud page loading handler cannot load $url"
            if (!frameworkCall) {
                report("Page loading issue: $lastIssue")
            }
            result = false
            smokeCloudStopTesting = true
        } else {
            result = true
        }

        return result
    }


    /** Claim task */
    private Map claimTask() {

        return

        if (isLocalRun()) {

            // debug scenario
            log('DEBUG RUNNING: returning debug scenario', console_color_green)
            return DebugData.testScenario

        } else {

            // claiming a real task for current test
            String claimId = UUID.randomUUID().toString()
            Integer cnt = 1
            while(cnt < 6) {
                cnt++
                try {
                    log("Looking ($cnt) for a free task for test id: $testSmokeCloudTestId", console_color_green)
                    new Db('qa').updateQuery("""
                        UPDATE SmokeCloud_Tasks 
                        SET SmokeCloud_Tasks.claimed = '$claimId', task_status = 1 
                        FROM (
                            SELECT TOP 1 id 
                            FROM SmokeCloud_Tasks
                            WHERE SmokeCloud_Tasks.task_status = 0 AND SmokeCloud_Tasks.claimed = '' AND SmokeCloud_Tasks.smoke_test_id = $testSmokeCloudTestId) b
                        WHERE 
                            SmokeCloud_Tasks.id = b.id;""")
                    break
                } catch(Ignored) {
                    sleep(1000)
                }
            }

            def claimed = DbTools.selectAll("SELECT id, scenario_id, test_level FROM SmokeCloud_Tasks WHERE claimed = '$claimId'", "qa")
            // no tasks left for current test
            if (!claimed) {
                log("NO FREE TASKS FOUND FOR TEST ID: $testSmokeCloudTestId", console_color_green)
                return null
            }
            // return scenario by claimed id
            log("CLAIMED task id: ${claimed[0].scenario_id}", console_color_green)
            return getScenario(claimed[0].scenario_id, claimed[0].id, claimed[0].test_level)
        }
    }


    /** Get scenario by id */
    private Map getScenario(Integer id, Integer taskId, Integer testLevel) {

        return

        Map result = [
                steps: []
        ]

        String query = """
            SELECT sc.product, sc.name, sc.url, sc.test_level, sn.name AS scenario_name, st.call_scenario_id FROM SmokeCloud_Scenarios sc
              INNER JOIN SmokeCloud_Steps st
                ON sc.id = st.scenario_id
              LEFT JOIN SmokeCloud_StepNames sn
                ON st.test_name_id = sn.id
            WHERE sc.id = ${ -> id }
            ORDER BY st.sort
        """
        List data
        try {
            data = DbTools.selectAll(query, 'qa')
        } catch(err) {
            log(err.getMessage(), console_color_red)
            data = []
        }

        if (data) {
            result.id = id
            result.taskId = taskId
            result.product = data[0].product
            result.name = data[0].name
            result.url = getSmokeUrl(data[0].url)
            result.level = testLevel
            data.each {
                if (it.scenario_name) {
                    result.steps << it.scenario_name
                } else {
                    result.steps << it.call_scenario_id
                }
            }
        }

        return result
    }


    /**
     * Smoke Cloud to Web Tool API Call Back
     * (!) for framework check() and report() calls only
     * (!) do not call this method from any test classes
     */
    Boolean smokeCloudCallBack(boolean scenarioDone = false) {

        return

        // debug running
        if (runDebug) { // || !testSmokeCloudTestId) {
            return true
        }

        // check time
        long newTimestamp = new Date().getTime()
        if (!scenarioDone && newTimestamp - smokeLastCallBackTimestamp < 3000) {
            return true
        }

        log('-- doing Smoke Cloud to Web Tool callback', console_color_green)
        Boolean result = true

        // API call
        def api = new all.RestApi(smokeCallBackHost + "?testId=$testSmokeCloudTestId", 'GET', '', 'Content-Type: application/json')
        def res = null
        int tryTimes = 1
        while (tryTimes > 0) {
            tryTimes--
            if (api.response.getStatusCode() == 200) {
                res = api.apiResult
                log 'Got:'
                log res
                break
            } else {
                log("(!) Callback API ERROR:", console_color_red)
                log("-- status code: ${api.response.getStatusCode()}", console_color_yellow)
                log(api.response.getResponseBodyContent(), console_color_yellow)
            }
            log("-- (!) callback failed. Attempts left: $tryTimes", console_color_yellow)
            if (tryTimes) {
                sleep(5000)
            }
        }
        if (!res) {
            log("-- FAILED callback for test id: $testSmokeCloudTestId", console_color_red)
            smokeLastCallBackTimestamp = new Date().getTime()
            return true
        }

        cbCount++;
        smokeLastCallBackTimestamp = new Date().getTime()
        smokeCloudStopTesting = !res.continueTesting
        return res.continueTesting
    }


    /** Step Statistics Init */
    void smokeCloudStatistics1(Object test, String step, String url) {

        return

        // test class author checking
        if (!AzureDevOpsApi.accessTokens.keySet().contains(test.author)) {
            throw new Exception("Wrong test author [${test.author}] this name must be present in above.azure.AzureDevOps.accessTokens")
        }

        // current test statistics saving
        if (!runDebug) {
            log('\nSMOKE CLOUD STATISTICS init for current step...', console_color_green)
            smokeStepRunId = UUID.randomUUID().toString()
//            startStats(
//                    smokeStepRunId,
//                    test.author,
//                    "${test.class.name}($url)",
//                    test.title + " - $step",
//                    'smoke cloud'
//            )
        }
    }

    /** Step Statistics Final */
    void smokeCloudStatistics2() {

        return

        // current test statistics saving
        if (!runDebug) {
            log('\nSMOKE CLOUD STATISTICS saving for current step done...', console_color_green)
            testSmokeCloudTaskId = currentTaskScenario.id
            finalStats(smokeStepRunId)
            testSmokeCloudTaskId = 0
            log("Step results https://qatools.dev.clarkinc.biz/run?id=$smokeStepRunId&filter=test", console_color_green)
            log ''
        }

        // (!) cleaning run data
        testCases[0].checks = []
        testCases[0].reports = []
        issueTrackerList = []
        cleanLog()
    }

}

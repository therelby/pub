package above.allrun

/**
 *      Blocking Issue Reporting Stuff
 */

abstract class AaRunIssueReport extends AaVerify {

    /**
     * BPIs based approach
     */
    void report(String issueDescription) {

        if (!runPbiBased) throw new Exception('Wrong report() parameters for TFS testcases based approach')
        if (!issueDescription) throw new Exception('report() issueDescription parameter must be provided')

        xCheckIssueDescription(issueDescription)
        xCheckSetup()

        // adding current url and screenshot to issueDescription
        String url = null
        String screenshot = null
        if (!runDebug && xTestConcept() == 'web' && driverStorage.get() && !isAlertPresentRunStuffOnly()) {
            url = getCurrentUrl()
            //screenshot = takeScreenshot()
        }

        // adding report
        String uuid = addToTcList(testCases[0].tcId, 'reports', [
                msg:        issueDescription,
                url:        url,
                screenshot: screenshot,
                issueList:  getIssueTrackerList()
        ])

        // save lastIssue
        lastIssue = issueDescription

        log('--')
        log("REPORT for PBI: ${testCases[0].tcId}, UUID: $uuid", console_color_yellow)
        log("REPORT: $issueDescription", console_color_yellow)
        log('--')

        resetIssueTracker()
    }


    /**
     * Reporting any blocking issue that doesn't allow to continue testing
     *      -- like 'no items for the test' or 'Sorry about that' error showed
     * Involved testcases will be marked 'Blocked' by the .finalActions() when updateTfs = true
     *
     * @param updateTfsTestcase - set to false to avoid turning the tcIds TFS testcase(s) to 'Blocked'
     *
     * @param forceIsReady - means calls without previously isTcReady called
     *                (!) intended only for big number of testcases that found blocked by a global issue
     *                (!) please never use this param with true for regular testing
     *
     */
    def report(tcIds, String issueDescription, Boolean updateTfsTestcase = false, Boolean forceIsReady = false) {

        if (runPbiBased) throw new Exception('Wrong report() parameters for PBIs based approach')
        if (!issueDescription) throw new Exception('report() issueDescription parameter must be provided')

        xCheckIssueDescription(issueDescription)
        xCheckSetup()

        // List or single?
        List<Integer> tcs = []
        if (tcIds instanceof List<Integer>) {
            tcs = tcIds
        } else if (tcIds instanceof Integer) {
            tcs << tcIds
        } else if (autoTcReady && forceIsReady) {
            tcs << testCases[0].tcId
        } else {
            throw new Exception("report(): tcIds must be List<Integer> or Integer but ${tcIds.getClass()} provided")
        }

        // adding current url and screenshot to issueDescription
        String url = null
        if (!runDebug && xTestConcept() == 'web' && driverStorage.get() && !isAlertPresentRunStuffOnly()) {
            url = getCurrentUrl()
        }

        // do for each testcase id
        for (tc in tcs) {

            // checking tc state
            if (!forceIsReady && !autoIsTcReady(tc)) {
                continue
            }

            // adding report
            addToTcList(tc, 'reports', [
                    msg: issueDescription,
                    url: url,
                    screenshot: null,
                    issueList: getIssueTrackerList(),
                    tcUpdateRequired: updateTfsTestcase,
                    tcUpdated: false
            ])
        }

        // save lastIssue
        lastIssue = issueDescription
        issueDescription = issueDescription.replace('\n', ' ').replace('  ', ' ')

        log('--')
        log("REPORT tcIds: $tcs", console_color_yellow)
        log("REPORT: $issueDescription", console_color_yellow)
        log('--')

        resetIssueTracker()
    }


    /**
     * Report in Check style
     * @param conditionResult = false will call report
     */
    boolean report(Boolean conditionResult, tcIds, String issueDescription, Boolean updateTfsTestcase = false, Boolean forceIsReady = false) {
        if (!conditionResult) {
            report(tcIds, issueDescription, updateTfsTestcase, forceIsReady)
        }
        return conditionResult
    }


    /**
     * Report page loading issues like 'Sorry about that'
     * com.wss.tryGoTo -> false
     */
    void reportUrlIssue(tcIds) {
        report(tcIds, "Page load issue: $lastIssue", false)
    }


    /**
     * Check if issue description contains stop data
     */
    void xCheckIssueDescription(String issueDescription) {
        issueDescription = issueDescription.toLowerCase()
        if (issueDescription.contains('exception') && issueDescription.contains("java.")) {
            log('--', console_color_red)
            log('report: issueDescription should not contain any Java generated error texts, but got:', console_color_red)
            log(issueDescription, console_color_red)
            log('--', console_color_red)
            log('report: No wide try/catches allowed. Only focused try/catches allowed in the tests and the common stuff.', console_color_yellow)
            log('report: Each caught exception must be known and must be processed individually and in expected cases only.', console_color_yellow)
            log('report: Any report() calls must have a clear manually assembled description about what is blocking our testing.', console_color_yellow)
            log('--', console_color_red)
            throw new Exception("report() description should not contain any Java error texts.")
        }
    }

}

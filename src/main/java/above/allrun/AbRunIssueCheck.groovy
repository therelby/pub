package above.allrun

/**
 *      Test Check Points Handling
 */
abstract class AbRunIssueCheck extends AcRunIssueTestcase {

    /**
     * Stop test on max fails came
     */
    void handleMaxChecksFails() {

        // increasing fails counter
        testChecksFailed += 1

        // Regular runs only
        if (runDebug || testChecksAllowedPercent == 100) {  // TODO: Smoke Cloud handling
            return
        }

        // calculating max based on the percent
        int maxFailsAllowed = (testChecksTotal / 100) * testChecksAllowedPercent
        if (maxFailsAllowed < testChecksAllowedMin) {
            maxFailsAllowed = testChecksAllowedMin
        }

        // exit test?
        if (testChecksFailed > maxFailsAllowed) { // stopping test
            String msg = "Framework: Test stopped because of reached $maxFailsAllowed fails"
            log('==', console_color_red)
            log('FRAMEWORK TEST INTERRUPTION', console_color_red)
            log('==', console_color_red)
            log(msg, console_color_red)

            xStop(msg)
        }
    }


    /**
     * New style check() with Map details instead of the String description
     * -- the details are going to be converted to JSON string for the old check() compatibility
     */
    boolean check(Boolean testResult, Map details, String checkId = null) {
        return check(testResult, details.toPrettyString(), checkId)
    }


    /**
     * PBIs based approach
     * -- checkId must be unique for the test
     */
    Boolean check(Boolean testResult, String testCaseDescription, String checkId = null) {

        if (!runPbiBased) { throw new Exception('Wrong check() parameters for TFS testcases based approach') }

        if (!testCaseDescription) { throw new Exception('check() issueDescription parameter must be provided') }

        xCheckSetup()

        testChecksTotal += 1

        // adding current URL and screenshot to issueDescription -- (!) for fails only
        String url = null
        String screenshot = null
        if (!testResult && !runDebug && concept == 'web' && driverStorage.get() && !isAlertPresentRunStuffOnly()) {
            url = getCurrentUrl()
            screenshot = takeScreenshot()
        }

        // adding issue to the list
        String uuid = addToTcList(testCases[0].tcId, 'checks', [
                checkId: checkId,
                checkResult: testResult,
                msg: testCaseDescription,
                url: url,
                screenshot: screenshot,
                issueList: getIssueTrackerList()
        ])

        // save lastIssue
        lastIssue = testCaseDescription
        testCaseDescription = testCaseDescription.replace('\n', ' ').replace('  ', ' ')

        if (!testResult || !noPositiveChecksPrinting) {
            log('--')
            log("CHECK for PBI = ${testCases[0].tcId}${checkId ? ", checkId = $checkId" : ''}, UUID: $uuid", console_color_yellow)
            if (testResult) {
                log("CHECK PASSED (issue NOT happened: $testCaseDescription)", console_color_green)
            } else {
                log("CHECK FAILED: $testCaseDescription", console_color_red)
                handleMaxChecksFails()
            }
            log('--')
        }

        resetIssueTracker()

        return testResult
    }


    /**
     * Test case based (old) approach "smart assertion" (also Katalon project style check() replacement)
     *      -- collects issues with testcase ids and descriptions when testResult == false
     *      -- does nothing for testResult == true
     *      Example:
     *      check( expectedPrice == displayedPrice,
     *          tcId, "Displayed price is incorrect: [$displayedPrice] must be: [$expectedPrice]"
     *      )
     *
     * @testCaseDescription like 'Price from API == WSS displayed price'
     *
     * @return testResult value as is
     *      Return handling example:
     *      if (check( expectedPrice == displayedPrice,
     *          tcId, "Displayed price is incorrect: [$displayedPrice] must be: [$expectedPrice]"
     *      )) {
     *          // success actions (for true result)
     *      } else {
     *          // fail actions (for false result)
     *      }
     */
    Boolean testCase(Boolean testResult, String testCaseDescription, tcIds, Boolean oldCheck = false) {

        if (runPbiBased) { throw new Exception('Wrong check() parameters for PBIs based approach') }

        if (!testCaseDescription) { throw new Exception('check() issueDescription parameter must be provided') }

        xCheckSetup()

        // List or single?
        List<Integer> tcs = []
        if (tcIds instanceof List<Integer>) {
            tcs = tcIds
        } else if (tcIds instanceof Integer) {
            tcs << tcIds
        } else {
            throw new Exception("check(): tcIds must be List<Integer> or Integer but ${tcIds.getClass()} provided")
        }

        testChecksTotal += 1

        // adding current URL and screenshot to issueDescription
        String url = null
        String screenshot = null
        if (!testResult && !runDebug && concept == 'web' && driverStorage.get() && !isAlertPresentRunStuffOnly()) {
            url = getCurrentUrl()
            screenshot = takeScreenshot()
        }

        // do for each testcase id
        for (tc in tcs) {

            // checking testcase state
            if (!autoIsTcReady(tc)) {
                continue
            }

            // adding issue to the list
            addToTcList(tc, 'checks', [
                    checkResult: testResult,
                    msg: testCaseDescription,
                    url: url,
                    screenshot: screenshot,
                    issueList: getIssueTrackerList(),
                    tfsUpdated: false
            ])
        }

        // save lastIssue
        lastIssue = testCaseDescription
        testCaseDescription = testCaseDescription.replace('\n', ' ').replace('  ', ' ')

        log('--')
        log("CHECK for tcIds = $tcIds", console_color_yellow)
        if (oldCheck) {
            if (testResult) {
                log("CHECK PASSED (issue NOT happened: $testCaseDescription)", console_color_green)
            } else {
                log("CHECK FAILED: $testCaseDescription", console_color_red)
                handleMaxChecksFails()
            }
        } else {
            if (testResult) {
                log("TEST CASE PASSED: $testCaseDescription", console_color_green)
            } else {
                log("TEST CASE FAILED: $testCaseDescription", console_color_red)
                handleMaxChecksFails()
            }
        }
        log('--')

        resetIssueTracker()

        return testResult
    }


    /**
     * Check points processor "assertion replacement"
     *      -- Collects issues with testcase ids and descriptions when testResult == false
     *      -- does nothing for testResult == true
     *      Example:
     *      check( expectedPrice == displayedPrice,
     *          tcId, "Displayed price is incorrect: [$displayedPrice] must be: [$expectedPrice]"
     *      )
     * @return testResult value as is
     *      Return handling example:
     *      if (check( expectedPrice == displayedPrice,
     *          tcId, "Displayed price is incorrect: [$displayedPrice] must be: [$expectedPrice]"
     *      )) {
     *          // success actions (for true result)
     *      } else {
     *          // fail actions (for false result)
     *      }
     */
    Boolean check(Boolean testResult, tcIds, String issueDescription) {
        return testCase(testResult, issueDescription, tcIds, true)
    }

    /**
     * Katalon-style check() call variant
     */
    Boolean check(tcIds, String issueDescription, Boolean testResult) {
        return testCase(testResult, issueDescription, tcIds, true)
    }

}

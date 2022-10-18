package above.allrun

import above.types.IssueCategory

/**
 *      The Correct Run Stopping
 */

abstract class ZaRunStop extends ZbRunLogger {

    private boolean runStopped = false
    private boolean runStopAlreadyInitiated = false
    private String runStopDescription = ''

    /**
     *  Stops test execution correctly
     */
    void testStop(String message = '', boolean failTest = false) {
        xTestStop(message, failTest, false)
    }

    /** Real testStop() */
    void xTestStop(String message, boolean failTest, boolean framework) {

        if (runStopAlreadyInitiated) return
        runStopAlreadyInitiated = true

        System.gc()

        if (!message) message = 'Silent test stop'
        log '--', console_color_green
        log "testStop: STOPPING TEST EXECUTION... $message", console_color_green
        if (!framework)
            log "testStop: Called by $testAuthor"
        else
            xVerifyReport(false, [
                title: 'testStop()',
                details: [
                        message: message,
                        failTest: failTest
                ]
            ], IssueCategory.EMERGENCY_TEST_STOP)

        afterTest()

        if (failTest) {
            throw new Exception(message)
        } else {
            xStopExecutionQuietly()
        }
    }


    /** Framework test stop */
    void xStop(String message = '', boolean failTest = false) {
        xTestStop(message, failTest, true)
    }


    /** Stop execution */
    void xStopExecutionQuietly() {
        runNormalExit = true
        log "Thread name: ${Thread.currentThread().getName()}"
        if (Thread.currentThread().getName() != 'main') {
            throw new Exception('Automation Framework: CURRENT THREAD GOT STOPPED.')
        } else {
            System.exit(0) // for local main() method way runs only
        }
    }


    /** Stop Request From External Starters */
    void xTestStopRequest(String description) {
        runStopDescription = description
        runStopped = true
    }


    /** Is Run Stopped */
    boolean isRunStopped() {
        return runStopped
    }


    /** Stop Description */
    String getStopDescription() {
        return runStopDescription
    }

}

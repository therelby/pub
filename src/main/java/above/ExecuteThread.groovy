package above

import java.util.concurrent.Callable

/**
 *      Thread Object For Test Execution
 */
class ExecuteThread implements Callable {

    private Run test // test class instance reference
    private regularRun
    private parallel
    private suite
    public static final ThreadLocal<Object> runTestReference = new ThreadLocal<Object>()

    ExecuteThread(Object test, boolean regularRun, boolean parallel, Map suite = [:]) {
        this.test = test
        this.regularRun = regularRun
        this.parallel = parallel
        this.suite = suite
    }

    @Override
    Boolean call() throws Exception {
        runTestReference.set(test)
        try {
            return test.runner(runTestReference, regularRun, parallel, suite)
        } catch (InterruptedException ignored) {
            test.log '\n(!) INTERRUPTED Exception'
        } catch (OutOfMemoryError e) {
            System.gc()
            test.xStop('Out of memory\n' + test.exceptionToString(e))
            test = null
            System.gc()
            return true
        } catch(Exception e) {
            String eMsg = e.getMessage() ? e.getMessage() : ''
            if (eMsg != 'Automation Framework: CURRENT THREAD GOT STOPPED.' && !eMsg.contains('STOPPING TEST')) {
                test.log "ExecuteThread: got runtime error [$eMsg]"
                test.xStop(eMsg)
                return false
            } else {
                return true
            }
        }
    }

}

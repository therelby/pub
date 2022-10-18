package above

import above.allrun.helpers.RunFinalActions
import above.allrun.helpers.RunSecurityManager
import groovy.json.JsonBuilder
import above.allrun.helpers.RunReferenceStorage
import java.util.concurrent.TimeUnit

/**
 *      Test Run Base
 *      (!) Impacts to all of our tests and classes
 */
abstract class Run extends above.allrun.AaRunIssueReport {

    private static final ThreadLocal<Map> runInstances = new ThreadLocal<Map>()
    private long runInstancesLastCheck = new Date().getTime()

    /**
     * Framework Classes Instantiation & Caching
     */
    Object xInst(String fullClassName, int instanceIdleMilliseconds = 40000, boolean checkCacheOnly = false) {
        // checking for expiration
        long timeStamp = new Date().getTime()
        if (timeStamp - runInstancesLastCheck >= 30000) {
            List<String> remove = []
            Map stored = runInstances.get()
            if (!stored) stored = [:]
            for (className in stored.keySet())
                if (className != fullClassName && timeStamp - stored[className].lastGetTime >= instanceIdleMilliseconds)
                    remove << className
            remove.each {
                stored[it].instance = null
                stored[it].lastGetTime = null
                stored[it] = null
                stored.remove(it)
            }
            runInstances.set(stored)
            runInstancesLastCheck = new Date().getTime()
        }
        // only check cache?
        if (checkCacheOnly) return null
        // stored?
        Map stored = runInstances.get()
        if (!stored) stored = [:]
        if (stored.containsKey(fullClassName)) {
            // stored
            stored[fullClassName].lastGetTime = new Date().getTime()
            return stored[fullClassName].instance
        } else {
            // new
            Object instance = Class.forName(fullClassName).getConstructor().newInstance()
            stored.put(fullClassName, [
                    instance   : instance,
                    lastGetTime: new Date().getTime()
            ])
            runInstances.set(stored)
            return instance
        }
    }


    /**
     * After test mandatory actions
     *  -- for the correct test execution finishing
     *  -- saves statistics and closes browsers and db connections when needed
     * (!) framework use only
     */
    void afterTest(boolean checkSetup = true) {

        if (checkSetup) xCheckSetup()

        log('AFTER TEST:', console_color_yellow)

        // web concept - browser closing
        if (xTestConcept() == 'web' && driverStorage.get() &&
                (testBrowser.startsWith('remote') ||
                 testBrowser.startsWith('headless') ||
                 testBrowser.startsWith('mobile') ||
                 testBrowser.startsWith('ios') ||
                 testBrowser.startsWith('android'))) {
            log ' -- closing browser'
            closeBrowser()
        }

        // statistics recording
        new RunFinalActions().finalActions()

        // closing all db connections
        dbCloseAll()

        if (!runDebug) {
            log "\nRun results https://qatools.dev.clarkinc.biz/run?id=$runId&filter=test"
            if (runPipelineBuildId) {
                // flushing DevOps to WebTool logs
                lastRealTimeLogTime = new Date().getTime() - 5001
                runFlushPipelineLog = true
                log ''
            }
        } else {
            println '\n'
        }
    }


    /**
     * Test Runner -- The Execution Entry Point
     *  (!) framework use only
     */
    boolean runner(runTestReference, boolean regularRun, boolean parallel, Map suite) {

        // run reference handling
        ExpandoMetaClass.enableGlobally()
        GroovyObject.metaClass.static.run << { ->
            return runTestReference.get()
        }

        // run data adjusting
        runStartTime = getRealTimeStamp()
        if (!runId) xSetRunId(UUID.randomUUID().toString())

        Map<String, String> env = System.getenv()
        if (env["BUILD_BUILDID"])
            runPipelineBuildId = env["BUILD_BUILDID"]

        runParallel = parallel
        runSuite = suite

        runDebug = !regularRun
        if (isServerRun()) {
            runDebug = false
        }
        /** (!) Removed by Jared's request because not working when Grid has issues
        if (!runDebug) {
            if (isLocalRun() && xTestConcept() == 'web' && !testBrowserRemoteLocally) {
                log 'Runner: forcing [ testBrowserRemoteLocally = true ] for Regular run'
                testBrowserRemoteLocally = true
            }
        }*/

        // adding useful methods to current run/test object
        List.metaClass.toPrettyString { return new JsonBuilder(delegate).toPrettyString() }
        Map.metaClass.toPrettyString { return new JsonBuilder(delegate).toPrettyString() }
        String.metaClass.toPrettyString { return new JsonBuilder(delegate).toPrettyString() }

        // adding Security Manager
        System.setSecurityManager(new RunSecurityManager())

        setLogLevel('info')

        // web browser preparations
        if (xTestConcept() == 'web') {
            // suite level browser override
            if (runSuite.suiteCurrentBrowser &&
                    runSuite.suiteCurrentBrowser != 'default' &&
                    runSuite.suiteCurrentBrowser != testBrowser &&
                    "remote${runSuite.suiteCurrentBrowser}" != testBrowser) {
                logDebug("Suite override: testBrowser old value is $testBrowser", console_color_yellow)
                testBrowser = runSuite.suiteCurrentBrowser
                logDebug("Suite override: testBrowser = $testBrowser", console_color_yellow)
            }
            // checking remote
            if ((isServerRun() || testBrowserRemoteLocally) && !testBrowser.startsWith('remote')) {
                logDebug "Making '$testBrowser' remote"
                testBrowser = "remote$testBrowser"
                logDebug "testBrowser = $testBrowser"
            }
        }

        // console handling
        //System.setOut(new RunConsole(System.out))

        log('==', console_color_yellow)
        test() // test execution
        log('==', console_color_yellow)

        afterTest()

        // clean up
        testCases = null
        tcStatusCache = null
        runSuite = null

        // finishing run
        runNormalExit = true
        return !runFailed
    }


    /**
     * Local test runner
     *  -- runs sequentially only
     *  -- supports both Debug and Regular run types
     */
    void testExecute(Map params = [:]) {

        params.keySet().each {
            if (it != 'browser' && it != 'browserVersionOffset' && it != 'remoteBrowser' && it != 'environment' && it != 'runType')
                throw new Exception("Wrong using parameter: $it")
        }

        boolean regularRun = false
        if (params.runType == 'Regular')
            regularRun = true
        if (params.runParams && params.runParams instanceof Map)
            setRunParams(params.runParams)

        ThreadLocal<Object> runTestReference = new ThreadLocal<Object>()
        runTestReference.set(this)

        Execute.usingApply(this, params)

        runner(runTestReference, regularRun, false, [
                newServerRun: false,
                suiteScript: testClass,
                suiteCurrentBrowser: params.browser ? params.browser : '',
                suiteCurrentBrowserVersionOffset: params.browser_version_offset ? params.browser_version_offset as Integer : 0,
                isRegularRunRequested: regularRun,
                isServerRun: false,
                parallel: 1,
                parallelThreads: 1,
                smokeTestId: 0
        ])
    }


    /**
     * Test params setting
     */
    def usingParams(Map params) {
        setRunParams(params)
        return this
    }


    /**
     * Get real date/time for East Cost zone
     */
    Date getRealDate() {
        return new Date(getRealTimeStamp())
    }
    Long getRealTimeStamp() {
        if (isServerRun()) {
            return System.currentTimeMillis() - TimeUnit.HOURS.toMillis(5)
        } else {
            return System.currentTimeMillis()
        }
    }

}

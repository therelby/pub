package above

import com.cedarsoftware.util.io.GroovyJsonReader
import com.cedarsoftware.util.io.GroovyJsonWriter
import org.codehaus.groovy.runtime.StackTraceUtils
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.ThreadPoolExecutor

/**
 *      Test Executor
 */
final class Execute {

    final private static Integer parallelTreads = ConfigReader.get('parallelTreads').toInteger()
    final private static Integer parallelTreadsMax = ConfigReader.get('parallelTreadsMax').toInteger()

    /**
     * Execute tests in parallel and sequentially
     * @tests - list of run/test instances like [new TextClass1(), new TestClass2()]
     * @threads - number of parallel threads:
     *              -- null/0 - set default parallelTreads
     *              -- 999 - set parallelTreadsMax
     *              -- 1 - sequential run
     *              -- 2..parallelTreadsMax - set certain number of parallel threads
     * @isRegularRun - set true to make local run Regular
     * @using - map of params like [environment: 'test', browser: 'chrome', remoteBrowser: true]
     *              -- use any set of options in the map
     *              -- local using*() methods in @tests param override this map params
     *                 like [new TestClass1().usingBrowser('safari')]
     */
    synchronized static void suite(List tests, int threads = parallelTreads, boolean isRegularRun = false, Map using = [:]) {

        // correct call?
        boolean correctCall
        try { Run r = run(); correctCall = false } catch(ignore) { correctCall = true }
        if (!correctCall) throw new Exception('(!) Run conflict. Execute.suite() can not be called from a test class.')

        // checking test list
        if (!tests) { throw new Exception("The suite has no tests. Please provide at least one test class instance") }

        // setting threads to default values
        if (using.parallelThreads) { threads = using.parallelThreads }
        if (!threads) { threads = parallelTreads }
        if (threads == 999) { threads = parallelTreadsMax }

        // type of run
        if (using.runType && using.runType == 'Regular') {
            isRegularRun = true
        } else if (using.runType && using.runType != 'Debug') {
            throw new Exception("Execute.suite(): Wrong runType [${using.runType}]")
        }

        // checking number of threads
        if (threads < 1 || threads > parallelTreadsMax) {
            throw new Exception("Parallel.run: Wrong treads number [$threads] instead of 1..$parallelTreadsMax")
        }
        if (tests.size() < threads) {
            threads = tests.size()
        }

        // printing
        boolean parallel = false
        if (threads == 1 || tests.size() == 1) {
            println "Executing sequentially:\n${tests*.getClass()*.toString()*.replaceFirst('class ', '').join('\n')}\n" +
                    "- tests: [${tests.size()}]"
        } else {
            println "Executing in parallel:\n${tests*.getClass()*.toString()*.replaceFirst('class ', '').join('\n')}\n" +
                    "- tests:   [${tests.size()}]\n- threads: [$threads] " + ('|' * threads)
            parallel = true
        }

        // web concept and server run?
        String concept0 = tests[0].xTestConcept()
        boolean webConcept = concept0 == 'web'
        boolean isServerRun = System.getenv()['AUTOMATION_FRAMEWORK_RUN_ON_SERVER'].toString().toLowerCase() == 'true'
        boolean webServerRun = webConcept && isServerRun

        // preparing browser list for suite (will be working on the server for web concept tests only)
        List suiteBrowsers = []
        if (webServerRun && using.executeMode != 'OnceOnServer' && concept0 != 'base') {
            if (using.containsKey('serverBrowsers') && using.serverBrowsers instanceof List && using.serverBrowsers.size() > 0) {
                suiteBrowsers = using.serverBrowsers*.trim()*.toLowerCase().sort()
                ArrayList<String> allBrowsers = ConfigReader.get('browserList').split(',')*.trim()
                suiteBrowsers.each {
                    if (!allBrowsers.contains(it)) {
                        throw new Exception("Suite parameter 'serverBrowsers' contains unknown browser [$it]")
                    }
                }
            } else {
                suiteBrowsers = ConfigReader.get('browserListForServerRunning').split(',')*.trim().sort()
            }
            println "Suite browsers: $suiteBrowsers"
        } else {
            suiteBrowsers = ['default']
        }

        // creating suite data
        Map suiteData = [
                suiteScript: getScriptName(),
                suiteParams: using,
                suiteBrowsers: suiteBrowsers,
                suiteCurrentBrowser: '',
                suiteCurrentBrowserVersionOffset: 0,
                suiteExecutingLoopNumber: 0,
                suiteExecutingOrder: '',
                isRegularRunRequested: isRegularRun,
                isServerRun: isServerRun,
                parallel: parallel,
                parallelThreads: threads,
                tests: tests*.testClass
        ]

        // getting browser versions
        List<String> browserVersions
        if (using.executeMode == 'OnceOnServer' || concept0 == 'base') {
            browserVersions = [[ConfigReader.get('defaultBrowser'), '0']]
        } else {
            browserVersions = ConfigReader.get('browserVersions').split(',')*.split(':')
        }

        // creating executing plan
        List execList = []

        // running for every browser or ones depends of the conditions
        int loopNumber = 0
        int globalExitCode = 0
        for (browser in suiteBrowsers) {
            // running for all present versions of the current browser
            String bro = browser.replaceFirst('remote', '')
            int idx = browserVersions.findIndexOf { it[0] == bro }
            List<Integer> versions = []
            if (idx >= 0) {
                for (v in 0..browserVersions[idx][1].toInteger()) {
                    versions << v
                }
            } else {
                versions = [0]
            }
            for (version in versions) {
                loopNumber++
                int orderNumber = 0
                for (t in tests) {
                    orderNumber++
                    suiteData.suiteCurrentBrowser = browser
                    suiteData.suiteCurrentBrowserVersionOffset = version
                    suiteData.suiteExecutingLoopNumber = loopNumber
                    suiteData.suiteExecutingOrder = "$orderNumber/${tests.size()}"
                    execList << [
                            testIdx: orderNumber - 1,
                            suiteData: GroovyJsonReader.jsonToGroovy(GroovyJsonWriter.objectToJson(suiteData))
                    ]
                }
            }
        }

        // EXECUTING EXEC LIST AS A SUITE
        List<Future<Boolean>> executionResults = []
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(execList.size())
        for (item in execList) {
            String json = GroovyJsonWriter.objectToJson(tests[item.testIdx])
            def test = GroovyJsonReader.jsonToGroovy(json) // Eval.me("return new ${tests[item.testIdx].class.name}()")

            if (using) {
                usingApply(test, using)
            }
            if (webServerRun) {
                println "\n*\n***\n*******  Running suite for browser: ${item.suiteData.suiteCurrentBrowser}, " +
                        "version offset: ${item.suiteData.suiteCurrentBrowserVersionOffset}\n***\n*\n"
            }

            // checking parallel threads number
            if (parallel) {
                while (executor.getActiveCount() >= threads) {
                    sleep(200)
                }
            }

            // running
            executionResults << executor.submit(new ExecuteThread(test, isRegularRun, parallel, item.suiteData))

            // sequential test waiting
            if (!parallel) {
                while (executor.getActiveCount() > 0) {
                    sleep(200)
                }
                executor.shutdown()
                executor = null
                System.gc()
                executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(execList.size())
            }

            test = null
        }
        // waiting for parallel suite done
        if (parallel) {
            while (executor.getActiveCount() > 0) {
                sleep(200)
            }
        }


        // handling results
        int exitCode = 0
        executionResults.each {
            try {
                if (it && !it.get()) {
                    exitCode = 1
                }
            } catch (OutOfMemoryError err) {
                err.printStackTrace()
                exitCode = 1
            } catch (InterruptedException err) {
                err.printStackTrace()
                exitCode = 1
            } catch (ExecutionException err) {
                if (!err.getMessage().contains('CURRENT THREAD GOT STOPPED')) {
                    err.printStackTrace()
                    exitCode = 1
                }
            } catch (ExceptionInInitializerError err) {
                err.printStackTrace()
                exitCode = 1
            } catch (Exception err) {
                if (!err.getMessage().contains('STOPPING TEST')) {
                    err.printStackTrace()
                    exitCode = 1
                }
            }
        }
        if (exitCode != 0) {
            globalExitCode = 1
        }
        println "\nSUITE DONE WITH exitCode = $exitCode"

        // closing executor
        executor.shutdown()

        // main thread must exit here
        System.exit(globalExitCode)
    }

    /** Using first params suite() variant */
    synchronized static void suite(Map using, List tests, Integer threads = parallelTreads, Boolean regularRun = false) {
        suite(tests, threads, regularRun, using)
    }


    /** Applying using options */
    synchronized static void usingApply(Run test, Map using) {
        if (test.xTestConcept() != 'web') { return }
        if (using.browser && !test.usingBrowserCalled) {
            test.usingBrowser(using.browser)
        }
        if (using.browserVersionOffset && !test.usingEnvironmentCalled) {
            test.usingBrowserVersionOffset(using.browserVersionOffset)
        }
        if (using.remoteBrowser && !test.usingRemoteBrowserCalled) {
            test.usingRemoteBrowser()
        }
        if (using.environment && !test.usingEnvironmentCalled) {
            test.usingEnvironment(using.environment)
        }
    }


    /** Script name */
    synchronized static String getScriptName() {
        Throwable throwable = new Throwable()
        for (it in StackTraceUtils.sanitize(throwable).stackTrace.reverse()) {
            if (!it.className.startsWith('org.') && !it.className.startsWith('jdk.internal.reflect.')) {
                return it.className
            }
        }
        return '[unknown]'
    }

}

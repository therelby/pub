package above.allrun

import java.util.regex.Pattern

/**
 *      Run Variables
 */

abstract class ZzRunVariables {

    /** Run data */

    public final static String runVersion = '3.beta.0907'

    String runId = null                             // UUID
    String runPipelineBuildId = '0'                 // coming from system variables on server
    String runFlushPipelineLog = false              // for WebTool real-time logs with server running
    Integer runRecordId = null                      // in [QA_Automation].[dbo].[Stat_Test_Runs]
    Map runSuite = [:]                              // execution parameters
    Map runParams = null                            // custom data for current execution
    boolean runParamsMakeUniqueInStats = false      // based on runParams

    public final Long runCreateTime = getRealTimeStamp()    // Run/Test object creation time
    Long runStartTime = null                                // test start time

    String runRawSetupOptions = null
    boolean runPbiBased = true                      // BPI approach by default and manual TFS testcase approach when false
    boolean runVerifyBased = false                  // uses verify() approach
    boolean runOnesOnServer = false                 // running with default browser only instead of running with the server browser list
    boolean runParallel = false
    boolean runDebug = true                         // true - Debug, false - Regular running
    boolean runNormalExit = false                   // early exit blocker

    protected String runLastIssue = ''              // framework last issue

    Map runVerifyStats = [ passed: 0, failed: 0, reports: 0 ]
    boolean verifyAlwaysPrintDetails = false

    boolean runFailed = false                       // Java/Groovy exception happened
    String scriptError = ''                         // the exception text for runFailed = true

    final String runHost = InetAddress.getLocalHost().toString().toLowerCase()
    final String runOs = System.properties['os.name']

    List<Map> runLog = []                           // the console logs storage

    Object GLOBAL = null                            // Katalon project sequential tests compatibility


    /** Test data */

    final String projectPathRun = System.getProperty("user.dir").toString().replace('\\', '/') + '/'
    final String projectPath = (projectPathRun.split(Pattern.quote("/src/"))[0] + '/').replace('//', '/')
    String testClass = this.class.name
    Integer testClassId = null
    String testClassUuid = null
    String testAuthor = null
    Integer testAuthorId = null
    String testTitle = ''
    String testProduct = null
    String testTfsProject = null
    int testTfsProjectId = 0
    String testKeywords = null
    List<String> testNotes = []
    boolean testRealtimeResults = true
    int testChecksTotal = 0
    int testChecksFailed = 0
    int testChecksAllowedPercent = 3
    int testChecksAllowedMin = 10

    // Modern approach stuff
    protected Map testUsers = null                  // test users and their ids like [ login: id ]
    protected List<Integer> testPBIs = []           // test PBI ids

    // Issue handling/statistics data
    boolean autoTcReady = false
    boolean noPositiveChecksPrinting = false
    Integer currentTcId = null
    String lastIssue = ''

    // Testcases
    List testCases = []
    List tcStatusCache = []


    /** Get run/test data snapshot */
    Map getInfo() {

        String concept = xTestConcept()

        Map webData = [:]
        if (concept == 'web') {
            webData = [
                    webVersion: webVersion,
                    testEnv: webProject.testEnv,
                    testBrowser: testBrowser,
                    testBrowserVersion: testBrowserVersion
            ]
        }

        Map desktopData = [:]
        if (concept == 'desktop') {
            desktopData = [
                    APP_PROCESS: desktopProject.APP_PROCESS,
                    APP_NAME: desktopProject.APP_NAME,
                    APP_ID: desktopProject.APP_ID,
                    PLATFORM_NAME: desktopProject.PLATFORM_NAME,
                    DEVICE_NAME: desktopProject.DEVICE_NAME,
                    WINAPP_DRIVER_URL: desktopProject.WINAPP_DRIVER_URL
            ]
        }

        return [
                run: [
                        runVersion: runVersion,
                        runId: runId,
                        runRecordId: runRecordId,
                        runPipelineBuildId: runPipelineBuildId,
                        runFlushPipelineLog: runFlushPipelineLog,
                        runParams: runParams,
                        runMakeUniqueInStats: runParamsMakeUniqueInStats,
                        runRawSetupOptions: runRawSetupOptions,
                        runPbiBased: runPbiBased,
                        runVerifyBased: runVerifyBased,
                        runOnesOnServer: runOnesOnServer,
                        runParallel: runParallel,
                        runDebug: runDebug,
                        runFailed: runFailed,
                        runStopped: isRunStopped(),
                        runStopDescription: getStopDescription(),
                        runHost: runHost,
                        runOs: runOs,
                        runCreateTime: runCreateTime,
                        runStartTime: new Date(runStartTime).format('MM/dd/yyyy HH:mm:ss'),
                        runStartTimeStamp: runStartTime,
                        runSuite: runSuite
                ],
                test: [
                        projectPath: projectPath,
                        projectRunPath: projectPathRun,
                        testClass: testClass,
                        testAuthor: testAuthor,
                        testTitle: testTitle,
                        testProduct: testProduct,
                        testTfsProject: testTfsProject,
                        testKeywords: testKeywords,
                        testRealtimeResults: testRealtimeResults,
                        testChecksTotal: testChecksTotal,
                        testChecksFailed: testChecksFailed,
                        testChecksAllowedPercent: testChecksAllowedPercent,
                        testChecksAllowedMin: testChecksAllowedMin
                ],
                webData: webData,
                desktopData: desktopData,
                testcases: [
                        autoTcReady: autoTcReady,
                        currentTcId: currentTcId,
                        tests: testCases
                ],
                issueTrackerList: getIssueTrackerList(),
                scriptError: scriptError
        ]
    }


    /** Current test concept */
    String xTestConcept() {
        try {
            return concept
        } catch (Ignored) {
            return 'base'
        }
    }


    /** Is it server or local run? */
    boolean isServerRun() {
        try {
            return System.getenv()['AUTOMATION_FRAMEWORK_RUN_ON_SERVER'].toString().toLowerCase() == 'true'
        } catch (ignored) {
            return false
        }
    }
    boolean isLocalRun() {
        return !isServerRun()
    }


    /** Check if setup() already called (testAuthor already set) */
    void xCheckSetup() {
        if (!testAuthor) {
            throw new Exception("Please call setup() method in very first test's code line")
        }
    }


    /** Framework Last Issue Message */
    String xRunLastIssue() {
        return runLastIssue
    }
    boolean xSetRunLastIssue(String issueMessage) { /** always returns false */
        //if (!xCalledMethod('ZzRunVariables$xSetRunLastIssue').startsWith('above.'))
        //    throw new Exception('Could not to change Run ID')
        runLastIssue = issueMessage
        return false
    }


    /*
            FRAMEWORK VARIABLES GETTERS AND SETTERS
     */

    /** Get Run ID */
    String xRunId() { return runId}
    void xSetRunId(String uuid) {
        //if (!xCalledMethod('ZzRunVariables$xSetRunId').startsWith('above.'))
        //    throw new Exception('Could not to change Run ID')
        runId = uuid
    }


    /** Test Users and their ids */
    Map xTestUsers() { return testUsers }
    void xSetTestUsers(Map users) { testUsers = users }

    /** Test PBIs */
    List<Integer> xTestBPIs() { return testPBIs }
    void xSetTestPBIs(List<Integer> pbis) { testPBIs = pbis }

}

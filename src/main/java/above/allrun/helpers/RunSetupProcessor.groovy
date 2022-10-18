package above.allrun.helpers

import above.Run
import camp.CampWebProject
import clarkassociates.CaWebProject
import eva.EvaWebProject
import forum.ForumWebProject
import ltl.LtlWebProject
import office.OfficeWebProject
import rkw.RkwWebProject
import statuscheck.StatusCheckWebProject
import switchboard.SWBProject
import transactions.TransactionsWebProject
import wap.WAPProject
import wss.WssWebProject
import sps.SpsDesktopProject

/**
 *      Setup Options Processor
 */

class RunSetupProcessor {

    final private Run r = run()
    List<String> infoText = []

    /** Apply setup() options */
    void xSetupProcessor(Map<String, Object> options) {

        // checking for all cases mandatory options
        if (!options.containsKey('author')) {
            throw new Exception("setup() must contain [ author: 'login' ] option")
        }
        if (!options.containsKey('title')) {
            throw new Exception("setup() must contain [ title: '...' ] option")
        }
        if (!options.containsKey('keywords') && r.xTestConcept() != 'base') {
            throw new Exception("setup() must contain [ keywords: '...' ] option")
        }

        r.log "Automation Framework ${r.runVersion} - runId: ${r.runId}"

        // processing options
        def logLevelOption
        for (String key in options.keySet()) {

            if (options[key] instanceof String) {
                options[key] = options[key].trim()
            }

            switch (key) {

                case 'dbEnvironmentDepending':
                    if (options.dbEnvironmentDepending == 'wss' && r.testProduct == 'wss') {
                        r.dbEnvironmentDepending = true
                    } else if (options.dbEnvironmentDepending == 'wss') {
                        throw new Exception("dbEnvironmentDepending is supporting 'wss' product only, but got: [${r.testProduct}]")
                    } else {
                        throw new Exception("Unknown dbEnvironmentDepending product: [${options.dbEnvironmentDepending}]")
                    }
                    break

                case 'allowUnlimitedCheckFails':
                    if (options.allowUnlimitedCheckFails) {
                        r.testChecksAllowedPercent = 100
                        r.log('(!) UNLIMITED check fails allowed for this test run. ' +
                                'Make sure you are able to handle all of them on ' +
                                'https://qatools.dev.clarkinc.biz/TestResults', r.console_color_yellow)
                    }
                    break

                case 'dbUsing':
                    r.dbUsing(options.dbUsing.trim())
                    addInfo "dbUsing = ${options.dbUsing}"
                    break

                case 'logLevel':
                    if (!r.logLevels.contains(options.logLevel.trim())) {
                        throw new Exception("Wrong log level option [${options.logLevel}]")
                    }
                    logLevelOption = options.logLevel.trim()
                    break

                case 'autoTcReady':
                    if (options.autoTcReady) {
                        r.autoTcReady = true
                        addInfo "autoTcReady = true"
                    }
                    break

                case 'noPositiveChecksPrinting':
                    if (r.isLocalRun() && options.noPositiveChecksPrinting) {
                        r.noPositiveChecksPrinting = true
                        addInfo "testStepsHideConsoleOutput = true"
                    }
                    break

                case 'testStepsHideConsoleOutput':
                    if (options.testStepsHideConsoleOutput) {
                        r.testStepsHideConsoleOutput = true
                        addInfo "noPositiveChecksPrinting = true"
                    }
                    break

                case 'verifyAlwaysPrintDetails':
                    if (options.verifyAlwaysPrintDetails) {
                        r.verifyAlwaysPrintDetails = true
                        addInfo "verifyAlwaysPrintDetails = true"
                    }
                    break

                case 'forceDebug':
                    if (options.forceDebug) {
                        r.log '(!) Debug mode forced', r.console_color_yellow
                        r.runDebug = true
                    }
                    break

                case 'keywords':
                    r.testKeywords = options.keywords.replace("'", "''").trim()
                    break

                case 'note':
                    r.testNotes << options.note.replace("'", "''").trim()
                    break
                case 'notes':
                    r.testNotes << options.notes.replace("'", "''").trim()
                    break

                case 'tfsProject':
                    r.testTfsProject = options.tfsProject.replace('  ', ' ').replace(' ', '%20').trim()
                    break
                case 'project':
                    r.testTfsProject = options.project.replace('  ', ' ').replace(' ', '%20').trim()
                    break

                case 'product':
                    r.testProduct = options.product.trim()
                    if (r.testProduct.contains('|')) {
                        List ops = r.testProduct.split(java.util.regex.Pattern.quote('|'))
                        r.testProduct = ops[0]
                        if (ops.size() > 1) {
                            ops = ops[1].split(',')*.trim()
                            for (i in 0..ops.size()-1) {
                                r.testEnvSupported << ops[i].toString()
                            }
                        }
                    }
                    switch (r.testProduct) {
                        case 'wss':
                            if (r.xTestConcept() == 'web') {
                                r.webProject = new WssWebProject()
                            }
                            break
                        case 'rkw':
                            if (r.xTestConcept() == 'web') {
                                r.webProject = new RkwWebProject()
                            }
                            break
                        case 'ca':
                            if (r.xTestConcept() == 'web') {
                                r.webProject = new CaWebProject()
                            }
                            break
                        case 'camp':
                            if (r.xTestConcept() == 'web') {
                                r.webProject = new CampWebProject()
                            }
                            break
                        case 'wss-sps':
                            if (r.xTestConcept() == 'desktop') {
                                r.desktopProject = new SpsDesktopProject()
                            }
                            break
                        case 'wap':
                            if (r.xTestConcept() == 'web') {
                                r.webProject = new WAPProject()
                            }
                            break
                        case 'swb':
                            if (r.xTestConcept() == 'web') {
                                r.webProject = new SWBProject()
                            }
                            break
                        case 'forum':
                            if (r.xTestConcept() == 'web') {
                                r.webProject = new ForumWebProject()
                            }
                            break
                        case 'eva':
                            if (r.xTestConcept() == 'web') {
                                r.webProject = new EvaWebProject()
                            }
                            break
                        case 'office':
                            if (r.xTestConcept() == 'web') {
                                r.webProject = new OfficeWebProject()
                            }
                            break
                        case 'ltl':
                            if (r.xTestConcept() == 'web') {
                                r.webProject = new LtlWebProject()
                            }
                            break
                        case 'statuscheck':
                            if (r.xTestConcept() == 'web') {
                                r.webProject = new StatusCheckWebProject()
                            }
                            break
                        default:
                            throw new Exception("Unknown product [$r.testProduct]")
                            break
                    }
                    // checking environments
                    if (r.xTestConcept() == 'web' && r.testEnvSupported) {
                        r.testEnvSupported.each {
                            if (!r.webProject.envs.containsKey(it)) {
                                throw new Exception("SETUP: Bad environment name [$it] for product [$r.testProduct]")
                            }
                        }
                    }
                    break

                case 'title':
                    if (!options.title.trim()) {
                        throw new Exception('setup() title option must be not empty String')
                    }
                    r.testTitle = options.title.trim()
                    break

                case 'author':
                    List<String> authors = []
                    if (options.author instanceof String) {
                        if (!options.author.trim()) throw new Exception('setup() author option must be not empty String')
                        authors << options.author.trim().toLowerCase()
                    } else if (options.author instanceof List<String>) {
                        authors = options.author
                    } else {
                        throw new Exception('setup() author option must be not empty String or List<String>')
                    }
                    r.testAuthor = '-patch-' // TODO: replace compatibility patch

                    // need class uuid right now for the next step -- class id getting
                    if (!r.isRunDebug()) {
                        try {
                            UUID.fromString(options.id)
                        } catch (e) {
                            throw new Exception("setup: id option must be valid and unique UUID. Got exception: ${e.getMessage()}")
                        }
                        r.testClassUuid = options.id
                    }

                    r.testClassId = new RunClassId().xClassId(r.testClass)
                    if (!new RunAuthors().xAuthorListUpdate(authors)) r.testStop(r.xRunLastIssue())
                    break

                case 'tfsTcIds':
                case 'PBI':
                    List ids = []
                    if (key == 'tfsTcIds' || (key == 'PBI' && options.PBI instanceof List)) {
                        if (key == 'tfsTcIds') {
                            r.runPbiBased = false
                            ids = options.tfsTcIds.split('\\,')*.trim().unique()
                            if (!ids*.isNumber())
                                throw new Exception("setup: Bad id(s) $ids provided in $key option")
                        } else {
                            ids = options.PBI.unique()
                        }
                    } else {
                        if (options.PBI instanceof Integer) {
                            ids << options.PBI
                        } else {
                            if (!(options.PBI instanceof String) || !options.PBI.isNumber())
                                throw new Exception('setup: PBI id must be a number')
                            ids << options.PBI.toInteger()
                        }
                    }
                    for (tcId in ids*.toInteger().unique().sort()) {
                        r.testCases << [
                                tcId: tcId, // tcId or pbiId depending of the current approach
                                tfsOutcome: '---',
                                reports: [],
                                checks: []
                        ]
                    }
                    if (!new RunPBIs().xTryClassPBIsUpdate(ids, r.testClassId)) r.testStop(r.xRunLastIssue())
                    break

                default:
                    if (key != 'id') throw new Exception("Unknown setup() option: $key")
                    break
            }
        }

        // setting default product if was not provided explicitly
        if (r.xTestConcept() == 'web' && !r.webProject) {
            r.webProject = new WssWebProject()
            r.testProduct = 'wss'
        }
        if (r.xTestConcept() == 'desktop' && !r.desktopProject) {
            r.desktopProject = new SpsDesktopProject()
            r.testProduct = 'wss-sps'
        }

        // checking mandatory options
        if (!r.testTfsProject && r.xTestConcept() != 'base')
            throw new Exception('project or tfsProject setup() option must be present')

        // TFS test cases or PBIs was not provided
        if (r.xTestConcept() != 'base' && !r.testCases && !r.xTestBPIs())
            throw new Exception('Test must have PBI id(s) in the setup() options')

        // web test environment
        if (r.xTestConcept() == 'web' && r.webProject) {

            // test environment supported list
            if (!r.testEnvSupported) {
                r.testEnvSupported << r.webProject.testEnv
            }

            // test environment override coming from .with()
            if (r.testEnv) {
                r.webProject.testEnv = r.testEnv
            }

            // checking conflicts
            if (!r.testEnvSupported.contains(r.webProject.testEnv)) {
                throw new Exception("Environment '${r.webProject.testEnv}' is not supported by ${r.testClass} | Supported: ${r.testEnvSupported}")
            }
        }

        // console printing
        addInfo "testTfsProject = ${r.testTfsProject}".toString()
        addInfo "testKeywords = ${r.testKeywords}".toString()
        if (logLevelOption) {
            r.setLogLevel(logLevelOption, true)
            addInfo "logLevel = ${r.logLevel}".toString()
        } else {
            addInfo "logLevel = info".toString()
        }
        if (r.runPbiBased) {
            addInfo "PBI = ${r.testCases.collect { it.tcId }}".toString()
        } else {
            addInfo "tfsTcIds = ${r.testCases.collect { it.tcId }}".toString()
        }
        if (r.testNotes) {
            addInfo "notes = ${r.testNotes}"
        }
        if (r.dbEnvironmentDepending && r.concept == 'web') {
            addInfo "dbEnvironmentDepending for '${r.testProduct}' databases is ON"
        }
        def msg
        if (r.runDebug) {
            msg = 'DEBUG run: no any statistics recording'
        } else {
            msg = 'REGULAR run: statistics going to be recorded'
        }
        r.log "$msg | Free memory: ${Math.round(Runtime.getRuntime().freeMemory() / 1024 / 1024)} Mb"
        r.log '--'
        def con = "${r.xTestConcept().toUpperCase()}"
        if (r.xTestConcept() == 'web') {
            con += " | product = '${r.testProduct}' | browser = '${r.testBrowser}', remoteBrowser = ${r.testBrowserRemoteLocally} | environment = "
            if (r.testEnv) {
                con += "'${r.testEnv}'"
            } else {
                con += "'${r.webProject.testEnv}'"
            }
        }
        r.log "${r.testAuthor} - ${r.testTitle} - (${r.testClass})"
        r.log "Run Concept: ${con}"
        r.log '--'
        infoText.each { String it -> r.log it }
        r.log('==', r.console_color_yellow)

        // stats
        new RunStatsBegin().statisticsInit()

        // running beforeTest()
        if (r.runSuite.suiteExecutingLoopNumber == 1 && this.metaClass.respondsTo(this, "beforeTest")) {
            r.log 'Calling beforeTest() method...'
            r.beforeTest()
        }

        infoText = ['-']
    }


    // Add info part
    private void addInfo(String text) {
        if (!infoText || !infoText.last().endsWith(' ') || (infoText.last()+text).length() > 160 || text.length() >
                50) {
            if (text.length() < 55) {
                text += ' ' * (55 - text.length())
            }
            infoText << text
        } else {
            if (text.length() < 55) {
                text += ' ' * (55 - text.length())
            }
            infoText[infoText.size()-1] = infoText[infoText.size()-1] + text
        }
    }

}

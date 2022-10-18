package above.allrun

import above.ConfigReader
import all.Db
import all.DbTools
import all.MemoryStorage
import org.codehaus.groovy.runtime.StackTraceUtils

/**
 *      Run Logger - console printing with different detailing level and collecting debug/reproducing data
 */
abstract class ZbRunLogger extends ZcRunHealth {

    // Options: info, debug
    String[] logLevels = ['info', 'debug', 'none']
    String logLevel = 'info'
    int logLineLength = 80
    boolean logRecursionLocked = false
    private int logDelay = ConfigReader.get('logPrintingDelayMoreThanSeconds').toInteger()

    // Last log data
    long lastLogTime = 0
    long lastRealTimeLogTime = getRealTimeStamp() - 5001
    String lastLogMethod = ''
    String lastPrint = ''

    // Console colors
    final String console_color_reset = '\u001B[0m'
    final String console_color_red = '\u001B[31m'
    final String console_color_yellow = '\u001B[33m'
    final String console_color_green = '\u001B[32m'

    // Long tests log saving blocker
    private boolean debugLogsDeleted = false


    /**
     * Prepare, save and print log data
     */
    private void printText(Object data, String color, String printMode, Boolean passGap = false) {

        boolean printTimestamp = true
        long currentTime = getRealTimeStamp()

        // new server run
        if (runSuite?.newServerRun && runParallel && logLevel != 'none')
            logLevel = 'none'

        // memory saver for long tests
        if (isServerRun() && !debugLogsDeleted && runStartTime && currentTime - runStartTime > 1800000) {
            runLog.each { Map it ->
                it.debug = null
            }
            debugLogsDeleted = true
            log '--', console_color_red
            log "$currentTime | $runStartTime"
            log 'framework: EMERGENCY LOG CLEAN UP', console_color_yellow
            log 'framework: Debug log data removed because of test execution time > 30 minutes', console_color_yellow
            log '--', console_color_red
            System.gc()
        }

        // printing time gap?
        if (!passGap) {
            if (lastLogTime) {
                long interval = (currentTime - lastLogTime) / 1000
                lastLogTime = currentTime
                if (interval > logDelay) {
                    printText("    (!) $interval seconds gap", color, printMode, true)
                }
            } else {
                lastLogTime = currentTime
            }
        }

        // data?
        if (!(data instanceof String || data instanceof org.codehaus.groovy.runtime.GStringImpl)) {
            try {
                data = data.toPrettyString()
            } catch(ignored) {
                data = data.toString()
            }
        } else {
            if (data.contains('\n')) {
                data.split('\n').each{ printText(it.toString(), color, printMode, passGap) }
                return
            }
        }

        // lines
        if (data == '--' || data == '==') {
            data = data * logLineLength
            printTimestamp = false
            if (runLog && (runLog.last().info.startsWith('-------') || runLog.last().info.startsWith('=======')))
                return
        }

        // debug
        String dataDebug = null // keeping this for over 1 hour tests execution
        String calledMethod = xCalledMethod()
        if (!debugLogsDeleted) {
            if (!data.startsWith('-------') && !data.startsWith('=======')) {
                if (logLevel == 'debug' && lastLogMethod && calledMethod != lastLogMethod) {
                    println ''
                }
                if (calledMethod.length() < 40) {
                    dataDebug = calledMethod + ('.' * (39 - calledMethod.length())) + '  ' + data
                } else {
                    dataDebug = calledMethod[0..38] + '  ' + data
                }
            } else {
                dataDebug = data
            }
        }
        lastLogMethod = calledMethod

        // preventing double lines printing
        if ((data.startsWith('-------') || data.startsWith('=======')) &&
                (lastPrint.startsWith('-------') || lastPrint.startsWith('======='))) {
            return
        }

        // saving data
        runLog << [
            time: currentTime,
            printMode: printMode,
            info: data,
            debug: dataDebug
        ]

        // adding timestamp
        if (printTimestamp && isServerRun()) {
            String timestamp = new Date(currentTime).format("HH:mm:ss.SSS")
            runLog.last().info = "[$timestamp]: ${runLog.last().info}"
            runLog.last().debug = "[$timestamp]: ${runLog.last().debug}"
        }

        // console printing
        if (logLevel != 'none' && (printMode == logLevel || (logLevel == 'debug' && printMode == 'info'))) {
            synchronized (PrintWriter) {
                if (color) { print color }
                if (runParallel) {
                    print(Thread.currentThread().getName() + ' > ')
                }
                lastPrint = runLog.last()[logLevel]
                print lastPrint
                if (color) { print console_color_reset }
                println()
            }
        }

        // WebTool real-time logs
        if (runPipelineBuildId != '0') { // if runs in a pipeline
            Long ts = getRealTimeStamp()
            if (ts - lastRealTimeLogTime < 5000)
                return
            lastRealTimeLogTime = ts
            String llSave = logLevel
            logLevel = 'none'
            try {
                List request
                if (!runFlushPipelineLog) {
                    request = DbTools.selectAll("SELECT id, got_rows FROM WebTool_DevOps_Log " +
                            "WHERE build_id = $runPipelineBuildId AND run_id = '$runId' AND got_rows = sent_rows", 'qa')
                } else {
                    request = DbTools.selectAll("SELECT id, got_rows FROM WebTool_DevOps_Log " +
                            "WHERE build_id = $runPipelineBuildId AND run_id = '$runId'", 'qa')
                }
                if (request) {
                    request.each { // for each WebTool session id
                        if (runLog.size() > it.got_rows) {
                            MemoryStorage.setData("framework-realtime-log-${it.id}", runLog[(it.got_rows)..(runLog.size()-1)], true)
                            new Db('qa').updateQuery("UPDATE WebTool_DevOps_Log SET sent_rows = ${runLog.size()}, " +
                                    "date_update = getdate() WHERE id = ${it.id}")
                        }
                    }
                }
            } catch(e) {
                log 'WebTool real-time logs error:', console_color_yellow
                log e.getMessage(), console_color_yellow
            }
            logLevel = llSave
        }

        // project health checker
        if (!logRecursionLocked) {
            logRecursionLocked = true
            xCheckRunHealth()
            logRecursionLocked = false
        }
    }


    // PUBLIC METHODS


    /**
     * Info logging
     */
    void log(data, String color = '') {
        printText(data, color, 'info')
    }


    /**
     * Debug logging
     */
    void logDebug(data, String color = '') {
        printText(data, color, 'debug')
    }


    /**
     * Info logging with nice print of complex data
     */
    void logData(data) {
        printText(data, '', 'info')
    }


    /**
     * Debug logging with nice print of complex data
     */
    void logDataDebug(data) {
        printText(data, '', 'debug')
    }


    /**
     * Script Error logging
     * (!) FRAMEWORK USAGE ONLY
     */
    @Deprecated
    void logError(String errorDescription, boolean mainThread = false) {
        xStop(errorDescription, true)
    }


    /**
     * Get method name
     * (!) framework use only
     * markerPoint
     *      0 - auto
     *      1 - self
     *      2 - log
     */
    String xCalledMethod(String marker = 'ZbRunLogger$log') {
        String marker2 = marker.replace('$', '.')
        Throwable throwable = new Throwable()
        boolean flag = false
        //println '------------------------------------'
        //println "MARKER $marker [$marker2]"
        for (int i = 0; i < StackTraceUtils.sanitize(throwable).stackTrace.size(); i++) {
            //println StackTraceUtils.sanitize(throwable).stackTrace[i].className + '.' +
            //        StackTraceUtils.sanitize(throwable).stackTrace[i].methodName
            if (!flag) {
                if (StackTraceUtils.sanitize(throwable).stackTrace[i].className.contains(marker) ||
                        StackTraceUtils.sanitize(throwable).stackTrace[i].className.contains(marker2))
                    flag = true
                continue
            }
            if (flag && (!StackTraceUtils.sanitize(throwable).stackTrace[i].className.contains('$') ||
                    i == StackTraceUtils.sanitize(throwable).stackTrace.size()-1))
                return StackTraceUtils.sanitize(throwable).stackTrace[i].className + '.' +
                        StackTraceUtils.sanitize(throwable).stackTrace[i].methodName
        }
        return 'UNKNOWN'
    }


    /**
     * Set log level
     */
    void setLogLevel(String levelName, boolean silent = false) {
        if (!logLevels.contains(levelName)) {
            throw new Exception("Wrong log level [$levelName]")
        }
        if (!silent) { log "Logger: Setting logLevel = '$levelName'" }
        logLevel = levelName
    }

    /**
     * Get log level
     */
    String getLogLevel() {
        return logLevel
    }


    /**
     * Debug mode switchers
     */
    void logDebugOn() {
        log "Logger: Setting logLevel = 'debug'"
        setLogLevel('debug')
    }
    void logDebugOff() {
        log "Logger: Setting logLevel = 'info'"
        setLogLevel('info')
    }


    /**
     * Remove all log records
     * -- adds a message about the removing
     */
    void cleanLog() {
        runLog = []
        log 'Logs are deleted.'
    }


    /** Exception to String */
    String exceptionToString(Exception e) {
        if (!e) { return }
        StringWriter sw = new StringWriter()
        PrintWriter pw = new PrintWriter(sw)
        e.printStackTrace(pw)
        return sw.toString()
    }


    /** Log Exception */
    void logException(Exception e, String color = '') {
        if (!e) { return }
        printText(exceptionToString(e), color, 'info')
    }


    /** Set log delay */
    void setLogDelay(int seconds) {
        logDelay = seconds
        log "FW-Logger: new log delay is $seconds seconds"
    }

}

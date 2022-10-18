package above.report

import above.ConfigReader
import all.DateTools
import all.VariableStorage

/**
 *      Web Tool Monitor Data Preparation
 *      - for https://qatools.dev.clarkinc.biz/Monitor
 */

class WebToolMonitor {

    /** Update data slice */
    synchronized static void updateDataSlice() {

        Date day = new Date()
        List<Map> data = [[
                sliceDate: day.format("MM/dd/yyyy HH:mm:ss"),

                concepts: WebToolMonitorDb.getConcepts(day),

                servers:  WebToolMonitorDb.getDevOpsServers(day),

                parallelTreads: ConfigReader.get('parallelTreads'),
                parallelTreadsMax: ConfigReader.get('parallelTreadsMax'),

                browserList: ConfigReader.get('browserList'),
                browserListLocal: ConfigReader.get('browserListLocal'),
                browserListForServerRunning: ConfigReader.get('browserListForServerRunning'),
                browserVersions: ConfigReader.get('browserVersions'),
                defaultBrowser: ConfigReader.get('defaultBrowser'),
                gridHub: ConfigReader.get('gridHub'),
                gridConsole: ConfigReader.get('gridConsole'),

                tests: WebToolMonitorDb.getTests(day),
                severalDays: getExecTime(30).reverse()
        ]]
        VariableStorage.setData('web-tool-monitor-data-slice', data, true)
    }


    /** Execution time report */
    synchronized static private List<Map> getExecTime(int forLastDaysCount) {

        List<Map> result = []
        Date day = new Date()
        for (i in 1..forLastDaysCount) {
            def pSec = getPhysicalTime(day)
            def teSec = WebToolMonitorDb.getDayTotalExecutionTimeSeconds(day, '')
            def tsSec = WebToolMonitorDb.getDayTotalExecutionTimeSeconds(day, 'AND stats_involved = 1')
            result << [
                    day: day.format('MM/dd/yyyy'),
                    physicalExecTimeSec: pSec,
                    physicalExecTimeHours: DateTools.formatSecondsToReadableTime(pSec),
                    totalExecTimeSec: teSec,
                    totalExecTimeHours: DateTools.formatSecondsToReadableTime(teSec),
                    totalStatsDoneExecTimeSec: tsSec,
                    totalStatsDoneExecTimeHours: DateTools.formatSecondsToReadableTime(tsSec),
                    testsCount: WebToolMonitorDb.getTestsCount(day),
                    testsCountSequentially: WebToolMonitorDb.getTestsCount(day, 'AND parallel = 1'),
                    testsCountParallel: WebToolMonitorDb.getTestsCount(day, 'AND parallel > 1')
            ]
            day = day.minus(1)
        }
        return result
    }


    /** Physical exec time for day */
    synchronized static private int getPhysicalTime(Date day) {

        def last = WebToolMonitorDb.getLastTestDateDone(day)
        def d2 = last ? last : null
        if (!d2) {
            return 0
        }

        def todaySec
        if (last.getDay() == day.getDay()) {
            todaySec = ((d2.getTime() - new Date(d2.format('MM/dd/yyyy 00:00:00')).getTime()) / 1000).intValue()
        } else {
            todaySec = 0
        }
        if (day < new Date('04/05/2021')) {
            return todaySec
        } else if (day < new Date('05/06/2021')) {
            return todaySec + 10800
        } else if (day < new Date('07/25/2021')) {
            return todaySec + 18000
        } else {
            return todaySec + 28800
        }
    }

}


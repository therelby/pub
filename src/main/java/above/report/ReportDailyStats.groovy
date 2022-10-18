package above.report

import above.Run
import all.Db
import all.DbTools

/**
 *      Statistics Processing
 */
class ReportDailyStats {

    static sql = new Db('qa')
    static testResult = [skipped: 0, blocked: 1, failed: 2, passed: 3, checks: 'test_result_id']

    static String selectNewRuns = """
            SELECT id, test_class FROM (
                SELECT 
                  id, test_class, 
                  rank() OVER(PARTITION BY date_report, test_class, environment, browser ORDER BY date_done DESC) AS rnk
                FROM (
                  SELECT 
                    CASE WHEN convert(time, date_done) >= '7:00' THEN dateadd(day, 1, convert(date, date_done)) ELSE convert(date, date_done) END AS date_report, 
                    id, date_done, test_class, environment, browser
                  FROM Stat_Test_Runs
                  WHERE
                    CASE WHEN convert(time, date_done) >= '7:00' THEN dateadd(day, 1, convert(date, date_done)) ELSE convert(date, date_done) END = '%day' 
                    AND date_done is not null 
                    AND date_done < '${new Date().format('yyyy-MM-dd')} 7:00' 
                    AND stats_reviewed = 0
                    ) a
                ) b
            WHERE rnk = 1
            ORDER BY id"""
    static String updateRunRecordToDone = "UPDATE Stat_Test_Runs SET stats_reviewed = 1, stats_involved = 1 WHERE id = %id;"
    static String updateRunRecordToDoneButNotInvolved = "UPDATE Stat_Test_Runs SET stats_reviewed = 1 WHERE id = %id;"


    /**
     * Database tables update:
     * -- Stat_Test_Runs as source
     * -- Stat_Testcases as target
     * @return
     */
    static update() {

        Run r = run()
        r.dbUsing('qa')

        // displaying queue
        int inQueue = r.dbSelect("SELECT count(id) cnt FROM ServerRuns_Queue WHERE [status] = 0")[0].cnt
        int inRun = r.dbSelect("SELECT count(id) cnt FROM ServerRuns_Queue WHERE [status] = 2")[0].cnt
        r.log ">> Still in the queue: ${inQueue}"
        r.log ">> Still executing:    ${inRun}"
        // stopping night queue
        if (inQueue) {
            r.dbUpdate("UPDATE ServerRuns_Queue SET [status] = 99 WHERE [status] = 0")
        }
        EmailTools.sendEmail("akudin@webstaurantstore.com", "akudin@webstaurantstore.com", 'Night Queue',
                "<html><body>Where in queue: $inQueue<br/>Where running: $inRun</body></html>")

        Integer lastId = 0
        Integer cnt = 0
        for (day in getDays()) {

            r.log "[${day.format('MM/dd/yyyy')}] Selecting run records..."
            def data = DbTools.selectAll(selectNewRuns.replace('%lastId', lastId.toString()).replace('%day', day.format('yyyy-MM-dd')), 'qa')
            if (!data) {
                continue
            }

            cnt++

            // updates queries
            String updateQueries = ''

            // rows for the day
            for (row in data) {

                lastId = row.id
                //row.test_info = Json.getData(row.test_info)

                // unit tests and service scripts
                if (row.test_class.startsWith('framework.') ||
                    row.test_class.startsWith('above.') ||
                    row.test_class.startsWith('local.')) {
                        updateQueries += updateRunRecordToDoneButNotInvolved.replace('%id', row.id.toString())
                } else {
                        updateQueries += updateRunRecordToDone.replace('%id', row.id.toString())
                }

            }

            // updating stats involved and ignored records
            if (updateQueries) {
                r.log "Updating next 1000 (loop: $cnt)"
                sql.updateQuery(updateQueries)
            }

            // making current set as reviewed
            if (lastId) {
                r.log "Updating as reviewed up to id = $lastId ..."
                sql.updateQuery("UPDATE Stat_Test_Runs SET stats_reviewed = 1 WHERE id < $lastId AND stats_reviewed <> 1;")
            }

        }

        r.log '>>> Parsing results details...'
        ReportTestResults.groupResults()

        r.log '>>> Updating Web Tool data...'
        WebToolWeekly.update2()
        WebToolMonitor.updateDataSlice()
        WebToolRunsQuality.updateQualityStats()

        r.log '>>> Runs Results Email notifications...'
        //TestResultsRunsPersonalReports.checkAndSend(new Date())

        r.log '>>> Test Results Reviews...'
        TestResultsAutoReview.checkAndCreateAutoReviews(new Date())

        r.log '>>> Deleting old stuff in db...'
        ReportCleaning.deleteOldDetails()

        r.log('Done', r.console_color_green)
        r.log '--'

        return true
    }


    private static List<Date> getDays() {

        Run r = run()
        List<Date> result = []

        def startDay = null
        def entryPoint = DbTools.selectAll("SELECT min(date_done) dt FROM Stat_Test_Runs WHERE stats_reviewed = 0", 'qa')
        if (!entryPoint || !entryPoint[0].dt) {
            r.log 'NO NEW RUNS DATA TO PROCESS'
            r.log 'Script stopped.'
            return
        } else {
            // date of last reviewed record in db
            startDay = entryPoint[0].dt
            if (startDay.format('HH').toInteger() >= 7) {
                // next day report
                startDay = startDay.plus(1)
            }
        }

        Long tomorrow = new Date(new Date().plus(1).format('MM/dd/yyyy 00:00:00')).getTime()
        while (startDay.getTime() < tomorrow) {
            result << startDay
            startDay = startDay.plus(1)
        }

        return result
    }

}

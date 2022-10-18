package above.report

import above.Run
import above.RunWeb
import all.Db
import all.DbTools
import all.Json

/**
 *      Test Result Details Handling
 */

class ReportTestResults {

    static sql = new Db('qa')

    static updateQuery = ''
    static updateCounter = 0

    static selectRuns = "SELECT top 100 id, test_info FROM [Stat_Test_Runs] WHERE id >= %maxId AND stats_involved = 1 ORDER BY id;"

    static insertReport = """
        DROP TABLE IF EXISTS #RunsInvolved, #ClassRunChecksAllTime, #MaxChecks;
        
        CREATE TABLE #RunsInvolved (
            test_class VARCHAR (500)
            , test_tfs_project VARCHAR (500)
            , test_author VARCHAR (20)
            , checks INT
            , passed INT
            , failed INT
            , reports INT
            , tfs_docs INT
            , environment VARCHAR (500)
            , browser VARCHAR (500)
        );

        CREATE TABLE #ClassRunChecksAllTime (
            test_class VARCHAR (500)
            , checks INT
        );

        CREATE TABLE #MaxChecks (
            test_class VARCHAR (500)
            , max_checks INT
        );
        
        INSERT INTO #RunsInvolved
        SELECT
            test_class
            , test_tfs_project
            , test_author
            , checks
            , passed
            , failed
            , reports
            , tfs_docs
            , environment
            , browser
        FROM [dbo].[Stat_Test_Runs] WITH (NOLOCK)
        WHERE
            date_done >= '%day1 07:00:00'
            AND date_done < '%reportDate 07:00:00'
            AND stats_involved = 1
        ;
        
        INSERT INTO #ClassRunChecksAllTime
        SELECT DISTINCT
            RI.test_class
            , R.checks
        FROM #RunsInvolved RI
        INNER JOIN [dbo].[Stat_Test_Runs] R WITH (NOLOCK)
            ON R.test_class = RI.test_class
            AND R.date_done < '%reportDate 07:00:00'
            AND R.stats_involved = 1
        ;
        
        INSERT INTO #MaxChecks
        SELECT
            test_class
            , MAX(checks)
        FROM #ClassRunChecksAllTime
        GROUP BY test_class
        ;
        
        INSERT INTO Stat_Result_Report
        SELECT result.*, tst.tests FROM (
            SELECT '%reportDate' as [date], f.prj as project, f.aut as author, sum(f.maxchecks) checks_max, sum(f.checks) checks, sum(f.passed) passed, sum(f.failed) failed, sum(f.reports) reports, sum(f.tdocs) tfs_docs
            FROM (
                SELECT 
                    run.test_tfs_project as prj, 
                    run.test_author as aut,
                    max(CASE WHEN mx.max_checks IS NOT NULL THEN mx.max_checks ELSE 0 END) as maxchecks,
                    sum(run.checks) checks,
                    sum(run.passed) passed,
                    sum(run.failed) failed,
                    sum(run.reports) reports,
                    sum(run.tfs_docs) tdocs
                FROM #RunsInvolved run
                LEFT JOIN #MaxChecks AS mx
                    ON run.test_class = mx.test_class
                GROUP BY run.test_tfs_project, run.test_author, run.test_class, run.environment, run.browser
            ) as f
            GROUP BY f.prj, f.aut) as result
        INNER JOIN (
              SELECT a.test_tfs_project, a.test_author, count(a.test_class) tests FROM (
                SELECT test_tfs_project, test_author, test_class FROM #RunsInvolved
                GROUP BY test_tfs_project, test_author, test_class) a
              GROUP BY test_tfs_project, test_author) as tst
          ON tst.test_author = result.author AND tst.test_tfs_project = result.project;
          
          DROP TABLE IF EXISTS #RunsInvolved, #ClassRunChecksAllTime, #MaxChecks;"""


    /** Grouping statistics details for each day */
    static groupResults() {

        Run r = run()

        r.log 'GROUPING BY DAYS'
        Date maxReportDate = new Date(DbTools.selectAll('SELECT max([date]) as dt from Stat_Result_Report;', 'qa')[0].dt.getTime())
        r.log "Last report date = $maxReportDate"
        Date today = new Date()
        if (maxReportDate < today) {
            while (maxReportDate + 1 < today) {
                maxReportDate += 1
                r.log "((())) Grouping and saving report for $maxReportDate ..."
                new Db('qa').updateQuery(insertReport
                        .replace('%reportDate', maxReportDate.format('yyyy-MM-dd'))
                        .replace('%day1', maxReportDate.minus(1).format('yyyy-MM-dd')))
            }
        }
        r.log '((())) Grouping is done.'
    }


    /** Parse JSON check() and report() results to DB table */
    static parseResults() {

        // preventing loose of data because of previous incorrect importing break
        def maxId = DbTools.selectAll('SELECT max(run_record_id) as mi FROM [Stat_Result_Details];', 'qa')[0].mi
        sql.updateQuery("DELETE FROM [Stat_Result_Details] WHERE run_record_id = $maxId;")

        while (true) {

            // adding data starting from not imported point
            run().log "maxId = $maxId"
            def records = DbTools.selectAll(selectRuns.replace('%maxId', maxId.toString()), 'qa')
            if (!records) {
                break
            }

            def recCount = records.size()
            def recDone = 0
            for (row in records) {

                recDone++
                if (recDone.toString().endsWith('00')) {
                    run().log ">>>>>>>>>> Records: $recDone/$recCount"
                }

                def data = Json.jsonToData(row.test_info)

                // already saved
                if (data.test.testRealtimeResults) {
                    continue
                }

                data.testcases.tests.each {

                    for (type in ['checks', 'reports']) {

                        def ty = 1
                        if (type == 'reports') {
                            ty = 0
                        }

                        it[type].each { ch ->

                            def res = 1
                            if (ty == 1) {
                                if (!ch.checkResult) {
                                    res = 0
                                }
                            }

                            def scr = ch.screenshot ?: 0
                            if (scr.toString().contains("/") || scr.toString().contains("\\")) {
                                scr = 0
                            }
                            String url = ch.url ?: ''

                            if (ty == 0 || (ty == 1 && res == 0)) {
                                sqlUpdateOptimizer("INSERT INTO Stat_Result_Details " +
                                        "(run_record_id, tfs_item_id, tfs_project, is_check, result, time, description, url, screenshot, details) " +
                                        "VALUES " +
                                        "(${-> row.id}, ${-> it.tcId}, '${data.test.testTfsProject}', ${ty}, ${res}, ${ch.time}, '${-> ch.msg.replace("'", "''")}', " +
                                        "'${url}', ${scr}, '${Json.getJson(ch).replace("'", "''")}');")
                            }
                        }
                    }
                }

                maxId = row.id + 1
            }
        }
        sqlUpdateOptimizer()

        // grouping results for daily reports
        groupResults()
    }


    /** Group update queries */
    static private void sqlUpdateOptimizer(String query = '') {
        if (query) {
            updateQuery += query
            updateCounter++
        }
        if ((!query && updateQuery) || updateCounter >= 500) {
            sql.updateQuery(updateQuery)
            updateQuery = ''
            updateCounter = 0
        }
    }

}

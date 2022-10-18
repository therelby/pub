package above.report

import above.azure.AzureDevOpsTestcase
import all.Db
import all.DbTools
import all.LoopedChain

/**
 * 		Daily Report Maker
 */
class ReportDailyMaker extends ReportDailyData {

    static Date day = new Date()
    static Integer dayChecks = 0
    static Integer dayPassed = 0
    static Integer dayFailed = 0
    static Integer dayBlocked = 0
    static Integer daySkipped = 0
    static Integer dayNightExecTimeSec = 0
    static List projects = []
    static selectRunsForDates = "SELECT * FROM [QA_Automation].[dbo].[Stat_Test_Runs] WHERE date_start >= '%d1' AND date_start < '%d2' AND stats_done = 1"
    static insertDailyReport = "INSERT INTO Stat_Reports_Daily (date_report, passed ,failed ,blocked, skipped, physical_exec_time_sec, checks) VALUES (" +
                               "'%day', %passed, %failed, %blocked, %skipped, %time, %checks)"


    /** Creating Daily Report for the day */
    static createReport(Date day) {

        return
        /**
         * Blocked forever
         */

        this.day = day
        dayChecks = 0
        dayPassed = 0
        dayFailed = 0
        dayBlocked = 0
        daySkipped = 0
        dayNightExecTimeSec = 0

        def body = new ReportEmailTemplate()
        body.addTitle('QA Automation Report', 'font-size: 24pt; color: %green%')
        body.addTitle(day.format('EEEE, MM/dd/yyyy') + ' 7:00 am', 'font-size: 12pt')
        body.addSpace()
        body.addTitle("""<p>Hello All,</p><p>QA Automation daily report and issue handling stuff are moved to the QA Automation Tools website.</p><p>Please review: <a href="https://qatools.dev.clarkinc.biz/">qatools.dev.clarkinc.biz</a></p><p>Direct links:</p><ol><li><a href="https://qatools.dev.clarkinc.biz/Report">Automation Team Daily Reports</a></li><li><a href="https://qatools.dev.clarkinc.biz/Issues">Automation Team Testing Results</a></li></ol><p>Thank you,</p><p>Automation Team</p>""", 'font-size: 14pt;')

        /*
        // adding totals
        ReportEmailTemplateTable table1 = getTotalTable()
        ReportEmailTemplateTable table2 = getScriptTable()
        body.addColumns('49%|1%|50%')
        body.addToColumn(1, table1)
        body.addToColumn(3, table2)
        body.addSpace()

        body.addTitle('Total Test Case Execution Report - Project Specific', 'font-size: 14pt')
        def lc = new LoopedChain([1, 3, 5])
        List<ReportEmailTemplateTable> tfsProjectTables = getTfsProjectTables()
        for (table in tfsProjectTables) {
            def idx = lc.getNext()
            if (idx == 1) {
                body.addColumns('30%|1%|30%|1%|31%')
            }
            body.addToColumn(idx, table)
        }
        body.addSpace()

        // saving early for daily time graph
        new Db('qa').updateQuery(insertDailyReport.replace('%day', day.format('yyyy-MM-dd'))
                .replace('%checks', dayChecks.toString())
                .replace('%passed', dayPassed.toString())
                .replace('%failed', dayFailed.toString())
                .replace('%blocked', dayBlocked.toString())
                .replace('%skipped', daySkipped.toString())
                .replace('%time', dayNightExecTimeSec.toString()))

        // adding graphs
        body.addTitle('<img src="' + chartAutomated(day, getTotalAutomated(day)[1]) + '" width="100%" />')
        chartMonth(day)
        body.addTitle('<img src="' + pics[pics.size()-2] + '" width="100%" />')
        body.addTitle('<img src="' + pics.last() + '" width="100%" />')
        body.addSpace()

        // adding by authors
        body.addTitle('Total Test Case Execution Report - Author Specific', 'font-size: 14pt')
        body.addSpace()
        lc = new LoopedChain([1, 3, 5])
        List<ReportEmailTemplateTable> authorTables = getAuthorTables()
        for (table in authorTables) {
            def idx = lc.getNext()
            if (idx == 1) {
                body.addColumns('30%|1%|30%|1%|31%')
            }
            body.addToColumn(idx, table)
        }
        body.addSpace()

        body.addTitle("""Checks - number of test points verified during the test like 'real automation testcases'
                        Passed - testcases with no product defects were found while successful running.
                        Failed - testcases with product defects were found while running.
                        Blocked - testing was not run because of blocking issues found like 'Sorry about that' error message.
                        Skipped - testing was not run because TFS testcases had not 'Active' or 'Passed' states.""",
                        'font-size: 10pt; color: %green%')
        body.addSpace()

        body.addTitle('Product Defects Found - By Projects', 'font-size: 14pt')
        body.addSpace()
        addProductDefects(body)
        */

        // emailing
        ReportSender.emailReport("Automation Report ${day.format('MM/dd/yyyy')}", body.getHtml()) //, ['akudin'])

        /*
        // deleting graph files
        run().log 'Deleting files:'
        pics.each {
            run().log "-- $it"
            new File(it).delete()
        }
        pics = []
        */
    }


    /** Add defects details list */
    static void addProductDefects(ReportEmailTemplate body) {
        for (prj in projects) {
            run().log "- - - defects - - - $prj"

            String q = """
                SELECT run.test_info AS info, tc.tfs_testcase_id AS tcId FROM [QA_Automation].[dbo].[Stat_Testcases] AS tc 
                   INNER JOIN [QA_Automation].[dbo].[Stat_Test_Runs] AS run
                      ON tc.run_record_id = run.id
                WHERE tc.run_date >= '${day.minus(1).format('yyyy-MM-dd')} 07:00:00'
                      AND tc.run_date < '${day.format('yyyy-MM-dd')} 07:00:00' 
                      AND tc.tfs_project = '${ -> prj}' AND tc.test_result_id = 2
                      AND run.stats_done = 1"""

            prj = prj.replace('%20', ' ')

            def tcs = DbTools.selectAll(q, 'qa')
            if (tcs) {
                body.addTitle("&nbsp;&nbsp;$prj", 'font-size: 14pt; background-color: #EEFFEE; height: 45px;')
                body.addSpace()
            }
            List tcDone = []
            for (tc in tcs) {
                def data = all.Json.getData(tc.info)
                String issues = ''
                String title = ''
                String title2 = ''
                String webDetails = null
                if (data) {
                    if (data.webData) {
                        webDetails = "<strong>Environment: ${data.webData.testEnv}, " +
                                     "Browser: ${data.webData.testBrowser.replaceFirst('remote', '')} " +
                                     "${data.webData.testBrowserVersion}</strong>"
                    }
                    title = "<strong>${data.test.testTitle}</strong> " // end space for formatting
                    title2 = "<strong>${data.test.testAuthor} | ${data.test.testClass}</strong> " // end space for formatting
                }
                for (test in data.testcases.tests) {
                    int id = test.tcId
                    if (tcDone.contains(id)) { continue } else {
                        tcDone << id
                    }
                    for (t in test.checks) {
                        if (!t.checkResult) {
                            if (issues) { issues += '<br />' }
                            issues += "<a href=\"${AzureDevOpsTestcase.getTfsUrl(id, prj)}\">${id}</a> - ${t.msg} " // end space for formatting
                        }
                    }
                }
                if (issues) {
                    def table = new ReportEmailTemplateTable('100%', title, 'font-size: 14pt')
                    if (webDetails) { table.addRow([webDetails]) }
                    table.addRow([title2])
                    table.addRow([issues])
                    body.addTitle(table)
                    body.addSpace()
                }
            }

        }
    }


    /** Author stats tables */
    static getAuthorTables() {
        def tables = []
        def authors = getColumnUniqueListForDay('test_author').sort()
        authors.each {
            tables << getTotalTable(it)
        }
        return tables
    }


    /** TFS project stats tables */
    static getTfsProjectTables() {
        def tables = []
        projects = getColumnUniqueListForDay('tfs_project').sort()
        projects.each {
            tables << getTotalTable('', it)
        }
        return tables
    }


    /** Script level stats table */
    static getScriptTable() {
        def table = new ReportEmailTemplateTable('45%|55%', 'Script Level Results',
                            'vertical-align: top; height: 70px; background-color: #EEFFEE; font-size: 14pt;')
        table.addRow(getSumForColumn('checks', 'Total Test Points'))
        table.addRow(['Total Scripts Executed', getScriptsForDate()])
        table.addRow(['Total Script Errors', getScriptErrorsForDate()])
        Integer execTime = getScriptsExecTimeForDate(day)
        table.addRow(['Physical Time From 0:00 ', getPhysicalExecutionTimeForDay()])
        table.addRow(['Parallel Time', formatSecondsToInterval(execTime)])
        table.addRow(['Difference to Yesterday', getTimeDiff(getScriptsExecTimeForDate(day-1), execTime)])
        return table
    }


    /** Total stats table */
    static getTotalTable(String author = '', String project = '') {
        def table
        if (author) {
            table = new ReportEmailTemplateTable('45%|55%', author,
                                                 'font-weight: bold;', false)
        } else if (project) {
            table = new ReportEmailTemplateTable('45%|55%', project.replace('%20', ' '),
                                                 'font-weight: bold; height: 50px;', false)
        } else {
            table = new ReportEmailTemplateTable('45%|55%', 'Total Test Case Execution - All Projects',
                                                 'vertical-align: top; height: 70px; background-color: #EEFFEE; font-size: 14pt;')
        }
        table.addRow(getTotalForResult('Checks', author, project, 'checks', 'SUM'))
        table.addRow(getTotalForResult('Passed', author, project))
        table.addRow(getTotalForResult('Failed', author, project))
        table.addRow(getTotalForResult('Blocked', author, project))
        table.addRow(getTotalForResult('Skipped', author, project))
        table.addRow(getTotalForColumn('tfs_testcase_id', 'Total Ran', author, project))
        table.addRow(getTotalAutomated(day, author, project))
        return table
    }


    /** Total for specific test result */
    private static getTotalForResult(String status, String author = '', String project = '', String column = '', String func = 'COUNT') {
        def and = ''
        if (author) {
            and += " AND test_author='$author'"
        }
        if (project) {
            and += " AND tfs_project='$project'"
        }
        def data = ReportData.getForDates(day, day,
                            ' AND test_result_id = ' + ReportDailyStats.testResult[status.toLowerCase()] + and,
                            true, column, func)
        def cnt = 0
        if (data) { cnt = data[0].cnt }

        // save
        if (!author && !project) {
            if (status == 'Checks') {
                dayChecks = cnt
            } else if (status == 'Passed') {
                dayPassed = cnt
            } else if (status == 'Failed') {
                dayFailed = cnt
            } else if (status == 'Blocked') {
                dayBlocked = cnt
            } else if (status == 'Skipped') {
                daySkipped = cnt
            }
        }

        return [status, cnt]
    }


    /** Total for specific testcases stats table column */
    private static getTotalForColumn(String column, String title, String author = '', String project = '', Boolean count = true) {
        def and = ''
        if (author) {
            and += " AND test_author='$author'"
        }
        if (project) {
            and += " AND tfs_project='$project'"
        }
        def data = ReportData.getForDates(day, day, and, count, column)
        def cnt = 0
        if (data) { cnt = data[0].cnt }
        return [title, cnt]
    }


    /** Total Automated testcases number until the date */
    private static getTotalAutomated(Date day, String author = '', String project = '') {
        def and = ''
        if (author) {
            and += " AND test_author='$author'"
        }
        if (project) {
            and += " AND tfs_project='$project'"
        }
        String query = "SELECT COUNT(DISTINCT tfs_testcase_id) AS cnt FROM Stat_Testcases WHERE removed = 0 AND run_date <= '" +
                day.format('yyyy-MM-dd 7:00:00') + "'" + and
        return ['Total Automated', all.DbTools.selectAll(query, 'qa')[0].cnt]
    }


    private static getSumForColumn(String column, String title) {
        def data = ReportData.getForDates(day, day, '', true, column, 'SUM')
        def cnt = 0
        if (data) { cnt = data[0].cnt }
        return [title, cnt]
    }


    private static getScriptsForDate() {
        return DbTools.selectAll(selectRunsForDates.replace('%d1', day.minus(1).format('yyyy-MM-dd 07:00:00'))
                                    .replace('%d2', day.format('yyyy-MM-dd 07:00:00'))
                                    .replace('SELECT *', 'SELECT COUNT(id) AS cnt'), 'qa')[0].cnt
    }

    private static getScriptsExecTimeForDate(Date day) {
        if (day == this.day && dayPassed + dayFailed + daySkipped + dayBlocked == 0) {
            return 0
        } else {
            return DbTools.selectAll(selectRunsForDates.replace('%d1', day.minus(1).format('yyyy-MM-dd 07:00:00'))
                    .replace('%d2', day.format('yyyy-MM-dd 07:00:00'))
                    .replace('SELECT *', 'SELECT SUM(exec_time_sec) AS cnt'), 'qa')[0].cnt
        }
    }

    private static getScriptErrorsForDate() {
        return DbTools.selectAll((selectRunsForDates.replace('%d1', day.minus(1).format('yyyy-MM-dd 07:00:00'))
                                   .replace('%d2', day.format('yyyy-MM-dd 07:00:00'))
                                   .replace('SELECT *', 'SELECT COUNT(id) AS cnt')) +
                                   ' AND (test_error IS NULL OR test_error = 1)', 'qa')[0].cnt
    }

    static getColumnUniqueListForDay(String column) {
        return DbTools.selectAll(ReportData.selectTestcases.replace('%d1', day.minus(1).format('yyyy-MM-dd'))
                                   .replace('%d2', day.format('yyyy-MM-dd'))
                                   .replace('SELECT *', "SELECT $column"), 'qa').collect { it[column] }*.trim().unique()
    }


    private static String getPhysicalExecutionTimeForDay() {

        def lastRunTime
        try {
            lastRunTime = DbTools.selectAll(selectRunsForDates.replace('%d1', day.format('yyyy-MM-dd 00:00:00'))
                    .replace('%d2', day.format('yyyy-MM-dd 07:00:00'))
                    .replace('SELECT *', "SELECT TOP 1 date_done") + ' AND date_done IS NOT NULL',
                    'qa')[0].date_done
        } catch (ignored) {
            lastRunTime = new Date(day.format('MM/dd/yyyy 06:59:00'))
        }

        if (!lastRunTime) {
            dayNightExecTimeSec = 0
            return '0:00:00'
        } else {
            dayNightExecTimeSec = ((lastRunTime.getTime() - new java.util.Date(day.format('MM/dd/yyyy 00:00:00')).getTime()) / 1000).intValue()
            return formatSecondsToInterval(dayNightExecTimeSec)
        }
    }

}

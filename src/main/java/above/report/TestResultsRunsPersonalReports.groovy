package above.report

import above.ConfigReader
import above.Run
import above.RunWeb
import all.ClarkInc
import all.DateTools
import all.Db
import all.DbTools
import all.Json

/**
 *          Checking Test Runs and Sending Found Issue Notifications to Test Authors
 */

class TestResultsRunsPersonalReports {

    static Date day
    static String webToolUrl = ConfigReader.get('webtoolUrl')
    static String msg
    //static List<String> leftAuthors = ConfigReader.get('leftAuthors').split(',')


    /** Entry point */
    synchronized static checkAndSend(Date day) {
        this.day = day
        List authors = DbTools.selectAll("SELECT DISTINCT test_author author FROM Stat_Test_Runs WHERE date_done >= '${day.minus(10).format('yyyy-MM-dd 7:00')}' ORDER BY author", 'qa')
        authors.each {
            findAndReport(it.author)
        }
    }


    /** Report by authors */
    synchronized static findAndReport(String author) {

        if (!ClarkInc.isWorkday(day)) {
            return
        }

        Run r = run()
        r.log '=='
        r.log author

//        if (leftAuthors.contains(author)) {
//            r.log 'The author has left. Skipped'
//            return
//        }

        // checking report records
        List data = DbTools.selectAll("SELECT id, done FROM Report_Authors WHERE date_report = '${day.format('yyyy-MM-dd')}' AND author = '$author'", 'qa')
        int reportRecordId
        if (!data) {
            reportRecordId = new Db('qa').updateQuery("INSERT INTO Report_Authors (date_report, author) VALUES ('${day.format('yyyy-MM-dd')}', '$author')")
        } else {
            if (data[0].done) {
                r.log 'ALREADY DONE'
                return
            }
            r.log '--'
            reportRecordId = data[0].id
        }

        // running through the author's tests
        int id = 0
        String q = """
            SELECT TOP(1) * FROM Stat_Test_Runs 
            WHERE date_done >= '${day.minus(1).format('yyyy-MM-dd 7:00')}' AND date_done < '${day.format('yyyy-MM-dd 7:00')}' 
                AND test_author = '$author' AND id > %id AND stats_involved = 1
            ORDER BY id"""

        msg = ''
        while (true) {

            List res = DbTools.selectAll(q.replace('%id', id.toString()), 'qa')
            if (!res) { break }

            id = res[0]['id']
            r.log "$id - ${res[0]['test_class']}"

            def info = Json.getData(res[0]['test_info'])

            // no checks no reports - new approach
            if (info.run.runPbiBased) {
                if (res[0].checks == 0 && res[0].reports == 0) {
                    add "Wrong PBI based approach test run because of 0 checks and 0 reports", res[0]
                }
            }

            // Java error
            if (info.script_error) {
                add "Java/Groovy error", res[0]
            }

            // reports - max fails blocker
            if (info.testcases.tests) {
                info.testcases.tests.each {
                    if (it.reports) {
                        if (it.reports.last().msg.startsWith('Framework:')) {
                            add it.reports.last().msg, res[0]
                        }
                    }
                }
            }

            // long time executing
            if (res[0].exec_time_sec > 86400) {
                add "Executing time above 1 hour - ${DateTools.formatSecondsToReadableTime(res[0].exec_time_sec)}", res[0]
            }

            // Java errors
            if (res[0].test_error) {
                add "Java/Groovy Error", res[0], 'errors'
            }

        }

        if (r.isServerRun() && msg) {
            EmailTools.sendEmail("$author@webstaurantstore.com", "$author@webstaurantstore.com", "$author - Personal Issues Report ${day.format('MM/dd/yyyy')}", "<html><body>$msg</body></html>")
            EmailTools.sendEmail("$author@webstaurantstore.com", "akudin@webstaurantstore.com", "$author - Personal Issues Report ${day.format('MM/dd/yyyy')}", "<html><body>$msg</body></html>")
        } else {
            r.log msg
        }

        // making report record marked done
        new Db('qa').updateQuery("UPDATE Report_Authors SET done = 1 WHERE id = $reportRecordId")
    }


    /** Add test to message body */
    synchronized static private add(String text, data, String filter = '') {
        msg += "<p style=\"font-size:13pt\"><b>${data.test_class}</b><br />"
        msg += "$text<br />"
        msg += "<a href=\"$webToolUrl/Run?id=${data.run_id}\">$webToolUrl/Run?id=${data.run_id}${ filter ? "&filter=$filter" : "" }</a></p>"
    }

}

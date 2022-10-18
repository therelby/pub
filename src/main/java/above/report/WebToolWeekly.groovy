package above.report

import above.Run
import all.DbTools
import all.VariableStorage
import java.time.LocalDate
import java.time.temporal.WeekFields

/**
 *      Web Tool Weekly Report Generator
 */

class WebToolWeekly {

    static String vsName = 'webtool-report-covering'
    static String style = 'background-color: #eeffee;'

    static String query = """
        SELECT m.test_author, sum(m.mch) max_checks FROM (
            SELECT a.test_author, a.test_class, max(a.checks) mch FROM (
              SELECT 
                CASE WHEN convert(time, date_start) >= '7:00' 
                     THEN dateadd(day, 1, convert(date, date_start)) 
                     ELSE convert(date, date_start) 
                END as real_date, test_author, environment, browser, test_class, checks 
              FROM Stat_Test_Runs
              WHERE stats_involved = 1) a
            WHERE a.real_date <= '%date'
            GROUP BY a.environment, a.browser, a.test_author, a.test_class) m
        GROUP BY m.test_author
        ORDER BY m.test_author;"""

    static String checksQuery = """
        SELECT f.total_checks, u.Username, u.UserId FROM (
          SELECT m.author_id, sum(m.mch) total_checks FROM (
            SELECT a.author_id, a.test_class, max(a.checks) mch FROM (
              SELECT author_id, environment, browser, test_class, checks 
              FROM Stat_Test_Runs
              WHERE date_done < '%nextDate 7:00' and stats_involved = 1) a
            GROUP BY a.environment, a.browser, a.author_id, a.test_class) m
          GROUP BY m.author_id
        ) f
        INNER JOIN [QA_Automation].[user].[User] u
          ON f.author_id = u.UserId
        ORDER BY u.Username;"""


    /** Update Weekly report to actual data */
    synchronized static void update2() {
        Run r = run()
        List<Date> days = getWeekEndDatesList().reverse()
        for (day in days) {

            List<Map> data = r.dbSelect(
                checksQuery.replace('%nextDate', day.plus(1).format('yyyy-MM-dd')), 'qa')

            if (data) {

                String monday = day.minus(6).format('yyyy-MM-dd')
                // deleting old record for all the authors
                r.dbExecute("DELETE FROM statistic.WeeklyChecks WHERE monday = '$monday'", 'qa')

                String insertQuery = ''
                for (row in data) {
                    // adding new data for current author
                    insertQuery += "INSERT INTO statistic.WeeklyChecks " +
                            "(monday, author_id, total_checks) VALUES " +
                            "('${monday}', ${row.UserId}, ${row.total_checks});"
                }
                r.dbInsert(insertQuery, 'qa')
                r.log '=='
            }
        }
    }

    /** Old */
    synchronized static void update() {

        // collecting stats from database
        def rows = []
        for (day in getWeekEndDatesList()) {
            def data = DbTools.selectAll(query.replace('%date', day.format('yyyy-MM-dd')), 'qa')
            if (data) {
                rows << [
                    year: day.format('yyyy').toInteger(),
                    week: getWeekNumber(day),
                    monday: getLastMonday(day).format('MM/dd/yyyy'),
                    stats: data
                ]
            }
        }

        // collecting all authors
        List<String> authors = []
        rows.each { row ->
            row.stats.each {
                if (!authors.contains(it.test_author)) {
                    authors << it.test_author
                }
            }
        }
        authors = authors.sort()

        // making the data consistent
        List report = []
        rows.eachWithIndex { row, idx ->
            report << [authors: [:]]
            report[report.size()-1].year = row.year
            report[report.size()-1].week = row.week
            report[report.size()-1].monday = row.monday
            authors.each { aut ->
                report[report.size()-1]['authors'][aut] = [checks: 0, plus: 0, style: '']
                row.stats.eachWithIndex { it, i ->
                    if (it.test_author == aut) {
                        report[report.size() - 1]['authors'][aut].checks = it.max_checks
                        if (idx < rows.size() - 1) {
                            def ii = rows[idx + 1].stats.findIndexOf { it.test_author == aut }
                            if (ii > -1) {
                                report[report.size() - 1]['authors'][aut].plus = it.max_checks - rows[idx + 1].stats[ii].max_checks
                            } else {
                                report[report.size() - 1]['authors'][aut].plus = it.max_checks
                            }
                        } else {
                            report[report.size() - 1]['authors'][aut].plus = it.max_checks
                        }
                    }
                }
            }
        }

        // calculating totals and marking max pluses
        report.eachWithIndex{ week, int idx ->
            int max = 0
            int total = 0
            int plus = 0
            week.authors.keySet().each { aut ->
                if (report[idx]['authors'][aut].plus > max) {
                    max = report[idx]['authors'][aut].plus
                }
                total += report[idx]['authors'][aut].checks
                plus += report[idx]['authors'][aut].plus
            }
            report[idx].plus = plus
            report[idx].total = total
            if (max) {
                week.authors.keySet().each { aut ->
                    if (report[idx]['authors'][aut].plus == max) {
                        report[idx]['authors'][aut].style = style
                    }
                }
            }
        }

        VariableStorage.setData(vsName, report, true)

        // updating details
        //WebToolWeeklyDetails.update()
    }


    /** Create list of max weeks dates */
    synchronized static List<Date> getWeekEndDatesList() {

        List<Date> result = []
        def day = new Date('10/12/2020')
        def today = new Date()
        while (day <= today) {
            result << day.plus(6)
            day = day.plus(7)
        }
        return result.reverse()
    }

    /** Get week number */
    synchronized static int getWeekNumber(Date day) {
        LocalDate date = LocalDate.of(day.format('yyyy').toInteger(), day.format('MM').toInteger(), day.format('dd').toInteger());
        return date.get(WeekFields.of(new Locale('ru')).weekOfYear());
    }


    /** Get last Monday */
    synchronized static Date getLastMonday(Date day) {
        while (day.format('EEE') != 'Mon') {
            day = day.minus(1)
        }
        return day
    }

}

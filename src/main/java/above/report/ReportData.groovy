package above.report

import all.DbTools

/**
 *      Report Data Sources
 */

class ReportData {


    static selectTestcases = "SELECT * FROM Stat_Testcases WHERE run_date >= '%d1 7:00:00' AND run_date < '%d2 7:00:00'"


    /** Get data for specific month */
    static getMonth(Integer monthNumber, Integer yearNumber, String filters = '', Boolean count = false, String column) {
        Date d1 = new Date("$monthNumber/01/$yearNumber")
        Date d2 = d1
        while (d2.format('MM').toInteger() == monthNumber) {
            d2 += 1
        }
        d2 -=1
        return getForDates(d1, d2, filters, count, column)
    }


    /** Get data for specific week */
    static getWeek(Date mondayOfTheWeek, String filters = '', Boolean count = false, String column) {
        return getForDates(mondayOfTheWeek, mondayOfTheWeek + 7, filters, count, column)
    }


    /** Get data for a date range */
    static getForDates(Date d1, Date d2, String filters = '', Boolean count = false, String column = '', String func = 'COUNT') {
        d1 = d1 - 1
        def query = selectTestcases.replace('%d1', d1.format('yyyy-MM-dd')).replace('%d2',  d2.format('yyyy-MM-dd')) + filters
        if (count) {
            if (!column) {
                query = query.replace('SELECT *', "SELECT $func(DISTINCT tfs_testcase_id) AS cnt")
            } else {
                if (func == 'COUNT') {
                    query = query.replace('SELECT *', "SELECT $func(DISTINCT $column) AS cnt")
                }
                if (func == 'SUM') {
                    query = query.replace('SELECT *', "SELECT $func($column) AS cnt")
                }
            }
        }
        return DbTools.selectAll(query, 'qa')
    }

}

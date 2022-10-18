package above.report

import above.Run

/**
 *      Web Tool Runs Quality Data Preparation
 */

class WebToolRunsQuality {

    static String selectForPeriod = """
            INSERT INTO Stat_Quality 
            SELECT '%day' day, m.test_author, CONVERT(INT, sum(m.ok)/10.0 / (sum(m.er)/10.0 + sum(m.ok)/10.0) * 1000/10.0) ok, sum(m.ok) good, sum(m.er) weak FROM (
                SELECT a.test_author, a.test_class, sum(a.issues) er, sum(a.okays) ok FROM (
                    SELECT 
                    CASE WHEN convert(time, date_start) >= '7:00' 
                            THEN dateadd(day, 1, convert(date, date_start)) 
                            ELSE convert(date, date_start) 
                    END as real_date, test_author, test_class, environment, browser, 
                CASE WHEN test_error <> 0 --OR (checks = 0 AND reports = 0)
                    THEN 1
                    ELSE 0
                END as issues,
                CASE WHEN test_error = 0 --AND (checks <> 0 OR reports <> 0)
                    THEN 1
                    ELSE 0
                END as okays
                    FROM Stat_Test_Runs
                    WHERE stats_involved = 1) a
                WHERE a.real_date = convert(date, '%day')
                GROUP BY a.test_author, a.test_class, a.environment, a.browser) m
            GROUP BY m.test_author
            ORDER BY ok DESC, test_author;"""


    static updateQualityStats() {

        Run r = run()
        r.dbUsing('qa')

        Date now = new Date(new Date().format('MM/dd/yyyy 0:00:00'))

        // updating last 7 days
        r.dbExecute("DELETE FROM Stat_Quality WHERE day > '${now.minus(7).format('yyyy-MM-dd')}'")

        // finding max day in data
        List max = r.dbSelect("SELECT max(day) day FROM Stat_Quality")
        Date day = max[0].day
        if (!day) {
            day = new Date('12/31/2020')
        }
        r.log day

        // writing data
        while (day <= now) {
            day++
            r.dbInsert(selectForPeriod.replace('%day', day.format('yyyy-MM-dd')))
        }

    }

}

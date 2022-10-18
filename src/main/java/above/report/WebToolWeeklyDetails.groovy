package above.report

import above.RunWeb
import all.DbTools
import all.LomTools
import all.VariableStorage

/**
 *      Web Tool Weekly Details Slice Maker
 */

class WebToolWeeklyDetails {

    static sliceNameInVariableStorage = 'WebTool-weekly-details-'

    static selectDetails = """
        SELECT * FROM (
          SELECT run_id, date_done, CASE WHEN convert(time, date_done) >= '7:00' THEN dateadd(day, 1, convert(date, date_done)) ELSE convert(date, date_done) END report_date, test_title, test_class, concept, environment, browser, checks,
              row_number() OVER(PARTITION BY CASE WHEN convert(time, date_done) >= '7:00' THEN dateadd(day, 1, convert(date, date_done)) ELSE convert(date, date_done) END, test_class ORDER BY checks DESC) AS rnum
          FROM Stat_Test_Runs
          WHERE stats_involved = 1 AND test_author = '%author' AND checks <> 0
        ) w2
        --WHERE w2.rnum = 1
        ORDER BY w2.report_date, w2.test_class;
    """

    static selectAuthors = 'SELECT DISTINCT test_author FROM Stat_Test_Runs ORDER BY test_author;'

    static update() {

        RunWeb r = run()

        r.log '\nMAKING: Web Tool Weekly Details...\n'

        // getting authors
        def authors = DbTools.selectAll(selectAuthors, 'qa').collect {it.test_author}
        r.log authors

        // creating
        List<Date> weeks = mondays()
        for (author in authors) {
            r.log "\n$author"

            List history = DbTools.selectAll(selectDetails.replace('%author', author), 'qa')
            r.log "Checks history size: ${history.size()}"

            // fix Smoke Cloud classes
            for (int i = 0; i < history.size(); i++) {
                if (history[i].test_class.contains('(')) {
                    history[i].test_class = history[i].test_class.split('\\(')[0]
                }
            }

            // processing classes stats
            List classes = []
            for (day in weeks) {
                Date day2 = day.plus(6)
                history.each { row ->
                    if (row.report_date >= day && row.report_date <= day2) {
                        List olderMms = classes.findAll {
                            it.report_date < row.report_date && it.test_class == row.test_class &&
                                    it.browser == row.browser && it.environment == row.environment
                        }
                        if (olderMms) {
                            int oldMax = LomTools.maxInt(olderMms, 'checks', [test_class: row.test_class])
                            if (row.checks > oldMax) {
                                int idx = classes.findIndexOf {
                                    it.week_of == day && it.test_class == row.test_class &&
                                     it.browser == row.browser && it.environment == row.environment
                                }
                                if (idx > -1) {
                                    classes[idx] = [
                                            week_of    : day,
                                            run_id     : row.run_id,
                                            test_title : row.test_title,
                                            test_class : row.test_class,
                                            concept    : row.concept,
                                            browser    : row.browser,
                                            environment: row.environment,
                                            report_date: row.report_date,
                                            checks     : row.checks,
                                            plus       : row.checks - oldMax
                                    ]
                                } else {
                                    classes << [
                                            week_of    : day,
                                            run_id     : row.run_id,
                                            test_title : row.test_title,
                                            test_class : row.test_class,
                                            concept    : row.concept,
                                            browser    : row.browser,
                                            environment: row.environment,
                                            report_date: row.report_date,
                                            checks     : row.checks,
                                            plus       : row.checks - oldMax
                                    ]
                                }
                            }
                        } else {
                            classes << [
                                    week_of    : day,
                                    run_id     : row.run_id,
                                    test_title : row.test_title,
                                    test_class : row.test_class,
                                    concept    : row.concept,
                                    browser    : row.browser,
                                    environment: row.environment,
                                    report_date: row.report_date,
                                    checks     : row.checks,
                                    plus       : row.checks
                            ]
                        }
                    }
                }
            }

            // saving result
            //r.log classes
            VariableStorage.setData(sliceNameInVariableStorage + author, classes, true)
            //break
        }
    }


    /** Mondays list */
    static List<Date> mondays() {
        def start = new Date('10/12/2020')
        def today = new Date()
        List<Date> result = []
        while (start < today) {
            result << start
            start = start.plus(7)
        }
        return result
    }

}

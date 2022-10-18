package above.exec.tools

import above.Run
import above.report.ReportTestResults
import above.report.WebToolRunsQuality

class QualityRefresh extends Run {

    static void main(String[] args) {
        new QualityRefresh().testExecute()
    }

    void test() {

        setup([ author: 'akudin', title: 'Quality Refresh', logLevel: 'info' ])

        dbUsing('qa')

        /** (!) SETUP THE START DAY BEFORE RUNNING */
        String day1 = '2021-12-24'
        String day2 = '2021-12-13' // new Date().format('yyyy-MM-dd')


        /** THIS SECTION CHANGES QUALITY STATS FOREVER */
        dbExecute("UPDATE Stat_Test_Runs SET test_error = 0 WHERE date_done >= '$day1 07:00' AND date_done <= '$day2' AND stats_involved = 1 AND test_error <> 0")

        // cleaning quality stats
        dbExecute("DELETE FROM Stat_Quality WHERE day >= '$day1'")

        // cleaning daily stats
        dbExecute("DELETE FROM Stat_Result_Report WHERE [date] >= '$day1'")

        // updating quality stats
        WebToolRunsQuality.updateQualityStats()

        // generating daily stats
        ReportTestResults.groupResults()
    }

}

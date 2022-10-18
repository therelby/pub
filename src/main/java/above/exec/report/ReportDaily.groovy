package above.exec.report

import above.Run
import above.report.ReportDailyStats

/**
 *      Daily Statistics Processing
 */
class ReportDaily extends Run {

    static void main(String[] args) {
        new ReportDaily().testExecute()
    }

    // Test
    def test() {

        setup([ author: 'akudin', title: 'Daily Statistics Processing', logLevel: 'info' ])

        // Processing ready statistics data
        ReportDailyStats.update()

        // framework logs deleting
        cleanLog()

    }

}

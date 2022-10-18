package above.report

import above.Run

/**
 *      Report Screenshots Handling
 */

class ReportCleaning {

    private static String top = '100'

    private static def queries = [
            [
                    t: 'Deleting 7+ days old console logs...',
                    q1: "SELECT TOP($top) id FROM Stat_Test_Runs WHERE ((stats_reviewed = 1 and stats_involved = 0) or date_start < DATEADD(day, -6, GETDATE())) AND test_log <> '[]';",
                    q2: "UPDATE Stat_Test_Runs SET test_log = '[]' WHERE id = %id;"
            ],
            [
                    t: 'Deleting 7+ days old screenshots...',
                    q1: "SELECT TOP($top) id FROM Stat_Images WHERE date_add < DATEADD(day, -6, GETDATE());",
                    q2: 'DELETE FROM Stat_Images WHERE id = %id;'
            ],
            [
                    t: 'Deleting 60+ days old report() details...',
                    q1: "select top $top id from Stat_Result_Details where time < ${new Date().minus(60).getTime()}",
                    q2: "DELETE FROM [QA_Automation].[dbo].[Stat_Result_Details] WHERE id = %id;"
            ]
    ]


    synchronized static void deleteOldDetails() {

        Run r = run()
        r.dbUsing('qa')
        queries.each {
            r.log it.t
            int cnt = 0
            while (true) {
                List ids = r.dbSelect(it.q1, 'qa').collect { db -> db.id }
                if (!ids) { break }
                cnt += ids.size()
                r.log "RECORDS TOTAL: $cnt"
                String q = ''
                ids.each { id ->
                    q += it.q2.replace('%id', id.toString())
                }
                r.dbExecute(q)
            }
        }

    }

}

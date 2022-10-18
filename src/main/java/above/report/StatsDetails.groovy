package above.report

import above.Run
import all.Json

class StatsDetails {

    static String q = ''

    static void restoreDetails() {

        Run r = run()
        r.dbUsing('qa')

        int minId = 375998

        while (true) {
            r.cleanLog()
            List data = r.dbSelect("""
                SELECT TOP 1 id, test_info FROM Stat_Test_Runs 
                WHERE id > $minId AND test_info <> '[]' 
                    AND (failed > 0 OR reports > 0) 
                    AND stats_involved = 1 
                ORDER BY id""")
            if (!data) { break }

            boolean start = true
            for (row in data) {
                def info = Json.getData(row.test_info)
                if (start) {
                    r.log info.run.runStartTime
                    start = false
                }
                for (doc in info.testcases.tests) {
                    for (ch in doc.checks) {
                        if (!ch.checkResult) {
                            addQ query(ch, 'checks', info, doc.tcId)
                        }
                    }
                    for (re in doc.reports) {
                        addQ query(re, 'reports', info, doc.tcId)
                    }
                }
                minId = row.id
            }
            if (q) {
                r.dbExecute(q)
            }
        }

    }


    static addQ(String query) {
        if (q.length() > 10000) {
            run().dbExecute(q)
            q = ''
        }
        q += query
    }



    static String query(Map item, String listName, info, int tcId) {

        String checkId = item?.checkId
        if (!checkId) { checkId = '' } else { checkId = checkId.replace("'", "''") }

        String url = item.url
        if (!url) { url = '' }

        return """INSERT INTO Stat_Result_Details 
            (run_record_id, 
             tfs_item_id, 
             tfs_project, 
             is_check, 
             result, 
             time, 
             description, 
             url, 
             screenshot, 
             details,
             smoke_test_id,
             check_id,
             uuid) 
            VALUES 
            (${info.run.runRecordId ? info.run.runRecordId : 0}, 
             ${tcId}, 
             '${info.test.testTfsProject}', 
             ${listName.replace('checks', '1').replace('reports', '0')}, 
             ${item.checkResult.toString().replace('true', '1').replace('false', '0').replace('null', '0')}, 
             ${item.time}, 
             '${item.msg.replace("'", "''")}', 
             '${url.replace("'", "''")}', 
             0, 
             '${Json.getJson(item).replace("'", "''").replaceAll("\\p{Cntrl}", "")}',
             0,
             '${checkId ? checkId : ''}',
             '${item.uuid ? item.uuid : ''}');"""
    }

}

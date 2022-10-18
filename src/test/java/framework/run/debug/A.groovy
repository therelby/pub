package framework.run.debug

import above.RunWeb

import java.time.Instant

class A extends RunWeb {

    static void main(String[] args) {
        new A().testExecute([
                //browser: 'safari',
                remoteBrowser: true,
                //browserVersionOffset: -1,
                //environment: 'test',
                runType: 'Regular'
        ])
    }

    def test() {

        dbInfoLoggingForceOn()

        setup([ author: 'akudin', title: 'Debug', PBI: 1, product: 'wss',
                project: 'Webstaurant.StoreFront', keywords: 'debug', logLevel: 'info',
                id: 'eaa7192c-d515-4b8d-af80-ed0929016e1e' ])

        Instant instant = Instant.now();
        long timeStampMillis = instant.toEpochMilli();

        log timeStampMillis
        log new Date().getTime()

        return


        List<Map> rows = dbSelect("""
            SELECT c.id, r.project_id FROM ServerRuns_Classes c
                INNER JOIN (SELECT id = max(id), class_id, project_id FROM Stat_Test_Runs GROUP BY class_id, project_id) r
                    ON c.id = r.class_id
            WHERE r.project_id <> 0 and c.project_id = 0
            ORDER BY c.id""", 'qa')

        log rows.size()

        String query = ''
        for (row in rows) {
            query += "UPDATE ServerRuns_Classes SET project_id = ${row.project_id} WHERE id = ${row.id};\n"
            if (query.length() > 15000) {
                flush(query)
                query = ''
            }
        }
        if (query) flush(query)

    }

    void flush(String query) {
        dbExecute(query, 'qa')
    }

}

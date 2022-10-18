package above.azure.pipeline.control

import above.RunWeb
import above.report.EmailSender
import all.DateTools
import all.Json
import all.MemoryStorage

/**
 *      Server Runs Tools
 */

class ServerRunsTools extends RunWeb {

    void test() {

        setup('akudin', "Server Runs Tools",
                ["product:wss", "tfsProject:Automation%20Projects",
                 'keywords:server', "PBI:456468", 'logLevel:info'])

        //log MemoryStorage.getString("webtool-debug")
        //control()
        //queueDeleteAll()

/*
        def ppl = Json.getData(MemoryStorage.getString("webtool-server-run-pipelines"))
        def nPpl = ppl.findAll { DateTools.dateFromYmd(it.dateStart[0..9]) > new Date('07/18/2021') }
        MemoryStorage.setData("webtool-server-run-pipelines", nPpl)
        log nPpl
*/

        queue()

    }


    /** Server Runs Options */
    void control() {
        MemoryStorage.setData("webtool-server-run-options", [
                serverRunsOn: true
        ])
        log Json.getData(MemoryStorage.getString("webtool-server-run-options"))
    }


    /** Queue & pipelines */
    void queue() {
        def q = Json.getData(MemoryStorage.getString("webtool-server-run-queue"))
        log q
        log q.findAll{ it.status == 'new' && !it.sequentially }.size()
        log q.findAll{ it.status == 'new' && it.sequentially }.size()
        log Json.getData(MemoryStorage.getString("webtool-server-run-pipelines"))
        log "webtool-timerActionsCallCount: ${MemoryStorage.getString("webtool-timerActionsCallCount")}"
    }
    void queueDeleteAll() {
        MemoryStorage.setData("webtool-server-run-queue", [])
        MemoryStorage.setData("webtool-server-run-pipelines", [])
        log 'Queue and pipelines data has been deleted'
    }

}

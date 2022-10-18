package above.exec.tfsstats

import above.RunWeb
import above.azure.AzureDevOpsStats
import above.report.EmailSender

class TfsStatistics extends RunWeb {

    def tcIds = [265465]

    // Test
    def test() {

        setup('akudin', "Server Debug",
              ['product:wss', 'tfsProject:Webstaurant.StoreFront',
               'keywords:server', "tfsTcIds:${tcIds.join(',')}", 'logLevel:info'])

        AzureDevOpsStats.allProjectsStats()  // the last project is 'v8eval (Internal)'

    }

}

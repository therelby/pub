package framework.run

import above.RunWeb
import above.azure.AzureDevOpsTestcase
import all.DbTools

class Azure extends RunWeb {

    def tcId = 265465 // debugging testcase
    def tcss = [270071, 296881]

    def test() {

        setup('akudin', 'Web Concept Unit Test | Azure DevOps API Handling', [
                'product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test',
                "tfsTcIds:$tcId,${tcss.join(',')}",
        ])

        log 'Set status = Blocked'
        AzureDevOpsTestcase.updateStatus(tcId, 'Blocked')
        String status = AzureDevOpsTestcase.getStatus(tcId)
        log "status = $status"
        assert status == 'Blocked'

        log 'Set status = Passed'
        assert AzureDevOpsTestcase.updateStatus(tcId, 'Passed')
        status = AzureDevOpsTestcase.getStatus(tcId)
        log "status = $status"
        assert status == 'Passed'

        assert isTcReady(tcId)

    }

}

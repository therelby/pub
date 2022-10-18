package above.exec.tools


import above.RunWeb
import above.azure.AzureDevOpsApi
import all.Json

class CancelPipelines extends RunWeb {

    static void main(String[] args) {
        new CancelPipelines().testExecute()
    }

    void test() {

        setup([ author: 'akudin', title: 'Debug', PBI: 1, product: 'wss|dev,test',
                project: 'Webstaurant.StoreFront', keywords: 'debug', logLevel: 'info' ])


        while (true) {

            def qu = AzureDevOpsApi.apiCall(
                    "${AzureDevOpsApi.apiHost}/DefaultCollection/Automation%20Projects/_apis/build/builds?statusFilter=inProgress&api-version=5.1",
                    'GET').value

            log qu.size()
            log qu
            return

            log 'Found:'
            log qu.size()
            log ''

            if (!qu) break

            for (it in qu.reverse()) {

                if (it.definition.id == 1714) { // !!!!!!!!!!!!!!!!!!!!!!!!!
                    AzureDevOpsApi.apiCall(
                            "${AzureDevOpsApi.apiHost}/DefaultCollection/Automation%20Projects/_apis/build/builds/${it.id}?api-version=5.1",
                            'PATCH', Json.getJson([
                            status: 'cancelling'
                    ]))
                }

                //break
            }

        }

    }

}

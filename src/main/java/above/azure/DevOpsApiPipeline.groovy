package above.azure

import all.Json

/**
 *      DevOps Pipelines Handling
 */

class DevOpsApiPipeline {


    /** Run DevOps pipeline */
    static runPipeline(int pipelineId, Map variables = [:]) {
        def params = [previewRun: false]
        if (variables) {
            params = [
                    resources: [
                            repositories: [
                                    self: [
                                            refName: "refs/heads/master"
                                    ]
                            ]
                    ],
                    variables: [:]
            ]
            variables.keySet().each {

                params.variables << [
                        "$it":[
                                isSecret: false,
                                value: variables[it]
                        ]
                ]
            }
        }
        run().log params
        return AzureDevOpsApi.apiCall(
                "${AzureDevOpsApi.apiHost}/DefaultCollection/Automation%20Projects/_apis/pipelines/$pipelineId/runs?${AzureDevOpsApi.apiVersionParam}",
                'POST', Json.getJson(params)
        )
    }

}

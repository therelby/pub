package above.azure

/**
 * Azure DevOps Work Items Handling
 * @author akudin
 */
class AzureDevOpsWorkItems {


    /**
     * @Deprecated
     * This method is not working anymore because of Issue with tokens
     *  Download attachment
     * 	 (!) To get it working locally please install SSL certificate:
     * 	 https://tfs.clarkinc.biz/DefaultCollection/Automation%20Projects/_wiki/wikis/Automation-Projects.wiki?wikiVersion=GBwikiMaster&pagePath=%2FKatalon%20Studio%20Setup%2FKatalon%20Grid%20Setup&pageId=844
     * @param fileName
     * @param tcId
     * @param project
     */
    @Deprecated
    synchronized static downloadAttachment(String fileName, Integer tcId, String project) {

        project = project.replace(' ', '%20')

        def history = getWorkItemUpdates(tcId, project)
        /**
         run().logData history

         run().logData AzureDevOpsTestPlans.apiCall(
         "https://tfs.clarkinc.biz/DefaultCollection/${project}/_apis/wit/workItems/${tcId}/revisions/3?api-version=5.0")
         */
        def url = null
        for (val in history.value) {
            if (val.relations) {
                for (item in val.relations.added) {
                    if (item.attributes.name == fileName) {
                        url = item.url
                    }
                }
            }
        }
        run().log "GOT ATTACHMENT URL: $url"

        if (url) {
            def path = System.getProperty("java.io.tmpdir").toString() + fileName
            run().log "SAVING ATTACHMENT TO: $path"
            URLConnection connection = url.toURL().openConnection()
            String auth = 'Basic ' + (":${AzureDevOpsApi.getAccessToken(run().testAuthor)}".bytes.encodeBase64().toString())
            connection.setRequestProperty('Authorization', auth)
            connection.setInstanceFollowRedirects(false)
            connection.setDoOutput(true)
            String newUrl = connection.getHeaderField('Location')
            new File(path).withOutputStream { out ->
                connection.inputStream.with { inp ->
                    out << inp
                }
            }
            return path
        } else {
            return null
        }
    }


    // Get list of updates
    synchronized static getWorkItemUpdates(Integer tcId, String project) {
        return AzureDevOpsTestPlans.apiCall(
                "https://tfs.clarkinc.biz/DefaultCollection/${project.replace(' ', '%20')}/_apis/wit/workItems/${tcId}/updates?api-version=5.0")
    }


    // Get work item history
    synchronized static getWorkItemHistory(Integer tcId, String project) {
        return AzureDevOpsTestPlans.apiCall(
                getWorkItem(tcId, project)._links.workItemUpdates.href)
    }


    // Get work items
    synchronized static getWorkItem(Integer tcId, String project) {
        return AzureDevOpsTestPlans.apiCall(
                "https://tfs.clarkinc.biz/DefaultCollection/${project.replace(' ', '%20')}/_apis/wit/workitems/${tcId}?api-version=5.0")
    }


    // Testcase steps list
    synchronized static isSteps(Integer tcId, String project) {

        // getting testcase points
        def points = AzureDevOpsTestcase.getTcPoints(tcId, project)
        if (!points) {
            return null
        }

        run().logData points
        println "------------------------------------"

        // getting testcase data
        def data = AzureDevOpsTestPlans.apiCall(
                "https://tfs.clarkinc.biz/DefaultCollection/${project.replace(' ', '%20')}/_apis/testplan/Plans/${points.points.last().testPlan.id}/Suites/${points.points.last().suite.id}/TestCase/${tcId}")
        if (!data) {
            return null
        }

        run().logData data

        // parsing data
        for (one in data.value.last().workItem.workItemFields) {
            if (one['Microsoft.VSTS.TCM.Steps']) {
                return true
            }
        }

        return false
    }


    // Get teams
    synchronized static getTeams() {
        return AzureDevOpsTestPlans.apiCall("https://tfs.clarkinc.biz/DefaultCollection/_apis/teams?api-version=5.0-preview.2")
    }


    // Get team members for a team
    synchronized static getTeamMembers(String teamName) {

        def teams = getTeams()

        for (team in teams) {
            if (team.name == teamName) {
                return AzureDevOpsTestPlans.apiCall(
                        "https://dev.azure.com/DefaultCollection/_apis/projects/${team.projectId}/teams/${team.id}/members?api-version=5.0")
            }
        }

        return null
    }


}

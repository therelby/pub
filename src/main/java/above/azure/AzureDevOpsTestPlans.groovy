package above.azure

/**
 * 		Azure DevOps (TFS) Test Plans and Related Data Handling
 * 		@author akudin
 */
class AzureDevOpsTestPlans {


	// API authorization
	static auth = 'Basic ' + (":${AzureDevOpsApi.getAccessToken(run().testAuthor)}".bytes.encodeBase64().toString())


	/**
	 * Get all testcases for suite id
	 */
	synchronized static getTcs(Integer suiteId, Integer planId, String project) {
		return apiCall("https://tfs.clarkinc.biz/DefaultCollection/${project}/_apis/test/Plans/${planId}/suites/${suiteId}/testcases?api-version=5.0")
	}


	/**
	 * Get all Suites for plan id
	 * @param palnId
	 * @return
	 */
	synchronized static getSuites(Integer palnId, String project) {
		return apiCall("https://tfs.clarkinc.biz/DefaultCollection/${project}/_apis/test/Plans/${ palnId }/suites?api-version=5.0")
	}


	/**
	 * Get certain plan details
	 */
	synchronized static getPlan(String plan, String project) {
		try {
			return getAllPlans(project).value.findAll{ it.name == plan }[0]
		} catch (e) {
			return null
		}
	}

	/**
	 * Test all Plans for project
	 */
	synchronized static getAllPlans(String project) {
		return apiCall("https://tfs.clarkinc.biz/DefaultCollection/${project}/_apis/test/plans?includePlanDetails=true&api-version=5.0")
	}


	//
	// API call
	synchronized static apiCall(String url) {
		def api = new all.RestApi(url, 'GET', '', auth)
		if (api.response.getStatusCode() == 200) {
			return api.apiResult
		} else {
			run().log("(!) API ERROR in AzureDevOpsTestPlans")
			println "url: $url"
			println "status code: ${api.response.getStatusCode()}"
			println api.response.getResponseBodyContent()
			return null
		}
	}

}

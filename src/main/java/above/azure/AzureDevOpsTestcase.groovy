package above.azure

import above.Run
import all.Json
import all.RestApi

/**
 * Azure DevOps (TFS) Testcases Updating
 * @author akudin
 * 
 * (!) This class impact to all of our test scripts
 */
class AzureDevOpsTestcase {

	// API stuff
	static final private baseUrl = 'https://tfs.clarkinc.biz/DefaultCollection'
	static private testcaseProject = 'Webstaurant.StoreFront'
	static private String auth = null

	// Console printing
	static consolePrint = false // debug stuff only

	// Test plan URL
	static private testPlanUrl = "https://tfs.clarkinc.biz/DefaultCollection/%project/_workitems/edit/%id"


	// Get test plan URL
	synchronized static getTfsUrl(Integer id, String project = testcaseProject) {
		return testPlanUrl.replace('%id', id.toString()).replace('%project', project)
	}


	// Set TFS testcase project
	synchronized static setProject(String name) {
		if (name) {
			testcaseProject = name.replace(' ', '%20')
		}
	}


	// Provides auth with access token encoded to Base64
	synchronized static checkAuth() {
		if (!auth) {
			auth = 'Basic ' + (":${AzureDevOpsApi.getAccessToken(run().testAuthor)}".bytes.encodeBase64().toString())
		}
	}


	// Console print before calls
	synchronized static cPrint(url, body) {
		if (consolePrint) {
			run().logDebug("API Call:")
			run().logDebug url
			run().logDebug body
		}
	}


	// Add comment to testcase
	synchronized static addComment(Integer testcaseId, String comment, String project = run().testTfsProject) {

		setProject(project)
		checkAuth()

		def url = "$baseUrl/$testcaseProject/_apis/wit/workitems/$testcaseId?api-version=5.0".toString()
		def body = Json.getJson([[
				op: "add",
				path: "/fields/System.History",
				value: comment
			]])
		cPrint(url, body)

		def api = new RestApi(url, 'PATCH', body, auth, 'application/json-patch+json')
		if (api.response.getStatusCode() == 200) {
			return api.apiResult
		} else {
			run().logDebug "url: $url"
			run().logDebug "body: $body"
			run().logDebug "status code: ${api.response.getStatusCode()}"
			run().logDebug api.response.getResponseBodyContent()
			return false
		}
	}


	// Update testcase status
	synchronized static updateStatus(Integer testcaseId, String status, String project = run().testTfsProject) {

		Run r = run()

		setProject(project)
		checkAuth()

		def tcData = getTcPoints(testcaseId, project)
		def api
		if (tcData && tcData.points) {

			// run through all configurations
			tcData.points.each {

				if (it.outcome != status) {

					def url = "${it.url}?api-version=5.0"
					def body = Json.getJson([outcome: status])
					cPrint(url, body)

					api = new RestApi(url, 'PATCH', body, auth)
					if (api.response.getStatusCode() == 200) {
						return api.apiResult
					} else {
						r.logDebug("(!) API ERROR in AzureDevOpsTestcase.updateStatus -> testcaseId $testcaseId")
						r.logDebug "url: $url"
						r.logDebug "body: $body"
						r.logDebug "status code: ${api.response.getStatusCode()}"
						r.logDebug api.response.getResponseBodyContent()
						return false
					}

				} else {

					return true
				}

			}

		} else {

			// testcase data issue
			return false

		}

	}


	// Get testcase status
	synchronized static getTcPoints(Integer testcaseId, String project = '') {

		setProject(project)
		checkAuth()

		def url = "$baseUrl/$testcaseProject/_apis/test/points?api-version=5.1-preview"
		def body = Json.getJson(["PointsFilter": ["TestCaseIds": [testcaseId.toString()]]])
		cPrint(url, body)

		def api = new RestApi(url, 'POST', body, auth)
		if (api.response.getStatusCode() == 200) {
			return api.apiResult
		} else {
			run().logDebug("(!) API ERROR in AzureDevOpsTestcase.getTcPoints -> testcaseId $testcaseId")
			run().logDebug "url: $url"
			run().logDebug "body: $body"
			run().logDebug "status code: ${api.response.getStatusCode()}"
			run().logDebug api.response.getResponseBodyContent()
			return false
		}
	}


	// Get testcase status
	synchronized static getStatus(Integer testcaseId, String project = '') {

		setProject(project)

		def data = getTcPoints(testcaseId, project)
		if (!data) {
			return null
		}

		if (data.points) {
			return data.points.last().outcome
		} else {
			run().log("Possible wrong TFS project name in the setup() 'tfsProject' option or non-existing testcase id provided", run().console_color_red)
			return null
		}
	}


	// Set automated
	synchronized static setAutomated(Integer testcaseId, String project) {
		setProject(project)
		checkAuth()

		def url = "$baseUrl/${testcaseProject.replace(' ', '%20')}/_apis/wit/workitems/${testcaseId}?api-version=5.1-preview"
		def body = Json.getJson([[
				op: 'add',
				path: "/fields/Microsoft.VSTS.TCM.AutomationStatus",
				value: 'Automated'
			]])
		cPrint(url, body)

		def api = new RestApi(url, 'PATCH', body, auth, 'application/json-patch+json')
		if (api.response.getStatusCode() == 200) {
			return api.apiResult
		} else {
			run().logDebug("(!) API ERROR in AzureDevOpsTestcase.setAutomated -> testcaseId $testcaseId")
			run().logDebug "url: $url"
			run().logDebug "body: $body"
			run().logDebug "status code: ${api.response.getStatusCode()}"
			run().logDebug api.response.getResponseBodyContent()
			return false
		}
	}


	// Check if automated
	synchronized static isAutomated(Integer testcaseId, String project) {
		setProject(project)
		checkAuth()

		def points = AzureDevOpsTestcase.getTcPoints(testcaseId, project)
		boolean automated = false
		if (points && points.points) {
			points.points.last().workItemProperties.each {
				if (it.workItem.key == "Microsoft.VSTS.TCM.AutomationStatus") {
					if (it.workItem.value == "Automated") {
						automated = true
					}
				}
			}
		}

		return  automated
	}


	// Get testcase data
	synchronized static getTc(Integer tcId, String project) {

		// getting testcase points
		def points = getTcPoints(tcId, project)
		if (!points) { return null }

		return AzureDevOpsTestPlans.apiCall("$baseUrl/${project.replace(' ', '%20')}/_apis/testplan/Plans/${points.points.last().testPlan.id}/Suites/${points.points.last().suite.id}/TestCase/${tcId}")
	}

}

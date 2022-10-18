package above.azure

import above.Run
import above.types.IssueCategory

/**
 * 		AzureDevOps Access
 * 		@author akudin
 */

class AzureDevOpsApi {

	static final apiHost = 'https://tfs.clarkinc.biz'
	static final apiVersionParam = 'api-version=6.0-preview.1'

	static final accessTokens = [:] // gone

	// Returns access token
	// based on your login
	synchronized static getAccessToken(login) {
		Run r = run()
		for (i in [1,2,3]) r.log '(!) Access Tokens Stuff is OBSOLETE (!)'
		r.xVerifyReport(false, [ title: 'TFS Access Token', details: [ login: login ] ], IssueCategory.API_OTHER)
		return ''
	}


	// API call with authorization for DevOps endpoints
	synchronized static apiCall(String url, String method = 'GET', String body = '') {
		Run r = run()
		def api = new all.RestApi(url, method, body, 'Basic ' + (":${getAccessToken(run().testAuthor)}".bytes.encodeBase64().toString()))
		if (api.response.getStatusCode() == 200) {
			return api.apiResult
		} else {
			r.log("(!) DevOps API ERROR in AzureDevOps.apiCall()")
			r.log "-- status code: ${api.response.getStatusCode()}"
			r.log api.response.getResponseBodyContent()
			return null
		}
	}

}

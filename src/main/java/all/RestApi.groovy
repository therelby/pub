package all

import above.Run
import groovy.json.JsonSlurper

/**
 * 		REST API Handling
 * @author akudin
 */
class RestApi {

	Run r = run()
	List<List<String>> headers = []
	RestApiResponse response = null
	def httpHeaders = []
	def apiResult = null
	static final String defaultFormDataBoundary = '*****rqst_boundary*****'
	static final int defaultReadTimeOut = 120000
	static final int defaultConnectTimeOut = 6000


	/**
	 * Class constructor
	 * Does API call or nothing
	 */
	RestApi(String url = '', String requestMethod = '', String body = '', String authHeader = '',
			String contentType = 'application/json', int readTimeout = defaultReadTimeOut, Map customHeaders = [:],
			int connectTimeout = defaultConnectTimeOut) {
		// API call if url and requestMethod are set
		if (!contentType) {
			contentType = 'application/json'
		}
		if (url && requestMethod) {
			apiCall(url, requestMethod, body, authHeader, contentType, readTimeout, customHeaders, connectTimeout)
		}
	}

	/**
	 * Use this constructor for API calls when you have a Map request (good for json or form-data).
	 * The type of request built depends on the contentType.
	 * @param url
	 * @param requestMethod
	 * @param requestData
	 * @param authHeader
	 * @param contentType
	 * @param readTimeout
	 */
	RestApi(String url, String requestMethod, Map requestData, String authHeader = '',
			String contentType = 'application/json', int readTimeout = defaultReadTimeOut, Map customHeaders = [:],
			int connectTimeout = defaultConnectTimeOut) {
		String requestBody
		if (!contentType) {
			contentType = 'application/json'
		} else if (contentType == 'multipart/form-data') {
			contentType = "multipart/form-data;boundary=$defaultFormDataBoundary"
			requestBody = buildFormDataRequest(requestData)
		} else if (contentType.contains('multipart/form-data;boundary=')) {
			def boundarySplit = contentType.split(';boundary=')
			requestBody = buildFormDataRequest(requestData, boundarySplit[1])
		} else {
			// This can be 'else if(contentType.contains('json')' if there are other types of requests supported eventually.
			requestBody = requestData.toPrettyString()
		}
		apiCall(url, requestMethod, requestBody, authHeader, contentType, readTimeout, customHeaders, connectTimeout)
	}


	/**
	 * Simple API call
	 * @return status code (200 is ok, any else is error)
	 */
	Integer apiCall(String url, String requestMethod, String body = '', String authHeader = '',
					String contentType = 'application/json', int readTimeout = defaultReadTimeOut, Map customHeaders = [:],
					int connectTimeout = defaultConnectTimeOut) {

		// Process by request method
		buildHeaders(authHeader, contentType, customHeaders)
		api(url, requestMethod, body, readTimeout, connectTimeout)

		// Return status code
		def statusCode = 0
		if (response != null) {
			statusCode = response.getStatusCode()
		}

		// Parse response
		if (statusCode == 200) {
			if (response || response.getResponseBodyContent()) {
				def slurper = new JsonSlurper()
				if (response.getResponseBodyContent()) {
					apiResult = slurper.parseText(response.getResponseBodyContent())
				} else {
					apiResult = [:]
				}
			}
		} else {
			apiResult = null
		}

		return statusCode
	}

	/**
	 * API request
	 * @return status code (200 is ok, any else are errors)
	 */
	private Integer api(String url, String method, String body = '', int readTimeout = defaultReadTimeOut, int connectTimeout = defaultConnectTimeOut) {

		r.log "Calling API [$method]: $url"
		if (body) {
			r.logDebug body
		}

		// creating connection
		URLConnection connection = url.toURL().openConnection()
		if (method != 'PATCH') {
			connection.setRequestMethod(method)
		} else {
			connection.setRequestMethod('POST')
			connection.setRequestProperty('X-HTTP-Method-Override', 'PATCH')
		}
		headers.each {
			connection.setRequestProperty(it[0], it[1])
			r.logDebug "${it[0]}: ${it[1]}"
		}
		connection.setReadTimeout(readTimeout)
		connection.setConnectTimeout(connectTimeout)
		connection.setDoInput(true)
		connection.setDoOutput(true)

		// request body handling
		if (body || method != 'GET') {
            try {
				OutputStream stream = connection.getOutputStream()
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stream, "UTF-8"))
				writer.write(body)
				writer.flush()
				writer.close()
				stream.close()
			} catch(e) {
				if (r.isRunDebug()) {
					throw new Exception(e)
				} else {
					r.log(e.getMessage(), r.console_color_yellow)
				}
			}
		}

		// calling and handling the response
		Integer resCode
		try {
			resCode = connection.getResponseCode()
		} catch(e) {
			if (r.isRunDebug()) {
				throw new Exception(e)
			} else {
				r.log(e.getMessage(), r.console_color_yellow)
			}
			resCode = 999
		}
		if (resCode == 200) {
			try {
				response = new RestApiResponse(resCode, connection.getInputStream().getText())
				httpHeaders = new Http().getAllHeaders('', connection)
			} catch (e) {
				if (r.isServerRun()) {
					r.log(e.getMessage(), r.console_color_yellow)
					response = new RestApiResponse(resCode, null)
					httpHeaders = null
				} else {
					throw new Exception(e)
				}
			}
		} else {
			response = new RestApiResponse(resCode, null)
			httpHeaders = null
		}

		r.logDebug "ResponseCode = $resCode"
		return resCode
	}


	/**
	 * Create request headers
	 */
	def buildHeaders(String authHeader, String contentType = 'application/json', Map customHeaders) {
		headers = []
		if (authHeader) {
			headers << ["Authorization", authHeader]
		}
		if (contentType == 'multipart/form-data') {
			contentType = "multipart/form-data;boundary=${defaultFormDataBoundary}"
		}
		if (customHeaders) {
			customHeaders.each {
				headers << [it.key, it.value]
			}
		}
		headers << ["Content-Type", contentType]
		headers << ["Accept", "application/json"]
	}

	/**
	 * This currently does not support file uploads. Will need to check type of data.key to do so. Right now it just
	 * handles text/plain key value pairs
	 * @param data key:value pairs
	 * @param boundary can be anything that won't be used in the request. This class has a default boundary
	 * @return
	 */
	synchronized static String buildFormDataRequest(Map data, String boundary = defaultFormDataBoundary) {
		StringBuilder request = new StringBuilder()
		for (pair in data) {
			request << "--" + boundary
			request << "\r\nContent-Disposition: form-data; name=\"${pair.key}\""
			request << "\r\nContent-type: text/plain"
			request << "\r\n\r\n" + pair.value + "\r\n"
		}
		request << "--" + boundary
		return request.toString()
	}
}

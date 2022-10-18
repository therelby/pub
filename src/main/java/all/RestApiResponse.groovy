package all

/**
 *      Katalon Stuff Compatibility For Old Style RestApi Handling
 */
class RestApiResponse {

    Integer statusCode
    String body


    RestApiResponse(Integer statusCode, String body) {
        this.statusCode = statusCode
        this.body = body
    }


    Integer getStatusCode() {
        return statusCode
    }


    String getResponseBodyContent() {
        return body
    }

}

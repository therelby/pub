package all

/**
 *      Http Protocol Stuff Handling
 *      @author akudin
 */
class Http {

    URLConnection conn = null


    /** Constructor */
    Http(String url = '') {
        if (url) {
            getConnection(url)
        }
    }


    /**
     * Get defined HTTP header value
     */
    String getHeaderByName(String name, String url = '') {
        if (url) {
            getConnection(url)
        }
        if (!conn) {
            return null
        }
        return conn.getHeaderField(name)
    }

    /**
     * Getting Edge Cash
     * @return empty String if no value
     */
    String getCacheValue() {
        String edgeCacheActualValue = getHeaderByName('cache-tag')
        if (!edgeCacheActualValue ) {
            return ''
        }
        return edgeCacheActualValue
    }
    /**
     * Get HTTP headers as list of maps like [name: 'content-type', value: '[text/html; charset=utf-8]', ...]
     */
    List<Map> getAllHeaders(String url = '', URLConnection connection = conn) {
        try {
            if (url && connection == conn) {
                getConnection(url)
            }
            if (!connection) {
                return null
            }
            Map<String, List<String>> map = connection.getHeaderFields()
            List<Map> res = []
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                for (cur in entry.getValue()) {
                    res << [name: entry.getKey(), value: cur]
                }
            }
            return res
        } catch(ignored) {
            return null
        }
    }


    /**
     * Get connection to an URL
     */
    URLConnection getConnection(String url) {
        try {
            URL obj = new URL(url)
            conn = obj.openConnection()
        } catch(ignored) {
            conn = null
        }
    }

}

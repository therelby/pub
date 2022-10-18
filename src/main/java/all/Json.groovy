package all

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

/**
 * 	JSON tools and utilities
 * @author akudin vdiachuk
 */
class Json {

    /**
     * Format JSon from text to data structure
     * @param text
     * @return empty List in case of issue
     */
    def static jsonToData(String text) {
        if (text) {
            def slurper = new JsonSlurper()
            try {
                return slurper.parseText(text)
            } catch (ignored) {
                return []
            }
        } else {
            return []
        }
    }

    /**
     * Format Data to JSON String
     */
    static String dataToJson(data) {
        if (data) {
            try {
                return JsonOutput.toJson(data)
            } catch (ignored) {
                return '[]'
            }
        } else {
            return '[]'
        }
    }

    /**
     * Cutting JSON from String
     * @param textWithJson - text with {} json inside
     * @return null in case of issue
     */
    static String extractJsonFromString(String textWithJson) {
        if (!textWithJson) {
            return null
        }
        int fromIndex = textWithJson.indexOf('{')
        if (fromIndex == -1) {
            return null
        }
        int toIndex = textWithJson.lastIndexOf('}')
        if (toIndex == -1) {
            return null
        }
        return textWithJson.substring(fromIndex, toIndex + 1)
    }

    @Deprecated
    def static getJson(data) { // old stuff compatibility
        return dataToJson(data)
    }

    @Deprecated
    def static getData(text) { // old stuff compatibility
        return jsonToData(text)
    }
}

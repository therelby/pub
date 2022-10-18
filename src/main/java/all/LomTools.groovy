package all

/**
 *      List Of Maps Tools
 */

class LomTools {


    /**
     * Count of key values
     */
    static int countPositiveInt(List listOfMaps, String key) {
        return listOfMaps.collect { it[key] > 0 }.size()
    }


    /**
     * Sum of a key values
     */
    static int sumInt(List listOfMaps, String key, Map where = [:]) {
        int result = 0
        for (it in listOfMaps) {
            if (where) {
                def res = true
                where.keySet().each { k ->
                    if (res && it[k] != where[k]) {
                        res = false
                    }
                }
                if (!res) { continue }
            }
            if (it[key]) {
                result += it[key]
            }
        }
        return result
    }


    /**
     * Find maximum value for a key
     * (!) Returns 0 for empty list
     * @param where - key/values for && comparing
     */
    static int maxInt(List listOfMaps, String key, Map where = [:]) {
        int result = 0
        for (it in listOfMaps) {
            if (where) {
                def res = true
                where.keySet().each { k ->
                    if (res && it[k] != where[k]) {
                        res = false
                    }
                }
                if (!res) { continue }
            }
            if (it[key] > result) {
                result = it[key]
            }
        }
        return result
    }


    /** Sort list of maps by a map key */
    static sort(List listOfMaps, String key) {
        listOfMaps.sort { a,b ->
            a[key] <=> b[key]
        }
    }

}

package all.util

import above.RunWeb

/**
 *     Utility methods for Double
 * @vdiachuk
 *
 */
class DoubleUtil {
    synchronized static RunWeb r = run()
    /**
     *     Compare two doubles with precision
     *
     */
    synchronized static Boolean equalWithPrecision(Double a, Double b, Double precision = 0.015) {
        try {
            if (a == null || b == null || precision == null) {
                r.addIssueTrackerEvent("Can not compare Double with Precision a: [$a] b: [$b] precision: [$precision] one of the value is null")
                return false
            }
            r.logDebug("Comparing double1:$a, double2:$b, with precision:$precision")
            return Math.abs(a - b) <= precision;
        } catch (Exception e) {
            r.log "Can not compare doubles with precision doulbe1:$a, double2:$b, precision:$precision. + $e"
            return null
        }

    }
    /**
     *    Get doubles from Strings - should be 1212.12 not 1212,12
     *    Compare two doubles with precision
     *    All chars that not numbers or (.) going to be removed before comparison
     * @return false in case of conversion error
     */
    synchronized static Boolean equalWithPrecisionFromStrings(String a, String b, double precision = 0.015) {
        try {
            r.logDebug("Comparing a: [$a], b: [$b], with precision: [$precision]")
            a = a.trim().replaceAll(/[^0-9\.]/, "")
            Double aDouble = a as Double
            Double bDouble = b as Double
            return Math.abs(aDouble - bDouble) <= precision
        } catch (Exception e) {
            r.log "Can not compare String-Double with precision a: [$a], b: [$b], precision: [$precision] Exception. + $e"
            return false
        }

    }

}

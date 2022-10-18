package all.util
/**
 *     Utility methods for Double
 * akudin vdiachuk
 *
 */
class NumberUtil {
    /**
     * Returns a random number
     * @param one floor (inclusive)
     * @param two ceiling (inclusive)
     * @return
     */
    static int random(int one, int two) {
        if (one == two) {
            return one
        }
        if (one > two) {
            (one, two) = [two, one]
        }
        Random random = new Random()
        return random.nextInt(two-one+1) + one
    }
}

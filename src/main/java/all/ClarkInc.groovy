package all

/**
 *      The Company Tools
 */

class ClarkInc {


    /** Is Workday */
    static boolean isWorkday(Date day = new Date()) {

        String dow = day.format('E')
        if (dow == 'Sat' || dow == 'Sun') {
            return false
        }

        if (day.format('MM/dd/yyyy') == new Date('9/6/2021').format('MM/dd/yyyy')) {
            return false
        }

        return true
    }

}

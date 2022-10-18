package all.util
/**
 *     Utility methods for Email's
 *      vdiachuk
 *
 */
class EmailUtil {

    //get email with timeStamp "yy.MM.dd-HH.mm.ss-SSS"
    synchronized static String getEmail() {
        return "QAautomation-" + TimeUtil.getTimeStamp() + "@m.com";
    }

    synchronized  static boolean isEmail(String email){
        return org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(email)
    }
}

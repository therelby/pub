package all.util

import above.RunWeb

import java.text.SimpleDateFormat

/**
 *     Utility methods for time functionality
 *      Refactored from Katalon common.Time
 *     akudin vdiachuk
 *
 */
class TimeUtil {

    /**
     *     get timeStamp
     *
     */
    public static String getTimeStamp() {
        RunWeb r = run()
        try {
            String timeStamp = new SimpleDateFormat("yy.MM.dd-HH.mm.ss-SSS").format(new Date())
            r.logDebug("Got timeStamp:$timeStamp")
            return timeStamp
        }catch(Exception e){
            r.log("Can not get timeStamp:$e")
            return null
        }
    }


    // Visual timeout in sec
    synchronized static visualTimeout(sec) {
        RunWeb r = run()
        while (sec > 0) {
            Thread.sleep(999)
             r.logDebug("$sec ")
            sec--
            if (sec % 10 == 0) {
                r.logDebug ''
            }
        }
        r.logDebug 'Done'
    }



}

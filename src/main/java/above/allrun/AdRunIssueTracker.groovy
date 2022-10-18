package above.allrun

import above.ConfigReader

abstract class AdRunIssueTracker extends CaRunSetup {

    int maxIssues = ConfigReader.get('maxIssuesCaught').toInteger()
    List<Map> issueTrackerList = []


    /** Start tracking issues over */
    void resetIssueTracker() {
        logDebug 'Resetting issue tracking'
        issueTrackerList = []
    }


    /**
     * Add an issue caught
     * (!) CHANGED to just printing to console log
     */
    void addIssueTrackerEvent(String description, Exception error = null) {
        // adding issue
        String msg = exceptionToString(error)
        log("ISSUE FOUND: $description", console_color_yellow)
        if (error)
            log("Exception  : ${error.getMessage()}", console_color_yellow)
        logDebug(msg, console_color_yellow)
    }


    /** Is issue */
    boolean isIssueTrackerList() {
        if (issueTrackerList) {
            logDebug 'Got tracked issues:'
            logDataDebug issueTrackerList
            return true
        } else {
            logDebug 'No tracked issues'
            return false
        }
    }

}

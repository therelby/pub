package above.allrun.helpers

import above.Run
import above.azure.AzureDevOpsTestcase

/**
 *      Run/Test Final Actions
 */
class RunFinalActions {

    final private Run r = run()

    /**
     * Final actions
     * - Test results handling
     * - TFS testcase status changing
     * - TFS testcase comments adding
     * - Statistics saving
     */
    void finalActions() {

        System.gc()

        // script is not using framework functional
        if (!r.testAuthor) {
            r.log(' -- This script is not using RUN stuff at all')
            return
        }

        // debug
        if (r.runDebug) {
            r.log ' -- no TFS and statistics updates because of DEBUG running'
            if (r.runStartTime) {
                r.log " -- test done in ${all.DateTools.formatSecondsToReadableTime(((new Date().getTime() - r.runStartTime) / 1000).intValue())}"
            }
            return
        }

        // updating the statistics record that was created at the test start
        new RunStatsEnd().finalStats()
    }


    // Get all issues
    private String getAsComment(Integer tcId, String name) {

        // collecting comments
        String comments = ''
        int num = 0
        r.testCases[r.getTcIndex(tcId)][name].each {
            if ((name == 'reports' && it.tcUpdateRequired) || (name == 'checks' && it.checkResult == false)) {
                num++
                if (comments) { comments += '<br />' }
                comments += "${num}. ${it.msg.replace(' ', '&nbsp;').replace('\n', '<br />')}<br />"
                if (it.url && (!it.msg.toLowerCase().contains('http') || !it.msg.contains('://'))) {
                    comments += " - ${it.url}"
                }
            }
        }
        return comments
    }

}

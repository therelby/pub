package above.allrun

import above.allrun.helpers.StorageScreenshots
import above.allrun.helpers.RunStatsEnd
import all.Json

/**
 *      Run Statistics Saving
 */
abstract class CbRunStatistics extends DaRunDb {

    /**
     * Updating test statistics after the .test() method is done
     */
    static void finalStats() {
        new RunStatsEnd().finalStats()
    }


    /** Real-time test result details saving */
    void saveTestResultDetails(Map item, String listName) {

        if (runDebug || (listName == 'checks' && item.checkResult)) {
            return // not saving for debug and any positive checks
        }
        if ((testClass.startsWith('framework') && !testClass.startsWith('framework.above')) || testClass.startsWith('above')) {
            return // not saving framework stuff
        }

        String checkId = item?.checkId
        if (!checkId) { checkId = '' } else { checkId = checkId.replace("'", "''") }

        String url = item.url
        if (!url) { url = '' }

        String rtName = listName.replace('s', '') + 'Type'
        int issueTypeId = 0
        if (item[rtName] && (item[rtName] instanceof Integer)) {
            issueTypeId = (int)item[rtName]
        }

        if (runRecordId == null)
            runRecordId = 0

        if (!dbUpdate("""INSERT INTO Stat_Result_Details 
            (run_record_id, 
             tfs_item_id, 
             tfs_project, 
             is_check, 
             result, 
             time, 
             description, 
             url, 
             screenshot, 
             details,
             smoke_test_id,
             check_id,
             uuid,
             issue_type_id) 
            VALUES 
            (${runRecordId}, 
             ${testCases[0].tcId}, 
             '${testTfsProject}', 
             ${listName.replace('checks', '1').replace('reports', '0')}, 
             ${item.checkResult.toString().replace('true', '1').replace('false', '0').replace('null', '1')}, 
             ${item.time}, 
             '${item.msg.replace("'", "''")}', 
             '${url.replace("'", "''")}', 
             ${new StorageScreenshots().save(item.screenshot)}, 
             '${Json.getJson(item).replace("'", "''").replaceAll("\\p{Cntrl}", "")}',
             0,
             '$checkId',
             '${item.uuid}',
             $issueTypeId);""", 'qa')) {
            xStop('Can not save the test statistics to the QA database')
        }
    }

}

package framework.above.test

import above.ConfigReader
import above.RunWeb

class Ut04TestStats extends RunWeb {

    static void main(String[] args) {
        new Ut04TestStats().testExecute([remoteBrowser:true,runType:'Regular'])
    }

    void test() {

        setup([
                author: ConfigReader.get('frameworkDebugPerson'),
                title: 'UNIT TEST Statistics Recording',
                product: 'wss',
                tfsProject: 'Webstaurant.StoreFront',
                keywords: 'framework unit test',
                PBI: 1
        ])

        if (!isRunDebug()) {

            assert runRecordId

            List data = dbSelect("SELECT run_id FROM Stat_Test_Runs WHERE id = $runRecordId", 'qa')
            assert data && data.size()
            assert data[0].run_id && data[0].run_id.toLowerCase() == runId

            tryLoad() // to get screenshot and URL available

            report('Framework UNIT TEST - Blocking Issue')
            data = dbSelect("SELECT id FROM Stat_Result_Details WHERE uuid = '${testCases[0].reports[0].uuid}'", 'qa')
            assert data && data.size()

            check(true, 'Framework UNIT TEST - Passed Check')
            data = dbSelect("SELECT id FROM Stat_Result_Details WHERE uuid = '${testCases[0].checks[0].uuid}'", 'qa')
            assert !data

            check(false, 'Framework UNIT TEST - Filed Check')
            data = dbSelect("SELECT run_record_id FROM Stat_Result_Details WHERE uuid = '${testCases[0].checks[1].uuid}'", 'qa')
            assert data && data[0].run_record_id == runRecordId

        } else {

            assert !runRecordId

        }

    }

}

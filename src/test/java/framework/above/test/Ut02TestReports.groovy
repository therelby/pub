package framework.above.test

import above.ConfigReader
import above.RunWeb

class Ut02TestReports extends RunWeb {

    static void main(String[] args) {
        new Ut02TestReports().testExecute([:])
    }

    void test() {

        setup([
                author: ConfigReader.get('frameworkDebugPerson'),
                title: 'UNIT TEST Reports',
                product: 'wss',
                tfsProject: 'Webstaurant.StoreFront',
                keywords: 'framework unit test',
                PBI: 1
        ])

        assert testCases.size() == 1
        assert testCases[0].checks.size() == 0
        assert testCases[0].reports.size() == 0

        try {
            report('')
            assert false
        } catch(e) {
            assert e.getMessage().contains('issueDescription')
        }

        report('001')
        assert testCases[0].reports.size() == 1
        assert testCases[0].checks.size() == 0

        assert testCases.size() == 1
    }

}

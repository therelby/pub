package framework.above.test

import above.ConfigReader
import above.RunWeb

class Ut01TestChecks extends RunWeb {

    static void main(String[] args) {
        new Ut01TestChecks().testExecute([:])
    }

    void test() {

        setup([
                author: ConfigReader.get('frameworkDebugPerson'),
                title: 'UNIT TEST Checks',
                product: 'wss',
                tfsProject: 'Webstaurant.StoreFront',
                keywords: 'framework unit test',
                PBI: 1
        ])

        assert testCases.size() == 1
        assert testCases[0].checks.size() == 0
        assert testCases[0].reports.size() == 0

        try {
            check(false, '')
            assert false
        } catch(e) {
            assert e.getMessage().contains('issueDescription')
        }

        assert !check(false, '01')
        assert testCases[0].checks.size() == 1
        assert testCases[0].checks[0].msg == '01'

        assert !check(false, '02')
        assert !check(false, '03')
        assert !check(false, '04')
        assert !check(false, '05')

        assert check(true, '06')
        assert testCases[0].checks.last().msg == null

        assert check(true, '07', 'qwerty')
        assert check(true, '08', 'qwerty')
        assert testCases[0].checks.findAll{ it.checkId == 'qwerty' }.size() == 2

        assert testCases[0].checks.size() == 8
        assert testCases[0].reports.size() == 0

        report('001')
        assert testCases[0].reports.size() == 1
        assert testCases[0].checks.size() == 8

        assert testCases.size() == 1
    }

}

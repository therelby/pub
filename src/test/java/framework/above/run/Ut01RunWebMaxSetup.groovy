package framework.above.run

import above.ConfigReader
import above.RunWeb

class Ut01RunWebMaxSetup extends RunWeb {

    void test() {

        setup([
                author: ConfigReader.get('frameworkDebugPerson'),
                title: 'UNIT TEST RunDesktop',
                product: 'wss',
                tfsProject: 'Webstaurant.StoreFront',
                keywords: 'framework unit test',
                note: '1',
                notes: '2',
                dbEnvironmentDepending: 'wss',
                allowUnlimitedCheckFails: true,
                noPositiveChecksPrinting: true,
                dbUsing: 'wss',
                logLevel: 'info',
                PBI: 1
        ])

        assert testAuthor == ConfigReader.get('frameworkDebugPerson')
        assert testTitle == 'UNIT TEST RunDesktop'
        assert testProduct == 'wss'
        assert testTfsProject == 'Webstaurant.StoreFront'
        assert testKeywords == 'framework unit test'
        assert testNotes.contains('1') & testNotes.contains('2')
        assert dbEnvironmentDepending
        assert testChecksAllowedPercent == 100
        if (isLocalRun()) {
            assert noPositiveChecksPrinting
        }
        assert dbDefaultConnectionName == 'wss'
        assert testCases.size() == 1 & testCases[0].tcId == 1

        tryLoad()

    }

}

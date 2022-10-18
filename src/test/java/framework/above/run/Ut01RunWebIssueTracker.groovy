package framework.above.run

import above.ConfigReader
import above.RunWeb

class Ut01RunWebIssueTracker extends RunWeb {

    static void main(String[] args) {
        new Ut01RunWebIssueTracker().testExecute([remoteBrowser:true, runType:'Regular'])
    }

    void test() {

        setup([
                author: ConfigReader.get('frameworkDebugPerson'),
                title: 'UNIT TEST RunWeb Issue Tracker',
                product: 'wss',
                tfsProject: 'Webstaurant.StoreFront',
                keywords: 'framework unit test',
                PBI: 1
        ])

        click('////////')
        assert issueTrackerList

    }

}

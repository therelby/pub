package framework.above.test

import above.ConfigReader
import above.RunWeb

class Ut05TestStop extends RunWeb {

    static void main(String[] args) {
        new Ut05TestStop().testExecute([remoteBrowser:true, runType:'Regular'])
    }

    void test() {

        setup([
                author: ConfigReader.get('frameworkDebugPerson'),
                title: 'UNIT TEST Stop Test',
                product: 'wss',
                tfsProject: 'Webstaurant.StoreFront',
                keywords: 'framework unit test',
                PBI: 1
        ])

        testStop('UT Test Stopped')
        assert false

    }

}

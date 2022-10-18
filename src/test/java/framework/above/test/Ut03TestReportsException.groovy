package framework.above.test

import above.ConfigReader
import above.RunWeb

class Ut03TestReportsException extends RunWeb {

    static void main(String[] args) {
        new Ut03TestReportsException().testExecute([remoteBrowser:true])
    }

    void test() {

        setup([
                author: ConfigReader.get('frameworkDebugPerson'),
                title: 'UNIT TEST RunWeb Exception Reported',
                product: 'wss',
                tfsProject: 'Webstaurant.StoreFront',
                keywords: 'framework unit test',
                PBI: 1
        ])

        try {
            report(new Exception('Error!'))
            assert false
        } catch (ignored) {
            assert true
        }

    }

}

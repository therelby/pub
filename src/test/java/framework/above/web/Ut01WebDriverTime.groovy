package framework.above.web

import above.ConfigReader
import above.RunWeb

import java.lang.reflect.Field

class Ut01WebDriverTime extends RunWeb {

    static void main(String[] args) {
        new Ut01WebDriverTime().testExecute([remoteBrowser:true])
    }

    void test() {

        setup([
                author: ConfigReader.get('frameworkDebugPerson'),
                title: 'UNIT TEST Driver Life Time',
                product: 'wss',
                tfsProject: 'Webstaurant.StoreFront',
                keywords: 'framework unit test',
                PBI: 1
        ])

        if (isServerRun()) {

            tryLoad()
            testBrowserDriverCreated = new Date().getTime() - 900001
            xSetLastHealthCheck(new Date().getTime() - 60001)
            sleep(3)

            try {
                log UUID.randomUUID().toString()
                assert false
            } catch (e) {
                e.printStackTrace()
                assert true
                closeBrowser()
            }

        }

    }

}

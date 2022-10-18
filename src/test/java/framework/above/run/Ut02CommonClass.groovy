package framework.above.run

import above.ConfigReader
import above.Run

class Ut02CommonClass {

    Run r = run()

    void go() {

        assert r.testAuthor == ConfigReader.get('frameworkDebugPerson')

    }

}

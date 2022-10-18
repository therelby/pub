package framework.above.run

import above.ConfigReader
import above.Run

class Ut02CommonClassStatic {

    synchronized static void go() {

        Run r = run()
        assert r.testAuthor == ConfigReader.get('frameworkDebugPerson')

    }

}

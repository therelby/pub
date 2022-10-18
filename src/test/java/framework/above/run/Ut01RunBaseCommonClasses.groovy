package framework.above.run

import above.ConfigReader
import above.Run

class Ut01RunBaseCommonClasses extends Run {

    void test() {

        setup([
                author: ConfigReader.get('frameworkDebugPerson'),
                title:  'UNIT TEST Run Base'
        ])

        new Ut02CommonClass().go()

        Ut02CommonClassStatic.go()

    }

}

package framework.above.run

import above.ConfigReader
import above.Run

class Ut01RunBaseEmptySetup extends Run {

    void test() {

        try {

            setup([:])

            assert false

        } catch (e) {

            assert e.getMessage().contains('setup')

            setup([
                    author: ConfigReader.get('frameworkDebugPerson'),
                    title:  'UNIT TEST Run Base'
            ])

        }

    }

}

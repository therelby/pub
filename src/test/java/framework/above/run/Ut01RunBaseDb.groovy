package framework.above.run

import above.ConfigReader
import above.Run

class Ut01RunBaseDb extends Run {

    void test() {

        setup([
                author: ConfigReader.get('frameworkDebugPerson'),
                title:  'UNIT TEST Run Base'
        ])

        try {

            dbUsing('wss')
            log dbSelect('SELECT top 1 * from sysobjects')

        } catch (e) {

            e.printStackTrace()
            assert false

        }

    }

}

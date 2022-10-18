package framework.above.run

import above.ConfigReader
import above.Run

class Ut01RunBase extends Run {

    static void main(String[] args) {
        new Ut01RunBase().testExecute([:])
    }

    void test() {

        assert runId instanceof String
        assert runId.length() == 36

        setup([
                author: ConfigReader.get('frameworkDebugPerson'),
                title:  'UNIT TEST Run Base'
        ])

        try {

            setup([
                    author: ConfigReader.get('frameworkDebugPerson'),
                    title : 'UNIT TEST Run Base'
            ])

            assert false

        } catch (e) {

            assert e.getMessage().startsWith('Second call of setup()')

        }

    }

}

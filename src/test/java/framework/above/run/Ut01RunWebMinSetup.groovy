package framework.above.run

import above.ConfigReader
import above.RunWeb

class Ut01RunWebMinSetup extends RunWeb {

    void test() {

        setup([
                author: ConfigReader.get('frameworkDebugPerson'),
                title: 'UNIT TEST RunDesktop',
                tfsProject: 'Webstaurant.StoreFront',
                keywords: 'framework unit test',
                PBI: 1
        ])

        try {

            setup([
                    author: ConfigReader.get('frameworkDebugPerson'),
                    title: 'UNIT TEST RunDesktop',
                    tfsProject: 'Webstaurant.StoreFront',
                    keywords: 'framework unit test',
                    PBI: 1
            ])

            assert false

        } catch (e) {

            assert e.getMessage().startsWith('Second call of setup()')

        }

        tryLoad()

    }

}

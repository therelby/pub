package framework.run

import above.RunDesktop

class Desk extends RunDesktop {

    void test() {

        setup([ author  : 'akudin', title: 'Desktop Debug',
                product : 'wss-sps', project: 'Webstaurant.StoreFront',
                keywords: 'careers', PBI: 1, logLevel: 'info' ])

    }

}

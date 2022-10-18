package framework.all.http

import above.RunWeb
import all.Http

class UtHttpGetCache extends RunWeb {
    static void main(String[] args) {
        new UtHttpGetCache().testExecute([

                browser      : 'chrome',
                remoteBrowser: false,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])
    }

    def test() {
        final int PBI = 0
        setup([
                author  : 'vdiachuk',
                title   : 'HTTP get cache unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'http cache unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        assert new Http(getUrl('hp') + "/recipe_resizer.html").getCacheValue() == 'Secondary'
        assert new Http('FAKEURL').getCacheValue() == ""
        assert new Http("https://www.dev.webstaurantstore.com/asdfsdfasdf").getCacheValue() ==""
    }
}

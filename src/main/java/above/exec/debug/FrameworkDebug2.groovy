package above.exec.debug

import above.RunWeb

/**
 *      Framework Server-Side Debug
 */

class FrameworkDebug2 extends RunWeb {

    static void main(String[] args) {
        new FrameworkDebug2().testExecute([ remoteBrowser: true ])
    }

    void test() {

        setup('akudin', 'Framework Server-Side Debug - 2',
                ['product:wss|dev,test,prod', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:debug', "PBI:1", 'logLevel:info'])

        tryLoad()
        log getTitle()

        log 'Debug 2'
        log '3'
        log '2'
        log '1'
        log '0'

    }

}

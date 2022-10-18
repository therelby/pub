package above.exec.debug

import above.RunWeb

/**
 *      Framework Server-Side Debug
 */

class FrameworkDebug1 extends RunWeb {

    static void main(String[] args) {
        new FrameworkDebug1().testExecute([ remoteBrowser: true ])
    }

    void test() {

        setup('akudin', 'Framework Server-Side Debug - 1',
                ['product:wss|dev,test,prod', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:debug', "PBI:1", 'logLevel:info'])

        tryLoad()
        log getTitle()

        log 'Debug 1'
        log '3'
        log '2'
        log '1'
        log '0'

    }

}

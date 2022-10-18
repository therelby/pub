package above.exec.debug

import above.RunWeb
import groovy.io.FileType

import java.time.Instant

class FrameworkDebug3 extends RunWeb {

    static void main(String[] args) {
        new FrameworkDebug3().testExecute([
                browser: 'chrome',
                remoteBrowser: true,
                //browserVersionOffset: -1,
                //environment: 'test',
                //runType: 'Regular'
        ])
    }

    void test() {

        //dbInfoLoggingForceOn()

        setup([ author: ['akudin'], title: 'Debug', PBI: [1], product: 'wss|dev,test',
                project: 'Webstaurant.StoreFront', keywords: 'debug', logLevel: 'info' ])

        Instant instant = Instant.now()
        long timeStampMillis = instant.toEpochMilli()

        log timeStampMillis
        log new Date().getTime()
    }

}

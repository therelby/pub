package framework.grid

import above.RunWeb

class TcIosSafari extends RunWeb {

    def test() {

        setup('akudin', "TEST iOS Safari",
                ['product:wss|dev,test,prod', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:debug', "PBI:1", 'logLevel:info'])

        for (i in 1..25) {
            if (!tryLoad()) { break }
            log getTitle()
        }

    }

}

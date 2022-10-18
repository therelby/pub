package framework.run.issuehandling

import above.RunWeb

class Check extends RunWeb {

    def tcIds = [265465]

    // Test
    def test() {

        setup('akudin', 'Check',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront',
               'keywords:unit test', "tfsTcIds:${tcIds.join(',')}", 'logLevel:debug'])

        log 'Testing...'

        tryLoad()
        click('//WRONG//CRAZY//XPATH')
        check(false, tcIds[0], 'Opps, an issue happened')

        logData getInfo()
    }

}

package framework.run.environment

import above.RunWeb

class UtEnvironmentHandling extends RunWeb {

    def tcIds = [265465]

    // Test
    def test() {

        setup('akudin', 'Environment Handling',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront',
               'keywords:unit test', "tfsTcIds:${tcIds.join(',')}", 'logLevel:info'])

        log 'Testing...'

        tryLoad()
        assert getCurrentUrl() == getUrl()
        setEnvironment('test')
        tryLoad()
        assert getCurrentEnvironment() == 'test'
        assert getCurrentUrl() == getUrl()

    }

}

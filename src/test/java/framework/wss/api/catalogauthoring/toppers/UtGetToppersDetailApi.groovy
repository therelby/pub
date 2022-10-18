package framework.wss.api.catalogauthoring.toppers

import above.RunWeb

class UtGetToppersDetailApi extends RunWeb {

    def tcIds = 1

    // Test
    def test() {

        //inherited from micurtis
		setup('kyilmaz', 'UtGetToppersDetailApi',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test', "tfsTcIds:$tcIds", 'logLevel:debug'])

        log 'Testing...'


    }

}

package framework.wss.api.catalogauthoring.virtualgrouping

import above.RunWeb
import wss.api.catalogauthoring.virtualgrouping.VirtualGroupsApi

class UtVirtualGroupsApi extends RunWeb {

    // Test
    def test() {

        //inherited from micurtis
		setup('kyilmaz', 'UtVirtualGroupsApi',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test, virtual grouping, api', 'logLevel:debug'])

        log 'Testing...'

        VirtualGroupsApi virtualGroupsApi = new VirtualGroupsApi()
        assert virtualGroupsApi.getValues('virtualGroups/id') != null

    }
}
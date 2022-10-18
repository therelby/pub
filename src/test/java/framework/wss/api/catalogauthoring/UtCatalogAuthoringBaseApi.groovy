package framework.wss.api.catalogauthoring

import above.Run
import above.RunWeb
import all.RestApi
import wss.api.catalogauthoring.CatalogAuthoringApiBase
import wss.api.catalogauthoring.virtualgrouping.VirtualGroupsApi

class UtCatalogAuthoringBaseApi extends RunWeb {


    def test() {

        //inherited from micurtis
		setup('kyilmaz', 'UtCatalogAuthoringBaseApi',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test, catalog authoring', "tfsTcIds:1", 'logLevel:debug'])

        log 'Testing...'

        CatalogAuthoringApiBase api = new VirtualGroupsApi()

        assert api.callApi()
        assert api.getRestApi() instanceof RestApi
    }
}

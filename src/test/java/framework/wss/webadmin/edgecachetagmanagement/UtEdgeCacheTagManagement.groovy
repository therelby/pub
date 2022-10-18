package framework.wss.webadmin.edgecachetagmanagement

import above.RunWeb
import wss.webadmin.edgecache.EdgeCacheTagManagement

class UtEdgeCacheTagManagement extends RunWeb {
    def test() {

        setup('chorne', 'EdgeCacheTagManagement unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',

                 'keywords:unit test EdgeCacheTagManagement Edge Cache Tag Management webadmin',

                 "tfsTcIds:265471", 'logLevel:info'])

        tryLoad(getUrl())
        EdgeCacheTagManagement edgeCacheTagManagement = new EdgeCacheTagManagement()
        edgeCacheTagManagement.navigateToEdgeCacheTagsManagement()

        assert edgeCacheTagManagement.setEdgeCacheTagParameters("Jeff is old", "01234", "no", "56789")
        assert edgeCacheTagManagement.setEdgeCacheTagParameters("Jeff is older", "43210", "yes", "98765")
        assert edgeCacheTagManagement.setEdgeCacheTagParameters("Jeff is pretty old", null, null, null)
        assert edgeCacheTagManagement.setEdgeCacheTagParameters("Jeff is very old", "80", null, null)
        assert edgeCacheTagManagement.setEdgeCacheTagParameters("Jeff is the oldest", null, "yes", null)
        assert edgeCacheTagManagement.setEdgeCacheTagParameters("Jeff is ancient", null, null, "123")
        assert edgeCacheTagManagement.setEdgeCacheTagParameters("Jeff is dust", null, null, null)
        assert edgeCacheTagManagement.setEdgeCacheTagParameters(null, null, null, null)
    }
}

package framework.wss.api.catalogauthoring.virtualgrouping

import above.RunWeb
import wss.api.catalogauthoring.virtualgrouping.VirtualGroupSaveProductsApi
import wss.item.ItemUtil

class UtVirtualGroupSaveProductsApi extends RunWeb {

    // Test
    def test() {

        //inherited from micurtis
		setup('kyilmaz', 'UtVirtualGroupSaveProductsApi',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test, virtual grouping, api, save products', 'logLevel:debug'])

        log 'Testing...'

        def testItem = ItemUtil.getItemByType('lone')[0].Item_Number
        log testItem

        def testOptions = [
                "productNumbers": testItem
        ]

        def test = new VirtualGroupSaveProductsApi(330, testOptions)
        assert test.buildRequest()
        assert test.verifyRequest()


    }

}

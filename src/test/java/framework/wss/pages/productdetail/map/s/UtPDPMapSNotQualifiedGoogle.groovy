package framework.wss.pages.productdetail.map.s

import wss.actions.WssRenderingModeBanner
import wss.pages.productdetail.PDPage
import wss.user.UserQuickLogin
import wss.user.userurllogin.UserUrlLogin

class UtPDPMapSNotQualifiedGoogle extends hUtPDPMapS{
    def test() {

        setup('mwestacott', 'MAP PDP S Not qualified Google unit test',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test, pdp, map style S, not qualified, google ',
                 "PBI: 382234",
                 'logLevel:info'])

        assert openAndTryLoad(getUrl('hp') + "/plus/")
        assert new WssRenderingModeBanner().setMode("SF")

        assert UserQuickLogin.logOut()

        runTests(itemNumberSLoneHasOverrideUrl, "Lone", itemNumberSLoneHasOverrideP1, itemNumberSLoneHasOverrideP4, true)

        closeBrowser()
    }

    void runTests(String url, String itemType, String p1, String p4, boolean hasLogin){
        for(googleUrl in googleUrls) {
            tryLoad(url + googleUrl)
            PDPage pdPage = new PDPage()
            assert pdPage.verifyItemType(itemType)
            testingAddToCart(mapStyle, p1, p4, hasLogin, true)
        }
    }
}

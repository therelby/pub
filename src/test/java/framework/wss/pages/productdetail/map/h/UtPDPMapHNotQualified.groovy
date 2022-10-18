package framework.wss.pages.productdetail.map.h

import all.Money
import wss.actions.WssRenderingModeBanner
import wss.pages.productdetail.PDPage
import wss.user.UserQuickLogin
import wss.user.userurllogin.UserUrlLogin

class UtPDPMapHNotQualified extends hUtPDPMapH{

    def test() {

        setup('mwestacott', 'MAP PDP H Not qualified unit test',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test, pdp, map style H, not qualified ',
                 "PBI: 354094",
                 'logLevel:info'])

        assert openAndTryLoad(getUrl('hp') + "/plus/")
        assert new WssRenderingModeBanner().setMode("SF")

        assert UserQuickLogin.logOut()
        runTests(itemNumberHLoneNoOverrideUrl, "Lone", itemNumberHLoneNoOverrideP1, itemNumberHLoneNoOverrideP4, false)
        runTests(itemNumberHLoneHasOverrideUrl, "Lone", itemNumberHLoneHasOverrideP1, itemNumberHLoneHasOverrideP4, true)

        assert new UserUrlLogin().loginAs(userIndexNoCompany) != null
        runTests(itemNumberHLoneNoOverrideUrl, "Lone", itemNumberHLoneNoOverrideP1, itemNumberHLoneNoOverrideP4, false)

        closeBrowser()
    }



    void runTests(String url, String itemType, String p1, String p4, boolean hasLogin){
        tryLoad(url)
        PDPage pdPage = new PDPage()
        assert pdPage.verifyItemType(itemType)
        testingAddToCart(mapStyle, p1, p4, hasLogin)
    }
}

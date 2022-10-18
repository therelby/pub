package framework.wss.pages.productdetail.map.n

import all.Money
import wss.actions.WssRenderingModeBanner
import wss.pages.productdetail.PDPage
import wss.user.UserQuickLogin
import wss.user.userurllogin.UserUrlLogin

/**
 * Unit test for MAP Style N products without override
 * @author mwestacott
 */

class UtPDPMapNNotQualified extends hUtPDPMapN{

    String userIndex = "99"

    def test() {

        setup('mwestacott', 'MAP PDP N Not qualified unit test',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test, pdp, map style N, not qualified ',
                 "PBI: 382234",
                 'logLevel:info'])

        assert openAndTryLoad(getUrl('hp') + "/plus/")
        assert new WssRenderingModeBanner().setMode("SF")

        assert UserQuickLogin.logOut()
        runTests(itemNumberNLoneNoOverrideUrl, "Lone", itemNumberNLoneNoOverrideP1, itemNumberNLoneNoOverrideP4, false)
        runTests(itemNumberNLoneHasOverrideUrl, "Lone", itemNumberNLoneHasOverrideP1, itemNumberNLoneHasOverrideP4, true)

        assert new UserUrlLogin().loginAs(userIndex) != null
        runTests(itemNumberNLoneNoOverrideUrl, "Lone", itemNumberNLoneNoOverrideP1, itemNumberNLoneNoOverrideP4, false)

        closeBrowser()
    }

    void runTests(String url, String itemType, String p1, String p4, boolean hasLogin){
        tryLoad(url)
        PDPage pdPage = new PDPage()
        assert pdPage.verifyItemType(itemType)
        testingAddToCart(mapStyle, p1, p4, hasLogin)
    }
}

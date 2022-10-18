package framework.wss.pages.productdetail.map.d

import all.Money
import wss.actions.WssRenderingModeBanner
import wss.pages.productdetail.PDPage
import wss.user.UserQuickLogin
import wss.user.userurllogin.UserUrlLogin

/**
 * Unit test for MAP Style D products without override
 * @author mwestacott
 */

class UtPDPMapDNotQualified extends hUtPDPMapD{

    def test() {

        setup('mwestacott', 'MAP PDP D Not qualified unit test',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test, pdp, map style D, not qualified ',
                 "PBI: 350967",
                 'logLevel:info'])

        assert openAndTryLoad(getUrl('hp') + "/plus/")
        assert new WssRenderingModeBanner().setMode("SF")

        assert UserQuickLogin.logOut()
        runTests(itemNumberDLoneNoOverrideUrl, "Lone", itemNumberDLoneNoOverrideP1, itemNumberDLoneNoOverrideP4, false)
        runTests(itemNumberDLoneHasOverrideUrl, "Lone", itemNumberDLoneHasOverrideP1, itemNumberDLoneHasOverrideP4, true)
        runTests(itemNumberDVirtualGroupingHasOverrideUrl, "Virtual Grouping", itemNumberDVirtualGroupingHasOverrideP1, itemNumberDVirtualGroupingHasOverrideP4, true)

        assert new UserUrlLogin().loginAs(userIndexNoCompany) != null
        runTests(itemNumberDLoneNoOverrideUrl, "Lone", itemNumberDLoneNoOverrideP1, itemNumberDLoneNoOverrideP4, false)

        closeBrowser()
    }

    void runTests(String url, String itemType, String p1, String p4, boolean hasLogin){
        tryLoad(url)
        PDPage pdPage = new PDPage()
        assert pdPage.verifyItemType(itemType)
        testingAddToCart(mapStyle, p1, p4, hasLogin)
    }
}

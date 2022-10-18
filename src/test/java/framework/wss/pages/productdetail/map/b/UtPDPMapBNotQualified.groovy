package framework.wss.pages.productdetail.map.b

import wss.actions.WssRenderingModeBanner
import wss.user.UserQuickLogin
import wss.user.userurllogin.UserUrlLogin

class UtPDPMapBNotQualified extends hUtPDPMapB{

    def test() {

        setup('mwestacott', 'MAP PDP B Not qualified unit test',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test, pdp, map style B, not qualified ',
                 "PBI: 349799",
                 'logLevel:info'])
        assert openAndTryLoad(getUrl('hp') + "/plus/")
        assert new WssRenderingModeBanner().setMode("SF")

        assert UserQuickLogin.logOut()

        assert tryLoad(itemNumberBLoneNoOverrideUrl)
        testLoginForDetailsLinkAndHyperlink("B", false)

        assert tryLoad(itemNumberBLoneSuffixNoOverrideUrl)
        testLoginForDetailsLinkAndHyperlink("B", false)

        assert tryLoad(itemNumberBVirtualGroupingNoOverrideUrl)
        testLoginForDetailsLinkAndHyperlink("B", false)

        assert tryLoad(itemNumberBLoneHasOverrideUrl)
        testLoginForDetailsLinkAndHyperlink("B", true)

        assert tryLoad(itemNumberBLoneSuffixHasOverrideUrl)
        testLoginForDetailsLinkAndHyperlink("B", true)

        assert tryLoad(itemNumberBVirtualGroupingHasOverrideUrl)
        testLoginForDetailsLinkAndHyperlink("B", true)

        assert new UserUrlLogin().loginAs(userIndexNoCompany) != null

        assert tryLoad(itemNumberBLoneNoOverrideUrl)
        testLoginForDetailsLinkAndHyperlink("B", false)

        assert tryLoad(itemNumberBLoneSuffixNoOverrideUrl)
        testLoginForDetailsLinkAndHyperlink("B", false)

        assert tryLoad(itemNumberBVirtualGroupingNoOverrideUrl)
        testLoginForDetailsLinkAndHyperlink("B", false)

        closeBrowser()
    }

}

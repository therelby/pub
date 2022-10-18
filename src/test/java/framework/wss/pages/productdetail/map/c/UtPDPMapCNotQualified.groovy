package framework.wss.pages.productdetail.map.c

import wss.actions.WssRenderingModeBanner
import wss.pages.productdetail.PDPMap
import wss.pages.productdetail.PDPage
import wss.user.UserQuickLogin
import wss.user.userurllogin.UserUrlLogin

class UtPDPMapCNotQualified extends hUtPDPMapC{

    def test() {

        setup('mwestacott', 'MAP PDP C Not qualified unit test',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test, pdp, map style C, not qualified ',
                 "PBI: 349810",
                 'logLevel:info'])
        assert openAndTryLoad(getUrl('hp') + "/plus/")
        assert new WssRenderingModeBanner().setMode("SF")

        assert UserQuickLogin.logOut()
        runTestsToSeeOurPrice(itemNumberCLoneNoOverrideUrl, "Lone")

        assert new UserUrlLogin().loginAs(userIndexNoCompany) != null
        runTestsToSeeOurPrice(itemNumberCLoneNoOverrideUrl, "Lone")

        closeBrowser()
    }

    void runTestsToSeeOurPrice(String url, String itemType){
        tryLoad(url)
        PDPage pdPage = new PDPage()
        assert pdPage.verifyItemType(itemType)
        assert verifyElement(PDPMap.getMapXpath("C"))
        assert getText(getXpathForToSeeLabel()) == "To see our price"
        assert getText(getXpathForToSeePriceAddToCart()) == "Add this item to your cart.\n(You can remove it at any time.)"
    }
}

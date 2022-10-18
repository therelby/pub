package framework.wss.pages.productdetail.map.uniquemapfunctionality

import all.Money
import framework.wss.pages.productdetail.map.hUtPDPMap
import wss.actions.WssRenderingModeBanner
import wss.user.UserQuickLogin
import wss.user.userurllogin.UserUrlLogin
import wss.pages.productdetail.PDPMap

class UtPDPMapRetailPrice extends hUtPDPMap{

    def test() {
        setup('mwestacott', 'UtPDPMapRetailPrice',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test map pdp', 'logLevel:debug'])

        assert openAndTryLoad(getUrl('hp') + "/plus/")
        assert new WssRenderingModeBanner().setMode("SF")

        assert UserQuickLogin.logOut()

        assert tryLoad(itemNumberDLoneNoOverrideUrl)
        testRetailPrice("D", itemNumberDLoneNoOverrideP1, false)

        assert tryLoad(itemNumberNLoneNoOverrideUrl)
        testRetailPrice("N", itemNumberNLoneNoOverrideP1, true)

        closeBrowser()
    }

    def testRetailPrice(String mapStyle, String p1, Boolean hasStrikethrough){
        String retailPriceLabelXpath = PDPMap.getXpathForMAPAspect(mapStyle, "Retail Price section", "retailPriceLabel")
        String retailPriceXpath = PDPMap.getXpathForMAPAspect(mapStyle, "Retail Price section", "retailPrice")
        String doesPriceHaveStrikethroughXpath = PDPMap.getXpathForMAPAspect(mapStyle, "Retail Price section", "retailPriceStrikethrough")

        assert getTextSafe(retailPriceLabelXpath) == "Retail Price"
        assert new Money(getText(retailPriceXpath)) == new Money(p1)
        assert verifyElement(doesPriceHaveStrikethroughXpath) == hasStrikethrough
    }
}

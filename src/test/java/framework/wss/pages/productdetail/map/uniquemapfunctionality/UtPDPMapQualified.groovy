package framework.wss.pages.productdetail.map.uniquemapfunctionality

import framework.wss.pages.productdetail.map.hUtPDPMap
import wss.actions.WssRenderingModeBanner
import wss.pages.productdetail.PDPage
import wss.user.userurllogin.UserUrlLogin

class UtPDPMapQualified extends hUtPDPMap{

    def test() {
        setup('mwestacott', 'UtPDPMapQualified',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test map pdp', 'logLevel:debug'])

        assert openAndTryLoad(getUrl('hp') + "/plus/")
        assert new WssRenderingModeBanner().setMode("SF")

        assert new UserUrlLogin().loginAs(userIndexNoCompany) != null
        testingOverrideWithUserName()

        assert new UserUrlLogin().loginAs(userIndexWithCompany) != null
        testingOverrideWithCompanyName()

        closeBrowser()
    }

    //This is the layout for testing all products that qualify for the override
    private def testingOverride(String userNameOrCompany){
        String customQuoteOverrideLabelXpath = getXpathForCustomQuoteOverrideLabel("Q")
        String customQuoteOverridePriceXpath = getXpathForCustomQuoteOverridePrice("Q")

        assert tryLoad(itemNumberQLoneHasOverrideUrl)
        assert new PDPage().verifyItemType('Lone')
        assert getTextSafe(customQuoteOverrideLabelXpath) == "Custom Quote for $userNameOrCompany"
        assert getTextSafe(customQuoteOverridePriceXpath) == "$itemNumberQLoneHasOverrideP4/$itemNumberQLoneHasOverrideUom"
    }

    //user has no company, so they see the custom quote for themselves
    private def testingOverrideWithUserName(){
        testingOverride(userIndexName)
    }

    //user has a company, so they see the custom quote for their company
    private def testingOverrideWithCompanyName(){
        testingOverride(userIndexCompanyName)
    }
}

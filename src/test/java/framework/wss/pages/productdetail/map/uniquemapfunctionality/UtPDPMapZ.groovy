package framework.wss.pages.productdetail.map.uniquemapfunctionality

import framework.wss.pages.productdetail.map.hUtPDPMap
import wss.actions.WssRenderingModeBanner
import wss.pages.productdetail.PDPMap
import wss.user.UserQuickLogin

//The purpose of this unit test is to test the standalone functionality of MAP Style Z
class UtPDPMapZ extends hUtPDPMap{

    private String actuallyHasPricingXpath = PDPMap.getXpathForMAPAspect("Z", "MAP Style Z", "priceAndUom")
    private String labelXpath = PDPMap.getXpathForMAPAspect("Z", "MAP Style Z", "label")

    def test() {
        setup('mwestacott', 'UtPDPMapZ',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test map pdp', 'logLevel:debug'])

        assert openAndTryLoad(getUrl('hp') + "/plus/")
        assert new WssRenderingModeBanner().setMode("SF")

        assert UserQuickLogin.logOut()

        //Testing MAP Style Z no override
        testingMapStyleZ(itemNumberZLoneNoOverrideUrl, itemNumberZLoneNoOverrideP1, itemNumberZLoneNoOverrideUom)

        //Testing MAP Style Z has override
        testingMapStyleZ(itemNumberZLoneHasOverrideUrl, itemNumberZLoneHasOverrideP1, itemNumberZLoneHasOverrideUom)

        closeBrowser()
    }

    //Basis for testing MAP style Z, whether it has an override or not.
    private def testingMapStyleZ(String url, String p1, String uom){
        assert tryLoad(url)
        assert verifyElement(actuallyHasPricingXpath)
        assert getTextSafe(actuallyHasPricingXpath) == "$p1/$uom"
        assert verifyElement(labelXpath)
        assert (getTextSafe(labelXpath) == "Each Only")
    }
}

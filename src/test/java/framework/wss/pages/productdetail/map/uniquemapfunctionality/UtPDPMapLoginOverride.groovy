package framework.wss.pages.productdetail.map.uniquemapfunctionality

import framework.wss.pages.productdetail.map.hUtPDPMap
import wss.actions.WssRenderingModeBanner
import wss.pages.element.modal.LoginModal
import wss.pages.productdetail.PDPage
import wss.user.UserQuickLogin

class UtPDPMapLoginOverride extends hUtPDPMap{

    def test() {
        setup('mwestacott', 'UtPDPMapLoginOverride',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test map pdp', 'logLevel:debug'])

        assert openAndTryLoad(getUrl('hp') + "/plus/")
        assert new WssRenderingModeBanner().setMode("SF")

        assert UserQuickLogin.logOut()

        testingLoginModal()
        testingLoginModalForMapStyleZ()

        closeBrowser()
    }

    // Tests the regular scenario for login modal functionality for when the users is logged out and viewing an item with an override.
    // Normally, we test if the login modal appears.
    def testingLoginModal(){
        assert tryLoad(itemNumberQLoneHasOverrideUrl)
        assert new PDPage().verifyItemType('Lone')
        assert verifyElement(mapStyleQXpath)

        LoginModal loginModal = new LoginModal()
        String mapStyleQLoginLinkXpath = getXpathForLoginLink('Q')
        assert verifyElement(mapStyleQLoginLinkXpath)

        assert loginModal.isLoginModal() == false
        assert tryClick(mapStyleQLoginLinkXpath)
        sleep(1000)
        assert loginModal.isLoginModal()
        assert loginModal.closeLoginModal()
        sleep(1000)
        assert loginModal.isLoginModal() == false
    }

    // Tests the MAP Style Z scenario for login modal functionality for when the users is logged out and viewing an item with an override.
    // For Z, we're testing to make sure no login links appear.
    def testingLoginModalForMapStyleZ(){
        String mapStyleZLoginLinkXpath = getXpathForLoginLink('Z')
        assert tryLoad(itemNumberZLoneHasOverrideUrl)
        assert new PDPage().verifyItemType('Lone')
        assert !verifyElement(mapStyleZLoginLinkXpath)
    }
}

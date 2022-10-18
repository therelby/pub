package framework.wss.pages.element.loginmodal

import above.RunWeb
import wss.item.itembox.ProductListingItemBox
import wss.pages.element.modal.LoginModal

class UtLoginModal extends RunWeb {
    def test() {

        setup('vdiachuk', 'Pagination Element Click Next Previous Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords: unit test pagination page click previous next ',
                 "tfsTcIds:265471", 'logLevel:info'])

        tryLoad("https://www.dev.webstaurantstore.com/search/2172725stne.html")
        ProductListingItemBox productListingItemBox = new ProductListingItemBox("2172725STNEF")
        productListingItemBox.setMapStyle("D")
        productListingItemBox.focus()
        String mapOverrideDivXpath = productListingItemBox.getMAPXpath("mapLoginLink")
        log "mapOverrideDivXpath: " + mapOverrideDivXpath

        LoginModal loginModal = new LoginModal()
        //Check No Login modal
        assert loginModal.isLoginModal() == false
        sleep(1000)
      assert   click(mapOverrideDivXpath)
        sleep(1000)

        //Check Modal Present
        assert loginModal.isLoginModal()
        assert loginModal.closeLoginModal()
        sleep(1000)
        assert loginModal.isLoginModal() == false

        /// Click to open Login modal again
        assert !loginModal.checkIncorrectLoginMessage()
        click(mapOverrideDivXpath)
        log "login enabled1:" + loginModal.isLoginButtonEnabled()
        assert !loginModal.checkIncorrectLoginMessage()
        assert !loginModal.clickLoginButton()
        loginModal.fillingUpEmailAndPassword("aaa@aaa.com", "11111")
        log "login enabled2:" + loginModal.isLoginButtonEnabled()
        assert loginModal.clickLoginButton()
        assert loginModal.checkIncorrectLoginMessage()
    }
}
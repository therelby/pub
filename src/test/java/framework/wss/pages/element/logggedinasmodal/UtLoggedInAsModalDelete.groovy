package framework.wss.pages.element.logggedinasmodal

import above.RunWeb
import wss.pages.element.modal.LoggedInAsModal
import wss.user.UserQuickLogin

class UtLoggedInAsModalDelete extends RunWeb {

    def test() {

        setup('vdiachuk', 'Logged In As Modal - DELETE MODAL unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test modal login delete webelement element',
                 'PBI: 0',
                 'logLevel:info'])


        String testUrl = "https://www.dev.webstaurantstore.com/carts.html"
        openAndTryLoad("homepage")
        log "login: " + UserQuickLogin.logIn("Regular User")
        tryLoad(testUrl)

        LoggedInAsModal modal = new LoggedInAsModal()

        assert modal.isModalPresent()
        assert modal.isModalVisible()

        assert modal.deleteModal()
        assert !modal.isModalPresent()
        assert !modal.isModalVisible()

    }
}
package framework.wss.pages.element.logggedinasmodal

import above.RunWeb
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import wss.pages.element.modal.LoggedInAsModal
import wss.user.UserQuickLogin

class UtLoggedInAsModal extends RunWeb {

    def test() {

        setup('vdiachuk', 'Logged In As Modal unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test modal login loggedin logout',
                 'PBI: 0',
                 'logLevel:info'])


        String testUrl = "https://www.dev.webstaurantstore.com/carts.html"
        openAndTryLoad("homepage")
        log "login: " + UserQuickLogin.logIn("Regular User")
        tryLoad(testUrl)

        LoggedInAsModal modal = new LoggedInAsModal()

        assert modal.isModalPresent()
        assert modal.isModalVisible()
        String name = modal.getUserName()
        assert name.size()>2

        assert modal.clickLogout()



    }

}

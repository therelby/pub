package framework.wss.pages.element.logoelement

import above.RunWeb
import wss.pages.element.LogoElement
import wss.user.UserQuickLogin

class UtLogoElement extends RunWeb {
    def test() {

        setup('vdiachuk', 'Logo Element unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test logo element user',
                 'PBI: 0',
                 'logLevel:info'])
        LogoElement logoElement = new LogoElement()

        tryLoad("cart")
      //  log "Regular User: " + logoElement.clickLogo()
        assert logoElement.clickLogo()
        assert logoElement.isLogoPresent()
        assert logoElement.getUserType() == "Regular User"



        assert UserQuickLogin.loginUser('Regular User')
        tryLoad("cart")
       // log "Regular User2: " + logoElement.clickLogo()
        assert logoElement.clickLogo()
        assert logoElement.isLogoPresent()
        assert logoElement.getUserType() == 'Regular User'
        assert UserQuickLogin.logOut()

        assert UserQuickLogin.loginUser('Web Plus')
        tryLoad("cart")
       // log "Web Plus: " + logoElement.clickLogo()
        assert logoElement.clickLogo()
        assert logoElement.isLogoPresent()
        assert logoElement.getUserType() == 'Web Plus'
        assert UserQuickLogin.logOut()

        assert UserQuickLogin.loginUser('Platinum')
        tryLoad("cart")
        //log "Platinum: " + logoElement.clickLogo()
        assert logoElement.clickLogo()
        assert logoElement.isLogoPresent()
        assert logoElement.getUserType() == 'Platinum'
        assert UserQuickLogin.logOut()

        assert UserQuickLogin.loginUser('Platinum WebPlus')
        tryLoad("cart")
        //log "Platinum WebPlus: " + logoElement.clickLogo()
        assert logoElement.clickLogo()
        assert logoElement.isLogoPresent()
        assert logoElement.getUserType() == 'Platinum WebPlus'
        assert UserQuickLogin.logOut()



    }
}

package framework.wss.pages.element.topmenu

import above.RunWeb
import wss.pages.element.topmenu.AccountTopElement
import wss.user.userurllogin.UserUrlLogin

class UtAccountTopElement extends RunWeb {
    def test() {

        setup('vdiachuk', 'Account Top Element unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test top menu account myaccount user',
                 'PBI: 0',
                 'logLevel:info'])


        tryLoad('homepage')
        AccountTopElement accountTopElement = new AccountTopElement()
        assert accountTopElement.isAccountTopElement()
        UserUrlLogin userUrlLogin = new UserUrlLogin()
        log "login Regular: " + userUrlLogin.loginNewUser("Regular User", 0)
        assert accountTopElement.isAccountTopElement()
        log "logOut: " + userUrlLogin.logOut()
        log "login Plus: " + userUrlLogin.loginNewUser('Web Plus', 0)
        assert accountTopElement.isAccountTopElement()
        log "logOut: " + userUrlLogin.logOut()
        log "login Platinum: " + userUrlLogin.loginNewUser('Platinum', 0)
        assert accountTopElement.isAccountTopElement()
        log "logOut: " + userUrlLogin.logOut()
        log "login Platinum Plus: " + userUrlLogin.loginNewUser('Platinum WebPlus', 0)
        assert accountTopElement.isAccountTopElement()
        tryLoad("https://www.google.com/")
        assert !accountTopElement.isAccountTopElement()



    }
}

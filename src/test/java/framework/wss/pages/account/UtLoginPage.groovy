package framework.wss.pages.account

import above.RunWeb
import wss.api.user.UserCreationApi
import wss.pages.account.LoginPage
import wss.user.UserUtil
import wss.user.userurllogin.UserUrlLogin
import wss.webadmin.WebAdmin

class UtLoginPage extends RunWeb {
    def test() {

        setup('vdiachuk', 'Unit test Login Page | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test login page user account ',
                 "tfsTcIds: 265471",
                 'logLevel: info'])


        //  tryLoad("homepage")

        LoginPage loginPage = new LoginPage()
        assert !loginPage.clickLoginButton()
        assert !loginPage.isLoginPage()

        assert loginPage.navigate()
        assert !loginPage.setLogin(null)
        assert !loginPage.setPassword(null)
        assert loginPage.isLoginPage()
        assert loginPage.setLogin("Sample Login")
        assert loginPage.setPassword("Sample Password")

        assert loginPage.clickLoginButton()

        tryLoad("homepage")
        assert loginPage.navigate()
        assert loginPage.setLoginAndPassword("SampleLogin", "SamplePass")

        tryLoad("homepage")
        assert loginPage.navigate()
        assert !loginPage.login("SampleLogin", "SamplePass")

        UserCreationApi user = UserCreationApi.createUniqueRegularUser(0, false)
        //  log user
        //  log user.requestMap['emailAddress']

//success login
        tryLoad("homepage")
        assert loginPage.navigate()
        assert loginPage.login(user.requestMap['emailAddress'], user.requestMap['password'])

        assert loginPage.isLoggedIn()
    }
}

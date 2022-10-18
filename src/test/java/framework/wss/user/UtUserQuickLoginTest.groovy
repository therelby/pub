package framework.wss.user

import above.RunWeb
import wss.user.UserQuickLogin

class UtUserQuickLoginTest extends RunWeb {

    // Test
    def test() {

        setup('vdiachuk', 'user.UserQuickLogin Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test login user UserQuickLogin',
                 "tfsTcIds:265471", 'logLevel:info'])


        //Quantity of checks for each user type
        Integer checkQuantity = 10

        String homePage = getUrl("homepage")
        openBrowser(homePage)
        assert UserQuickLogin.loginAs("13398767")
        assert UserQuickLogin.isLoggedIn()
        assert !UserQuickLogin.isLoggedOut()
        assert UserQuickLogin.logOut()
        assert UserQuickLogin.isLoggedOut()

        for (int i = 0; i < checkQuantity; i++) {
            log "test:$i, from:$checkQuantity"
            for (String userType : UserQuickLogin.userTypes) {
                //check if returns true, there is a check for the right user type inside the method
                assert UserQuickLogin.loginUser(userType)
                assert UserQuickLogin.isLoggedIn()
                assert UserQuickLogin.logOut()
                assert UserQuickLogin.isLoggedOut()
                assert deleteAllCookies()
            }
        }


        //assert findInElement(parentMenuElementXpath, menuItemXpath) == element.findElement(By.xpath("." + menuItemXpath))


    }
}

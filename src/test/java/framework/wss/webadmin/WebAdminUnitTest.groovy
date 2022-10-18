package framework.wss.webadmin

import above.RunWeb

import wss.webadmin.WebAdmin

class WebAdminUnitTest extends  RunWeb {
    def test() {

        setup('vdiachuk', 'Unit test for WebAdmin | Framework Self ' +
                'Testing Tool',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test webadmin admin newwebadmin ',
               "tfsTcIds:265471", 'logLevel:info'])


        String homePage = getUrl("default")
        openBrowser(homePage)
        deleteAllCookies()
        assert !WebAdmin.isLoggedIn()
       // assert WebAdmin.loginToWebAdmin()
        assert WebAdmin.navigateAndSignIn("124PIBAG5VNL")
        assert WebAdmin.isLoggedIn()



    }
}

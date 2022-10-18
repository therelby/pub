package framework.wss.webadmin

import above.RunWeb

import wss.webadmin.WebAdmin
import wss.webadmin.userdetail.WebAdminUser

class WebAdminUserUnitTest extends RunWeb {
    def test() {

        setup('vdiachuk', 'Unit test for WebAdminUser | Framework Self ' +
                'Testing Tool',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test webadminuser admin newwebadmin' +
                      ' user edit webadmin',
               "tfsTcIds:265471", 'logLevel:info'])


        String homePage = getUrl("default")
        openBrowser(homePage)
        deleteAllCookies()

        assert WebAdmin.loginToWebAdmin()
        assert WebAdminUser.navigateToUserDetailsPage('8650701')


    }
}

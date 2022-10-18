package framework.wss.user.userurllogin

import above.RunWeb
import all.util.Addresses
import wss.user.UserDetail
import wss.user.UserQuickLogin
import wss.user.UserUtil
import wss.user.userurllogin.UserUrlLogin

class UtUserUrlLogin extends RunWeb {
    def test() {

        setup('vdiachuk', ' UserUrlLogin unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test user login url logout ',
                 "tfsTcIds:265471", 'logLevel:info'])


        //    tryLoad("homepage")

        UserUrlLogin userUrlLogin = new UserUrlLogin()
        assert userUrlLogin.loginAs("2010053").detail.getAt('index')=='2010053'
        assert userUrlLogin.logOut()

        //   setLogLevel("debug")
        /*  TODO Change after update to Sproc purchase history
        assert UserUtil.getUserForUrlLogin("Regular User", '3', '1').size() > 0
        assert userUrlLogin.loginUser("Regular User", '1', '1') instanceof UserDetail
        assert userUrlLogin.loginUser("Regular User", '1', '1')
        assert userUrlLogin.loginUser("Regular User", '3', '1')
        assert userUrlLogin.logOut()
        try {
            userUrlLogin.loginUser("Regular UserFAKE", '1', '1') == null
        } catch (Exception e) {
            assert e.toString() == 'java.lang.Exception: Wrong user type Regular UserFAKE'
        }
    */

    }
}

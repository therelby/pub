package framework.wss.user.userurllogin

import above.RunWeb
import all.util.Addresses
import wss.user.UserDetail
import wss.user.userurllogin.UserUrlLogin

class UtUserUrlLoginAllTypes extends RunWeb {

    def test() {

        setup('vdiachuk', ' UserUrlLogin All User Types unit test | Framework Self  Testing Tool',
                ['product:wss|dev,test', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test user login url logout type',
                 "tfsTcIds:265471", 'logLevel:info'])

        tryLoad("homepage")
        int attempts = 10
        //    def userTypes = UserUrlLogin.userTypes.collect() { it['type'] }
        def userTypes = ['Regular User',
                         'Web Plus',
                         'Platinum',
                         'Platinum WebPlus']
        for (def userType in userTypes) {
            for (int i in 1..attempts) {
                log "--"
                log "TEST: [$i] user type: [$userType]"
                UserUrlLogin userUrlLogin = new UserUrlLogin()
                UserDetail userDetail = userUrlLogin.loginNewUser(userType, 0)
                log "USERDETAIL: $userDetail"
                assert userDetail
                assert userUrlLogin.logOut()
            }
        }


    }
}

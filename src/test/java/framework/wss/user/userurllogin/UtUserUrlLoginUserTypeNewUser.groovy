package framework.wss.user.userurllogin

import above.RunWeb
import wss.user.UserDetail
import wss.user.UserType
import wss.user.userurllogin.UserUrlLogin

class UtUserUrlLoginUserTypeNewUser extends RunWeb {

    static void main(String[] args) {
        new UtUserUrlLoginUserTypeNewUser().testExecute([

                browser      : 'chrome',
                remoteBrowser: true,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])
    }

    def test() {
        final int PBI = 572571
        setup([
                author  : 'vdiachuk',
                title   : 'User Url Login with UserType enum | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'usertype login url new user',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        def userTypes = [UserType.GUEST, UserType.REGULAR_USER, UserType.WEB_PLUS, UserType.PLATINUM, UserType.PLATINUM_PLUS]
        int attempts = 10

        UserUrlLogin userUrlLogin = new UserUrlLogin()
        for (int i = 0; i < attempts; i++) {
            for (userType in userTypes) {
                tryLoad()
                log "Attempt: [$i] for user type: [$userType]"

                UserDetail userDetail = userUrlLogin.loginNewUser(userType, PBI)
                //  userUrlLogin.loginNewUser(UserType.REGULAR_USER,0)
                assert userDetail
                UserType pageUserType = userUrlLogin.getUserTypeEnum()
                assert pageUserType == userType
                assert userUrlLogin.logOut()
            }
        }

    }
}

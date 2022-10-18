package framework.wss.user.userurllogin

import above.RunWeb
import wss.user.UserType

class UtUserUrlLoginUserTypeNames extends RunWeb{


    static void main(String[] args) {
        new UtUserUrlLoginUserTypeNames().testExecute([

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


        assert  UserType.getUserTypeByString('Guest')== UserType.GUEST
        assert  UserType.getUserTypeByString('LoggedOut')== UserType.GUEST
        assert  UserType.getUserTypeByString('Logged Out')== UserType.GUEST
        assert  UserType.getUserTypeByString('Plus')== UserType.WEB_PLUS
        assert  UserType.getUserTypeByString('Web Plus')== UserType.WEB_PLUS
        assert  UserType.getUserTypeByString('Platinum')== UserType.PLATINUM
        assert  UserType.getUserTypeByString('Platinum Plus')== UserType.PLATINUM_PLUS
        assert  UserType.getUserTypeByString('Regular User')== UserType.REGULAR_USER
        assert  UserType.getUserTypeByString('Regular')== UserType.REGULAR_USER

        assert  UserType.getUserTypeByString('FAKE')== null


    }
}

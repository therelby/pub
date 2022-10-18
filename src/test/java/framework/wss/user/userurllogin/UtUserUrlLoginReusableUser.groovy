package framework.wss.user.userurllogin

import above.RunWeb
import all.util.Addresses
import wss.api.user.UserCreationApi
import wss.user.UserDetail
import wss.user.userurllogin.UserUrlLogin

class UtUserUrlLoginReusableUser extends RunWeb {


    static void main(String[] args) {
        new UtUserUrlLoginReusableUser().testExecute([

                browser      : 'chrome',//'safari' edge
                remoteBrowser: false,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,*/
               browserVersionOffset: -1
        ])
    }

    def test() {

        setup('kyilmaz', ' UserUrlLogin REUSABLE User API unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:UnitTestUserLogin',
                 'keywords:unit test user login url new user api ',
                 "tfsTcIds:265471", 'logLevel:info'])


        /*  def userCreationalApi =  UserCreationApi.createUniquePlatinumUser(0, true)
            log userCreationalApi.currentEmail
            log "result:" + userCreationalApi.result*/

        tryLoad("homepage")
        UserUrlLogin userUrlLogin = new UserUrlLogin()
        UserDetail userDetail = userUrlLogin.loginReusableUser("Regular User", 555333, new Addresses(Addresses.canadaCommercialAddress))
        log userDetail.detail.index
        log userDetail.detail.index.class
        assert userDetail.detail.index as boolean
        assert userDetail.detail.index > 0
        assert userDetail.detail.type == "Regular User"
        assert userDetail.detail.email.contains('555333')
        assert userUrlLogin.isLoggedIn()

        userDetail = userUrlLogin.loginReusableUser("Platinum", 1000, new Addresses(Addresses.australiaAddress))
        assert userDetail.detail.index as boolean
        assert userDetail.detail.type == "Platinum"
        assert userUrlLogin.isLoggedIn()
    //    assert userUrlLogin.logOut()
     //   assert !userUrlLogin.isLoggedIn()

        userDetail = userUrlLogin.loginReusableUser("Web Plus", 0)
        assert userDetail.detail.type == "Web Plus"
        assert userDetail.detail.index as boolean
        assert !userUrlLogin.isLoggedOut()

        testSubordinateUsers()
    }

     def testSubordinateUsers() {
        UserUrlLogin userUrlLogin = new UserUrlLogin()
        UserDetail userDetail = userUrlLogin.loginReusableUser("Supervisor", 0, UserCreationApi.defaultAddress)
        def supervisor = userDetail.detail.index as Integer
        log "Supervisor ID is $supervisor"

        userDetail = userUrlLogin.loginReusableUser("Subordinate",0, UserCreationApi.defaultAddress, supervisor)
         log userDetail.toString()
        assert userDetail.detail.type == "Subordinate"
        assert userDetail.detail.index as boolean
        assert !userUrlLogin.isLoggedOut()
        assert userDetail.detail.supervisorIndex == supervisor

        userDetail = userUrlLogin.loginReusableUser("Subordinate Needs Approval", 0, UserCreationApi.defaultAddress, supervisor)
        assert userDetail.detail.type == "Subordinate Needs Approval"
        assert userDetail.detail.index as boolean
        assert !userUrlLogin.isLoggedOut()
        assert userDetail.detail.supervisorIndex == supervisor
    }
}

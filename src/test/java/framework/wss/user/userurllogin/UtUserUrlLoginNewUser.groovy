package framework.wss.user.userurllogin

import above.RunWeb
import all.util.Addresses
import wss.user.UserDetail
import wss.user.userurllogin.UserUrlLogin

class UtUserUrlLoginNewUser extends RunWeb {
    def test() {

        setup('vdiachuk', ' UserUrlLogin NEW User API unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test user login url new user api ',
                 "tfsTcIds:265471", 'logLevel:info'])


        /*  def userCreationalApi =  UserCreationApi.createUniquePlatinumUser(0, true)
            log userCreationalApi.currentEmail
            log "result:" + userCreationalApi.result*/

        tryLoad("homepage")
        UserUrlLogin userUrlLogin = new UserUrlLogin()
        UserDetail userDetail = null
        userDetail = userUrlLogin.loginNewUser("Regular User",555333, new Addresses(Addresses.canadaCommercialAddress)  )
        assert userDetail.detail.index.size() > 2
        assert userDetail.detail.type == "Regular User"
        assert userDetail.detail.addresses.companyName == 'Walmart Supercenter'
        assert userDetail.detail.email.contains('555333')
        assert userUrlLogin.isLoggedIn()

        userDetail = userUrlLogin.loginNewUser("Platinum",1000, new Addresses(Addresses.australiaAddress))
        assert userDetail.detail.index.size() > 2
        assert userDetail.detail.type == "Platinum"
        assert userDetail.detail.addresses.zip == "B1P 7H9"
        assert userUrlLogin.isLoggedIn()
        assert userUrlLogin.logOut()
        assert !userUrlLogin.isLoggedIn()


        userDetail = userUrlLogin.loginNewUser("Web Plus",0)
        assert userDetail.detail.type == "Web Plus"
        assert userDetail.detail.index.size() > 2
        assert !userUrlLogin.isLoggedOut()


    }
}

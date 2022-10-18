package framework.wss.user

import above.RunWeb
import all.util.Addresses
import wss.user.UserDetail
import wss.user.UserQuickLogin

class UtUserDetail extends RunWeb {

    static void main(String[] args) {
        new UtUserDetail().testExecute([

                browser      : 'chrome',//'safari' edge
                remoteBrowser: false,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])
    }

    def test() {

        setup('vdiachuk', ' UserDetail unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test userdetail user data dynamic details',
                 "tfsTcIds:265471", 'logLevel:info'])


        tryLoad("homepage")
        log UserQuickLogin.loginUser("Platinum")
        UserDetail userDetail = new UserDetail()
        log userDetail.getDynamicData()
        log "userIndex: " + userDetail.detail.index
        assert userDetail.detail.index.size() > 1
        log "emailAddress: " + userDetail.detail.emailAddress
        assert userDetail.detail.emailAddress.size() > 0
        log userDetail

        UserDetail userDetail1 = new UserDetail([index: 1111, emailAddress: "bla@bla.com"])
        assert userDetail1.detail.index == 1111
        assert userDetail1.detail.emailAddress == "bla@bla.com"

        Addresses addresses = new Addresses(Addresses.usResidentialAddressAK)
        UserDetail userDetail2 = new UserDetail([index: 2222, address: addresses])
        log userDetail2
        assert userDetail2.detail.index == 2222
        assert userDetail2.detail.address.address == "141 Patterson St"


    }
}

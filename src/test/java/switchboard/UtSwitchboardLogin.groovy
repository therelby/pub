package switchboard

import above.RunWeb
import wap.common.WAPLogin
import wap.common.wapadmin.accesspolicies.WAPAccessPoliciesEdit

class UtSwitchboardLogin extends RunWeb {
    def userName
    def password

    UtSwitchboardLogin(def userName = WAPLogin.usernameDev, def password=WAPLogin.passwordDev){
        this.userName = userName
        this.password = password
    }
    static void main(String[] args) {
        new UtSwitchboardLogin().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }
    def test() {
        setup('ttoanle', 'AdminPortal Edit Policies unit test | Framework Self ' +
                'Testing Tool',
                ['product:swb', 'tfsProject:Switchboard', 'keywords:Load switchboard pages',
                 'PBI: 0', 'logLevel:info'])

        assert tryLoad('DirectChat')
        assert tryLoad('WSSChat')
        assert tryLoad('CNAChat')
        assert tryLoad('ClarkProChat')
        assert tryLoad('LogisticChat')
        assert tryLoad('TaxChat')
        assert tryLoad('TRSChat')
    }
}

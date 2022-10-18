package statuscheck.common.element

import above.RunWeb
import wap.common.WAPLogin

class UtStatusCheckLogin extends RunWeb {
    static void main(String[] args) {
        new UtStatusCheckLogin().testExecute([

                browser      : 'safari',//'chrome',
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
        final int PBI = 0

        setup('vdiachuk', 'Status Check Login | Framework  unit tests',
                ['product:statuscheck|dev',
                 "tfsProject: Webstaurant.StoreFront",
                 'keywords: status check login',
                 "PBI: $PBI",
                 'logLevel:info',])

        StatusCheckLogin login = new StatusCheckLogin()
        assert login.login(WAPLogin.usernameDev, WAPLogin.passwordDev)
        closeBrowser()
        assert login.loginDefault()

    }
}


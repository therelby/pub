package framework.runweb

import above.RunWeb
import wss.myaccount.Shipping
import wss.user.UserQuickLogin

class UtTimeout extends RunWeb {

    static void main(String[] args) {
        new UtTimeout().testExecute([
                browser      : 'chrome',//'safari',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    def test() {

        setup('vdiachuk', 'Set Implicit and Page Load Timeout Unit Test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords: unit test implicit pageLoad load page' +
                        ' timeout set',
                 "tfsTcIds:265471",
                 'logLevel:info'])


        tryLoad('homepage')

        assert setImplicitTimeout(10)
        assert setImplicitTimeoutToDefault()
        assert setPageLoadTimeout(2)
        assert setPageLoadTimeoutToDefault()



    }
}

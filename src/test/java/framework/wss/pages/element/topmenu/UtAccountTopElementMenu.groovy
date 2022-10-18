package framework.wss.pages.element.topmenu

import above.RunWeb
import wss.pages.element.topmenu.AccountTopElement
import wss.pages.servicepage.FoodServiceLayout
import wss.user.UserDetail
import wss.user.UserType
import wss.user.userurllogin.UserUrlLogin

class UtAccountTopElementMenu extends RunWeb {

    static void main(String[] args) {
        new UtAccountTopElementMenu().testExecute([

                browser      : 'chrome',//'chrome',
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

        setup([
                author  : 'vdiachuk',
                title   : 'Account Top Element Menu unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'account top menu element data unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        UserUrlLogin userUrlLogin = new UserUrlLogin()
        userUrlLogin.loginNewUser(UserType.REGULAR_USER, PBI)
        tryLoad()
        AccountTopElement accountTopElement = new AccountTopElement()
        def accountData = accountTopElement.getMenuElementsData()
        assert accountData.size() > 4
        assert accountData.every() { it['text'].size() > 2 && it['link'].startsWith('https') }
    }
}

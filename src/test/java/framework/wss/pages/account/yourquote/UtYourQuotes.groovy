package framework.wss.pages.account.yourquote

import above.RunWeb
import wss.pages.account.YourQuotesPage
import wss.user.UserType
import wss.user.userurllogin.UserUrlLogin

class UtYourQuotes extends RunWeb{

    static void main(String[] args) {
        new UtYourQuotes().testExecute([

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
        final int PBI = 616807

        setup([
                author  : 'vdiachuk',
                title   : 'Your Quotes unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'quote your  unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        UserUrlLogin userUrlLogin = new UserUrlLogin()
        userUrlLogin.loginNewUser(UserType.REGULAR_USER, PBI)

        YourQuotesPage yourQuotesPage = new YourQuotesPage()
        // for private method log yourQuotesPage.getTableHeaders(YourQuotesPage.currentQuotesTableXpath)
        assert !yourQuotesPage.isYourQuotesPage()
        assert yourQuotesPage.navigate()
        assert yourQuotesPage.isYourQuotesPage()

        // for private method log yourQuotesPage.getTableHeaders(YourQuotesPage.currentQuotesTableXpath)
    }
}

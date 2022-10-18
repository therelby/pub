package framework.wss.webadmin.createquote

import above.RunWeb
import all.util.ListUtil
import wss.item.ItemUtil
import wss.user.UserType
import wss.user.userurllogin.UserUrlLogin
import wss.webadmin.WebAdmin
import wss.webadmin.quote.WebAdminCreateQuote
import wss.webadmin.quote.WebAdminCreateQuoteRegularPopup

class UtWebAdminCreateQuoteSimpleQuote extends RunWeb {

    static void main(String[] args) {
        new UtWebAdminCreateQuoteSimpleQuote().testExecute([

                browser      : 'chrome',//'chrome',
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
        final int PBI = 605862

        setup([
                author  : 'vdiachuk',
                title   : 'Create Quotes in New Web Admin unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'create quote user nwa new web admin unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        UserUrlLogin userUrlLogin = new UserUrlLogin()
        def userIndex = userUrlLogin.loginNewUser(UserType.REGULAR_USER, 0).detail.index
        log userIndex

        // item '109K478' - can not be added
        def itemNumbers = ['177W50CKR', '50016W', '795PTOKFT4', '109K478']
        //    def itemNumbers = ['109K478']
        WebAdminCreateQuote webAdminCreateQuote = new WebAdminCreateQuote()
//        assert !webAdminCreateQuote.navigate()
//        assert !webAdminCreateQuote.createSimpleQuoteP1(userIndex, "Automation Quote", itemNumbers)

        log "--"
        assert WebAdmin.loginToWebAdmin()
        assert webAdminCreateQuote.navigate()


        assert webAdminCreateQuote.createSimpleQuoteP1(userIndex, "Automation Quote", itemNumbers)
          log webAdminCreateQuote.addRegularItem(itemNumbers[0], WebAdminCreateQuoteRegularPopup.PriceTypeTiers.P3, 3, 3)
        refresh()
        log "--"
        assert !webAdminCreateQuote.createSimpleQuoteP1(userIndex+ "111", "Automation Quote", itemNumbers)
    }
}

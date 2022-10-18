package framework.wss.webadmin.createquote

import above.RunWeb
import wss.pages.cart.ViewCartPage
import wss.user.UserType
import wss.user.userurllogin.UserUrlLogin
import wss.webadmin.WebAdmin
import wss.webadmin.quote.WebAdminCreateQuote

class UtWebAdminCreateQuoteFromCardID extends RunWeb {


    static void main(String[] args) {
        new UtWebAdminCreateQuoteFromCardID().testExecute([

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
        final int PBI = 605862

        setup([
                author  : 'vdiachuk',
                title   : 'Create Quotes in New Web Admin for Cart ID unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'create quote user nwa new web admin cart id unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        UserUrlLogin userUrlLogin = new UserUrlLogin()
        userUrlLogin.loginNewUser(UserType.REGULAR_USER, PBI)
        ViewCartPage viewCartPage = new ViewCartPage()
        viewCartPage.addMultipleItemsToCart(['333401NSF', '177ICFC9HC'])
        def cartID = viewCartPage.getCardID()
        log "cart id:[$cartID]"

        WebAdminCreateQuote webAdminCreateQuote = new WebAdminCreateQuote()
        assert WebAdmin.loginToWebAdmin()
        assert webAdminCreateQuote.navigate()

        log webAdminCreateQuote.generateQuoteByCartId(cartID)


    }
}

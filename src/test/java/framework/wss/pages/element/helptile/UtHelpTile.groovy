package framework.wss.pages.element.helptile

import above.RunWeb
import wss.pages.account.PaymentPage
import wss.pages.cart.ViewCartPage
import wss.pages.element.HelpTile
import wss.user.UserType
import wss.user.userurllogin.UserUrlLogin

import java.time.LocalDateTime

class UtHelpTile extends RunWeb {

    static void main(String[] args) {
        new UtHelpTile().testExecute([

                browser      : 'chrome',
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
                author  : 'ikomarov',
                title   : 'Help Tile unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'chat help tile wait element any unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        UserUrlLogin userUrlLogin = new UserUrlLogin()
        ViewCartPage viewCartPage = new ViewCartPage()

        String itemNumber = "10207380"

        viewCartPage.navigate()
        unitTestIteration()

        tryLoad("https://www.dev.webstaurantstore.com/ask.html")
        waitForPage()
        HelpTile helpTile = new HelpTile()

        assert helpTile.isEmailBlockPresent()
        assert verifyElement(HelpTile.emailLink)
        assert helpTile.getEmailBlockUrl() as boolean
        assert helpTile.isLiveChatBlockPresent()
        assert verifyElement(HelpTile.chatTextOnlineStatus)
        assert helpTile.getLiveChatStatus() as boolean
        assert verifyElement(HelpTile.chatButton)

        userUrlLogin.loginNewUser(UserType.REGULAR_USER, PBI)
        addCreditCartNewUser ()
        viewCartPage.addItemToCart(itemNumber)
        tryClick(ViewCartPage.quickCheckoutPlaceOrderXpath)

        unitTestIteration()

        closeBrowser()
    }

    void unitTestIteration() {
        HelpTile helpTile = new HelpTile()
        waitForPage()

        assert helpTile.isEmailBlockPresent()
        assert verifyElement(HelpTile.emailLink)
        assert helpTile.getEmailBlockUrl() as boolean
        assert helpTile.isLiveChatBlockPresent()
        assert verifyElement(HelpTile.chatTextOnlineStatus)
        assert helpTile.getLiveChatStatus() as boolean
        assert verifyElement(HelpTile.chatButton)
        assert verifyElement(HelpTile.faqLink)
    }

    boolean addCreditCartNewUser () {
        RunWeb r = run()

        PaymentPage paymentPage = new PaymentPage()
        LocalDateTime now = LocalDateTime.now()

        int year = now.getYear()
        int month = now.getMonthValue()
        int expirationYear = year + 1
        if (!r.tryLoad(PaymentPage.billingPartUrl)) {
            return false
        }
        paymentPage.addNewCreditCard('John Wein', '4957817983833494', '123', month.toString(), expirationYear.toString())
        if (!paymentPage.verifyUserHasSavedCard()) {
            return false
        }
        return true
    }
}

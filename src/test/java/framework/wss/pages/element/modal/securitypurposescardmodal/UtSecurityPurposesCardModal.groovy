package framework.wss.pages.element.modal.securitypurposescardmodal

import above.RunWeb
import wss.pages.account.PaymentPage
import wss.pages.cart.CartBottomCheckout
import wss.pages.cart.CartQuickCheckout
import wss.pages.cart.ViewCartPage
import wss.pages.element.modal.SecurityPurposesCardModal
import wss.user.UserType
import wss.user.userurllogin.UserUrlLogin
import java.time.LocalDateTime

class UtSecurityPurposesCardModal extends RunWeb {
    static void main(String[] args) {
        new UtSecurityPurposesCardModal().testExecute([
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

    int pbi = 0

    def test() {
        //https://tfs.clarkinc.biz/DefaultCollection/Automation%20Projects/_workitems/edit/621536
        setup([
                author  : 'ikomarov',
                title   : 'Verify Credit Card Modal | Cart',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'verify credit card quick checkout modal unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        UserUrlLogin userUrlLogin = new UserUrlLogin()
        SecurityPurposesCardModal securityPurposesCardModal = new SecurityPurposesCardModal()
        userUrlLogin.loginNewUser(UserType.REGULAR_USER, pbi)
        addCreditCardNewUser()
        List items = ["38598001"]

        new ViewCartPage().addMultipleItemsToCart(items)

        waitForPage()
        waitForElement(CartBottomCheckout.shippingOptionsXpath)

        jsClick(CartQuickCheckout.orderNowButtonXpath)

        assert securityPurposesCardModal.isCardSecurityModal()
        assert securityPurposesCardModal.fillOutCardSecurityModal()

        tryLoad()
        assert !securityPurposesCardModal.isCardSecurityModal()
        assert !securityPurposesCardModal.fillOutCardSecurityModal()
    }

    boolean addCreditCardNewUser() {
        PaymentPage paymentPage = new PaymentPage()
        LocalDateTime now = LocalDateTime.now()

        int year = now.getYear()
        int month = now.getMonthValue()
        int expirationYear = year + 1
        if (!tryLoad(PaymentPage.billingPartUrl)) {
            return false
        }
        if (!paymentPage.addNewCreditCard('John Wein', '4957817983833494', '123', month.toString(), expirationYear.toString())) {
            return false
        }
        return true
    }
}

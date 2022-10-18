package framework.wss.pages.element.modal.viewinfoduplicateorder

import above.RunWeb
import wss.checkout.Checkout
import wss.pages.account.PaymentPage
import wss.pages.cart.CartTopCheckout
import wss.pages.cart.ViewCartPage
import wss.pages.element.modal.ViewInfoDuplicateOrderModal
import wss.user.UserType
import wss.user.userurllogin.UserUrlLogin

import java.time.LocalDateTime


class UtDuplicateOrderModalViewInfo extends RunWeb {
    static void main(String[] args) {
        new UtDuplicateOrderModalViewInfo().testExecute([

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
        final int PBI = 609916
        setup([
                author  : 'ikomarov',
                title   : 'Duplicating Order Modal | View Info ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'duplicating order modal  unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        ViewInfoDuplicateOrderModal viewInfoDuplicateOrderModal = new ViewInfoDuplicateOrderModal()
        UserUrlLogin userUrlLogin = new UserUrlLogin()
        PaymentPage paymentPage = new PaymentPage()
        ViewCartPage viewCartPage = new ViewCartPage()
        LocalDateTime now = LocalDateTime.now()

        int year = now.getYear()
        int month = now.getMonthValue()
        int expirationYear = year + 1

        List items = ["HPSK2356203"]

        userUrlLogin.loginNewUser(UserType.PLATINUM, PBI)

        tryLoad(PaymentPage.billingPartUrl)

        paymentPage.addNewCreditCard('John Wein', '4957817983833494', '123', month.toString(), expirationYear.toString())
        Checkout.checkoutItem(items, 1)
        viewCartPage.addMultipleItemsToCart(items, 1)
        clickAndTryLoad(CartTopCheckout.buttonCheckoutXpath, '*')

        assert viewInfoDuplicateOrderModal.isDuplicateOrderModal()

        tryLoad()
        assert !viewInfoDuplicateOrderModal.isDuplicateOrderModal()









    }
}

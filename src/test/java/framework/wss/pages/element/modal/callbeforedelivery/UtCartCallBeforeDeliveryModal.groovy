package framework.wss.pages.element.modal.callbeforedelivery

import above.RunWeb
import wss.pages.cart.CartQuickCheckout
import wss.pages.cart.ViewCartPage
import wss.pages.element.modal.CallBeforeDeliveryModal
import wss.user.userurllogin.UserUrlLogin

class UtCartCallBeforeDeliveryModal extends RunWeb {
    static void main(String[] args) {
        new UtCartCallBeforeDeliveryModal().testExecute([

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
                author  : 'vdiachuk',
                title   : 'Call Before Delivery, Cart, Modal | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'cart modal call before delivery checkbox',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        UserUrlLogin userUrlLogin = new UserUrlLogin()
        userUrlLogin.loginAs('17202547')
        ViewCartPage viewCartPage = new ViewCartPage()
        viewCartPage.addItemToCart('495NOWFU3FDV')

        CallBeforeDeliveryModal callBeforeDeliveryModal = new CallBeforeDeliveryModal()

        assert !callBeforeDeliveryModal.waitForModalPresent(5)
        log click(CartQuickCheckout.callBeforeDeliveryQuestionMarkXpath)


        assert callBeforeDeliveryModal.waitForModalPresent()
        assert callBeforeDeliveryModal.isModal()

        assert callBeforeDeliveryModal.getLinesData().size() == 6

        tryLoad()
        assert callBeforeDeliveryModal.isModal() == false
        assert callBeforeDeliveryModal.getLinesData() == []


    }
}

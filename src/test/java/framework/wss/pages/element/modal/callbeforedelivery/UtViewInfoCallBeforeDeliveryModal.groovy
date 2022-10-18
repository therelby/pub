package framework.wss.pages.element.modal.callbeforedelivery

import above.RunWeb
import wss.pages.cart.ViewCartPage
import wss.pages.element.modal.ViewInfoCallBeforeDeliveryModal
import wss.user.userurllogin.UserUrlLogin

class UtViewInfoCallBeforeDeliveryModal extends RunWeb{
    static void main(String[] args) {
        new UtViewInfoCallBeforeDeliveryModal().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    def test() {
        final int PBI = 0
        setup([
                author  : 'gkohlhaas',
                title   : 'Call Before Delivery, Viewinfo, Modal | Framework Self Testing Tool',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'Viewinfo call before delivery modal',
                logLevel: 'info'
        ])

        UserUrlLogin userUrlLogin = new UserUrlLogin()
        userUrlLogin.loginAs('17202547')
        ViewCartPage viewCartPage = new ViewCartPage()
        viewCartPage.addItemToCart('495NOWFU3FDV')

        tryLoad('viewinfo.cfm')
        ViewInfoCallBeforeDeliveryModal callBeforeDeliveryModal = new ViewInfoCallBeforeDeliveryModal()
        assert callBeforeDeliveryModal.isCbdCheckoutRowPresent()
        assert callBeforeDeliveryModal.isCbdCheckboxPresent()
        assert callBeforeDeliveryModal.isCbdLabelPresent()
        assert callBeforeDeliveryModal.isCbdToolTipPresent()

        click(callBeforeDeliveryModal.cbdToolTipXpath)
        assert callBeforeDeliveryModal.isCbdModalPresent()
        assert callBeforeDeliveryModal.getBulletsData().size() == 6
        assert callBeforeDeliveryModal.clickModalCloseButton()
        assert callBeforeDeliveryModal.isCbdModalClosed()

        refresh()
        assert callBeforeDeliveryModal.isCbdModalClosed()
        assert callBeforeDeliveryModal.getBulletsData() == []
        assert callBeforeDeliveryModal.clickCbdToolTip()
        assert callBeforeDeliveryModal.isCbdModalPresent()
        assert callBeforeDeliveryModal.clickModalXButton()
        assert callBeforeDeliveryModal.isCbdModalClosed()
    }
}
package framework.wss.pages.element.modal.viewinfohighelevationmodal

import above.RunWeb
import wss.pages.cart.ViewCartPage
import wss.pages.element.modal.ViewInfoHighElevationModal
import wss.user.userurllogin.UserUrlLogin

class UtViewInfoHighElevationModal extends RunWeb {
    static void main(String[] args) {
        new UtViewInfoHighElevationModal().testExecute([
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
                title   : 'High Elevation, Viewinfo, Modal | Framework Self Testing Tool',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'Viewinfo high elevation modal',
                logLevel: 'info'
        ])

        UserUrlLogin userUrlLogin = new UserUrlLogin()
        userUrlLogin.loginAs('122527') // high elevation zip code user
        ViewCartPage viewCartPage = new ViewCartPage()
        viewCartPage.addItemToCart('195HV1AGN') // high elevation item

        tryLoad('viewinfo.cfm')
        ViewInfoHighElevationModal highElevationModal = new ViewInfoHighElevationModal()
        assert highElevationModal.isCheckoutRowPresent()
        assert highElevationModal.isCheckboxPresent()
        assert highElevationModal.isLabelPresent()
        def getLabel = highElevationModal.getLabelText()
        assert getLabel == highElevationModal.expectedValues.labelText
        assert highElevationModal.isToolTipPresent()
        assert highElevationModal.checkCheckbox()

        click(highElevationModal.highElevationToolTipXpath)
        assert highElevationModal.isModalPresent()
        assert highElevationModal.isModalBodyPresent()
        def modalText = highElevationModal.getModalText()
        assert modalText == highElevationModal.expectedValues.modalParagraph
        assert highElevationModal.clickModalCloseButton()
        assert highElevationModal.isModalClosed()
        assert highElevationModal.uncheckCheckbox()

        refresh()
        assert highElevationModal.isModalClosed()
        assert !highElevationModal.isModalBodyPresent()
        assert highElevationModal.clickToolTip()
        assert highElevationModal.isModalPresent()
        assert highElevationModal.isModalBodyPresent()
        assert highElevationModal.clickModalXButton()
        assert highElevationModal.isModalClosed()
        assert highElevationModal.checkCheckbox()
        assert highElevationModal.uncheckCheckbox()
    }
}
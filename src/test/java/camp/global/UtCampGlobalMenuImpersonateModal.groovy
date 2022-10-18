package camp.global

import above.RunWeb
import camp.common.page.element.globalmenu.CampGlobalMenuDropDown
import camp.common.page.element.CampLogin
import camp.common.page.element.modal.CampGlobalMenuImpersonateModal

class UtCampGlobalMenuImpersonateModal extends RunWeb {
    static void main(String[] args) {
        new UtCampGlobalMenuImpersonateModal().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'gkohlhaas',
                title   : 'UtCampGlobalMenuImpersonateModal',
                PBI     : 0,
                product : 'camp',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        CampLogin campLogin = new CampLogin()
        CampGlobalMenuDropDown menuElement = new CampGlobalMenuDropDown()
        CampGlobalMenuImpersonateModal impersonateModal = new CampGlobalMenuImpersonateModal()
        assert campLogin.login()
        setCookie('tours', '{"seenHomePage":true,"seenCandidateSearchPage":true,"seenCandidatesPage":true}')
        refresh() //do this after setting cookie
        waitForElementClickable(menuElement.mainMenuAccountNameButtonXpath, 10)

        assert menuElement.clickAccountNameButton()
        assert menuElement.isDropDownContainerPresent()
        assert menuElement.isImpersonateButtonPresent()
        assert impersonateModal.clickImpersonateButton()
        assert impersonateModal.isModalPresent()
        assert impersonateModal.isModalHeaderPresent()
        assert impersonateModal.isHeaderTextPresent()
        assert impersonateModal.isXButtonPresent()
        assert impersonateModal.isModalBodyPresent()
        assert impersonateModal.isLabelContainerPresent()
        assert impersonateModal.isLabelPresent()
        def labelText = impersonateModal.getLabelText()
        assert labelText == impersonateModal.expectedValues.modalLabelText
        def modalHeaderText = impersonateModal.getHeaderText()
        assert modalHeaderText == impersonateModal.expectedValues.modalTitleText
        assert impersonateModal.isFooterPresent()
        assert impersonateModal.isCancelButtonPresent()
        def cancelText = impersonateModal.getModalCancelButtonText()
        assert cancelText == impersonateModal.expectedValues.cancelButtonText
        assert impersonateModal.isImpersonateUserButtonPresent()
        assert !impersonateModal.isImpersonateButtonEnabled()
        def impersonateText = impersonateModal.getImpersonateUserButtonText()
        assert impersonateText == impersonateModal.expectedValues.impersonateButtonText
        assert impersonateModal.clickXButton()
        assert impersonateModal.isModalClosed()

        assert menuElement.clickAccountNameButton()
        assert menuElement.isDropDownContainerPresent()
        assert menuElement.isImpersonateButtonPresent()
        assert impersonateModal.clickImpersonateButton()
        assert impersonateModal.isModalPresent()
        assert impersonateModal.clickCancelButton()
        assert impersonateModal.isModalClosed()

        closeBrowser()
    }
}
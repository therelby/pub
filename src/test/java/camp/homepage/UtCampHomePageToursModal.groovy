package camp.homepage

import above.RunWeb
import camp.common.page.element.CampLogin
import camp.common.page.homepage.CampHomePageToursModal

class UtCampHomePageToursModal extends RunWeb {
    static void main(String[] args) {
        new UtCampHomePageToursModal().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'gkohlhaas',
                title   : 'UtCampHomePageToursModal',
                PBI     : 0,
                product : 'camp',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        CampLogin campLogin = new CampLogin()
        CampHomePageToursModal toursModal = new CampHomePageToursModal()
        assert campLogin.login()

        scrollTo(toursModal.modalOne.modalOneBodyXpath)
        assert toursModal.isModalPresent(toursModal.modalOne.modalOneBodyXpath)
        assert toursModal.isXButtonPresent(toursModal.modalOne.modalOneXButtonXpath)
        assert toursModal.isTitlePresent(toursModal.modalOne.modalOneTitleXpath)
        def oneHeaderText = toursModal.getHeaderText(toursModal.modalOne.modalOneTitleXpath)
        assert oneHeaderText == toursModal.modalOneExpectedValues.modalOneTitle
        log oneHeaderText
        assert toursModal.isMainTextPresent(toursModal.modalOne.modalOneTextXpath)
        def modalText = toursModal.getMainText(toursModal.modalOne.modalOneTextXpath)
        assert modalText == toursModal.modalOneExpectedValues.modalOneText
        log modalText

        assert toursModal.isDividerPresent(toursModal.modalOne.modalOneDividerXpath)

        assert toursModal.isFooterContainerPresent(toursModal.modalOne.modalOneFooterButtonContainerXpath)
        assert toursModal.isExitButtonPresent(toursModal.modalOne.modalOneExitButtonXpath)
        assert toursModal.isExitButtonTextPresent(toursModal.modalOne.modalOneExitButtonTextXpath)
        def exitText = toursModal.getExitButtonText(toursModal.modalOne.modalOneExitButtonTextXpath)
        assert exitText == toursModal.modalOneExpectedValues.modalOneExitButtonText
        log exitText

        assert toursModal.isNextButtonPresent(toursModal.modalOne.modalOneNextButtonXpath)
        assert toursModal.isNextButtonTextPresent(toursModal.modalOne.modalOneNextButtonTextXpath)
        def nextText = toursModal.getNextButtonText(toursModal.modalOne.modalOneNextButtonTextXpath)
        assert nextText == toursModal.modalOneExpectedValues.modalOneNextButtonText
        log nextText

        assert toursModal.clickXButton(toursModal.modalOne.modalOneXButtonXpath)
        assert !toursModal.isModalPresent(toursModal.modalOne.modalOneBodyXpath)

        closeBrowser()
    }
}
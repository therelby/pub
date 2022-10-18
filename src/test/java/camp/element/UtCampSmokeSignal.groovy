package camp.element

import above.RunWeb
import camp.common.page.addcandidate.CampAddCandidatePage
import camp.common.page.element.CampButton
import camp.common.page.element.CampSmokeSignal
import camp.test.page.addcandidatepage.HpAddCandidatePage

class UtCampSmokeSignal extends RunWeb {
    static void main(String[] args) {
        new UtCampSmokeSignal().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    CampAddCandidatePage addCandidatePage
    HpAddCandidatePage hpAddCandidatePage
    CampSmokeSignal smokeSignal
    CampButton button
    def inputs
    def submitButton

    void test() {
        setup([
                author  : 'jreumont',
                title   : 'UtCampSmokeSignal',
                PBI     : 0,
                product : 'camp',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        addCandidatePage = new CampAddCandidatePage()
        hpAddCandidatePage = new HpAddCandidatePage()
        hpAddCandidatePage.setAddCandidatePage(addCandidatePage)
        smokeSignal = new CampSmokeSignal()
        button = new CampButton()
        inputs = addCandidatePage.getFormInputs()
        submitButton = addCandidatePage.getFormButtons().find({ it -> it.testid.contains('submit') }).testid

        hpAddCandidatePage.loginAndNavigate()
        hpAddCandidatePage.fillRandomForm(inputs, "Unit Test")

        assert button.clickInputField(submitButton)
        assert smokeSignal.waitForSmokeSignal()
        assert smokeSignal.isSmokeSignalGreen()
        assert smokeSignal.isSmokeSignalConfirmationCirclePresent()
        assert smokeSignal.getSmokeSignalText() == addCandidatePage.getSmokeSignalSubmitSuccessText()
        assert smokeSignal.isSmokeSignalCloseButtonPresent()
        assert smokeSignal.clickSmokeSignalCloseButton()
        assert smokeSignal.waitForSmokeSignal()

        closeBrowser()
    }
}

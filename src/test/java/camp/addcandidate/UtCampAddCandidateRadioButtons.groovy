package camp.addcandidate

import above.RunWeb
import camp.common.page.addcandidate.CampAddCandidatePage
import camp.common.page.element.CampLogin
import camp.test.page.element.HpCampRadioButton

class UtCampAddCandidateRadioButtons extends RunWeb{
    static void main(String[] args) {
        new UtCampAddCandidateRadioButtons().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'jreumont',
                title   : 'UtCampAddCandidateInputs',
                PBI     : 0,
                product : 'camp',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        CampAddCandidatePage addCandidatePage = new CampAddCandidatePage()
        CampLogin campLogin = new CampLogin()
        HpCampRadioButton hpRadioButton = new HpCampRadioButton()
        assert campLogin.login()
        assert addCandidatePage.navigate()
        //Handle Lazy Loading: Waits would not work here
        sleep(1500)

        def buttonRowId = addCandidatePage.getRadioButtonRowIds().get(1)
         hpRadioButton.radioButton.setRadioButtonOptions(addCandidatePage.getRadioButtonOptions().get(buttonRowId))
         hpRadioButton.checkRadioButtonLabels(buttonRowId)
         hpRadioButton.checkRadioButtonFunctionality(buttonRowId)
        closeBrowser()
    }
}

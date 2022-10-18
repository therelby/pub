package camp.addcandidate

import above.RunWeb
import camp.common.page.addcandidate.CampAddCandidatePage
import camp.common.page.element.CampDropDown
import camp.common.page.element.CampInputField
import camp.common.page.element.CampLogin
import camp.common.page.element.CampRadioButton

class UtCampAddCandidateInputs extends RunWeb {
    static void main(String[] args) {
        new UtCampAddCandidateInputs().testExecute([
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
        CampInputField inputField = new CampInputField()
        assert campLogin.login()
        assert addCandidatePage.navigate()
        //Handle Lazy Loading: Waits would not work here
        sleep(1500)

        assert inputField.isFormInputPresent('preferredFirstName')
        assert inputField.isFormInputPresent('lastName')

        assert inputField.setFormInputText('preferredFirstName', "Joey")
        assert inputField.setFormInputText('lastName', "Smith")

        assert inputField.getFormInputText('preferredFirstName') == "Joey"
        assert inputField.getFormInputText('lastName') == "Smith"

        closeBrowser()
    }
}

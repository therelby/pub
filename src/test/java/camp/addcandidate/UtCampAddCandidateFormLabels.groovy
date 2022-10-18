package camp.addcandidate

import above.RunWeb
import camp.common.page.addcandidate.CampAddCandidatePage
import camp.common.page.element.CampInputField
import camp.common.page.element.CampLogin

class UtCampAddCandidateFormLabels extends RunWeb {
    static void main(String[] args) {
        new UtCampAddCandidateFormLabels().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'jreumont',
                title   : 'UtCampAddCandidateFormLabels',
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

        assert inputField.isFormLabelPresent('firstName')
        assert inputField.isFormLabelPresent('email')
        assert inputField.isFormLabelPresent('phone')

        assert inputField.getFormLabelText('firstName') == "Legal First Name"
        assert inputField.getFormLabelText('email') == "Email"
        assert inputField.getFormLabelText('phone') == "Phone Number"

        closeBrowser()
    }

}

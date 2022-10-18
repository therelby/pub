package camp.addcandidate

import above.RunWeb
import camp.common.page.addcandidate.CampAddCandidatePage
import camp.common.page.element.CampDropDown
import camp.common.page.element.CampInputField
import camp.common.page.element.CampLogin
import camp.test.page.element.HpCampDropDown

class UtCampAddCandidateDropDown extends RunWeb {
    static void main(String[] args) {
        new UtCampAddCandidateDropDown().testExecute([
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
        CampDropDown dropDown = new CampDropDown()
        HpCampDropDown hpDropDown = new HpCampDropDown()
        assert campLogin.login()
        assert addCandidatePage.navigate()
        //Handle Lazy Loading: Waits would not work here
        sleep(1500)

        def currentTestMessage = "Unit Test for Drop Downs"
        assert hpDropDown.checkSetRandomDropDownInput('resumeObtainedFrom', currentTestMessage)
        assert hpDropDown.formClearDropDownSelection('resumeObtainedFrom', currentTestMessage)
        assert dropDown.waitFormInputAlertEnabled('resumeObtainedFrom')
        assert dropDown.isFormInputAlertPresent('resumeObtainedFrom')
        assert dropDown.getFormInputAlertText('resumeObtainedFrom') == addCandidatePage.getFormInputs().find {
            input -> input.id == 'resumeObtainedFrom'
        }.get('requiredError')
        assert dropDown.isFormInputErrorState('resumeObtainedFrom')
        assert hpDropDown.formSetDropDownInput('resumeObtainedFrom', 'Billboard', currentTestMessage)
        assert dropDown.waitFormInputAlertDisabled('resumeObtainedFrom')
        assert !dropDown.isFormInputErrorState('resumeObtainedFrom')
        assert !dropDown.isFormInputAlertPresent('resumeObtainedFrom')

        assert hpDropDown.formSetDropDownInput('highestLevelOfEducation', 'Some College', currentTestMessage)
        assert hpDropDown.formClearDropDownSelection('highestLevelOfEducation', currentTestMessage)
        assert !dropDown.isFormInputAlertPresent('highestLevelOfEducation')

        assert hpDropDown.checkSetRandomDropDownInput('job', currentTestMessage)
        assert dropDown.isFormInputDisabled('department')

        closeBrowser()
    }
}

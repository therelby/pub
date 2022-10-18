package framework.runweb

import above.RunWeb
import camp.common.page.addcandidate.CampAddCandidatePage
import camp.common.page.users.add.CampAddUserPage
import camp.test.page.addcandidatepage.HpAddCandidatePage
import camp.test.page.userspage.add.HpAddUserPage

class UtWaitForInputElementInteractable extends RunWeb {
    static void main(String[] args) {
        new UtWaitForInputElementInteractable().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'jreumont',
                title   : 'UtWaitForInputElementInteractable',
                PBI     : 0,
                product : 'camp',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        CampAddUserPage addUserPage = new CampAddUserPage()
        HpAddUserPage hpAddUserPage = new HpAddUserPage()
        hpAddUserPage.setUserPage(addUserPage)
        hpAddUserPage.loginAndNavigate()
        hpAddUserPage.hpCampInputField.inputField.isFormInputPresent('username')
        def xpath = hpAddUserPage.hpCampInputField.inputField.getFormInputXpath()
        assert waitForInputElementInteractable(xpath, 5)
        assert hpAddUserPage.hpCampInputField.checkSetTextInput('username', 'Tom', 'Unit Test')

        HpAddCandidatePage hpAddCandidatePage = new HpAddCandidatePage()
        CampAddCandidatePage addCandidatePage = new CampAddCandidatePage()
        hpAddCandidatePage.setAddCandidatePage(addCandidatePage)
        addCandidatePage.navigate()
        hpAddCandidatePage.hpInputField.inputField.isFormInputPresent('firstName')
        xpath = hpAddCandidatePage.hpInputField.inputField.getFormInputXpath()
        assert waitForInputElementInteractable(xpath, 5)
        assert hpAddCandidatePage.hpInputField.checkSetTextInput('firstName', 'Joe', 'Unit Test')

        closeBrowser()
    }

}


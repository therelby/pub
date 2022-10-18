package camp.users.add

import above.RunWeb
import camp.common.CampUser
import camp.common.page.users.add.CampAddUserPage
import camp.test.page.element.HpCampCheckBox
import camp.test.page.element.HpCampInputField
import camp.test.page.userspage.add.HpAddUserPage

class UtAddUserPageCheckBox extends RunWeb {
    static void main(String[] args) {
        new UtAddUserPageCheckBox().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'jreumont',
                title   : 'UtAddUserPageCheckBox',
                PBI     : 0,
                product : 'camp',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])
        CampAddUserPage addUserPage = new CampAddUserPage()
        HpAddUserPage hpAddUserPage = new HpAddUserPage()
        HpCampInputField hpCampInputField = new HpCampInputField()
        HpCampCheckBox hpCampCheckBox = new HpCampCheckBox()
        hpAddUserPage.setUserPage(addUserPage)
        hpAddUserPage.loginAndNavigate()
        CampUser thomas = new CampUser(CampUser.sampleBlankUserNevim)

        def currentTestMessage = "Unit Tests"
        assert hpCampInputField.checkSetTextInput('username', thomas.username, currentTestMessage)
        assert hpCampInputField.clickLabel('username', currentTestMessage)
        sleep(200)
        assert hpCampCheckBox.checkSetCheckBoxInput('staywithinjobposting-checkbox', true, currentTestMessage)
        assert hpCampCheckBox.checkSetCheckBoxInput('staywithinjobposting-checkbox', false, currentTestMessage)
        assert hpCampCheckBox.checkBox.isFormLabelPresent('staywithinjobposting-checkbox')
        assert hpCampCheckBox.checkBox.getFormLabelText('staywithinjobposting-checkbox') == 'Stay within job posting when eliminating'
        hpCampCheckBox.formCheckBoxInputTests(
                addUserPage.getCheckBoxes().stream().find({checkbox -> checkbox.id =='receiveDesktopAlerts-checkbox'}),
                currentTestMessage
        )
    }
}

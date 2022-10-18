package camp.element

import above.RunWeb
import camp.common.CampUser
import camp.common.page.element.CampButton
import camp.common.page.element.CampToolTip
import camp.common.page.users.CampUserRow
import camp.common.page.users.edit.CampEditUserPage
import camp.test.page.userspage.HpUsersPage
import camp.test.page.userspage.edit.HpEditUserPage

class UtCampToolTip extends RunWeb {
    static void main(String[] args) {
        new UtCampToolTip().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    CampEditUserPage editUserPage
    HpEditUserPage hpEditUserPage
    HpUsersPage hpUsersPage
    CampUser johnR
    CampToolTip toolTip
    CampUserRow userRow
    CampButton campButton

    void test() {
        setup([
                author  : 'jreumont',
                title   : 'UtCampToolTip',
                PBI     : 0,
                product : 'camp',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])
        editUserPage = new CampEditUserPage()
        hpEditUserPage = new HpEditUserPage()
        hpUsersPage = new HpUsersPage()
        userRow = new CampUserRow()
        toolTip = new CampToolTip()
        hpEditUserPage.setUserPage(editUserPage)
        johnR = new CampUser(CampUser.johnRUser)
        campButton = new CampButton()

        toolTip.setToolTipButtons(hpEditUserPage.userPage.getCancelToolTipButtons())
        toolTip.button.setFormInputTemplateXpath(hpEditUserPage.userPage.getCancelToolTipButtonsXpath())

        hpEditUserPage.loginAndNavigate(johnR.name)
        hpEditUserPage.formClearInput('roles', "Unit")

        def cancelButton = editUserPage.getFormButtons().stream().find({ button -> button.id.contains('cancel') })
        assert campButton.clickInputField(cancelButton.id)
        assert toolTip.waitForToolTip()

        def actualText = toolTip.getToolTipText().toUpperCase()
        def expectedText = hpEditUserPage.userPage.getCancelToolTipText().toUpperCase()
        assert actualText == expectedText
        assert toolTip.isToolTipButtonPresent('No')

        actualText = toolTip.getToolTipButtonLabelText('No').toUpperCase()
        expectedText = 'No'.toUpperCase()
        assert actualText == expectedText

        assert toolTip.clickToolTipButton('No')
        assert toolTip.waitForToolTipClosed()

        closeBrowser()
    }
}

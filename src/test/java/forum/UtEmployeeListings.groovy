package forum

import above.RunWeb
import forum.common.element.ForumLogin
import forum.common.element.EmployeeListing

class UtEmployeeListing extends RunWeb {
    static void main(String[] args) {
        new UtEmployeeListing().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'dejenkins',
                title   : 'UtEmployeeListing',
                PBI     : 0,
                product : 'forum',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        EmployeeListing employeeListing = new EmployeeListing()
        ForumLogin forumLogin = new ForumLogin()

        assert forumLogin.login()
        assert tryLoad('birthdays')
        assert employeeListing.getListingBlocks()
        assert employeeListing.getEmployeeBlocks(employeeListing.blockXpath)

        closeBrowser()
    }
}
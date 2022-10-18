package forum

import above.RunWeb
import forum.common.element.ForumLogin
import forum.common.element.globalmenu.ForumGlobalMenu

class UtForumGlobalMenu extends RunWeb{

    static void main(String[] args) {
        new UtForumGlobalMenu().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'dejenkins',
                title   : 'UtForumGlobalMenu',
                PBI     : 0,
                product : 'forum',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        ForumGlobalMenu forumGlobalMenu = new ForumGlobalMenu()
        ForumLogin forumLogin = new ForumLogin()


        assert forumLogin.login()
        assert forumGlobalMenu.isHeaderContainerPresent()
        assert forumGlobalMenu.isGlobalMenuButtonContainerPresent()
        assert forumGlobalMenu.isForumLogoPresent()
        assert forumGlobalMenu.isLogoClicked()
        assert forumGlobalMenu.isButtonPresent(forumGlobalMenu.globalMenuButtonsContainerXpath)
        assert forumGlobalMenu.isButtonClicked(forumGlobalMenu.badgesXpath)
        assert forumGlobalMenu.getMenuButtonsData()
        assert forumGlobalMenu.isLogoutButtonPresent()
        assert forumGlobalMenu.clickLogoutButton()

        closeBrowser()

    }

}


package forum

import above.RunWeb
import forum.common.element.ForumLogin

class UtForumLogin extends RunWeb {
    static void main(String[] args) {
        new UtForumLogin().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'gkohlhaas',
                title   : 'UtForumLogin',
                PBI     : 0,
                product : 'forum',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        ForumLogin forumLogin = new ForumLogin()
        assert forumLogin.login()

        closeBrowser()
    }
}
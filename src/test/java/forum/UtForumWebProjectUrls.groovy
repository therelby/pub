package forum

import above.RunWeb
import forum.common.element.ForumLogin

class UtForumWebProjectUrls extends RunWeb {
    static void main(String[] args) {
        new UtForumWebProjectUrls().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'gkohlhaas',
                title   : 'UtForumWebProjectUrls',
                PBI     : 0,
                product : 'forum',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        ForumLogin forumLogin = new ForumLogin()
        ForumWebProject forumWeb = new ForumWebProject()
        assert forumLogin.login()

        def linkList = forumWeb.pages.collect() { it['value'] }
        linkList.each {
            assert tryLoad(it)
        }
        closeBrowser()
    }
}
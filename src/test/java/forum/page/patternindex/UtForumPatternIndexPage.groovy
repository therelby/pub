package forum.page.patternindex

import above.RunWeb
import forum.common.element.ForumLogin
import forum.common.page.patternindex.ForumPatternIndexPage
import forum.test.page.patternindex.HpPatternIndexPage

class UtForumPatternIndexPage extends RunWeb {
    static void main(String[] args) {
        new UtForumPatternIndexPage().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'gkohlhaas',
                title   : 'Verify Forum Pattern Index Page layout and functionality',
                PBI     : 0,
                product : 'forum',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        ForumLogin forumLogin = new ForumLogin()
        ForumPatternIndexPage patternIndexPage = new ForumPatternIndexPage()
        HpPatternIndexPage hpPattern = new HpPatternIndexPage()
        assert forumLogin.login()
        assert tryLoad('patterns')
        assert verifyElement(patternIndexPage.contentWrapperXpath)
        assert verifyElement(patternIndexPage.patternWrapperXpath)
        def patternData = hpPattern.getPatternData()
        log patternData
        assert click("//a[@data-testid='pattern-1']")
        assert back()
        assert waitForPage()
        assert verifyElement("//a[@data-testid='pattern-9']")

        closeBrowser()
    }
}
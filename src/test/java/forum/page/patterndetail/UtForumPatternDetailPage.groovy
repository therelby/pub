package forum.page.patterndetail

import above.RunWeb
import forum.common.element.ForumLogin
import forum.common.page.patterndetail.ForumPatternDetailPage

class UtForumPatternDetailPage extends RunWeb {
    static void main(String[] args) {
        new UtForumPatternDetailPage().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'gkohlhaas',
                title   : 'Forum Pattern Details Page layout and functionality',
                PBI     : 0,
                product : 'forum',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        ForumLogin forumLogin = new ForumLogin()
        ForumPatternDetailPage patternDetail = new ForumPatternDetailPage()
        assert forumLogin.login()
        assert tryLoad('Pattern/Pattern?Id=1')
        assert verifyElement(patternDetail.pageWrapperXpath)
        assert verifyElement(patternDetail.leftSection.leftMenuContainerXpath)
        assert verifyElement(patternDetail.rightSection.discOverViewTextXpath)
        def headerText = getTextSafe(patternDetail.leftSection.headerXpath)
        assert headerText == patternDetail.headerText

        closeBrowser()
    }
}
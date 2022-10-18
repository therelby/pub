package forum

import above.RunWeb
import forum.common.element.ForumLogin
import forum.common.element.footer.ForumFooter

class UtForumFooter extends RunWeb {
    static void main(String[] args) {
        new UtForumFooter().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'gkohlhaas',
                title   : 'UtForumFooter',
                PBI     : 0,
                product : 'forum',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        ForumLogin forumLogin = new ForumLogin()
        ForumWebProject forumWeb = new ForumWebProject()
        ForumFooter forumFooter = new ForumFooter()
        assert forumLogin.login()

        def linkList = forumWeb.pages.collect() { it['value'] }
        linkList.each {
            assert tryLoad(it)

            assert scrollTo(forumFooter.footerContainerXpath)
            assert forumFooter.isFooterContainerPresent()
            assert forumFooter.isCopyrightPresent()
            def copyText = forumFooter.getCopyrightText()
            assert copyText == forumFooter.expectedValues.copyrightText
            log copyText

            assert forumFooter.isMailToPresent()
            def mailTo = forumFooter.getEmailMailTo()
            log mailTo
            assert mailTo == forumFooter.expectedValues.mailToEmail
        }
        closeBrowser()
    }
}
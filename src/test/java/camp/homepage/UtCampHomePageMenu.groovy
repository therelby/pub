package camp.homepage

import above.RunWeb
import camp.common.page.homepage.CampHomePageMenu
import camp.common.page.element.CampLogin

class UtCampHomePageMenu extends RunWeb {
    static void main(String[] args) {
        new UtCampHomePageMenu().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'gkohlhaas',
                title   : 'UtCampHomePageMenu',
                PBI     : 0,
                product : 'camp',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        CampLogin campLogin = new CampLogin()
        CampHomePageMenu menuElement = new CampHomePageMenu()
        assert campLogin.login()
        setCookie('tours', '{"seenHomePage":true,"seenCandidateSearchPage":true,"seenCandidatesPage":true}')
        refresh()
        sleep(4500) //do this for lazy loading

        assert menuElement.isMainContainerPresent()
        assert menuElement.isMenuContainerPresent()
        assert menuElement.isViewCandidatesButtonPresent()
        assert menuElement.isViewCandidatesIconPresent()
        assert menuElement.isViewCandidatesTextPresent()
        def viewCandidatesButtonText = menuElement.getViewCandidatesButtonText()
        assert viewCandidatesButtonText == menuElement.expectedValues.viewCandidatesText
        assert menuElement.clickViewCandidatesButton()
        assert back()

        assert menuElement.isManageUsersButtonPresent()
        assert menuElement.isManageUsersIconPresent()
        assert menuElement.isManageUsersTextPresent()

        closeBrowser()
    }
}
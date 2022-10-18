package camp.homepage

import above.RunWeb
import camp.common.page.element.CampLogin
import camp.common.page.homepage.CampHomePage

class UtCampHomePage extends RunWeb {
    static void main(String[] args) {
        new UtCampHomePage().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'gkohlhaas',
                title   : 'UtCampHomePage',
                PBI     : 0,
                product : 'camp',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        CampLogin campLogin = new CampLogin()
        CampHomePage homePage = new CampHomePage()
        assert campLogin.login()
        setCookie('tours', '{"seenHomePage":true,"seenCandidateSearchPage":true,"seenCandidatesPage":true}')
        refresh()
        sleep(1500) //do this for lazy loading

        assert homePage.isMainContainerPresent()
        assert homePage.isHomePageHeaderContainerPresent()
        assert homePage.isTitlePresent()
        assert homePage.isHeadingPresent()
        assert homePage.isDividerPresent()

        def titleText = homePage.getTitleText()
        assert titleText == homePage.expectedValues.pageTitleText

        def headingText = homePage.getHeadingText()
        assert headingText == homePage.expectedValues.pageHeadingText

        closeBrowser()
    }
}
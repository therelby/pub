package camp.global

import above.RunWeb
import camp.common.page.element.CampLogin
import camp.common.page.element.globalmenu.CampGlobalMenu

class UtCampGlobalMenu extends RunWeb {
    static void main(String[] args) {
        new UtCampGlobalMenu().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'gkohlhaas',
                title   : 'UtCampGlobalMenu',
                PBI     : 0,
                product : 'camp',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        CampLogin campLogin = new CampLogin()
        CampGlobalMenu globalMenu = new CampGlobalMenu()
        assert campLogin.login()
        setCookie('tours', '{"seenHomePage":true,"seenCandidateSearchPage":true,"seenCandidatesPage":true}')
        refresh()
        sleep(5000) //do this for lazy loading
        assert globalMenu.isHeaderContainerPresent()
        assert globalMenu.isLogoPresent()
        def tLogoUrl = globalMenu.getLogoUrl()
        assert tLogoUrl == "https://camp.dev.clarkinet.com/home"
        assert globalMenu.isHeaderDividerPresent()
        assert globalMenu.isNotificationIconPresent()

        assert globalMenu.isButtonContainerPresent()
        assert globalMenu.isJobsButtonPresent()
        assert globalMenu.isJobsTextPresent()
        def aJobsText = globalMenu.getJobsButtonText()
        def eJobsText = globalMenu.expectedValues.jobsText
        assert aJobsText == eJobsText
        def tJobsUrl = globalMenu.getJobsButtonUrl()
        assert globalMenu.clickJobsButton()
        def cUrl = getCurrentUrl()
        assert tJobsUrl == cUrl

        assert globalMenu.isCandidatesButtonPresent()
        assert globalMenu.isCandidatesTextPresent()
        def aCanText = globalMenu.getCandidatesButtonText()
        def eCanText = globalMenu.expectedValues.candidatesText
        assert aCanText == eCanText
        assert globalMenu.clickCandidatesButton()
        assert globalMenu.isNotificationIconPresent()

        assert globalMenu.isAccountButtonPresent()
        def aAccountButtonText = globalMenu.getAccountButtonText()
        def eAccountButtonText = globalMenu.expectedValues.accountNameText
        assert aAccountButtonText == eAccountButtonText
        assert globalMenu.isArrowIconPresent()
        assert globalMenu.isMergeCandidatesTextPresent()
        assert globalMenu.isReportsTextPresent()
        assert globalMenu.isAccountNameTextPresent()
        assert globalMenu.isPreEmploymentTextPresent()

        def targetReportsUrl = globalMenu.getReportsButtonUrl()
        assert targetReportsUrl == "https://camp.dev.clarkinet.com/reports"

        closeBrowser()
    }
}
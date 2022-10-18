package camp.homepage

import above.RunWeb
import camp.common.page.homepage.CampHomePageRecaps
import camp.common.page.element.CampLogin

class UtCampHomePageRecaps extends RunWeb {
    static void main(String[] args) {
        new UtCampHomePageRecaps().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'gkohlhaas',
                title   : 'UtCampHomePageRecaps',
                PBI     : 0,
                product : 'camp',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        CampLogin campLogin = new CampLogin()
        CampHomePageRecaps recapElement = new CampHomePageRecaps()
        assert campLogin.login()
        setCookie('tours', '{"seenHomePage":true,"seenCandidateSearchPage":true,"seenCandidatesPage":true}')
        refresh()
        sleep(2000) //do this for lazy loading

        assert recapElement.isMainContainerPresent()
        assert recapElement.isRecapsMainContainerPresent()
        assert recapElement.isRecapsHeaderContainerPresent()
        assert recapElement.isRecapsHeaderWrapperPresent()
        assert recapElement.isRecapsHeaderTextPresent()
        def getText = recapElement.getRecapsHeaderText()
        assert getText == recapElement.expectedValues.headerText

        assert recapElement.isTabsContainerPresent()
        assert recapElement.isTabsListPresent()
        assert recapElement.isPendingTabPresent()
        assert recapElement.isPendingTabTextPresent()
        def pendingText = recapElement.getPendingRecapsTabText()
        assert pendingText == recapElement.expectedValues.pendingTabText

        assert recapElement.clickCompletedRecapsTab()
        assert recapElement.clickPendingRecapsTab()
        assert recapElement.isPendingBodyContainerPresent()
        assert recapElement.isPendingPhoneIconPresent()
        assert recapElement.isPendingRecapsSubTextPresent()
        def subText = recapElement.getPendingRecapsSubText()
        assert subText == recapElement.expectedValues.pendingSubText

        assert recapElement.isCompletedTabPresent()
        assert recapElement.isCompletedTabTextPresent()
        def completedText = recapElement.getCompletedRecapsTabText()
        assert completedText == recapElement.expectedValues.completedTabText
        assert recapElement.clickCompletedRecapsTab()
        assert recapElement.isCompletedBodyContainerPresent()
        assert recapElement.isCompletedPhoneIconPresent()
        assert recapElement.isCompletedRecapsSubTextPresent()
        def completedSubText = recapElement.getCompletedRecapsSubText()
        assert completedSubText == recapElement.expectedValues.completedSubText

        closeBrowser()
    }
}
package camp.global

import above.RunWeb
import camp.common.page.element.CampLogin
import camp.common.page.element.globalmenu.CampGlobalMenuSearch

class UtCampGlobalMenuSearch extends RunWeb {
    static void main(String[] args) {
        new UtCampGlobalMenuSearch().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'gkohlhaas',
                title   : 'UtCampGlobalMenuSearch',
                PBI     : 0,
                product : 'camp',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        CampLogin campLogin = new CampLogin()
        CampGlobalMenuSearch globalMenu = new CampGlobalMenuSearch()
        assert campLogin.login()
        setCookie('tours', '{"seenHomePage":true,"seenCandidateSearchPage":true,"seenCandidatesPage":true}')
        refresh()
        sleep(3000) //do this for lazy loading
        assert globalMenu.isHeaderContainerPresent()

        assert globalMenu.isSearchContainerPresent()
        assert globalMenu.isSearchButtonPresent()
        assert globalMenu.isSearchFieldPresent()
        def defaultSearchText = globalMenu.getPlaceHolderSearchFieldText()
        def expectedSearchText = globalMenu.expectedValues.defaultSearchText
        assert defaultSearchText == expectedSearchText

        //todo more to come for drop down

        closeBrowser()

    }
}
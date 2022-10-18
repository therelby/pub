package camp.manageupdates

import above.RunWeb
import camp.common.page.element.CampLogin
import camp.common.page.element.CampManageUpdatesPageBanner
import camp.common.page.manageupdates.CampManageUpdatesPage

class UtSiteBannerMessage extends RunWeb {
    static void main(String[] args) {
        new UtSiteBannerMessage().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'gkohlhaas',
                title   : 'UtSiteBannerMessage',
                PBI     : 0,
                product : 'camp',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        CampManageUpdatesPage manageUpdatesPage = new CampManageUpdatesPage()
        CampLogin campLogin = new CampLogin()
        CampManageUpdatesPageBanner manageElement = new CampManageUpdatesPageBanner()
        assert campLogin.login()
        setCookie('tours', '{"seenHomePage":true,"seenCandidateSearchPage":true,"seenCandidatesPage":true}')
        assert manageUpdatesPage.navigate()
        //Handle Lazy Loading: Waits would not work here
        sleep(1500)

        assert manageElement.isHeaderContainerPresent()
        assert manageElement.isSiteBannerContainerPresent()
        assert manageElement.isSiteBannerTextPresent()
        def getBannerFieldText = manageUpdatesPage.getSiteBannerFieldText()
        log getBannerFieldText

        tryLoad('homepage')
        sleep(1500) //do this for lazy loading
        assert manageElement.isHeaderContainerPresent()
        assert manageElement.isSiteBannerContainerPresent()
        assert manageElement.isSiteBannerTextPresent()
        def getBannerText = manageElement.getSiteBannerText()
        log getBannerText
        assert getBannerFieldText == getBannerText

        closeBrowser()
    }
}
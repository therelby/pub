package camp.manageupdates

import above.RunWeb
import camp.common.page.element.CampLogin
import camp.common.page.manageupdates.CampManageUpdatesPage

class UtCampManageUpdatesPage extends RunWeb {
    static void main(String[] args) {
        new UtCampManageUpdatesPage().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'gkohlhaas',
                title   : 'UtCampManageUpdatesPage',
                PBI     : 0,
                product : 'camp',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        CampManageUpdatesPage manageUpdatesPage = new CampManageUpdatesPage()
        CampLogin campLogin = new CampLogin()
        assert campLogin.login()
        assert manageUpdatesPage.navigate()
        //Handle Lazy Loading: Waits would not work here
        sleep(1500)

        assert manageUpdatesPage.isManageUpdatesPage()
        assert manageUpdatesPage.isMainContainerPresent()
        assert manageUpdatesPage.isBackButtonPresent()
        def backButtonText = manageUpdatesPage.getBackButtonText()
        assert backButtonText == manageUpdatesPage.expectedValues.backButtonText
        assert manageUpdatesPage.isHeaderPresent()
        def headerText = manageUpdatesPage.getHeaderText()
        assert headerText == manageUpdatesPage.expectedValues.headerText
        assert manageUpdatesPage.isHomePageUpdatesLabelPresent()
        def updateText = manageUpdatesPage.getHomePageUpdatesLabelText()
        assert updateText == manageUpdatesPage.expectedValues.updatesLabel
        assert manageUpdatesPage.isSiteBannerFieldPresent()
        assert manageUpdatesPage.getSiteBannerFieldText()
        assert manageUpdatesPage.isSiteBannerXButtonPresent()
        assert manageUpdatesPage.clickSiteBannerXButton()
        assert manageUpdatesPage.getSiteBannerFieldText() == ''
        assert manageUpdatesPage.setSiteBannerText()
        assert manageUpdatesPage.getSiteBannerFieldText()
        assert manageUpdatesPage.isSiteBannerSubTextPresent()
        def subText = manageUpdatesPage.getSiteBannerSubText()
        assert subText == manageUpdatesPage.expectedValues.bannerSubText
        assert manageUpdatesPage.getSubmitButtonText()
        assert manageUpdatesPage.clickSubmitButton()
        assert manageUpdatesPage.clickBackButton()
        assert !manageUpdatesPage.isManageUpdatesPage()

        closeBrowser()
    }
}
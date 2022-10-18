package camp.global

import above.RunWeb
import camp.common.page.element.globalmenu.CampGlobalMenuDropDown
import camp.common.page.element.CampLogin

class UtCampGlobalMenuDropDown extends RunWeb {
    static void main(String[] args) {
        new UtCampGlobalMenuDropDown().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'gkohlhaas',
                title   : 'UtCampGlobalMenuDropDown',
                PBI     : 0,
                product : 'camp',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        CampLogin campLogin = new CampLogin()
        CampGlobalMenuDropDown menuElement = new CampGlobalMenuDropDown()
        assert campLogin.login()
        setCookie('tours', '{"seenHomePage":true,"seenCandidateSearchPage":true,"seenCandidatesPage":true}')
        refresh()
        sleep(3500) //do this for lazy loading

        assert menuElement.isHeaderContainerPresent()
        assert menuElement.isButtonContainerPresent()
        assert menuElement.isAccountButtonPresent()
        assert menuElement.clickAccountNameButton()
        assert menuElement.isDropDownContainerPresent()

        assert menuElement.isPreferencesButtonPresent()
        assert menuElement.isPreferencesTextPresent()
        def aPrefText = menuElement.getPreferencesButtonText()
        def ePrefText = menuElement.expectedValues.preferencesText
        assert aPrefText == ePrefText
        def aPrefUrl = menuElement.getPreferencesButtonUrl()
        assert aPrefUrl == "https://camp.dev.clarkinet.com/preferences"

        assert menuElement.isImpersonateButtonPresent()
        assert menuElement.isImpersonateTextPresent()
        def aImpText = menuElement.getImpersonateButtonText()
        def eImpText = menuElement.expectedValues.impersonateText
        assert aImpText == eImpText

        assert menuElement.isEditCategoriesButtonPresent()
        assert menuElement.isEditCategoriesTextPresent()
        def aEditCatText = menuElement.getEditCategoriesButtonText()
        def eEditCatText = menuElement.expectedValues.editCategoriesText
        assert aEditCatText == eEditCatText
        def aEditCatUrl = menuElement.getEditCategoriesButtonUrl()
        assert aEditCatUrl == "https://camp.dev.clarkinet.com/categories"

        assert menuElement.isManageUpdatesButtonPresent()
        assert menuElement.isManageUpdatesTextPresent()
        assert menuElement.isManageJobFairsButtonPresent()
        assert menuElement.isManageEmailsButtonPresent()
        assert menuElement.isLogoutIconPresent()

        assert menuElement.clickPreferencesButton()
        def currentUrl = getCurrentUrl()
        assert currentUrl == aPrefUrl
        sleep(3500) //do this for lazy loading
        assert menuElement.clickAccountNameButton()
        assert menuElement.isDropDownContainerPresent()
        assert menuElement.isLogoutButtonPresent()
        assert menuElement.isLogoutTextPresent()
        def logoutText = menuElement.getLogoutButtonText()
        assert logoutText == menuElement.expectedValues.logoutText
        assert menuElement.clickLogoutIcon()

        closeBrowser()
    }
}
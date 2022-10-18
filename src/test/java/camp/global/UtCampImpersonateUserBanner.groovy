package camp.global

import above.RunWeb
import camp.common.page.element.CampLogin
import camp.common.page.element.banner.CampImpersonateUserBanner
import camp.common.page.element.globalmenu.CampGlobalMenuDropDown
import camp.test.page.element.banner.impersonateuser.HpImpersonateUserBanner

class UtCampImpersonateUserBanner extends RunWeb {
    static void main(String[] args) {
        new UtCampImpersonateUserBanner().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'gkohlhaas',
                title   : 'Ut for CampImpersonateUserBanner',
                PBI     : 0,
                product : 'camp',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        CampLogin campLogin = new CampLogin()
        CampGlobalMenuDropDown menuElement = new CampGlobalMenuDropDown()
        CampImpersonateUserBanner impersonateBanner = new CampImpersonateUserBanner()
        HpImpersonateUserBanner hpBanner = new HpImpersonateUserBanner()
        assert campLogin.login()
        setCookie('tours', '{"seenHomePage":true,"seenCandidateSearchPage":true,"seenCandidatesPage":true}')
        refresh()
        waitForElementClickable(menuElement.mainMenuAccountNameButtonXpath, 10)

        assert hpBanner.verifyImpersonateUserScrollToBanner()

        assert elementVisible(impersonateBanner.bannerContainerXpath)
        assert elementVisible(impersonateBanner.iconXpath)
        assert elementVisible(impersonateBanner.contentContainerXpath)
        assert elementVisible(impersonateBanner.alertMessageXpath)
        assert elementVisible(impersonateBanner.alertWarningTextXpath)
        assert elementVisible(impersonateBanner.alertLineOneXpath)
        assert elementVisible(impersonateBanner.alertLineOneUserTextXpath)
        assert elementVisible(impersonateBanner.alertLineTwoTextXpath)
        assert elementVisible(impersonateBanner.xButtonXpath)

        def warningText = getTextSafe(impersonateBanner.alertWarningTextXpath)
        log warningText
        assert warningText == impersonateBanner.expectedValues.warningText

        def lineOneText = getTextSafe(impersonateBanner.alertLineOneXpath).replaceAll("[^:]*\$", '').trim()
        log lineOneText
        assert lineOneText == impersonateBanner.expectedValues.lineOneText

        def lineOneUserText = getTextSafe(impersonateBanner.alertLineOneUserTextXpath)
        log lineOneUserText
        lineOneUserText == "Aaron Weber [HR Admin]"

        def lineTwoText = getTextSafe(impersonateBanner.alertLineTwoTextXpath)
        log lineTwoText
        assert lineTwoText == impersonateBanner.expectedValues.lineTwoText

        assert click(impersonateBanner.xButtonXpath)
        assert waitForElementClickable(menuElement.mainMenuAccountNameButtonXpath, 10)
        assert !elementVisible(impersonateBanner.bannerContainerXpath)
        closeBrowser()

    }
}
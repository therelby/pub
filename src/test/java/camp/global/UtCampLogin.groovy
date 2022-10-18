package camp.global

import above.RunWeb
import camp.common.page.element.CampLogin
import camp.common.page.element.globalmenu.CampGlobalMenuDropDown

class UtCampLogin extends RunWeb {
    static void main(String[] args) {
        new UtCampLogin().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'gkohlhaas',
                title   : 'Ut for CampLogin as PIN',
                PBI     : 0,
                product : 'camp',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        CampLogin campLogin = new CampLogin()
        CampGlobalMenuDropDown menuElement = new CampGlobalMenuDropDown()
        assert campLogin.loginPin(campLogin.impersonateUserEmployeeNumbers[0],campLogin.impersonateUserPin)
        setCookie('tours', '{"seenHomePage":true,"seenCandidateSearchPage":true,"seenCandidatesPage":true}')
        refresh()
        waitForElementClickable(menuElement.mainMenuAccountNameButtonXpath, 10)

        closeBrowser()
    }
}
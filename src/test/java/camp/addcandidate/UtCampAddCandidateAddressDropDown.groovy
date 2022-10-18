package camp.addcandidate

import above.RunWeb
import camp.common.page.addcandidate.CampAddCandidatePage
import camp.common.page.element.CampAddress
import camp.common.page.element.CampDropDown
import camp.common.page.element.CampLogin
import camp.test.page.element.HpCampAddress

class UtCampAddCandidateAddressDropDown extends RunWeb {
    static void main(String[] args) {
        new UtCampAddCandidateAddressDropDown().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'jreumont',
                title   : 'UtCampAddCandidateAddressDropDown',
                PBI     : 0,
                product : 'camp',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        CampAddCandidatePage addCandidatePage = new CampAddCandidatePage()
        CampLogin campLogin = new CampLogin()
        HpCampAddress hpAddress = new HpCampAddress()

        assert campLogin.login()
        assert addCandidatePage.navigate()
        //Handle Lazy Loading: Waits would not work here
        sleep(1500)

        def currentTestMessage = "Unit Tests"
        assert hpAddress.formSetRandomAddressInput(currentTestMessage)
        assert hpAddress.formClearTextInput('address1', currentTestMessage)
        assert hpAddress.formSetAddressInput('2000 Aberdeen Dr Crofton, MD 21114', currentTestMessage)

        closeBrowser()
    }
}

package framework.wss.pages.servicepage.bentobox

import above.RunWeb
import wss.pages.servicepage.BentoBoxPage

class UtBentoBoxFillEmailAndZipCode extends RunWeb {

    static void main(String[] args) {
        new UtBentoBoxFillEmailAndZipCode().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    int pbi = 0

    void test() {
        setup([
                author  : 'mnazat',
                title   : 'Bento Box Email and Zip Code Unit Test',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'bento box email and zip code unit test',
                logLevel: 'info'
        ])

        BentoBoxPage bentoBoxPage = new BentoBoxPage()
        assert bentoBoxPage.navigate()

        assert bentoBoxPage.fillBentoBoxEmail("") == false
        assert bentoBoxPage.fillBentoBoxEmail("sample@test.com") == true
        assert bentoBoxPage.fillBentoBoxEmail("sample-test@gmail.com") == true
        assert bentoBoxPage.fillBentoBoxEmail("sample123@gmail.com") == true

        assert bentoBoxPage.fillBentoBoxZipCode("") == false
        assert bentoBoxPage.fillBentoBoxZipCode("12345") == true
        assert bentoBoxPage.fillBentoBoxZipCode("123456789") == true
        assert bentoBoxPage.fillBentoBoxZipCode("1234-12345") == true

        closeBrowser()
    }
}
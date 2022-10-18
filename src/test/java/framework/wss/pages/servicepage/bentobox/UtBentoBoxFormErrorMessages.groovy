package framework.wss.pages.servicepage.bentobox

import above.RunWeb
import wss.pages.servicepage.BentoBoxPage

class UtBentoBoxFormErrorMessages extends RunWeb {

    static void main(String[] args) {
        new UtBentoBoxFormErrorMessages().testExecute([
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
                title   : 'Bento Box Form Error Messages Unit Test',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'bento box form error messages unit test',
                logLevel: 'info'
        ])

        BentoBoxPage bentoBoxPage = new BentoBoxPage()
        assert bentoBoxPage.navigate()
        assert bentoBoxPage.clickFormDemoButton()

        def bentoBoxFormErrorMessages = bentoBoxPage.getBentoBoxFormErrorMessages()
        assert bentoBoxFormErrorMessages != null
        assert bentoBoxFormErrorMessages.size() > 0
        assert bentoBoxFormErrorMessages.size() == 6

        assert bentoBoxFormErrorMessages.get('name') == "Name is required"
        assert bentoBoxFormErrorMessages.get('restaurantName') == "Restaurant Name is required"
        assert bentoBoxFormErrorMessages.get('email') == "Email is required"
        assert bentoBoxFormErrorMessages.get('phoneNumber') == "Phone Number is required"
        assert bentoBoxFormErrorMessages.get('restaurantZipCode') == "Restaurant Zip Code is required"
        assert bentoBoxFormErrorMessages.get('privacyPolicy') == "Accept Terms & Conditions is required"

        closeBrowser()
    }
}
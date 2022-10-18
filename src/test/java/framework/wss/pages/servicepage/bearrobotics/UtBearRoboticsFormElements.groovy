package framework.wss.pages.servicepage.bearrobotics

import above.RunWeb
import wss.pages.servicepage.BearRoboticsPage

class UtBearRoboticsFormElements extends RunWeb {
    static void main(String[] args) {
        new UtBearRoboticsPageElements().testExecute([
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
                title   : 'Bear Robotics Form Elements Unit Test',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'bear robotics form elements unit test',
                logLevel: 'info'
        ])

        BearRoboticsPage bearRoboticsPage = new BearRoboticsPage()

        def bearRoboticsFormLabels = bearRoboticsPage.getBearRoboticsFormLabelsActualTexts()
        assert bearRoboticsFormLabels.size() > 0
        assert bearRoboticsFormLabels.size() == 10
        assert bearRoboticsFormLabels.get('FullName') == "Full Name *"
        assert bearRoboticsFormLabels.get('email') == "Email *"
        assert bearRoboticsFormLabels.get('Company') == "Company *"
        assert bearRoboticsFormLabels.get('IndustryType') == "Industry Type *"
        assert bearRoboticsFormLabels.get('PhoneNumber') == "Phone Number *"
        assert bearRoboticsFormLabels.get('Country') == "Country *"
        assert bearRoboticsFormLabels.get('City') == "City *"
        assert bearRoboticsFormLabels.get('HowCanWeHelp') == "How can we help you? *"
        assert bearRoboticsFormLabels.get('HowDidYouHear') == "How did you hear about us? *"

        assert bearRoboticsPage.getBearRoboticsFormInputElements() != null
        assert bearRoboticsPage.getBearRoboticsFormInputElements().size() > 0
        assert bearRoboticsPage.getBearRoboticsFormInputElements().size() == 7

        assert bearRoboticsPage.getBearRoboticsFormTextAreaElements() != null
        assert bearRoboticsPage.getBearRoboticsFormTextAreaElements().size() > 0
        assert bearRoboticsPage.getBearRoboticsFormTextAreaElements().size() == 2

        assert bearRoboticsPage.getTermsAndConditionsElements() != null
        assert bearRoboticsPage.getTermsAndConditionsElements().size() > 0
        assert bearRoboticsPage.getTermsAndConditionsElements().size() == 3

        def bearRoboticsTermsAndConditionsLinks = bearRoboticsPage.getTermsAndConditionsExpectedLinks()
        assert bearRoboticsTermsAndConditionsLinks != null
        assert bearRoboticsTermsAndConditionsLinks[0].contains("/policies.html")
        assert bearRoboticsTermsAndConditionsLinks[1].contains("/policies.html#Privacy_Policy")
        assert bearRoboticsTermsAndConditionsLinks[2] == "https://www.bearrobotics.ai/privacy"

        closeBrowser()
    }
}

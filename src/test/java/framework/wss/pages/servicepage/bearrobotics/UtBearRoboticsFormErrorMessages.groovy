package framework.wss.pages.servicepage.bearrobotics

import above.RunWeb
import wss.pages.servicepage.BearRoboticsPage

class UtBearRoboticsFormErrorMessages extends RunWeb{
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
                title   : 'Bear Robotics Form Error Messages Unit Test',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'bear robotics error messages unit test',
                logLevel: 'info'
        ])

        BearRoboticsPage bearRoboticsPage = new BearRoboticsPage()

        def bearRoboticsFormErrorMessages = bearRoboticsPage.getBearRoboticsFormErrorMessages()
        assert bearRoboticsFormErrorMessages.size() > 0
        assert bearRoboticsFormErrorMessages.size() == 11

        assert bearRoboticsFormErrorMessages.get('FullName') == "Full Name * is required"
        assert bearRoboticsFormErrorMessages.get('email') == "Email * is required"
        assert bearRoboticsFormErrorMessages.get('Company') == "Company * is required"
        assert bearRoboticsFormErrorMessages.get('IndustryType') == "Industry Type * is required"
        assert bearRoboticsFormErrorMessages.get('PhoneNumber') == "Phone Number * is required"
        assert bearRoboticsFormErrorMessages.get('Country') == "Country * is required"
        assert bearRoboticsFormErrorMessages.get('City') == "City * is required"
        assert bearRoboticsFormErrorMessages.get('StateProvince') == "State/Province * is required"
        assert bearRoboticsFormErrorMessages.get('HowCanWeHelp') == "How can we help you? * is required"
        assert bearRoboticsFormErrorMessages.get('HowDidYouHear') == "How did you hear about us? * is required"
        assert bearRoboticsFormErrorMessages.get('termstrue') == "Accept Terms & Conditions is required"

        closeBrowser()
    }
}

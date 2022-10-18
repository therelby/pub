package framework.wss.pages.servicepage.bearrobotics

import above.RunWeb
import wss.pages.servicepage.BearRoboticsPage

class UtBearRoboticsFillEmailAndPhone extends RunWeb{
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
                title   : 'Bear Robotics Email And Phone Number fields Unit Test',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'bear robotics email and phone number fields unit test',
                logLevel: 'info'
        ])

        BearRoboticsPage bearRoboticsPage = new BearRoboticsPage()

        assert bearRoboticsPage.fillBearRoboticsEmail("") == false
        assert bearRoboticsPage.fillBearRoboticsEmail("sample@test.com") == true
        assert bearRoboticsPage.fillBearRoboticsEmail("sample-test@gmail.com") == true
        assert bearRoboticsPage.fillBearRoboticsEmail("sample123@gmail.com") == true

        assert bearRoboticsPage.fillBearRoboticsPhoneNumber("") == false
        assert bearRoboticsPage.fillBearRoboticsPhoneNumber("8041111111") == true
        assert bearRoboticsPage.fillBearRoboticsPhoneNumber("804-111-1111") == true
        assert bearRoboticsPage.fillBearRoboticsPhoneNumber("(804) 111 1111") == true

        closeBrowser()
    }
}

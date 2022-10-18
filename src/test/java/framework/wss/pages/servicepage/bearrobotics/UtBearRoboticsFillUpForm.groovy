package framework.wss.pages.servicepage.bearrobotics

import above.RunWeb
import wss.pages.servicepage.BearRoboticsPage
import wss.user.UserDetail
import wss.user.UserType
import wss.user.userurllogin.UserUrlLogin

class UtBearRoboticsFillUpForm extends RunWeb{
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
                title   : 'Bear Robotics Form Unit Test',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'bear robotics form unit test',
                logLevel: 'info'
        ])

        BearRoboticsPage bearRoboticsPage = new BearRoboticsPage()

        log("--")
        def valuesToSet = [:]
        valuesToSet['FullName'] = "Sample Name"
        valuesToSet['email'] = "sample@gmail.com"
        valuesToSet['Company'] = "Sample Company"
        List industryTypeOptions = selectGetOptions(bearRoboticsPage.industryTypeDropdownXpath)?.findAll() { !getAllAttributesSafe(it).keySet().contains('disabled') }?.collect() { getTextSafe(it) }
        valuesToSet['IndustryType'] = industryTypeOptions?.shuffled()?.getAt(0)
        valuesToSet['PhoneNumber'] = "(407) 345-4345"
        valuesToSet['Country'] = "USA"
        valuesToSet['City'] = "Tampa"
        valuesToSet['StateProvince'] = "FL"
        valuesToSet['HowCanWeHelp'] = "Test"
        valuesToSet['HowDidYouHear'] = "Test"
        valuesToSet['termstrue'] = true
        log(valuesToSet)
        assert bearRoboticsPage.fillBearRoboticsForm(valuesToSet)

        log "--"
        def formData = bearRoboticsPage.getBearRoboticsFormData()
        log(formData)
        assert formData['FullName'] == valuesToSet['FullName']
        assert formData['email'] == valuesToSet['email']
        assert formData['Company'] == valuesToSet['Company']
        assert formData['IndustryType'] == valuesToSet['IndustryType']
        assert formData['PhoneNumber'] == valuesToSet['PhoneNumber']
        assert formData['Country'] == valuesToSet['Country']
        assert formData['City'] == valuesToSet['City']
        assert formData['StateProvince'] == valuesToSet['StateProvince']
        assert formData['HowCanWeHelp'] == valuesToSet['HowCanWeHelp']
        assert formData['HowDidYouHear'] == valuesToSet['HowDidYouHear']
        assert formData['termstrue'] == valuesToSet['termstrue']

        closeBrowser()
    }
}

package framework.wss.pages.servicepage.bearrobotics

import above.RunWeb
import wss.pages.servicepage.BearRoboticsPage

class UtBearRoboticsPageElements extends RunWeb {
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
                title   : 'Bear Robotics Page Elements Unit Test',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'bear robotics page elements unit test',
                logLevel: 'info'
        ])

        BearRoboticsPage bearRoboticsPage = new BearRoboticsPage()

        assert bearRoboticsPage.navigate()

        assert bearRoboticsPage.clickGetStartedTodayButton()

        assert bearRoboticsPage.getIntroducingServiGridImageWebElements() != null
        assert bearRoboticsPage.getIntroducingServiGridImageWebElements().size() > 0
        assert bearRoboticsPage.getIntroducingServiGridImageWebElements().size() == 3

        assert bearRoboticsPage.getIntroducingServiGridImageWebElements() != null
        assert bearRoboticsPage.getIntroducingServiGridImageWebElements().size() > 0
        assert bearRoboticsPage.getIntroducingServiGridImageWebElements().size() == 3

        assert bearRoboticsPage.getIntroducingServiGridHeaderWebElements() != null
        assert bearRoboticsPage.getIntroducingServiGridHeaderWebElements().size() > 0
        assert bearRoboticsPage.getIntroducingServiGridHeaderWebElements().size() == 3

        assert bearRoboticsPage.getIntroducingServiSmallHeaderWebElements() != null
        assert bearRoboticsPage.getIntroducingServiSmallHeaderWebElements().size() > 0
        assert bearRoboticsPage.getIntroducingServiSmallHeaderWebElements().size() == 3

        def bearRoboticsActualServiData = bearRoboticsPage.getActualServiImageGridElements()
        assert bearRoboticsActualServiData[0] == [
                imgLink         : "https://cdnimg.webstaurantstore.com/uploads/images/2021/11/servi01.jpg",
                header          : "Drink Serving",
                smallHeader     : "Stack up soft drinks and never spill a drop with Servi delivering drinks."
        ]
        assert bearRoboticsActualServiData[1] == [
                imgLink         : "https://cdnimg.webstaurantstore.com/uploads/images/2021/11/servi02.jpg",
                header          : "Food Running",
                smallHeader     : "Servi does the heavy lifting so you can focus on what matters - exceptional customer service."
        ]
        assert bearRoboticsActualServiData[2] == [
                imgLink         : "https://cdnimg.webstaurantstore.com/uploads/images/2021/11/servi03.jpg",
                header          : "Bussing",
                smallHeader     : "Clear tables in record time with Servi bussing dirty dishes back to the kitchen."
        ]

        def bearRoboticsIntelligenceSolutionsData = bearRoboticsPage.getIntelligentSolutionsGridActualTexts()
        assert bearRoboticsIntelligenceSolutionsData != null
        assert bearRoboticsIntelligenceSolutionsData[0] == "Equipped with LiDar sensors for 100% safe and self-driving navigation."

        assert bearRoboticsPage.getInformationTilesGridWebElements() != null
        assert bearRoboticsPage.getInformationTilesGridWebElements().size() > 0
        assert bearRoboticsPage.getInformationTilesGridWebElements().size() == 3

        assert bearRoboticsPage.getInformationTilesGridImageWebElements() != null
        assert bearRoboticsPage.getInformationTilesGridImageWebElements().size() > 0
        assert bearRoboticsPage.getInformationTilesGridImageWebElements().size() == 3

        assert bearRoboticsPage.getInformationTilesGridHeaderWebElements() != null
        assert bearRoboticsPage.getInformationTilesGridHeaderWebElements().size() > 0
        assert bearRoboticsPage.getInformationTilesGridHeaderWebElements().size() == 3

        assert bearRoboticsPage.getInformationTilesGridSmallHeaderWebElements() != null
        assert bearRoboticsPage.getInformationTilesGridSmallHeaderWebElements().size() > 0
        assert bearRoboticsPage.getInformationTilesGridSmallHeaderWebElements().size() == 3

        def bearRoboticsInformationTilesData = bearRoboticsPage.getInformationTilesActualTexts()
        assert bearRoboticsInformationTilesData[0] == [
                imgLink         : "https://cdnimg.webstaurantstore.com/uploads/design/2021/11/perk01.png",
                header          : "Support\nYour Staff",
                smallHeader     : "Expand stations and take on more tables at once without running back and forth from the kitchen to the table."
        ]
        assert bearRoboticsInformationTilesData[1] == [
                imgLink         : "https://cdnimg.webstaurantstore.com/uploads/design/2021/11/perk02.png",
                header          : "Improve Operational\nEfficiency",
                smallHeader     : "Reduce employee turnover, improve staff workflow, reduce labor hours."
        ]
        assert bearRoboticsInformationTilesData[2] == [
                imgLink         : "https://cdnimg.webstaurantstore.com/uploads/design/2021/11/perk03.png",
                header          : "Create Satisfied\nCustomers",
                smallHeader     : "Spend more time with guests and focus on customer experience for better tips and stellar reviews."
        ]

        closeBrowser()
    }
}
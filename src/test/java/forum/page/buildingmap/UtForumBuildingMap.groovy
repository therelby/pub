package forum.page.buildingmap

import above.RunWeb
import forum.ForumWebProject
import forum.common.element.ForumLogin
import forum.common.page.buildingmap.ForumBuildingMapPage

class UtForumBuildingMap extends RunWeb {
    static void main(String[] args) {
        new UtForumBuildingMap().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'gkohlhaas',
                title   : 'Verify building map id pages load successfully, Back To Forum button',
                PBI     : 0,
                product : 'forum',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        ForumLogin forumLogin = new ForumLogin()
        ForumWebProject forumWeb = new ForumWebProject()
        ForumBuildingMapPage buildingMap = new ForumBuildingMapPage()
        assert forumLogin.login()

        def linkList = forumWeb.buildingMapPages.collect() { it['value'] }
        linkList.each {
            assert tryLoad(it)

            assert buildingMap.isBackToForumButtonPresent()

            def actualButtonText = buildingMap.getBackToForumButtonText()
            assert actualButtonText == buildingMap.expectedValues.buttonText

            def buttonHref = buildingMap.getBackToForumButtonHref()
            assert buttonHref == buildingMap.expectedValues.buttonHref

            assert buildingMap.clickBackToForumButton()
            assert getCurrentUrl() == buttonHref

        }
        closeBrowser()
    }
}
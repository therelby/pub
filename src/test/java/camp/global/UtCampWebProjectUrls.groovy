package camp.global

import above.RunWeb
import camp.CampWebProject
import camp.common.page.element.CampLogin

class UtCampWebProjectUrls extends RunWeb {
    static void main(String[] args) {
        new UtCampWebProjectUrls().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'gkohlhaas',
                title   : 'UtCampWebProjectUrls',
                PBI     : 0,
                product : 'camp',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        CampLogin campLogin = new CampLogin()
        CampWebProject campWeb = new CampWebProject()
        assert campLogin.login()

        def linkList = campWeb.pages.collect() { it['value'] }
        linkList.each {
           assert tryLoad(it)
        }

        assert tryLoad('jobs')
        assert tryLoad('reports')
        assert tryLoad('manageupdates')
        assert tryLoad('clarkchallengereport')
        assert tryLoad('candidatesadd')
        closeBrowser()
    }
}
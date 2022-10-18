package camp.homepage

import above.RunWeb
import camp.common.page.homepage.CampHomePageSchedule
import camp.common.page.element.CampLogin

class UtCampHomePageSchedule extends RunWeb {
    static void main(String[] args) {
        new UtCampHomePageSchedule().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'gkohlhaas',
                title   : 'UtCampHomePageSchedule',
                PBI     : 0,
                product : 'camp',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])

        CampLogin campLogin = new CampLogin()
        CampHomePageSchedule scheduleElement = new CampHomePageSchedule()
        assert campLogin.login()
        setCookie('tours', '{"seenHomePage":true,"seenCandidateSearchPage":true,"seenCandidatesPage":true}')
        refresh()
        sleep(2000) //do this for lazy loading

        assert scheduleElement.isMainContainerPresent()
        assert scheduleElement.isScheduleMainContainerPresent()
        assert scheduleElement.isScheduleHeaderContainerPresent()
        assert scheduleElement.isScheduleHeaderWrapperPresent()
        assert scheduleElement.isScheduleHeaderTextPresent()
        def headerText = scheduleElement.getScheduleHeaderText()
        assert headerText == scheduleElement.expectedValues.headerText
        assert scheduleElement.isScheduleBodyPresent()
        assert scheduleElement.isCalendarIconPresent()
        assert scheduleElement.isSubTextOnePresent()
        def subTextOne = scheduleElement.getSubTextOne()
        assert subTextOne == scheduleElement.expectedValues.subTextOne

        closeBrowser()
    }
}
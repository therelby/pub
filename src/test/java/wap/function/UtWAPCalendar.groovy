package wap.function

import above.RunWeb
import wap.common.WAPLogin
import wap.common.content.pagecreator.WAPManagePageCreator
import wap.common.wapFunction.wapCalendar.WAPCalendar
import wap.common.wapFunction.wapCalendar.WAPCalendarRange

class UtWAPCalendar extends RunWeb {

    def userName
    def password

    UtWAPCalendar(def userName = WAPLogin.usernameDev, def password = WAPLogin.passwordDev) {
        this.userName = userName
        this.password = password
    }
    static void main(String[] args) {
        new UtWAPCalendar().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    def test() {

        setup('ttoanle', 'Admin Portal | DropDown Unit Text',
                ['product:wap', 'tfsProject:Webstaurant.AdminPortal',
                 'keywords:unit test table function',
                 'PBI: 0',
                 'logLevel:info'])
        WAPManagePageCreator pageCreator = new WAPManagePageCreator(userName,password)
        assert pageCreator.loadPageCreatorManagePages()
        waitForElementInvisible(pageCreator.loadingPageIconXpath)
        WAPCalendar calendar = new WAPCalendarRange("Active Start Date Range")
        assert calendar.isCalendarBoxDisplay()
        assert calendar.openCalendarDropDown()
        assert calendar.setDateRange('05/23/2022','06/28/2022')
        log(calendar.getCurrentDateRange())
        closeBrowser()
    }
}

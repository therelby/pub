package wap.content.productSpec

import above.RunWeb
import wap.common.WAPLogin
import wap.common.content.productspec.WAPFilterManagement

/**
 * Class unit test for filter management
 * @author ttoanle
 */
class UtWAPFilterManagement extends RunWeb {

    def userName
    def password

    UtWAPFilterManagement(def userName = WAPLogin.usernameDev, def password = WAPLogin.passwordDev) {
        this.userName = userName
        this.password = password
    }

    static void main(String[] args) {
        new UtWAPFilterManagement().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    def test() {

        setup('ttoanle', 'Admin Portal | filter management Unit Test',
                ['product:wap', 'tfsProject:Webstaurant.AdminPortal',
                 'keywords:unit test filter management',
                 'PBI: 0',
                 'logLevel:info'])
        WAPFilterManagement filterManagement = new WAPFilterManagement(userName,password)
        assert filterManagement.loginAndLoadFilterManagementPage()
        assert filterManagement.searchFilter('1/3 Size Pan Capacity')
        assert (filterManagement.getTableData().size() == 1)
        assert filterManagement.resetFilter()
        assert filterManagement.searchFilter('1/3 Size Pan Capacity',[IsShowOnSearch : true,IsShownOnCategory: true])
        assert (filterManagement.getTableData().size() == 1)
        closeBrowser()
    }
}

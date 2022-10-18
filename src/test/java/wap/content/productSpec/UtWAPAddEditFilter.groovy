package wap.content.productSpec

import above.RunWeb
import wap.common.WAPLogin
import wap.common.content.productspec.WAPAddEditFilter

/**
 * Class unit test for filter management
 * @author ttoanle
 */
class UtWAPAddEditFilter extends RunWeb {

    def userName
    def password

    UtWAPAddEditFilter(def userName = WAPLogin.usernameDev, def password = WAPLogin.passwordDev) {
        this.userName = userName
        this.password = password
    }

    static void main(String[] args) {
        new UtWAPAddEditFilter().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    def test() {

        setup('ttoanle', 'Admin Portal | filter management Unit Test',
                ['product:wap', 'tfsProject:Webstaurant.AdminPortal',
                 'keywords:unit test Add/Edit Filter',
                 'PBI: 0',
                 'logLevel:info'])
        WAPAddEditFilter addEditFilter = new WAPAddEditFilter(userName,password)
        assert addEditFilter.loginAndLoadAddEditFilter()
        assert addEditFilter.changeFilterShowDetails(true)
        assert addEditFilter.changeFilterShowDetails(false)
        assert addEditFilter.changeFilterShowDetails(true)
        assert addEditFilter.setShowOnSearch(true)
        assert addEditFilter.setShowOnSearch(false)
        assert addEditFilter.setShowOnSearch(false)
        assert addEditFilter.setShowOnSearch(true)
        assert addEditFilter.setShowOnSearch(true)
        assert addEditFilter.setShowOnSearch(false)
        assert addEditFilter.setShowOnCategory(false)
        assert addEditFilter.setExcludeFromCategory(true)
    }
}

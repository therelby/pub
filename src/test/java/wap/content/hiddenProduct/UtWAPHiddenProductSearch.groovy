package wap.content.hiddenProduct

import above.RunWeb
import wap.common.WAPLogin
import wap.common.content.hiddenproducts.WAPHpSearch

class UtWAPHiddenProductSearch extends RunWeb {

    def userName
    def password

    UtWAPHiddenProductSearch(def userName = WAPLogin.usernameDev, def password = WAPLogin.passwordDev) {
        this.userName = userName
        this.password = password
    }

    def test() {

        setup('ttoanle', 'Admin Portal | Hidden Product search Unit Test',
                ['product:wap', 'tfsProject:Webstaurant.AdminPortal',
                 'keywords:unit test hidden product search',
                 'PBI: 0',
                 'logLevel:info'])
        def classFilters =
                [
                        'A1 - Pending Content',
                        'A2 - Requires Additional info',
                        'A3 - Unavailable',
                        'C - Permanently Hidden',
                ]
        WAPHpSearch wapHpSearch = new WAPHpSearch(userName,password)

        assert wapHpSearch.loginAndLoadHiddenPageSearch()

        def filterDropDowns = wapHpSearch.getDropDownOptions()
        assert filterDropDowns.size() > 0
        assert filterDropDowns.contains('A1 - Pending Content')
        assert filterDropDowns.contains('A2 - Requires Additional Info')
        assert wapHpSearch.selectClassFilter('A1 - Pending Content')
        assert wapHpSearch.getSelectedClassFilter()[0].equals('A1 - Pending Content')
        assert wapHpSearch.selectSearchOptions(['Item','Vendor'])
        closeBrowser()
    }
}

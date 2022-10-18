package wap.content.hiddenProduct

import above.RunWeb
import wap.common.WAPLogin
import wap.common.content.hiddenproducts.WAPHpSearch

class UtWAPHpSearchFunction extends RunWeb {

    def userName
    def password

    UtWAPHpSearchFunction(def userName = WAPLogin.usernameDev, def password = WAPLogin.passwordDev) {
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



        def filter =
                [
                        Class : ['A1 - Pending Content','A2 - Requires Additional Info'],
                        Search: [option : ['Item #'],searchText : ['105282P11212','4071916PBPH']],
                        ExactSearch:false,
                        Writers:['gleach','jtorres'],
                        HasImage:['Yes'],
                ]
        waitForPage()
        assert wapHpSearch.filterHiddenProduct(filter)
        closeBrowser()
    }
}

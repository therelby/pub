package wap.function

import above.RunWeb
import all.util.StringUtil
import wap.common.WAPLogin
import wap.common.content.hiddenproducts.WAPHpSearch
import wap.common.wapFunction.WAPTable

class UtWAPTable extends RunWeb {

    def userName
    def password

    UtWAPTable(def userName = WAPLogin.usernameDev, def password = WAPLogin.passwordDev) {
        this.userName = userName
        this.password = password
    }
    static void main(String[] args) {
        new UtWAPTable().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    def test() {

        setup('ttoanle', 'Admin Portal | Table function Unit Test',
                ['product:wap', 'tfsProject:Webstaurant.AdminPortal',
                 'keywords:unit test table function',
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

        wapHpSearch.filterHiddenProduct([ Class : ['A1 - Pending Content','B - Unavailable','E - Waiting for Stock'],HasImage:['Rejected'] ])
        WAPTable wapTable = new WAPTable(getPageSource())
        assert wapTable.tableData.size() > 0

        /**
         * Check sorting for item number column
         * By default it should be sorted as asc
         * Click on the column and check the column is sorted desc
         */
        assert wapTable.isColumnSorted('Item #','asc')
        assert !(wapTable.isColumnSorted('Item #','desc'))
        tryClick(StringUtil.formatWithMap(wapHpSearch.columnXpath,[columnName:'Item #' ]))
        waitForElementInvisible(wapHpSearch.loadingTableIcon)
        wapTable = new WAPTable(getPageSource())
        assert wapTable.isColumnSorted('Item #','desc')

        /**
         * Check sorting for column class
         * By default this column will not be sorted
         * Click on the column and check the column is sorted asc
         * Click on the column again and check the column is sorted desc
         */
        assert !(wapTable.isColumnSorted('Class'))
        tryClick(StringUtil.formatWithMap(wapHpSearch.columnXpath,[columnName:'Class'] ))
        waitForElementInvisible(wapHpSearch.loadingTableIcon)
        wapTable = new WAPTable(getPageSource())
        assert wapTable.isColumnSorted('Class','asc')

        tryClick(StringUtil.formatWithMap(wapHpSearch.columnXpath,[columnName:'Class']))
        waitForElementInvisible(wapHpSearch.loadingTableIcon)
        wapTable = new WAPTable(getPageSource())
        assert wapTable.isColumnSorted('Class','desc')
        closeBrowser()

        WAPLogin login = new WAPLogin()
        login.loginAndLoadPage('',"/External/Virtual%20Grouping/Upsert?virtualGroupId=4978")
        sleep(3000)
        tryClick("//span[text()='See History']/parent::button")
        sleep(10000)
        WAPTable table = new WAPTable()
        log(table.getDataInTable("//div[contains(@class,'upsert-modal__table')]//table"))

        closeBrowser()
    }
}

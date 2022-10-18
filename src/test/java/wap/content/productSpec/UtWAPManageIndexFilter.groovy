package wap.content.productSpec

import above.RunWeb
import wap.common.WAPLogin
import wap.common.content.productspec.WAPManageIndexFilter

/**
 * Class unit test for filter management
 * @author ttoanle
 */
class UtWAPManageIndexFilter extends RunWeb {

    def userName
    def password

    UtWAPManageIndexFilter(def userName = WAPLogin.usernameDev, def password = WAPLogin.passwordDev) {
        this.userName = userName
        this.password = password
    }

    static void main(String[] args) {
        new UtWAPManageIndexFilter().testExecute([
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
        def testCategory = ['Dunnage Racks', '10 Strawberry Street Arctic Blue Porcelain Dinnerware', 'Air Curtain Merchandiser Parts and Accessories']
        WAPManageIndexFilter manageIndexFilter = new WAPManageIndexFilter(userName,password)
        assert manageIndexFilter.loginAndLoadManageIndexFilterPage()
        assert manageIndexFilter.searchCategory(testCategory)
        def categories = manageIndexFilter.getCategoriesInLeftForm()
        Collections.sort(testCategory)
        Collections.sort(categories)
        assert categories.equals(testCategory)

        log(manageIndexFilter.getFiltersFromCategory('Dunnage Racks'))
        log(manageIndexFilter.getFiltersFromCategory('10 Strawberry Street Arctic Blue Porcelain Dinnerware'))
        log(manageIndexFilter.getSubTypesFromFilters('Air Curtain Merchandiser Parts and Accessories','Type'))
        log(manageIndexFilter.getAllCategoriesFilterAndSubTypes('Dunnage Racks'))
        closeBrowser()
    }
}

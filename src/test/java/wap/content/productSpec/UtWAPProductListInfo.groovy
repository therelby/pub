package wap.content.productSpec

import above.RunWeb
import wap.common.WAPLogin
import wap.common.content.productspec.WAPAddEditFilter
import wap.common.content.productspec.WAPProductInfoListing

/**
 * Class unit test for filter management
 * @author ttoanle
 */
class UtWAPProductListInfo extends RunWeb {

    def userName
    def password

    UtWAPProductListInfo(def userName = WAPLogin.usernameDev, def password = WAPLogin.passwordDev) {
        this.userName = userName
        this.password = password
    }

    static void main(String[] args) {
        new UtWAPProductListInfo().testExecute([
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
        WAPProductInfoListing infoListing = new WAPProductInfoListing(userName,password)
        assert infoListing.loginAndLoadProductInfoListingPage()
        assert infoListing.selectSearchOption('ShowSpecTable')
        assert infoListing.searchInformationListing('category','10 Strawberry Street Crown Royal Flatware 18/0')
        assert waitForElementVisible(infoListing.tableXpath)
        assert infoListing.getTableInformation() != []
    }
}

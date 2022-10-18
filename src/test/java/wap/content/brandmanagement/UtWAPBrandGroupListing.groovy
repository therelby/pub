package wap.content.brandmanagement

import above.RunWeb
import wap.common.content.brandmanagement.brandgrouplisting.WAPBrandGroupListing
import wap.common.WAPLogin

class UtWAPBrandGroupListing extends RunWeb {

    def userName
    def password

    UtWAPBrandGroupListing(def userName = WAPLogin.usernameDev, def password = WAPLogin.passwordDev) {
        this.userName = userName
        this.password = password
    }

    def test() {

        setup('chorne', 'Admin Portal | Brand Group Listing Unit Test',
                ['product:wap', 'tfsProject:Webstaurant.AdminPortal',
                 'keywords:unit test admin portal wap brand group',
                 'PBI: 0',
                 'logLevel:info'])
        WAPBrandGroupListing brandGroupListing = new WAPBrandGroupListing(userName,password)

        assert brandGroupListing.loadBrandGroupListingPage()

        Map headerData = brandGroupListing.getHeaderData()
        assert headerData['Header Text'] == brandGroupListing.headerText
        assert headerData['Button Text'] == brandGroupListing.pageHeaderButtonText
        assert headerData['Button Link'] == "https://adminportal.dev.webstaurantstore.com/Content/BrandManagement/BrandGroup"

        List columns = brandGroupListing.getAllTableColumns()
        assert columns.size() > 0
        assert columns.contains('Parent Cache Entry')
        assert columns.contains('Children Cache Entries')
        assert columns.contains('Actions')

        List<Map> tableData = brandGroupListing.getTableData()
        assert tableData.size() > 0
        assert !tableData.contains("null") && !tableData.contains(null)

        for (data in tableData) {
            assert data['Parent Cache Entry'].toString() != ""
            assert data['Children Cache Entries'].toString().toList().size() > 0
            assert data['Actions'].toString().toList().size() > 0
        }
    }
}

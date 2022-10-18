package wap.content.brandmanagement

import above.RunWeb
import wap.common.WAPLogin
import wap.common.content.brandmanagement.vendorlisting.WAPVendorListing
import wap.common.wapFunction.wapPaginiation.WAPPagination
import wap.common.wapFunction.wapPaginiation.WAPPaginationSelect

class UtWAPVendorListingPagination extends RunWeb {

    def userName
    def password

    UtWAPVendorListingPagination(Object userName = WAPLogin.usernameDev, Object password = WAPLogin.passwordDev) {
        this.userName = userName
        this.password = password
    }

    def test() {

        setup('chorne', 'Admin Portal | Vendor Listing Unit Test',
                ['product:wap', 'tfsProject:Webstaurant.AdminPortal',
                 'keywords:unit test admin portal wap brand group',
                 'PBI: 0',
                 'logLevel:info'])
        def dropdownOptions = ["50 / page", "100 / page", "250 / page", "500 / page"]
        WAPVendorListing wapVendorListing = new WAPVendorListing(userName,password)
        WAPPagination pagination = new WAPPaginationSelect()

        assert wapVendorListing.loadVendorListingPage()
        assert wapVendorListing.setSearchField("Vendor Name", "as", false)
        assert wapVendorListing.clickSearchButton()

        // Pagination - Select Page
        assert pagination.getNumberOfPages() > 0
        assert pagination.getCurrentPageNumber() == 1
        assert pagination.clickNextPage()
        assert pagination.getCurrentPageNumber() == 2
        assert pagination.clickPreviousPage()
        assert pagination.getCurrentPageNumber() == 1
        assert pagination.clickOnLastPage()
        assert pagination.getCurrentPageNumber() == pagination.getNumberOfPages()
        assert pagination.clickFirstPage()
        assert pagination.getCurrentPageNumber() == 1

        // Pagination - Items Per Page
        assert pagination.getPaginationItemsPerPageOptions().size() != 0
        assert pagination.getPaginationItemsPerPageOptions() == dropdownOptions
        assert pagination.getCurrentPaginationItemsPerPage() != ""
        assert pagination.setPaginationItemsPerPage(100)
        assert pagination.getCurrentPaginationItemsPerPage() == "100 / page"
        closeBrowser()
    }
}
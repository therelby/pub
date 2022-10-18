package wap.content.brandmanagement

import above.RunWeb
import wap.common.WAPLogin
import wap.common.content.brandmanagement.vendorlisting.WAPVendorListing

class UtWAPVendorListing extends RunWeb {

    def userName
    def password

    UtWAPVendorListing(Object userName = WAPLogin.usernameDev, Object password = WAPLogin.passwordDev) {
        this.userName = userName
        this.password = password
    }

    def test() {

        setup('chorne', 'Admin Portal | Vendor Listing Unit Test',
                ['product:wap', 'tfsProject:Webstaurant.AdminPortal',
                 'keywords:unit test admin portal wap brand group',
                 'PBI: 0',
                 'logLevel:info'])
        WAPVendorListing wapVendorListing = new WAPVendorListing(userName,password)

        assert wapVendorListing.loadVendorListingPage()
        assert getCurrentUrl().contains(wapVendorListing.pageUrl)

        assert verifyElement(wapVendorListing.pageMainDiv)
        assert verifyElement(wapVendorListing.headerTitleXpath)
        assert getTextSafe(wapVendorListing.headerTitleXpath) == wapVendorListing.pageHeaderText

        assert verifyElement(wapVendorListing.searchFieldsMainDiv)
        for (key in wapVendorListing.searchFieldElements.keySet()) {
            assert verifyElement(wapVendorListing.searchFieldElements[key])
        }
        for (key in wapVendorListing.buttonElements.keySet()) {
            assert verifyElement(wapVendorListing.buttonElements[key])
        }

        assert getTextSafe(wapVendorListing.searchFieldElements.vendorNameHeader) == wapVendorListing.searchFieldNames.vendorNameField
        assert getTextSafe(wapVendorListing.searchFieldElements.searchRankHeader) == wapVendorListing.searchFieldNames.searchRankField
        assert getTextSafe(wapVendorListing.searchFieldElements.isOurBrandHeader) == wapVendorListing.searchFieldNames.isOurBrandField
        assert getTextSafe(wapVendorListing.searchFieldElements.showSidebySideHeader) == wapVendorListing.searchFieldNames.showSidebySideField
        assert getTextSafe(wapVendorListing.searchFieldElements.adminCommentsHeader) == wapVendorListing.searchFieldNames.adminCommentsField
        assert getTextSafe(wapVendorListing.buttonElements.search) == wapVendorListing.buttonNames.search
        assert getTextSafe(wapVendorListing.buttonElements.reset) == wapVendorListing.buttonNames.reset
        assert getTextSafe(wapVendorListing.buttonElements.exportResults) == wapVendorListing.buttonNames.exportResults
        assert getTextSafe(wapVendorListing.buttonElements.exportAllVendors) == wapVendorListing.buttonNames.exportAllVendors
        assert getTextSafe(wapVendorListing.buttonElements.newVendorPage) == wapVendorListing.buttonNames.newVendorPage
    }
}
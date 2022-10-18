package wap

import above.RunWeb
import wss.adminportal.AdminPortal
import wss.adminportal.vendor.AdminPortalVendor

class AdminPortalVendorUnitTest extends RunWeb {
    def test() {

        setup('vdiachuk', 'AdminPortal Vendors unit test | Framework Self ' +
                'Testing Tool',
              ['product:wap', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test adminportal admin vendor ' +
                      'portal ',
               "tfsTcIds:265471", 'logLevel:info'])

        log "Admin Portal Url:${getUrl("adminportal")}"
        //   loadPage("${getUrl( "adminportal")}")
        tryLoad("default")
        assert AdminPortal.adminPortalLogin()
        AdminPortalVendor.navigateHere()
        AdminPortalVendor.editVendorById(1589)
        waitForPage()
    //    log AdminPortalVendor.editVendorReadSearchRank()
        AdminPortalVendor.editVendorSetSearchRank(-1)
        AdminPortalVendor.updateVendor()
        log "After change:" + AdminPortalVendor.editVendorReadSearchRank()
        assert AdminPortalVendor.editVendorReadSearchRank() == -1
        AdminPortalVendor.editVendorSetSearchRank(1)
        AdminPortalVendor.updateVendor()
        log "After change:" + AdminPortalVendor.editVendorReadSearchRank()
        assert AdminPortalVendor.editVendorReadSearchRank() == 1

    }
}

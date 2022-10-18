package wap.logistics.installationservice

import above.RunWeb
import wap.common.WAPLogin
import wap.common.logistics.installationservice.WAPPricingMaintenance

class UtWAPPricingMaintenanceTableData extends RunWeb {

    def userName
    def password

    UtWAPPricingMaintenanceTableData(Object userName = WAPLogin.usernameDev, Object password = WAPLogin.passwordDev) {
        this.userName = userName
        this.password = password
    }

    def test() {

        setup('chorne', 'Admin Portal | Logistics | Pricing Maintenance Table Data Unit Test',
                ['product:wap', 'tfsProject:Webstaurant.AdminPortal',
                 'keywords:unit test admin portal wap pricing maintenance ui',
                 'PBI: 0',
                 'logLevel:info'])
        WAPPricingMaintenance wapPricingMaintenance = new WAPPricingMaintenance(userName, password)

        assert wapPricingMaintenance.loadPage()
        assert getCurrentUrl().contains(wapPricingMaintenance.pageUrl)

        waitForElement(wapPricingMaintenance.headerTitleXpath, 10)

        log "=="
        log "Checking Table Data"

        assert verifyElement(wapPricingMaintenance.tableMainDiv)

        List<Map> tableData = wapPricingMaintenance.getTableData()
        assert tableData.size() > 0
        for (value in tableData) {
            assert value['RowKey'] != "" || value['RowKey'] != null
            assert value['Name'] != "" || value['Name'] != null
            assert value['Vendor'] != "" || value['Vendor'] != null
            assert value['Address'] != "" || value['Address'] != null
            assert value['Priced'] != "" || value['Priced'] != null
            for (button in value['Action']) {
                assert button != "" || button != null
            }
        }
        closeBrowser()
    }
}
package wap.logistics.installationservice

import above.RunWeb
import wap.common.WAPLogin
import wap.common.logistics.installationservice.WAPPricingMaintenanceModal

class UtWAPPricingMaintenanceModal extends RunWeb {

    def userName
    def password

    UtWAPPricingMaintenanceModal(Object userName = WAPLogin.usernameDev, Object password = WAPLogin.passwordDev) {
        this.userName = userName
        this.password = password
    }

    def test() {

        setup('chorne', 'Admin Portal | Logistics | Pricing Maintenance Modal Unit Test',
                ['product:wap', 'tfsProject:Webstaurant.AdminPortal',
                 'keywords:unit test admin portal wap pricing maintenance modal',
                 'PBI: 0',
                 'logLevel:info'])
        WAPPricingMaintenanceModal pricingMaintenanceModal = new WAPPricingMaintenanceModal(userName, password)

        assert pricingMaintenanceModal.loadPage()
        assert getCurrentUrl().contains(pricingMaintenanceModal.pageUrl)
        waitForElement(pricingMaintenanceModal.headerTitleXpath, 10)

        assert pricingMaintenanceModal.clickEditButton("Tech24")

        log "=="
        log "Checking General Methods"

        assert pricingMaintenanceModal.isModalOpen()
        assert pricingMaintenanceModal.isPricingTabsPresent()
        assert pricingMaintenanceModal.getHeaderText() == "Add Pricing - TEC100 - Tech24 (Id 1)"
        assert pricingMaintenanceModal.getActiveTier() == "Tier 1"
        assert pricingMaintenanceModal.getAllModalColumns() == pricingMaintenanceModal.expectedColumns

        log "=="
        log "Checking Modal Data"

        List<Map> modalDataTier1 = pricingMaintenanceModal.getTierPricingData()
        assert modalDataTier1.size() > 0
        for (value in modalDataTier1) {
            assert value['Installation Type'] != "" || value['Installation Type'] != null
            assert value['Cost'] != "" || value['Cost'] != null
            assert value['Price'] != "" || value['Price'] != null
        }

        assert pricingMaintenanceModal.switchPricingTier("Tier 2")
        List<Map> modalDataTier2 = pricingMaintenanceModal.getTierPricingData()
        assert modalDataTier2.size() > 0
        for (value in modalDataTier2) {
            assert value['Installation Type'] != "" || value['Installation Type'] != null
            assert value['Cost'] != "" || value['Cost'] != null
            assert value['Price'] != "" || value['Price'] != null
        }

        assert pricingMaintenanceModal.switchPricingTier("Tier 3")
        List<Map> modalDataTier3 = pricingMaintenanceModal.getTierPricingData()
        assert modalDataTier2.size() > 0
        for (value in modalDataTier3) {
            assert value['Installation Type'] != "" || value['Installation Type'] != null
            assert value['Cost'] != "" || value['Cost'] != null
            assert value['Price'] != "" || value['Price'] != null
        }

        log "=="
        log "Checking Modal Buttons"

        assert pricingMaintenanceModal.clickUpdateAllLocations()
        assert pricingMaintenanceModal.clickCancelButton()
        assert !pricingMaintenanceModal.isModalOpen()

        assert pricingMaintenanceModal.clickEditButton("Tech24")

        assert pricingMaintenanceModal.isModalOpen()
        assert pricingMaintenanceModal.clickXButton()
        assert !pricingMaintenanceModal.isModalOpen()

        assert pricingMaintenanceModal.clickEditButton("Tech24")

        assert pricingMaintenanceModal.isModalOpen()
        assert pricingMaintenanceModal.clickUpdatePricingButton()
        assert !pricingMaintenanceModal.isModalOpen()

        assert pricingMaintenanceModal.clickAddPricingButton()
        assert pricingMaintenanceModal.isModalOpen()

        log "=="
        log "Checking Modal Fields - Add Pricing Modal"

        List<String> expectedTypes = WAPPricingMaintenanceModal.InstallationType.values().collect() { it.type }

        expectedTypes.each {
            assert pricingMaintenanceModal.updatePricingField(it, "Cost", "1")
            assert pricingMaintenanceModal.updatePricingField(it, "Price", "2")
        }

        assert pricingMaintenanceModal.clickCancelButton()

        assert pricingMaintenanceModal.clickEditButton("Tech24")

        assert pricingMaintenanceModal.isModalOpen()

        log "=="
        log "Checking Modal Fields - Edit Modal"

        expectedTypes.each {
            assert pricingMaintenanceModal.updatePricingField(it, "Cost", "1")
            assert pricingMaintenanceModal.updatePricingField(it, "Price", "2")
        }

        assert pricingMaintenanceModal.clickCancelButton()
        closeBrowser()
    }
}
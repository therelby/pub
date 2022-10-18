package wap.logistics.installationservice

import above.RunWeb
import all.util.StringUtil
import wap.common.WAPLogin
import wap.common.logistics.installationservice.WAPPricingMaintenance
import wap.common.logistics.installationservice.WAPPricingMaintenanceModal

class UtWAPPricingMaintenanceElements extends RunWeb {

    def userName
    def password

    UtWAPPricingMaintenanceElements(Object userName = WAPLogin.usernameDev, Object password = WAPLogin.passwordDev) {
        this.userName = userName
        this.password = password
    }

    def test() {

        setup('chorne', 'Admin Portal | Logistics | Pricing Maintenance Elements Unit Test',
                ['product:wap', 'tfsProject:Webstaurant.AdminPortal',
                 'keywords:unit test admin portal wap pricing maintenance ui',
                 'PBI: 0',
                 'logLevel:info'])
        WAPPricingMaintenance wapPricingMaintenance = new WAPPricingMaintenance(userName, password)

        assert wapPricingMaintenance.loadPage()
        assert getCurrentUrl().contains(wapPricingMaintenance.pageUrl)

        waitForElement(wapPricingMaintenance.headerTitleXpath, 10)

        assert verifyElement(wapPricingMaintenance.pageMainDiv)
        assert verifyElement(wapPricingMaintenance.headerTitleXpath)
        assert getTextSafe(wapPricingMaintenance.headerTitleXpath) == "Installation Pricing"

        log "=="
        log "Checking Dropdown Elements"

        assert verifyElement(wapPricingMaintenance.dropdownBox)
        assert click(wapPricingMaintenance.dropdownBox)
        assert waitForElementVisible(wapPricingMaintenance.dropdownOptions)
        assert verifyElement(wapPricingMaintenance.addPricingButton)
        assert click(wapPricingMaintenance.headerTitleXpath)

        log "=="
        log "Checking Table Elements"

        assert verifyElement(wapPricingMaintenance.tableMainDiv)
        for (key in wapPricingMaintenance.tableElements.keySet()) {
            assert verifyElement(key == 'editButton' || key == 'deleteButton' ? StringUtil.formatWithMap(wapPricingMaintenance.tableElements[key], [id: '1']) : wapPricingMaintenance.tableElements[key])
        }

        log "=="
        log "Checking Delete Message Elements"

        assert click(StringUtil.formatWithMap(wapPricingMaintenance.tableElements.deleteButton, [id: 1]))
        assert waitForElementVisible(wapPricingMaintenance.deleteMessageMainDiv)
        for (key in wapPricingMaintenance.deleteMessageElements.keySet()) {
            assert verifyElement(wapPricingMaintenance.deleteMessageElements[key])
        }

        log "=="
        log "Checking Edit Modal Elements"

        WAPPricingMaintenanceModal wapPricingMaintenanceEditModal = new WAPPricingMaintenanceModal(userName, password)
        assert wapPricingMaintenance.clickEditButton("Tech24")
        assert verifyElement(wapPricingMaintenanceEditModal.modalActiveTier)
        for (key in wapPricingMaintenanceEditModal.modalElements.keySet()) {
            if (key == "newTab" || key == "costField" || key == "priceField") {
                continue
            }
            assert verifyElement(wapPricingMaintenanceEditModal.modalElements[key])
        }
        closeBrowser()
    }
}
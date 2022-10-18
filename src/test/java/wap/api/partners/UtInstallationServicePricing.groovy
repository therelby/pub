package wap.api.partners

import above.RunWeb
import wap.common.wapApi.logistics.WAPLogisticsAPI

class UtInstallationServicePricing extends RunWeb {

    def test() {

        setup('chorne', 'Admin Portal | Partners Admin API | Installation Service Pricing Unit Test',
                ['product:wap', 'tfsProject:Webstaurant.AdminPortal',
                 'keywords:unit test admin portal wap partners api installation service pricing',
                 'PBI: 0',
                 'logLevel:info'])

        WAPLogisticsAPI logisticsAPI = new WAPLogisticsAPI()

        log "=="
        log "Checking End Point - /api/installationService/pricing"
        log "=="
        assert logisticsAPI.getInstallationServicePricingData() as Boolean

        log "=="
        log "Checking End Point - /api/installationService/pricing/getInstallationServices"
        log "=="
        assert logisticsAPI.getInstallationServiceServicesData() as Boolean

        log "=="
        log "Checking End Point - /api/installationService/pricing/getProviderLocations"
        log "=="
        assert logisticsAPI.getInstallationServiceProviderData() as Boolean

        log "=="
        log "Checking End Point - /api/installationService/pricing/getTierPricing"
        log "=="
        assert logisticsAPI.getInstallationServiceTierPricingData("Tech24") as Boolean
    }
}

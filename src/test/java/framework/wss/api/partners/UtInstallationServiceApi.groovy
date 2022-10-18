package framework.wss.api.partners

import above.RunWeb
import wss.api.partners.installationservicepricing.InstallationServicePricingData
import wss.installationservice.InstallationServiceUtil

class UtInstallationServiceApi extends RunWeb {
    static void main(String[] args) {
        new UtInstallationServiceApi().testExecute()
    }

    def test() {

        setup('kyilmaz', 'Unit Test for InstallationServiceApi',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test downloads data pdp product detail page ',
                 "tfsTcIds:0",
                 'logLevel:info'])
        testCall()
    }

    void testCall() {

        def items = InstallationServiceUtil.getItems(1)

        log items
        def zips = ["20206"]


        log zips
        if (zips == null) {
            assert false
            return
        }
        def call = new InstallationServicePricingData(items[0]['itemId'], items[0]['serviceTierId'], zips[0])
        call.printResult()
        assert call.getResult() as Boolean
    }
}

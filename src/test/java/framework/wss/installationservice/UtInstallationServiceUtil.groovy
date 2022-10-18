package framework.wss.installationservice

import above.RunWeb
import wss.installationservice.InstallationServiceUtil

class UtInstallationServiceUtil extends RunWeb{
    static void main(String[] args) {
        new UtInstallationServiceUtil().testExecute()
    }

    def test() {

        setup('kyilmaz', 'Unit Test for InstallationServiceUtil',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test downloads data pdp product detail page ',
                 "tfsTcIds:0",
                 'logLevel:info'])
        testGetItems()
        testGetDataForItemNumber()
    }

    void testGetItems() {
        def items = InstallationServiceUtil.getItems(10)
        log items
        assert items as Boolean
    }

    void testGetDataForItemNumber() {
        def items = InstallationServiceUtil.getDataForItemNumber('942TG2430GNL')
        log items
        assert items as Boolean
    }
}

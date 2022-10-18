package framework.wss.api.catalogadmin

import above.RunWeb
import wss.api.catalogadmin.AdminProduct

class UtAdminProduct extends RunWeb {
    static void main(String[] args) {
        new UtAdminProduct().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'kyilmaz',
                title   : 'UtAdminProducts',
                PBI     : 0,
                product : 'wss',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])
        testUpdateEndpoint()
        testUpdate()
    }

    void testUpdateEndpoint() {
        String endpointNone = AdminProduct.createEndpointProductUpdate(false, false)
        String endpoint1 = AdminProduct.createEndpointProductUpdate(true, false)
        String endpoint2 = AdminProduct.createEndpointProductUpdate(false, true)
        String endpoint3 = AdminProduct.createEndpointProductUpdate(true, true)

        String param1 = "runProductsIndexRefresh="
        String param2 = "preventProductCacheUpdate="

        assert !endpointNone.contains(param1) && !endpointNone.contains(param2)
        assert endpoint1.contains(param1 + 'true') && endpoint1.contains(param2 + 'false')
        assert endpoint2.contains(param1 + 'false') && endpoint2.contains(param2 + 'true')
        assert endpoint3.contains(param1 + 'true') && endpoint2.contains(param2 + 'true')
    }

    void testUpdate() {
         assert AdminProduct.update([
                "962wbs1432",
                "32644850"
        ], false, true, false)
    }
}

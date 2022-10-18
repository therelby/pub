package framework.wss.webadmin.shippingoverridecode

import above.RunWeb
import wss.webadmin.WebAdmin
import wss.webadmin.shippingoverridecode.WebAdminShippingOverrideCode

class UtWebAdminShippingOverrideGetCouponData extends RunWeb {


    static void main(String[] args) {
        new UtWebAdminShippingOverrideGetCouponData().testExecute([

                browser      : 'chrome',//'chrome',
                remoteBrowser: false,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])

    }

    def test() {
        final int PBI = 582485

        setup([
                author  : 'vdiachuk',
                title   : 'Shipping Override Code in New Web Admin unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'shipping override code nwa new web admin unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        log WebAdmin.loginToWebAdmin()
        WebAdminShippingOverrideCode webAdminShippingOverrideCode = new WebAdminShippingOverrideCode()
        log webAdminShippingOverrideCode.navigate()
        List<Map> couponData = webAdminShippingOverrideCode.getPresentCodesData()
        log "" + couponData
        assert couponData.every() { it.keySet().contains("Code") && it.keySet().contains("Entered By") && it.keySet().contains("Entered By") && it.keySet().contains("Entered By") && it.keySet().contains("Entered By") }

        assert couponData.size() == 200

        tryLoad()
        assert webAdminShippingOverrideCode.getPresentCodesData() == []
    }
}

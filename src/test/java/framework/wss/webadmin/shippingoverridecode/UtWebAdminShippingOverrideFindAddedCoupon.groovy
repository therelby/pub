package framework.wss.webadmin.shippingoverridecode

import above.RunWeb
import all.Money
import wss.webadmin.WebAdmin
import wss.webadmin.shippingoverridecode.WebAdminShippingOverrideCode

class UtWebAdminShippingOverrideFindAddedCoupon extends RunWeb {


    static void main(String[] args) {
        new UtWebAdminShippingOverrideFindAddedCoupon().testExecute([

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
                title   : 'Shipping Override Code in New Web Admin, find created coupon unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'shipping override code nwa new web admin find code add created unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        log WebAdmin.loginToWebAdmin()
        WebAdminShippingOverrideCode webAdminShippingOverrideCode = new WebAdminShippingOverrideCode()
        log webAdminShippingOverrideCode.navigate()

        String quotedShippingCost = '0.01'
        String quotedCartSubtotal = '11'
        String shippingMethod = 'Ground'
        Boolean volumeShipment = true
        String codeDefault = webAdminShippingOverrideCode.addPresetCodeAndCheckErrorMessage(quotedShippingCost, quotedCartSubtotal, shippingMethod, volumeShipment)
        log codeDefault

     assert   webAdminShippingOverrideCode.findCouponWithData(quotedShippingCost, quotedCartSubtotal, shippingMethod, volumeShipment)
    }

}

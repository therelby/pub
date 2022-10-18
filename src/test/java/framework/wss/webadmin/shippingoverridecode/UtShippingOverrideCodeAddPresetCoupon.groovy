package framework.wss.webadmin.shippingoverridecode

import above.RunWeb
import wss.webadmin.WebAdmin
import wss.webadmin.shippingoverridecode.WebAdminShippingOverrideCode

class UtShippingOverrideCodeAddPresetCoupon extends RunWeb {

    static void main(String[] args) {
        new UtShippingOverrideCodeAddPresetCoupon().testExecute([

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
        //   assert webAdminShippingOverrideCode.setForm(null, '10', '11', 'Free Shipping', true)
        assert webAdminShippingOverrideCode.addPresetCodeAndCheckErrorMessage('10', '11', 'Ground', false).size() > 3

        // negative
        tryLoad()
        assert webAdminShippingOverrideCode.addPresetCodeAndCheckErrorMessage('10', '11', 'Free Shipping', false) == null
    }

}

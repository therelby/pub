package framework.wss.cart

import above.RunWeb
import above.Execute
import wsstest.cart.countryrestrictions.HpCountryRestrictions

class UtCountryRestrictions extends RunWeb {
    static void main(String[] args) {
        new UtCountryRestrictions().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'kyilmaz',
                title   : 'UtCountryRestrictions',
                PBI     : 0,
                product : 'wss',
                project : 'Automation.Framework',
                keywords: 'unit test cart country restrictions',
                logLevel: 'info'
        ])
        testState()
        testVendor()
        testCategory()
        testProduct()
    }

    void testState() {
        def items = HpCountryRestrictions.getItemsWithStateRestrictions(1)
        assert items
    }

    void testVendor() {
        def items = HpCountryRestrictions.getItemsWithVendorRestrictions(1)
        assert items
    }

    void testCategory() {
        def items = HpCountryRestrictions.getItemsWithCategoryRestrictions(1)
        assert items
    }

    void testProduct() {
        def items = HpCountryRestrictions.getItemsWithProductRestrictions(1)
        assert items
    }
}

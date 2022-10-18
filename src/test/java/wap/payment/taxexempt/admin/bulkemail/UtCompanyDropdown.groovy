package wap.payment.taxexempt.admin.bulkemail

import above.RunWeb
import wap.common.payment.taxexempt.admin.bulkemail.WAPBulkEmail

class UtCompanyDropdown extends RunWeb {
    /**
     * Unit tests for WAP Bulk Email - Company Dropdown
     */

    static void main(String[] args) {
        new UtCompanyDropdown().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug'
        ])
    }

    void test() {
        setup([
                author  : 'jlogsdon',
                title   : 'Unit Tests - Bulk Email - Company Dropdown',
                product : 'wss|dev,test',
                PBI     : 709125,
                project : 'Clark.TaxSystems',
                keywords: 'unit tests, bulk emails, company dropdown, web admin portal'
        ])

        WAPBulkEmail bulk = new WAPBulkEmail()

        /**
         * Tests for Email Type: Filter By Tax Documents
         */
        assert bulk.loginAndLoadBulkEmailPage()
        assert bulk.isEmailTypeDropdownPresent()
        assert bulk.clickEmailTypeDropdown()
        assert bulk.isOptionFilterByTaxPresent()
        assert bulk.isOptionManualEntryPresent()
        assert bulk.clickOptionFilterByTax()
        assert bulk.isCompanyDropdownPresent()
        assert bulk.clickCompanyDropdown()
        assert bulk.clickCompanyOptionTRS()
        refresh()
        assert bulk.clickEmailTypeDropdown()
        assert bulk.clickOptionFilterByTax()
        assert bulk.isCompanyRequired()
        assert bulk.clickCompanyDropdown()
        assert bulk.clickCompanyOptionWSS()
        refresh()

        /**
         * Tests for Email Type: Manual Entry
         */
        assert bulk.isEmailTypeDropdownPresent()
        assert bulk.clickEmailTypeDropdown()
        assert bulk.isOptionFilterByTaxPresent()
        assert bulk.isOptionManualEntryPresent()
        assert bulk.clickOptionManualEntry()
        assert bulk.isCompanyDropdownPresent()
        assert bulk.clickCompanyDropdown()
        assert bulk.clickCompanyOptionTRS()
        refresh()
        assert bulk.clickEmailTypeDropdown()
        assert bulk.clickOptionFilterByTax()
        assert bulk.isCompanyRequired()
        assert bulk.clickCompanyDropdown()
        assert bulk.clickCompanyOptionWSS()
    }
}

package framework.wss.webadmin.createquote

import above.RunWeb
import wss.webadmin.WebAdmin
import wss.webadmin.quote.WebAdminCreateQuote
import wss.webadmin.quote.WebAdminM1tRowItem

class UtWebAdminCreateQuoteM1t extends RunWeb {
    static void main(String[] args) {
        new UtWebAdminCreateQuoteM1t().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug'
        ])
    }

    void test() {
        int pbi = 647689
        setup([author  : 'ggroce',
               title   : 'Cart: M1T Items in Quotes Unit Test',
               product : 'wss',
               PBI     : pbi,
               project : 'Webstaurant.StoreFront',
               keywords: 'Cart Generate Quote M1T Unit Test UnitTest'])

        // sets test first two rows, and setting input on a newly added row
        def m1tBasicItemsData = [
                [itemNumber: 'M1T', weight: 2.35, eachPrice: 65.99, taxClass: '167', cost: 59.99, indexOfLine: 0],
                [itemNumber: 'VOL5T', weight: 12.21, eachPrice: 22.49, taxClass: '389', cost: 11.99, indexOfLine: 1],
                [itemNumber: 'M1T', weight: 99.35, eachPrice: 65.99, taxClass: '167', cost: 59.99, indexOfLine: 5]]
        def m1tFullItemsData = [
                [m1tItem: new WebAdminM1tRowItem(WebAdminM1tRowItem?.m1tExampleItem2), indexOfLine: 0],
                [m1tItem: new WebAdminM1tRowItem(WebAdminM1tRowItem?.m1tExampleItem1), indexOfLine: 1],
                [m1tItem: new WebAdminM1tRowItem(WebAdminM1tRowItem?.m1tExampleItem2), indexOfLine: 5]]

        testAddM1tItemBasic(m1tBasicItemsData)
        testAddM1tItemFull(m1tFullItemsData)

        closeBrowser()
    }

    void testAddM1tItemBasic(def m1tBasicItemsData) {
        WebAdminCreateQuote webAdminCreateQuote = new WebAdminCreateQuote()
        assert !webAdminCreateQuote.addM1tItemBasic('M1T', 2.35, 65.99, '167', 59.99, 0)

        isBrowserOpen() && closeBrowser()

        assert WebAdmin.loginToWebAdmin()
        assert webAdminCreateQuote.navigate()
        assert webAdminCreateQuote.isCreateQuotePage()

        for (m1tItem in m1tBasicItemsData) {
            def (itemNumber, weight, eachPrice, taxClass, cost, indexOfLine) =
            [m1tItem?.itemNumber, m1tItem?.weight, m1tItem?.eachPrice, m1tItem?.taxClass, m1tItem?.cost, m1tItem?.indexOfLine]

            assert webAdminCreateQuote.addM1tItemBasic(itemNumber, weight, eachPrice, taxClass, cost, indexOfLine)

            assert itemNumber == getAttributeSafe(findElements(WebAdminCreateQuote?.m1tItemNumberInputXpath)[indexOfLine], 'value')
            assert weight.toString() == getAttributeSafe(findElements(WebAdminCreateQuote?.m1tWeightInputXpath)[indexOfLine], 'value')
            assert eachPrice.toString() == getAttributeSafe(findElements(WebAdminCreateQuote?.m1tEachPriceInputXpath)[indexOfLine], 'value')
            assert taxClass == getAttributeSafe(findElements(WebAdminCreateQuote?.m1tTaxClassSelectorXpath)[indexOfLine], 'value')
            assert cost.toString() == getAttributeSafe(findElements(WebAdminCreateQuote?.m1tCostInputXpath)[indexOfLine], 'value')
        }

        // row index is oob:
        assert !webAdminCreateQuote.addM1tItemBasic('M1T', 2.35, 65.99, '167', 59.99, 8)
    }

    void testAddM1tItemFull(def m1tFullItemsData) {
        WebAdminCreateQuote webAdminCreateQuote = new WebAdminCreateQuote()
        assert !webAdminCreateQuote.addM1tItemFull(new WebAdminM1tRowItem(WebAdminM1tRowItem?.m1tExampleItem1), 0)

        isBrowserOpen() && closeBrowser()

        assert WebAdmin.loginToWebAdmin()
        assert webAdminCreateQuote.navigate()
        assert webAdminCreateQuote.isCreateQuotePage()

        for (m1tItem in m1tFullItemsData) {
            def (item, indexOfLine) = [m1tItem?.m1tItem, m1tItem?.indexOfLine]

            def (itemNumber, weight, eachPrice, taxClass, cost) =
            [item?.itemNumber, item?.weight, item?.eachPrice, item?.taxClass, item?.cost]
            def (itemNumberSuffix, vendorItemNumber, upc, description, comments) =
            [item?.itemNumberSuffix, item?.vendorItemNumber, item?.upc, item?.description, item?.comments]
            def (shipping, quantity, uom, dropShip, freeShipping, puom) =
            [item?.shipping, item?.quantity, item?.uom, item?.dropShip, item?.freeShipping, item?.puom]

            assert webAdminCreateQuote.addM1tItemFull(item, indexOfLine)

            assert itemNumber == getAttributeSafe(findElements(WebAdminCreateQuote?.m1tItemNumberInputXpath)[indexOfLine], 'value')
            assert weight.toString() == getAttributeSafe(findElements(WebAdminCreateQuote?.m1tWeightInputXpath)[indexOfLine], 'value')
            assert eachPrice.toString() == getAttributeSafe(findElements(WebAdminCreateQuote?.m1tEachPriceInputXpath)[indexOfLine], 'value')
            assert taxClass == getAttributeSafe(findElements(WebAdminCreateQuote?.m1tTaxClassSelectorXpath)[indexOfLine], 'value')
            assert cost.toString() == getAttributeSafe(findElements(WebAdminCreateQuote?.m1tCostInputXpath)[indexOfLine], 'value')
            assert itemNumberSuffix == getAttributeSafe(findElements(WebAdminCreateQuote?.m1tItemNumberSuffixInputXpath)[indexOfLine], 'value')
            assert vendorItemNumber == getAttributeSafe(findElements(WebAdminCreateQuote?.m1tVendorItemNumberInputXpath)[indexOfLine], 'value')
            assert upc == getAttributeSafe(findElements(WebAdminCreateQuote?.m1tUpcInputXpath)[indexOfLine], 'value')
            assert description == getAttributeSafe(findElements(WebAdminCreateQuote?.m1tDescriptionInputXpath)[indexOfLine], 'value')
            assert comments == getAttributeSafe(findElements(WebAdminCreateQuote?.m1tCommentsInputXpath)[indexOfLine], 'value')
            assert quantity.toString() == getAttributeSafe(findElements(WebAdminCreateQuote?.m1tQuantityInputXpath)[indexOfLine], 'value')
            assert freeShipping.toString() == getAttributeSafe(findElements(WebAdminCreateQuote?.m1tFreeShippingCheckboxXpath)[indexOfLine], 'value')

            // optional fields with defaults
            if (shipping) {
                assert shipping == getAttributeSafe(findElements(WebAdminCreateQuote?.m1tShippingSelectorXpath)[indexOfLine], 'value')
            }
            if (uom){
                assert uom == getAttributeSafe(findElements(WebAdminCreateQuote?.m1tUomInputXpath)[indexOfLine], 'value')
            }
            if (dropShip) {
                assert dropShip == getAttributeSafe(findElements(WebAdminCreateQuote?.m1tDropShipSelectorXpath)[indexOfLine], 'value')
            }
            if (puom) {
                assert puom == getAttributeSafe(findElements(WebAdminCreateQuote?.m1tPuomSelectorXpath)[indexOfLine], 'value')
            }
        }

        // row index is oob:
        assert !webAdminCreateQuote.addM1tItemFull(new WebAdminM1tRowItem(WebAdminM1tRowItem?.m1tExampleItem1), 8)
    }
}

package framework.wss.api.catalog

import above.Execute
import above.RunWeb
import wss.api.catalog.product.Products
import wss.pages.productdetail.PDPage

class UtProducts extends RunWeb {

    static void main(String[] args) {
        Execute.suite(
                [
                        new UtProducts().usingEnvironment('test'),
                        new UtProducts().usingEnvironment('dev')
                ]
        , 1)
    }

    def test() {
        setup([
                author  : 'kyilmaz',
                title   : 'Catalog Products Unit Test',
                PBI     : 0,
                product : 'wss|dev,test',
                project : 'Catalog',
                keywords: 'catalog products unit test',
                logLevel: 'info'
        ])
        log getUrl('sp')
        def singleCall = testSingleItemNumber('100BTWINE')
        def listCall = testListItemNumber(['5535116HT', '55316JARFAIR'])
        testGetValues(singleCall)
        testGetValuesIdentifier(listCall, '5535116HT')
    }

    Products testSingleItemNumber(item) {
        def call = new Products(item, [allowGroupingProducts:true, showHidden:true, allowEverliving:true, ignoreVisibilityFilters:true])
        def statusCode = call.getStatusCode()
        if (call.verifyStatusCodeSuccess()) {
            assert !call.isNull()
        } else {
            assert call.isNull()
            assert statusCode >= 500
        }
        return call
    }

    Products testListItemNumber(list) {
        def call = new Products(list, [allowGroupingProducts:true, showHidden:true, allowEverliving:true, ignoreVisibilityFilters:true])
        def statusCode = call.getStatusCode()
        if (call.verifyStatusCodeSuccess()) {
            assert !call.isNull()
        } else {
            assert call.isNull()
            assert statusCode >= 500
        }
        return call
    }

    void testGetValues(call) {
        def item = call.getValues('itemNumber')
        log item
        assert item
    }

    void testGetValuesIdentifier(call, itemNumber) {
        def item = call.getValues('itemNumber', itemNumber)
        log item
        assert item
    }
}

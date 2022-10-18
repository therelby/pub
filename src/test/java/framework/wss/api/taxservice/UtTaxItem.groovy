package framework.wss.api.taxservice

import above.RunWeb
import wss.api.taxservice.model.taxtransactions.TaxItemModel

class UtTaxItem extends RunWeb {
    static void main(String[] args) {
        new UtTaxItem().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    void test() {
        setup([
                author  : 'kyilmaz',
                title   : 'UtTaxItem',
                PBI     : 0,
                product : 'wss',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])
        testApiDataModelMethods()
    }

    void testApiDataModelMethods() {
        TaxItemModel item = new TaxItemModel()
        item.setItemId(1)
        item.setLineIdentifier('something')
        item.setLineTotal(10.10000)
        item.setQuantity(1)
        assert item.toMap() == [
            "lineTotal": 10.10000,
            "itemId": 1,
            "lineIdentifier": "something",
            "quantity": 1
        ]
        assert item.toString() == '{"lineTotal":10.10000,"itemId":1,"lineIdentifier":"something","quantity":1}'
    }
}

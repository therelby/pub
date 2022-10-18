package framework.wss.pages.productdetail.pricetile.quantitydiscount.minmustbuy

import above.RunWeb
import wss.item.QuantityDiscountMinMustBuyUtil
import wsstest.product.productdetailpage.pricetile.quantitydiscount.minmustbuy.HpQuantityDiscountMinMustBuy

class UtQuantityDiscountMinMustBuyGreaterThanStartEndQuantity extends RunWeb{

    String scenario = "greaterthanstartendquantity"

    static void main(String[] args) {
        new UtQuantityDiscountMinMustBuyGreaterThanStartEndQuantity().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }
    
    def test() {

        setup('mwestacott', 'PDPage - Quantity Discount - Min/Must Buy - Min/Must Buy greater than start end quantity  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test pdpage pdp quantity discount min must buy greater than start end quantity',
                 "PBI:0",
                 'logLevel:info'])
        testingQuery()
        HpUtQuantityDiscountMinMustBuy test = new HpUtQuantityDiscountMinMustBuy()

        test.testingQuantityDiscount(scenario, "139A7054122A", true, true, "Each", 1, 25, "N", "https://www.dev.webstaurantstore.com/acco-54122-letter-size-side-bound-hanging-data-post-binder-6-capacity-with-2-fasteners-light-blue/139A7054122A.html")
    }

    void testingQuery(){
        String filter = new HpQuantityDiscountMinMustBuy().getFilterForTesting(scenario)
        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Lone", false, false, true, filter).size() == 0
        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Lone Suffix", false, false, true, filter).size() == 0
        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Virtual Grouping", false, false, true, filter).size() == 0

        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Lone", false, true, true, filter).size() == 0
        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Lone Suffix", false, true, true, filter).size() == 0
        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Virtual Grouping", false, true, true, filter).size() == 0

        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Lone", true, null, true, filter).size() > 0
        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Lone Suffix", true, null, true, filter).size() == 0
        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Virtual Grouping", true, null, true, filter).size() == 0
    }
}

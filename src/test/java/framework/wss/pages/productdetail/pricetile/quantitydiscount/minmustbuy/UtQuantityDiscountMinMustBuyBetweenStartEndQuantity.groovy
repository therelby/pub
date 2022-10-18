package framework.wss.pages.productdetail.pricetile.quantitydiscount.minmustbuy

import above.RunWeb
import wss.item.QuantityDiscountMinMustBuyUtil
import wsstest.product.productdetailpage.pricetile.quantitydiscount.minmustbuy.HpQuantityDiscountMinMustBuy

class UtQuantityDiscountMinMustBuyBetweenStartEndQuantity extends RunWeb{

    String scenario = "betweenstartendquantity"

    static void main(String[] args) {
        new UtQuantityDiscountMinMustBuyBetweenStartEndQuantity().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    def test() {

        setup('mwestacott', 'PDPage - Quantity Discount - Min/Must Buy - Min/Must Buy between start end quantity  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test pdpage pdp quantity discount min must buy between start end quantity',
                 "PBI:0",
                 'logLevel:info'])
        testingQuery()
        HpUtQuantityDiscountMinMustBuy test = new HpUtQuantityDiscountMinMustBuy()
        test.testingQuantityDiscount(scenario,"911HKCCADDY6", true, false, "Each", 1, 6, "N", "https://www.dev.webstaurantstore.com/suncast-hkccaddy6-black-caddy-for-janitorial-housekeeping-cart/911HKCCADDY6.html")
        test.testingQuantityDiscount(scenario,"375CHPSTKTAN", true, true, "Pack", 21, 1, "N", "https://www.dev.webstaurantstore.com/town-51316t-10-1-2-tangerine-melamine-chopsticks-set-pack/375CHPSTKTAN.html")
    }

    void testingQuery(){
        String filter = new HpQuantityDiscountMinMustBuy().getFilterForTesting(scenario)
        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Lone", false, false, true, filter).size() > 0
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

package framework.wss.pages.productdetail.pricetile.quantitydiscount.minmustbuy

import above.RunWeb
import wss.item.QuantityDiscountMinMustBuyUtil
import wsstest.product.productdetailpage.pricetile.quantitydiscount.minmustbuy.HpQuantityDiscountMinMustBuy

class UtQuantityDiscountMinMustBuyBiggerThanAllQuantityDiscountStartQuantity extends RunWeb{

    String scenario = "biggerallstartquantity"

    static void main(String[] args) {
        new UtQuantityDiscountMinMustBuyBiggerThanAllQuantityDiscountStartQuantity().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }
    
    def test() {

        setup('mwestacott', 'PDPage - Quantity Discount - Min/Must Buy - Min/Must Buy bigger than all quantity discount start quantity | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test pdpage pdp quantity discount min must buy bigger than all quantity discount start quantity',
                 "PBI:0",
                 'logLevel:info'])
        testingQuery()
        HpUtQuantityDiscountMinMustBuy test = new HpUtQuantityDiscountMinMustBuy()

        test.testingQuantityDiscount(scenario, "15474763", false, false, "Pack", 1, 72, "N", "https://www.dev.webstaurantstore.com/avery-74763-ultra-tabs-1-x-1-1-2-assorted-primary-color-repositionable-tab-pack/15474763.html")
        test.testingQuantityDiscount(scenario, "128NC948COMBO150", false, false, "Case", 1, 12, "Y", "https://www.dev.webstaurantstore.com/pactiv-newspring-nc948-48-oz-white-9-x-1-3-4-versatainer-round-microwavable-container-with-lid-150-case/128NC948COMBO150.html")
        test.testingQuantityDiscount(scenario, "394VPAG581XL", false, false, "Pair", 1, 12, "N", "https://www.dev.webstaurantstore.com/activgrip-advance-gray-purple-nylon-gloves-with-black-microfinish-nitrile-palm-coating-extra-large-pair/394VPAG581XL.html")

        test.testingQuantityDiscount(scenario, "3943755L", false, false, "Pair", 8, 1, "N", "https://www.dev.webstaurantstore.com/monarch-gray-engineered-fiber-cut-resistant-gloves-with-black-hct-nitrile-palm-coating-large-pair/3943755L.html")

        test.testingQuantityDiscount(scenario, "80810369", false, true, "Each", 14, 1, "N", "https://www.dev.webstaurantstore.com/tablecraft-10369-16-oz-red-stainless-steel-cocktail-shaker-tin-with-vinyl-coating/80810369.html")

        test.testingQuantityDiscount(scenario, "375SGN15", true, true, "Each", 1, 12, "N", "https://www.dev.webstaurantstore.com/clipper-mill-by-get-sgn-15-8-1-2-chrome-plated-iron-stand-with-ceramic-sign/375SGN15.html")
    }

    void testingQuery(){
        String filter = new HpQuantityDiscountMinMustBuy().getFilterForTesting(scenario)
        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Lone", false, false, false, filter).size() > 0
        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Lone Suffix", false, false, false, filter).size() > 0
        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Virtual Grouping", false, false, false, filter).size() > 0

        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Lone", false, false, true, filter).size() > 0
        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Lone Suffix", false, false, true, filter).size() == 0
        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Virtual Grouping", false, false, true, filter).size() == 0

        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Lone", false, true, false, filter).size() > 0
        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Lone Suffix", false, true, false, filter).size() == 0
        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Virtual Grouping", false, true, false, filter).size() > 0

        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Lone", false, true, true, filter).size() == 0
        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Lone Suffix", false, true, true, filter).size() == 0
        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Virtual Grouping", false, true, true, filter).size() == 0

        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Lone", true, null, true, filter).size() > 0
        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Lone Suffix", true, null, true, filter).size() == 0
        assert QuantityDiscountMinMustBuyUtil.queryGetQuantityDiscountMinMustBuyProducts(5, "Virtual Grouping", true, null, true, filter).size() == 0
    }
}

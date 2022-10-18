package framework.wss.pages.productdetail.stickyheader

import above.RunWeb
import wss.pages.productdetail.PDPStickyHeader
import wss.pages.productdetail.PDPage

class UtPDPStickyHeaderQuantityDiscount extends RunWeb{

    def test() {

        setup('mwestacott', 'PDPage Product Detail Page Sticky Header Quantity Discount unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test pdpage pdp product detail page sticky header quantity discount',
                 "PBI:0",
                 'logLevel:info'])

        //Lone product, no quantity discount
        testingQuantityDiscount('https://www.dev.webstaurantstore.com/tri-star-301034-orifice-hood-31-nat/HP301034.html', 'Lone', false, '$1.56', 'Each')

        //Lone suffix product, no quantity discount
        testingQuantityDiscount('https://www.dev.webstaurantstore.com/200-2-lb-red-check-paper-food-tray-case/795200RC%20%20%20%201M.html', 'Lone Suffix', false, '$25.49', 'Case')

        //Virtual Grouping product, no quantity discount
        testingQuantityDiscount('https://www.dev.webstaurantstore.com/genuine-grip-160-womens-size-11-medium-width-black-leather-athletic-non-slip-shoe/755W16011M.html', 'Virtual Grouping', false, '$47.99', 'Each')

        //Lone product, quantity discount
        testingQuantityDiscount('https://www.dev.webstaurantstore.com/thunder-group-5980-blue-jade-1-69-qt-round-melamine-soup-bowl-case/2715980.html', 'Lone', true, '$88.06', 'Case')

        //Lone suffix product, quantity discount
        testingQuantityDiscount('https://www.dev.webstaurantstore.com/wna-comet-ccw5240-classicware-5-oz-1-piece-clear-plastic-pedestal-wine-cup-case/625CCW5%20%20%20%20%20CL240.html', 'Lone Suffix', true, '$81.81', 'Case')

        //Virtual Grouping product, quantity discount
        testingQuantityDiscount('https://www.dev.webstaurantstore.com/mercer-culinary-genesis-m61012-black-unisex-customizable-traditional-neck-short-sleeve-chef-jacket-s/47061012BKS.html', 'Virtual Grouping', true, '$23.83', 'Each')

        //Lone product with quantity discount that currently triggers a bug
        testingQuantityDiscount('https://www.dev.webstaurantstore.com/choice-230-count-bulk-school-crayon-bucket/100CRAY230CT.html', 'Lone', true, '$7.31', 'Box')
    }

    private void testingQuantityDiscount(String url, String itemType, boolean expectedToHaveQuantityDiscount, String expectedPrice, String expectedUom){
        assert tryLoad(url)
        assert new PDPage().verifyItemType(itemType)

        PDPStickyHeader pdpStickyHeader = new PDPStickyHeader()
        log "before: " + pdpStickyHeader.isStickyHeader()
        assert !pdpStickyHeader.isStickyHeader()

        scrollToBottom()

        log "after: " + pdpStickyHeader.isStickyHeader()
        assert pdpStickyHeader.isStickyHeader()
        assert pdpStickyHeader.isShown()
        assert !pdpStickyHeader.isHidden()

        assert verifyElement(pdpStickyHeader.pricingFromXpath) == expectedToHaveQuantityDiscount
        if(expectedToHaveQuantityDiscount){
            assert getTextSafe(pdpStickyHeader.pricingFromXpath) == pdpStickyHeader.pricingFromText
        }

        String expectedPriceAndUom = (expectedToHaveQuantityDiscount ? "From\n" : "") + expectedPrice + "/" + expectedUom
        String actualPriceAndUom = getTextSafe(pdpStickyHeader.pricingBlockXpath + pdpStickyHeader.priceXpath)

        assert actualPriceAndUom == expectedPriceAndUom


    }
}

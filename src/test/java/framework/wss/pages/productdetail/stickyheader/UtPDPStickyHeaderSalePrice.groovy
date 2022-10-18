package framework.wss.pages.productdetail.stickyheader

import above.RunWeb
import wss.pages.productdetail.PDPStickyHeader
import wss.pages.productdetail.PDPage

class UtPDPStickyHeaderSalePrice extends RunWeb{

    def test() {

        setup('mwestacott', 'PDPage Product Detail Page Sticky Header Sale Price unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test pdpage pdp product detail page sticky header sale price',
                 "PBI:0",
                 'logLevel:info'])

        //Lone product, no sale price, no quantity discount, available
        testingSalePrice('https://www.dev.webstaurantstore.com/creative-converting-139190135-classic-pink-2-ply-1-4-fold-luncheon-napkin-pack/9992NAP1*4PP.html', 'Lone', false, false, '$2.19', 'Pack')

        //Lone product, no sale price, quantity discount, available
        testingSalePrice('https://www.dev.webstaurantstore.com/vollrath-48208-mariner-8-1-4-18-0-stainless-steel-extra-heavy-weight-serving-spoon-case/92248208VR.html', 'Lone', true, false, '$32.66', 'Case')

        //Lone product, no sale price, no quantity discount, unavailable
        testingSalePrice('https://www.dev.webstaurantstore.com/metro-mqsec53le-28-x-39-x-69-metromax-q-heavy-duty-mobile-security-unit/461MQSEC53LE.html', 'Lone', false, false, '$1,229.00', 'Each')

        //Lone product, no sale price, quantity discount, unavailable
        testingSalePrice('https://www.dev.webstaurantstore.com/dart-12bwhqr-quiet-classic-10-12-oz-honey-laminated-round-foam-bowl-case/30112BWHQR.html', 'Lone', true, false, '$58.33', 'Case')

        //Lone product, sale price, no quantity discount, available
        testingSalePrice('https://www.dev.webstaurantstore.com/avery-5522-trueblock-1-1-3-x-4-weatherproof-white-mailing-address-labels-box/1545522.html', 'Lone', false, true, '$31.77', 'Box')

        //Lone product, sale price, quantity discount, available
        testingSalePrice('https://www.dev.webstaurantstore.com/tuxton-ggm-501-tuxtrendz-artisan-geode-mushroom-9-square-china-plate-case/303GGM501.html', 'Lone', true, true, '$215.40', 'Case')

        //Lone product, sale price, no quantity discount, unavailable
        testingSalePrice('https://www.dev.webstaurantstore.com/shun-ssm0708-combination-sharpening-stone-400-1000-grit/921SS0708.html', 'Lone', false, true, '$59.95', 'Each')

        //Lone product, sale price, quantity discount, unavailable
        testingSalePrice('https://www.dev.webstaurantstore.com/tuxton-ggc-003-tuxtrendz-artisan-geode-coral-7-1-4-china-plate-case/303GGC003.html', 'Lone', true, true, '$123.72', 'Case')

        closeBrowser()
    }

    private void testingSalePrice(String url, String itemType, boolean hasQuantityDiscount, boolean hasSalePrice, String expectedPrice, String expectedUom){
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

        Boolean isProductExpectedToHaveQuantityDiscount = (hasQuantityDiscount && !hasSalePrice)

        assert verifyElement(pdpStickyHeader.pricingFromXpath) == isProductExpectedToHaveQuantityDiscount
        if(isProductExpectedToHaveQuantityDiscount){
            assert getTextSafe(pdpStickyHeader.pricingFromXpath) == pdpStickyHeader.pricingFromText
        }

        String expectedPriceAndUom = (isProductExpectedToHaveQuantityDiscount ? "From\n" : "") + expectedPrice + "/" + expectedUom
        String actualPriceAndUom = getTextSafe(pdpStickyHeader.pricingBlockXpath + pdpStickyHeader.priceXpath)

        assert actualPriceAndUom == expectedPriceAndUom
    }
}

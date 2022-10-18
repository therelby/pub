package framework.wss.pages.productdetail

import above.RunWeb
import wss.item.ItemUtil
import wss.pages.productdetail.PDPPriceTile

class UtPDPageWebplusMessages extends RunWeb {

    def test() {

        setup('mwestacott', 'PDPage Product Detail Page WebPlus Messages unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test pdpage pdp product detail page webplus messages ',
                 "tfsTcIds:0",
                 'logLevel:info'])

        assert ItemUtil.getProductsForTestingWebplusMessages('Lone', 5, false, 'N', false).size() == 5
        testingWebPlusMessages("https://www.dev.webstaurantstore.com/power-soak-27590-impeller/HP27590.html", false, "N", false)
        testingWebPlusMessages("https://www.dev.webstaurantstore.com/gojo-2230-08-nxt-2000-ml-dove-gray-maximum-capacity-manual-hand-soap-dispenser/381G223008GY.html", false, "N", true)
        testingWebPlusMessages("https://www.dev.webstaurantstore.com/wells-20637-69-splashguard/94220637.html", false, "Y", false)
        testingWebPlusMessages("https://www.dev.webstaurantstore.com/lucas-oil-10315-3-oz-mini-grease-cartridge-multi-pack-case/84310315.html", false, "Y", true)
        testingWebPlusMessages("https://www.dev.webstaurantstore.com/ts-b-1433-7102-01c-12-wall-mounted-enclosed-epoxy-coated-hose-reel-with-low-flow-spray-valve/51014337121C.html", true, "N", false)
        testingWebPlusMessages("https://www.dev.webstaurantstore.com/avantco-crm-5-hc-stainless-steel-countertop-display-refrigerator-with-swing-door-3-9-cu-ft/360CRM5HCS.html", true, "Y", false)
    }

    void testingWebPlusMessages(String url, boolean isWebstaurantPlusEligible, String freeShipping, boolean hasWebstaurantPlusDiscountedShipping){
        assert tryLoad(url)
        if(isWebstaurantPlusEligible){
            assert verifyElement(PDPPriceTile.shipsFreeWithPlus)
            assert getTextSafe(PDPPriceTile.shipsFreeWithPlus) == "Ships free with Plus"
            assert getAttribute(PDPPriceTile.shipsFreeWithPlus, "href") == "https://www.dev.webstaurantstore.com/plus/"
            assert verifyElement(PDPPriceTile.basePlusTextFlag)
        }
        else{
            if(freeShipping=='N' && hasWebstaurantPlusDiscountedShipping){
                assert verifyElement(PDPPriceTile.discountedShippingWithPlus)
                assert getTextSafe(PDPPriceTile.discountedShippingWithPlus) == "Discounted shipping with Plus"
                assert getAttribute(PDPPriceTile.discountedShippingWithPlus, "href") == "https://www.dev.webstaurantstore.com/plus/"
                assert verifyElement(PDPPriceTile.basePlusTextFlag)
            }
            else{
                assert !verifyElement(PDPPriceTile.shipsFreeWithPlus)
                assert !verifyElement(PDPPriceTile.discountedShippingWithPlus)
                assert !verifyElement(PDPPriceTile.basePlusTextFlag)
            }
        }
    }

}

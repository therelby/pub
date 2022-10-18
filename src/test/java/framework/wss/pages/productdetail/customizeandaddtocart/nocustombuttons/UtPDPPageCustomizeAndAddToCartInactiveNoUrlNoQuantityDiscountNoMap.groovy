package framework.wss.pages.productdetail.customizeandaddtocart.nocustombuttons

import framework.wss.pages.productdetail.customizeandaddtocart.UtPDPageCustomizeAndAddToCart

class UtPDPPageCustomizeAndAddToCartInactiveNoUrlNoQuantityDiscountNoMap extends UtPDPageCustomizeAndAddToCart{

    @Override
    void testingByUser(String userType, Integer userId = null){
        super.testingByUser(userType, userId)
        testingCustomizeAndAddToCart('https://www.dev.webstaurantstore.com/avantco-gds-47-hc-53-black-sliding-glass-door-merchandiser-refrigerator-with-led-lighting/178GDS47HCB.html', userType, 'https://www.dev.webstaurantstore.com/customized-products/178gds47hcb/', false, false, false, false, false,  false, false, '$2,699.00', '$0,00', '$0.00', 'Each')
        testingCustomizeAndAddToCart('https://www.dev.webstaurantstore.com/selectspace-7-green-square-weave-pattern-graphic-partition-panel/118PP7101005.html', userType, 'https://www.dev.webstaurantstore.com/customized-products/118pp7101005/', false, true, false, false, false,  false, false, '$625.99', '$0,00', '$0.00', 'Each')
    }

}

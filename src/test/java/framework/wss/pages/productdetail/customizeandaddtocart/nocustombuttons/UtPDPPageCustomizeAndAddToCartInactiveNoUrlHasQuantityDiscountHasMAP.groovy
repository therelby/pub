package framework.wss.pages.productdetail.customizeandaddtocart.nocustombuttons

import framework.wss.pages.productdetail.customizeandaddtocart.UtPDPageCustomizeAndAddToCart

class UtPDPPageCustomizeAndAddToCartInactiveNoUrlHasQuantityDiscountHasMAP extends UtPDPageCustomizeAndAddToCart{

    @Override
    void testingByUser(String userType, Integer userId = null){
        super.testingByUser(userType, userId)
        testingCustomizeAndAddToCart('https://www.dev.webstaurantstore.com/masters-reserve-9323-prism-16-oz-wine-glass-case/5519323.html', userType, 'https://www.dev.webstaurantstore.com/customized-products/5519323/', false, false, false, true, true, true, false,'$70.40', '$51.61', '$68.64', 'Case')
    }

}

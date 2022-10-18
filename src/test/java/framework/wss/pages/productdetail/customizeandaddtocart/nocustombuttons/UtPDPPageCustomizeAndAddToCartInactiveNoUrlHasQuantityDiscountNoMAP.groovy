package framework.wss.pages.productdetail.customizeandaddtocart.nocustombuttons

import framework.wss.pages.productdetail.customizeandaddtocart.UtPDPageCustomizeAndAddToCart

class UtPDPPageCustomizeAndAddToCartInactiveNoUrlHasQuantityDiscountNoMAP extends UtPDPageCustomizeAndAddToCart{

    @Override
    void testingByUser(String userType, Integer userId = null){
        super.testingByUser(userType, userId)
        testingCustomizeAndAddToCart('https://www.dev.webstaurantstore.com/menu-solutions-wk160a-water-street-wicker-5-1-2-x-8-1-2-customizable-quad-panel-6-view-booklet-menu-cover/659WK160A.html', userType, 'https://www.dev.webstaurantstore.com/customized-products/659wk160a/', false, false, false, true, false, false, false,'$38.49','$35.59','$0.00', 'Each')
    }

}

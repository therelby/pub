package framework.wss.pages.productdetail.customizeandaddtocart.redcustombutton

import framework.wss.pages.productdetail.customizeandaddtocart.UtPDPageCustomizeAndAddToCart

class UtPDPPageCustomizeAndAddToCartRedButtonQuantityDiscount extends UtPDPageCustomizeAndAddToCart{
    @Override
    void testingByUser(String userType, Integer userId = null){
        super.testingByUser(userType, userId)
        testingCustomizeAndAddToCart('https://www.dev.webstaurantstore.com/h-risch-inc-armada-4-1-4-x-11-customizable-menu-board/759ARMADAM41.html', userType, 'https://www.dev.webstaurantstore.com/customized-products/759armadam41/', true, true, false, true, false, false, false, '$16.49', '$15.40', '$15.40', 'Each')
    }
}

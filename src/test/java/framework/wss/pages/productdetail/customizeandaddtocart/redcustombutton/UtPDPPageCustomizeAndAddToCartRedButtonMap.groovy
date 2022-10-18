package framework.wss.pages.productdetail.customizeandaddtocart.redcustombutton

import framework.wss.pages.productdetail.customizeandaddtocart.UtPDPageCustomizeAndAddToCart

class UtPDPPageCustomizeAndAddToCartRedButtonMap extends UtPDPageCustomizeAndAddToCart{
    @Override
    void testingByUser(String userType, Integer userId = null){
        super.testingByUser(userType, userId)
        testingCustomizeAndAddToCart('https://www.dev.webstaurantstore.com/holland-bar-stool-l214c4228custom-28-round-logo-bar-height-pub-table-with-chrome-round-base/422L214C428C.html', userType, 'https://www.dev.webstaurantstore.com/customized-products/422l214c428c/', true, true, false, false, true, true, false, '$409.00', '$0.00', '$344.49', 'Each')
    }
}

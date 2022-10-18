package framework.wss.pages.productdetail.customizeandaddtocart.purplecustombutton

import framework.wss.pages.productdetail.customizeandaddtocart.UtPDPageCustomizeAndAddToCart

class UtPDPPageCustomizeAndAddToCartPurpleButtonQuantityDiscount extends UtPDPageCustomizeAndAddToCart{
    @Override
    void testingByUser(String userType, Integer userId = null){
        super.testingByUser(userType, userId)
        testingCustomizeAndAddToCart('https://www.dev.webstaurantstore.com/mercer-culinary-genesis-m61042-black-womens-customizable-traditional-neck-short-sleeve-chef-jacket-with-cloth-knot-buttons-3x/47061042BK3X.html', userType, "https://www.dev.webstaurantstore.com/customized-products/47061042bk3x/", true, false, false, true, false, false, false, '$30.40','$30.40', '$37.99', 'Each')

    }
}

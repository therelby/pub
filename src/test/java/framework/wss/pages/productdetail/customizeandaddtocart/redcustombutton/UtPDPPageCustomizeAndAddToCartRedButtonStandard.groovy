package framework.wss.pages.productdetail.customizeandaddtocart.redcustombutton

import framework.wss.pages.productdetail.customizeandaddtocart.UtPDPageCustomizeAndAddToCart

class UtPDPPageCustomizeAndAddToCartRedButtonStandard extends UtPDPageCustomizeAndAddToCart{
    @Override
    void testingByUser(String userType, Integer userId = null){
        super.testingByUser(userType, userId)
        testingCustomizeAndAddToCart('https://www.dev.webstaurantstore.com/curtron-service-pro-series-20-double-aluminum-swinging-traffic-door-84-x-96-door-opening/51720ALD8496.html', userType, 'https://www.dev.webstaurantstore.com/curtroncustomdoors/', false, true, true, false, false, false, false, '$1,549.00', '$0.00', '$1,209.00', 'Each')
        testingCustomizeAndAddToCart('https://www.dev.webstaurantstore.com/10-x-10-x-2-white-corrugated-plain-pizza-bakery-box-bundle/245CPBWCST10.html', userType, 'https://www.dev.webstaurantstore.com/customized-products/245cpbwcst10/', true, true, false, false, false, false, false, '$26.99', '$0.00', '$25.79', 'Bundle')
        testingCustomizeAndAddToCart('https://www.dev.webstaurantstore.com/customizable-red-peppermint-starlites-case/113CPSWL3M.html', userType, 'https://www.dev.webstaurantstore.com/customized-products/113cpswl3m/', true, true, true, false, false, false, false, '$114.49', '$0.00', '$74.44', 'Case')
    }
}

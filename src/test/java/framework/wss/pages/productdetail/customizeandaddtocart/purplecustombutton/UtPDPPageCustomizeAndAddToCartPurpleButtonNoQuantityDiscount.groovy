package framework.wss.pages.productdetail.customizeandaddtocart.purplecustombutton

import framework.wss.pages.productdetail.customizeandaddtocart.UtPDPageCustomizeAndAddToCart

class UtPDPPageCustomizeAndAddToCartPurpleButtonNoQuantityDiscount extends UtPDPageCustomizeAndAddToCart{
    @Override
    void testingByUser(String userType, Integer userId = null){
        super.testingByUser(userType, userId)
        testingCustomizeAndAddToCart('https://www.dev.webstaurantstore.com/avantco-gdc-69-hc-78-1-4-white-swing-glass-door-merchandiser-refrigerator-with-led-lighting/178GDC69HCW.html', userType, 'https://www.dev.webstaurantstore.com/customized-products/178GDC69WC/', false, false, true, false, false, false, false,'$3,899.00', '$0.00', '$0.00', "Each")
        testingCustomizeAndAddToCart('https://www.dev.webstaurantstore.com/chef-revival-white-poly-cotton-customizable-bistro-apron-with-2-pockets-34l-x-28w/167SJBA2WH.html', userType, 'https://www.dev.webstaurantstore.com/customized-products/167sjba2wh/', true, false, false, false, false, false, false, '$10.49', '$0.00', '$27.09', 'Each')
        testingCustomizeAndAddToCart('https://www.dev.webstaurantstore.com/avantco-gdc-12f-hc-27-1-8-white-swing-glass-door-merchandiser-freezer-with-led-lighting/178GDC12FHCW.html', userType, 'https://www.dev.webstaurantstore.com/customized-products/178GDC12FWC/', true, false, true, false, false, false, false, '$1,999.00', '$0.00', '$0.00', 'Each')
    }
}

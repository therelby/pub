package framework.wss.pages.productdetail.pricetile.warrantymodal.addtocart.nothanks

import framework.wss.pages.productdetail.pricetile.warrantymodal.addtocart.UtPriceTileWarrantyModalAddToCart
import wss.cart.Cart
import wss.pages.cart.CartItemBox

abstract class UtPriceTileWarrantyModalATCNoThanks extends UtPriceTileWarrantyModalAddToCart{

    def test() {

        setup('mwestacott', 'PDPage Product Detail Page Price Tile Warranty Modal Add to Cart No Thanks unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test pdpage pdp product detail page price tile warranty modal add to cart no thanks',
                 "PBI:0",
                 'logLevel:info'])

        testingWarrantyModalAddToCartScenarioNoThanks()
    }
    
    abstract void testingWarrantyModalAddToCartScenarioNoThanks()

    //Method for testing all possible warranty modal add to cart scenarios per user and if warranty gets selected.
    protected void testingWarrantyModalAddToCartNoThanksBasedOnUser(String userType){
        Integer userId = getUserIdBasedOnUserType(userType)

        testingWarrantyModalAddToCartNoThanks(rkwLoneProductWithNoWarning, rkwLoneProductWithNoWarningUrl, userId, "Lone", false, false)
        testingWarrantyModalAddToCartNoThanks(rkwVirtualGroupingProductWithNoWarning, rkwVirtualGroupingProductWithNoWarningUrl, userId, "Virtual Grouping", false, false)

        testingWarrantyModalAddToCartNoThanks(rkwLoneProductWithWarning, rkwLoneProductWithWarningUrl, userId, "Lone", false, true)

        if(userType != 'residential') {
            testingWarrantyModalAddToCartNoThanks(safewareLoneProductWithNoWarning, safewareLoneProductWithNoWarningUrl, userId, "Lone", true, false)
            testingWarrantyModalAddToCartNoThanks(safewareVirtualGroupingProductWithNoWarning, safewareVirtualGroupingProductWithNoWarningUrl, userId, "Virtual Grouping", true, false)

            testingWarrantyModalAddToCartNoThanks(safewareLoneProductWithWarning, safewareLoneProductWithWarningUrl, userId, "Lone", true, true)
            testingWarrantyModalAddToCartNoThanks(safewareVirtualGroupingProductWithWarning, safewareVirtualGroupingProductWithWarningUrl, userId, "Virtual Grouping", true, true)
        }
        closeBrowser()
    }
    
    //Method used as the basis for testing No Thanks for the Warranty Modal

    private void testingWarrantyModalAddToCartNoThanks(String itemNumber, String url, Integer userId, String itemType, boolean isSafeware, boolean hasWarning){
        baseTestingWarrantyModalAddToCart(url, userId, itemType, isSafeware, hasWarning)
        
        assert tryClick(baseWarrantyModal.buttonNoThanks)
        sleep(3000)
        assert !verifyElement(baseWarrantyModal.commercialUseOnlyModal)
        
        assert tryLoad("cart")

        CartItemBox cartItem = new CartItemBox(itemNumber)

        assert cartItem.verifyItemInCart(itemNumber)
        assert !cartItem.verifyAccessoryItem()
        Cart.emptyCart()
    }
}

package framework.wss.pages.productdetail.pricetile.warrantymodal.addtocart.updatecoverage.residentialoption

import framework.wss.pages.productdetail.pricetile.warrantymodal.addtocart.UtPriceTileWarrantyModalAddToCart
import wss.cart.Cart
import wss.pages.cart.CartItemBox

abstract class UtPriceTileWarrantyModalATCUCResidentialOption extends UtPriceTileWarrantyModalAddToCart{

    def test() {

        setup('mwestacott', 'PDPage Product Detail Page Price Tile Warranty Modal Add to Cart Update Coverage unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test pdpage pdp product detail page price tile warranty modal add to cart update coverage',
                 "PBI:0",
                 'logLevel:info'])

        testingWarrantyModalAddToCartScenarioUpdateCoverage()
    }

    abstract void testingWarrantyModalAddToCartScenarioUpdateCoverage()

    //Method for testing all possible warranty modal add to cart scenarios per user and if warranty gets selected.
    protected void testingWarrantyModalAddToCartUpdateCoverageBasedOnUser(String userType){
        Integer userId = getUserIdBasedOnUserType(userType)

        testingWarrantyModalAddToCartUpdateCoverage(rkwLoneProductWithNoWarning, rkwLoneProductWithNoWarningUrl, userId, "Lone", false, false)
        testingWarrantyModalAddToCartUpdateCoverage(rkwVirtualGroupingProductWithNoWarning, rkwVirtualGroupingProductWithNoWarningUrl, userId, "Virtual Grouping", false, false)

        testingWarrantyModalAddToCartUpdateCoverage(rkwLoneProductWithWarning, rkwLoneProductWithWarningUrl, userId, "Lone", false, true)

        if(userType != 'residential') {
            testingWarrantyModalAddToCartUpdateCoverage(safewareLoneProductWithNoWarning, safewareLoneProductWithNoWarningUrl, userId, "Lone", true, false)
            testingWarrantyModalAddToCartUpdateCoverage(safewareVirtualGroupingProductWithNoWarning, safewareVirtualGroupingProductWithNoWarningUrl, userId, "Virtual Grouping", true, false)

            testingWarrantyModalAddToCartUpdateCoverage(safewareLoneProductWithWarning, safewareLoneProductWithWarningUrl, userId, "Lone", true, true)
            testingWarrantyModalAddToCartUpdateCoverage(safewareVirtualGroupingProductWithWarning, safewareVirtualGroupingProductWithWarningUrl, userId, "Virtual Grouping", true, true)
        }
        closeBrowser()
    }

    //Method used as the basis for testing Update Coverage for the Warranty Modal

    private void testingWarrantyModalAddToCartUpdateCoverage(String itemNumber, String url, Integer userId, String itemType, boolean isSafeware, boolean hasWarning){
        baseTestingWarrantyModalAddToCart(url, userId, itemType, isSafeware, hasWarning)
        def warranty = baseWarrantyModal.getWarrantyAssociatedWithProductAndWarrantyId(itemNumber, selectedWarrantyId)
        assert tryClick(warrantyModal.buttonUpdateCoverage)
        assert waitForElement(baseWarrantyModal.commercialUseOnlyModal)
        assert tryClick(baseWarrantyModal.commercialUseOnlyNoResidentialUse)

        sleep(3000)
        assert !verifyElement(baseWarrantyModal.commercialUseOnlyModal)

        assert tryLoad("cart")

        CartItemBox cartItem = new CartItemBox(itemNumber)

        assert cartItem.verifyItemInCart(itemNumber)
        assert !cartItem.verifyAccessoryItem()

        Cart.emptyCart()
    }
}

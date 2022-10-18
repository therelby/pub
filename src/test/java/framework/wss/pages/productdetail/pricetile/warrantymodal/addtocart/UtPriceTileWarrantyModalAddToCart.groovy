package framework.wss.pages.productdetail.pricetile.warrantymodal.addtocart

import framework.wss.pages.productdetail.pricetile.warrantymodal.UtPriceTileWarrantyModal
import wss.pages.productdetail.PDPPriceTile

/**
 * Base class for handling all warranty modal add to cart unit tests
 *
 * @author mwestacott
 *
 */

abstract class UtPriceTileWarrantyModalAddToCart extends UtPriceTileWarrantyModal{

    protected Integer selectedWarrantyId

    protected void baseTestingWarrantyModalAddToCart(String url, Integer userId, String itemType, boolean isSafeware, boolean hasWarning){
        setup(url, userId, itemType, isSafeware)

        assert tryClick(PDPPriceTile.addToCartButtonXpath)
        sleep(3000)
        assert (verifyElement(baseWarrantyModal.warningModal) == hasWarning)

        if(hasWarning){
            assert tryClick(baseWarrantyModal.warningModalIUnderstandButton)
            sleep(3000)
            assert !verifyElement(baseWarrantyModal.warningModal)
        }

        assert verifyElement(modalWarrantyXpath)

        warrantyModal = baseWarrantyModal.inhouseWarrantyModal

        String isWarrantyGoingToBeSelectedBaseXpath = warrantyModal.planOptions
        String isWarrantyGoingToBeSelectedContainsNoAdditionalProtectionBaseXpath = "contains(text(), 'No Additional Protection')"
        String isWarrantyGoingToBeSelectedNotHandling = "[not($isWarrantyGoingToBeSelectedContainsNoAdditionalProtectionBaseXpath)]"
        String isWarrantyGoingToBeSelectedXpath = "$isWarrantyGoingToBeSelectedBaseXpath$isWarrantyGoingToBeSelectedNotHandling/input"

        assert tryClick(isWarrantyGoingToBeSelectedXpath)

        selectedWarrantyId = getAttribute(isWarrantyGoingToBeSelectedXpath, "value").toInteger()
    }

}

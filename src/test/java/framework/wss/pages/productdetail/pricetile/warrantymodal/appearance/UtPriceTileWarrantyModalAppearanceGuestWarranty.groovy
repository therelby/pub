package framework.wss.pages.productdetail.pricetile.warrantymodal.appearance

/**
 * Class for handling warranty modal appearance unit tests for guest users and when warranty is going to be selected
 *
 * @author mwestacott
 *
 */
class UtPriceTileWarrantyModalAppearanceGuestWarranty extends UtPriceTileWarrantyModalAppearance{
    void testingWarrantyModalAppearanceScenario(){
        testingWarrantyModalAppearanceBasedOnUser("guest", true)
    }
}

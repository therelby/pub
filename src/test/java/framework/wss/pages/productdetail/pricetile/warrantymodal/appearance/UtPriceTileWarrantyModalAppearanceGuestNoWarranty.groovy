package framework.wss.pages.productdetail.pricetile.warrantymodal.appearance

/**
 * Class for handling warranty modal appearance unit tests for guest users and when warranty isn't going to be selected
 *
 * @author mwestacott
 *
 */
class UtPriceTileWarrantyModalAppearanceGuestNoWarranty extends UtPriceTileWarrantyModalAppearance{

    void testingWarrantyModalAppearanceScenario(){
        testingWarrantyModalAppearanceBasedOnUser("guest", false)
    }
}

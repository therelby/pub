package framework.wss.pages.productdetail.pricetile.warrantymodal.appearance

/**
 * Class for handling warranty modal appearance unit tests for residential users and when warranty is going to be selected
 *
 * @author mwestacott
 *
 */
class UtPriceTileWarrantyModalAppearanceResidentialWarranty extends UtPriceTileWarrantyModalAppearance{
    void testingWarrantyModalAppearanceScenario(){
        testingWarrantyModalAppearanceBasedOnUser("residential", true)
    }
}

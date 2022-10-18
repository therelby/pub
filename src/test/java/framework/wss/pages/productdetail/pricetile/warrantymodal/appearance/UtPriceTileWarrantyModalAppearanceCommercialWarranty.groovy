package framework.wss.pages.productdetail.pricetile.warrantymodal.appearance

/**
 * Class for handling warranty modal appearance unit tests for commercial users and when warranty is going to be selected
 *
 * @author mwestacott
 *
 */
class UtPriceTileWarrantyModalAppearanceCommercialWarranty extends UtPriceTileWarrantyModalAppearance{
    void testingWarrantyModalAppearanceScenario(){
        testingWarrantyModalAppearanceBasedOnUser("commercial", true)
    }
}

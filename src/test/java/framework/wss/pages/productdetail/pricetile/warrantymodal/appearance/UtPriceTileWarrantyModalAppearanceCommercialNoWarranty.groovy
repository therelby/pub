package framework.wss.pages.productdetail.pricetile.warrantymodal.appearance

/**
 * Class for handling warranty modal appearance unit tests for commercial users and when warranty isn't going to be selected
 *
 * @author mwestacott
 *
 */
class UtPriceTileWarrantyModalAppearanceCommercialNoWarranty extends UtPriceTileWarrantyModalAppearance{

    void testingWarrantyModalAppearanceScenario(){
        testingWarrantyModalAppearanceBasedOnUser("commercial", false)
    }
}

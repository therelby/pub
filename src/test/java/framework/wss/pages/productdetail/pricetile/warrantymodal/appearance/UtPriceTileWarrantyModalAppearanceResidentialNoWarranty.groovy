package framework.wss.pages.productdetail.pricetile.warrantymodal.appearance

/**
 * Class for handling warranty modal appearance unit tests for residential users and when warranty isn't going to be selected
 *
 * @author mwestacott
 *
 */

class UtPriceTileWarrantyModalAppearanceResidentialNoWarranty extends UtPriceTileWarrantyModalAppearance{

    void testingWarrantyModalAppearanceScenario(){
        testingWarrantyModalAppearanceBasedOnUser("residential", false)
    }
}

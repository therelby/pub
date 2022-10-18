package framework.wss.pages.productdetail.pricetile.warrantymodal.appearance

import framework.wss.pages.productdetail.pricetile.warrantymodal.UtPriceTileWarrantyModal
import wss.pages.productdetail.PDPPriceTile

/**
 * Base class for handling all warranty modal appearance unit tests
 *
 * @author mwestacott
 *
 */

abstract class UtPriceTileWarrantyModalAppearance extends UtPriceTileWarrantyModal{

    def test() {

        setup('mwestacott', 'PDPage Product Detail Page Price Tile Warranty Modal Appearance unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test pdpage pdp product detail page price tile warranty modal appearance',
                 "PBI:0",
                 'logLevel:info'])

        testingWarrantyModalAppearanceScenario()
    }

    //Abstract method for testing warranty modal appearance.
    //This will be overriden in parent classes to use the appropriate test scenario.
    abstract void testingWarrantyModalAppearanceScenario()

    //Method for testing all possible warranty modal appearance scenarios per user and if warranty gets selected.
    protected void testingWarrantyModalAppearanceBasedOnUser(String userType, boolean isWarrantyGoingToBeSelected){
        Integer userId = getUserIdBasedOnUserType(userType)

        testingWarrantyModalAppearance(rkwLoneProductWithNoWarningUrl, userId, "Lone", false, false, isWarrantyGoingToBeSelected)
        testingWarrantyModalAppearance(rkwLoneSuffixProductWithNoWarningUrl, userId, "Lone Suffix", false, false, isWarrantyGoingToBeSelected)
        testingWarrantyModalAppearance(rkwVirtualGroupingProductWithNoWarningUrl, userId, "Virtual Grouping", false, false, isWarrantyGoingToBeSelected)

        testingWarrantyModalAppearance(rkwLoneProductWithWarningUrl, userId, "Lone", false, true, isWarrantyGoingToBeSelected)

        testingWarrantyModalAppearance(safewareLoneProductWithNoWarningUrl, userId, "Lone", true, false, isWarrantyGoingToBeSelected)
        testingWarrantyModalAppearance(safewareLoneSuffixProductWithNoWarningUrl, userId, "Lone Suffix", true, false, isWarrantyGoingToBeSelected)
        testingWarrantyModalAppearance(safewareVirtualGroupingProductWithNoWarningUrl, userId, "Virtual Grouping", true, false, isWarrantyGoingToBeSelected)

        testingWarrantyModalAppearance(safewareLoneProductWithWarningUrl, userId, "Lone", true, true, isWarrantyGoingToBeSelected)
        testingWarrantyModalAppearance(safewareLoneSuffixProductWithWarningUrl, userId, "Lone Suffix", true, true, isWarrantyGoingToBeSelected)
        testingWarrantyModalAppearance(safewareVirtualGroupingProductWithWarningUrl, userId, "Virtual Grouping", true, true, isWarrantyGoingToBeSelected)

        closeBrowser()
    }

    //Method used as the basis for testing the warranty modal
    private void testingWarrantyModalAppearance(String url, Integer userId, String itemType, boolean isSafeware, boolean hasWarning, boolean isWarrantyGoingToBeSelected){

        setup(url, userId, itemType, isSafeware)

        assert tryClick(PDPPriceTile.addToCartButtonXpath)
        sleep(3000)
        assert (verifyElement(baseWarrantyModal.warningModal) == hasWarning)

        if(hasWarning){
            assert tryClick(baseWarrantyModal.warningModalIUnderstandButton)
            sleep(3000)
            assert !verifyElement(baseWarrantyModal.warningModal)
        }

        boolean isUserResidential = (userId == 655)
        boolean isAModalExpectedToAppear = (!isUserResidential || !isSafeware)
        boolean doesModalAppear = verifyElement(modalWarrantyXpath)

        assert (doesModalAppear == isAModalExpectedToAppear)

        if(isAModalExpectedToAppear){
            def warrantyModal = isWarrantyGoingToBeSelected ? baseWarrantyModal.inhouseWarrantyModal: baseWarrantyModal.externalWarrantyModal

            String isWarrantyGoingToBeSelectedBaseXpath = warrantyModal.planOptions
            String isWarrantyGoingToBeSelectedContainsNoAdditionalProtectionBaseXpath = "contains(text(), 'No Additional Protection')"
            String isWarrantyGoingToBeSelectedNotHandling = isWarrantyGoingToBeSelected ? "[not($isWarrantyGoingToBeSelectedContainsNoAdditionalProtectionBaseXpath)]" : "[$isWarrantyGoingToBeSelectedContainsNoAdditionalProtectionBaseXpath]"
            String isWarrantyGoingToBeSelectedXpath = "$isWarrantyGoingToBeSelectedBaseXpath$isWarrantyGoingToBeSelectedNotHandling/input"

            assert tryClick(isWarrantyGoingToBeSelectedXpath)
            assert tryClick(warrantyModal.buttonUpdateCoverage)

            sleep(3000)

            if(isWarrantyGoingToBeSelected) {
                boolean isCommercialUseModalExpectedToAppear = isCommercialUseModalExpectedToAppear(userId, isWarrantyGoingToBeSelected, isSafeware)
                boolean doesCommercialUseModalAppear = verifyElement(baseWarrantyModal.commercialUseOnlyModal)

                assert (doesCommercialUseModalAppear == isCommercialUseModalExpectedToAppear)

                if (isCommercialUseModalExpectedToAppear) {
                    assert verifyElement(baseWarrantyModal.commercialUseOnlyModalWarning)
                    assert getTextSafe(baseWarrantyModal.commercialUseOnlyModalParagraphOne) == 'This product is only eligible for an extended warranty if used in a commercial setting. Please verify that this product will be used solely in a commercial setting.'
                    assert getTextSafe(baseWarrantyModal.commercialUseOnlyModalParagraphTwo) == 'WebstaurantStore will not be responsible for any loss due to use of this product in a non-commercial setting.'

                    assert verifyElement(baseWarrantyModal.commercialUseOnlyYesCommercialUseOnly)
                    assert verifyElement(baseWarrantyModal.commercialUseOnlyNoResidentialUse)
                    assert verifyElement(baseWarrantyModal.commercialUseOnlyModalXButton)

                    assert tryClick(baseWarrantyModal.commercialUseOnlyModalXButton)
                    sleep(3000)
                    assert !verifyElement(baseWarrantyModal.commercialUseOnlyModal)
                }
            }
        }
    }

}

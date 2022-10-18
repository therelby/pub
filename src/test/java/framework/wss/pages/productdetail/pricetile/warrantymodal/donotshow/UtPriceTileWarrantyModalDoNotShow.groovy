package framework.wss.pages.productdetail.pricetile.warrantymodal.donotshow

import framework.wss.pages.productdetail.pricetile.warrantymodal.UtPriceTileWarrantyModal
import org.openqa.selenium.WebElement
import wss.pages.productdetail.PDPPriceTile

/**
 * Base class for handling all warranty modal do not show unit tests
 *
 * @author mwestacott
 *
 */

abstract class UtPriceTileWarrantyModalDoNotShow extends UtPriceTileWarrantyModal{

    def test() {

        setup('mwestacott', 'PDPage Product Detail Page Price Tile Warranty Modal Do Not Show unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test pdpage pdp product detail page price tile warranty modal do not show',
                 "PBI:0",
                 'logLevel:info'])

        testingWarrantyModalDoNotShowScenario()
    }

    //Abstract method for testing warranty modal do not show.
    //This will be overriden in parent classes to use the appropriate test scenario.
    abstract void testingWarrantyModalDoNotShowScenario()

    //Method for testing all possible warranty modal do not show scenarios per user and if warranty gets selected.
    protected void testingWarrantyModalDoNotShowBasedOnUser(String userType){
        Integer userId = getUserIdBasedOnUserType(userType)

        testingWarrantyModalDoNotShow(rkwLoneProductWithNoWarningUrl, userId, "Lone", false, false)
        testingWarrantyModalDoNotShow(rkwLoneSuffixProductWithNoWarningUrl, userId, "Lone Suffix", false, false)
        testingWarrantyModalDoNotShow(rkwVirtualGroupingProductWithNoWarningUrl, userId, "Virtual Grouping", false, false)

        testingWarrantyModalDoNotShow(rkwLoneProductWithWarningUrl, userId, "Lone", false, true)

        if(userType != 'residential') {
            testingWarrantyModalDoNotShow(safewareLoneProductWithNoWarningUrl, userId, "Lone", true, false)
            testingWarrantyModalDoNotShow(safewareLoneSuffixProductWithNoWarningUrl, userId, "Lone Suffix", true, false)
            testingWarrantyModalDoNotShow(safewareVirtualGroupingProductWithNoWarningUrl, userId, "Virtual Grouping", true, false)

            testingWarrantyModalDoNotShow(safewareLoneProductWithWarningUrl, userId, "Lone", true, true)
            testingWarrantyModalDoNotShow(safewareLoneSuffixProductWithWarningUrl, userId, "Lone Suffix", true, true)
            testingWarrantyModalDoNotShow(safewareVirtualGroupingProductWithWarningUrl, userId, "Virtual Grouping", true, true)
        }
    }

    //Method used as the basis for testing the warranty modal
    private void testingWarrantyModalDoNotShow(String url, Integer userId, String itemType, boolean isSafeware, boolean hasWarning){

        setup(url, userId, itemType, isSafeware)

        gettingToModalWarranty(hasWarning, true)

        assert !verifyCookie("doNotShowWarrantyModal")

        assert !find(baseWarrantyModal.doNotShowSectionCheckbox).isSelected()
        assert tryClick(baseWarrantyModal.doNotShowSectionCheckbox)
        assert find(baseWarrantyModal.doNotShowSectionCheckbox).isSelected()

        assert verifyCookie("doNotShowWarrantyModal")
        assert getCookie("doNotShowWarrantyModal") == "true"

        assert tryClick(baseWarrantyModal.buttonNoThanks)
        sleep(3000)
        assert !verifyElement(baseWarrantyModal.modalWarranty)

        refresh()

        gettingToModalWarranty(hasWarning, false)

        assert removeCookie("doNotShowWarrantyModal")

        refresh()

        gettingToModalWarranty(hasWarning, true)

        assert !verifyCookie("doNotShowWarrantyModal")

        assert !find(baseWarrantyModal.doNotShowSectionCheckbox).isSelected()
        assert tryClick(baseWarrantyModal.doNotShowSectionCheckbox)
        assert find(baseWarrantyModal.doNotShowSectionCheckbox).isSelected()
        assert tryClick(baseWarrantyModal.doNotShowSectionCheckbox)
        assert !find(baseWarrantyModal.doNotShowSectionCheckbox).isSelected()

        assert verifyCookie("doNotShowWarrantyModal")
        assert getCookie("doNotShowWarrantyModal") == "false"

        refresh()

        gettingToModalWarranty(hasWarning, true)

        closeBrowser()
    }
    
    private void gettingToModalWarranty(boolean hasWarning, boolean isWarrantySupposedToAppear){
        assert tryClick(PDPPriceTile.addToCartButtonXpath)
        sleep(3000)
        assert (verifyElement(baseWarrantyModal.warningModal) == hasWarning)

        if(hasWarning){
            assert tryClick(baseWarrantyModal.warningModalIUnderstandButton)
            sleep(3000)
            assert !verifyElement(baseWarrantyModal.warningModal)
        }

        assert verifyElement(modalWarrantyXpath) == isWarrantySupposedToAppear
    }

}

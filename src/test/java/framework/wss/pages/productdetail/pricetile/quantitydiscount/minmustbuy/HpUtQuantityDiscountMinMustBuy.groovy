package framework.wss.pages.productdetail.pricetile.quantitydiscount.minmustbuy

import above.RunWeb
import all.Money
import wss.pages.element.PopoverTemplate
import wss.pages.productdetail.PDPPriceTile
import org.openqa.selenium.support.ui.Select
import wss.item.QuantityDiscountMinMustBuyUtil

class HpUtQuantityDiscountMinMustBuy extends RunWeb{

    protected void testingQuantityDiscount(String scenario, String itemNumber, boolean hasQuantityDiscount, boolean hasLotDiscount, String uom, int minBuy, int mustBuy, String isUnavailable, String url){

        openAndTryLoad(url)

        int count = 1

        boolean hasOnlyLotDiscounts = !hasQuantityDiscount && hasLotDiscount

        def quantityDiscounts = getQuantityDiscounts(scenario, itemNumber, hasOnlyLotDiscounts)
        int quantityDiscountSize = quantityDiscounts.size()

        Boolean doesQuantityDiscountSectionHaveLotDiscount = determineIfQuantityDiscountHasLotDiscount(quantityDiscounts[0]['has_discount_lot'])

        testingEverythingButLastRowOfQuantityDiscountTable(doesQuantityDiscountSectionHaveLotDiscount, uom, quantityDiscounts[0], true)
        if(quantityDiscountSize>1){

            for(int i = 1; i < quantityDiscountSize; i++){
                doesQuantityDiscountSectionHaveLotDiscount = determineIfQuantityDiscountHasLotDiscount(quantityDiscounts[i]['has_discount_lot'])
                testingEverythingButLastRowOfQuantityDiscountTable(doesQuantityDiscountSectionHaveLotDiscount, uom, quantityDiscounts[i], false, count)
                count++
            }
        }

        Boolean isUnavailableSectionExpectedToAppear = determineIfProductIsUnavailable(isUnavailable)
        Boolean doesUnavailableSectionAppear = verifyElement(PDPPriceTile.unavailableBlock)

        assert doesUnavailableSectionAppear == isUnavailableSectionExpectedToAppear

        if(!isUnavailableSectionExpectedToAppear){
            String expectedMinMustBuyMessage = getExpectedMinMustBuyMessage(minBuy, mustBuy)
            String actualMinMustBuyMessage = getTextSafe(PDPPriceTile.minMustTextXpath)

            assert actualMinMustBuyMessage == expectedMinMustBuyMessage

            assert verifyElement(PDPPriceTile.selectMinBuyQuantityXpath)

            int minimumDropdownQuantity = getMinMustDropdownQuantity(minBuy, mustBuy)
            Select minMustBuyDropdown = new Select(find(PDPPriceTile.selectMinBuyQuantityXpath))

            assert !verifyElement(PDPPriceTile.addToCartQuantityXpath)

            for(int i = 1; i <= 15; i++){
                minMustBuyDropdown.selectByIndex(i)

                Integer expectedValue = minimumDropdownQuantity + ((i-1) * mustBuy)
                Integer actualValue = minMustBuyDropdown.firstSelectedOption.getAttribute("value").toInteger()

                assert actualValue == expectedValue

                assert !verifyElement(PDPPriceTile.addToCartQuantityXpath)
            }

            minMustBuyDropdown.selectByValue('other')
            assert waitForElement(PDPPriceTile.addToCartQuantityXpath)
            assert getAttribute(PDPPriceTile.addToCartQuantityXpath, "value") == minimumDropdownQuantity.toString()
        }
        else{
            assert !verifyElement(PDPPriceTile.selectMinBuyQuantityXpath)
        }

        closeBrowser()
    }



    def getQuantityDiscounts(String scenario, String itemNumber, boolean hasOnlyLotDiscounts){
        switch(scenario){
            case "betweenstartendquantity":
                return QuantityDiscountMinMustBuyUtil.queryGetProductInformationForProductWithMinMustBuyBetweenStartEndQuantity(itemNumber, hasOnlyLotDiscounts)
                break
            case "biggerallstartquantity":
                return QuantityDiscountMinMustBuyUtil.queryGetProductInformationForProductWithMinMustBuyBiggerThanAllQuantityDiscountStartQuantity(itemNumber, hasOnlyLotDiscounts)
                break
            case "greaterthanstartendquantity":
                return QuantityDiscountMinMustBuyUtil.queryGetProductInformationForProductWithMinMustBuyGreaterThanStartEndQuantity(itemNumber, hasOnlyLotDiscounts)
                break
            default:
                return null
                break
        }
    }

    private void testingEverythingButLastRowOfQuantityDiscountTable(boolean hasLotDiscount, String uom, def quantityDiscountSection, boolean isFirstRow, int count = 0){
        String actualQuantityDiscountRowLabelXpath = isFirstRow ? PDPPriceTile.standardLabelXpath : PDPPriceTile.quantityDiscountTableXpath + "/tbody/tr[$count]/th"
        String actualQuantityDiscountRowLabelLotsQuestionMark = actualQuantityDiscountRowLabelXpath + "//a[@class='more-info']"
        String actualQuantityDiscountRowPriceXpath = isFirstRow ? PDPPriceTile.standardPriceXpath : PDPPriceTile.quantityDiscountTableXpath + "/tbody/tr[$count]/td"

        def defaultPrice = quantityDiscountSection["quantity_discount_amount"]

        String expectedQuantityDiscountLabel = getExpectedQuantityDiscountLabel(hasLotDiscount, quantityDiscountSection, isFirstRow)
        String actualQuantityDiscountLabel = getTextSafe(actualQuantityDiscountRowLabelXpath)

        assert (actualQuantityDiscountLabel == expectedQuantityDiscountLabel)
        Boolean expectedToHaveLotsQuestionMark = (hasLotDiscount && (quantityDiscountSection['quantity_discount_start_qty'] != null))
        assert verifyElement(actualQuantityDiscountRowLabelLotsQuestionMark) == expectedToHaveLotsQuestionMark

        if(expectedToHaveLotsQuestionMark){
            assert mouseOver(actualQuantityDiscountRowLabelLotsQuestionMark)
            assert waitForElementVisible(PopoverTemplate.popover)

            String expectedPopoverTitle = "Lot Pricing"
            String actualPopoverTitle = getTextSafe(PopoverTemplate.popoverTitle)

            assert (actualPopoverTitle == expectedPopoverTitle)

            String expectedPopoverText = "Lot pricing allows you to buy an item in a set bulk quantity to save money. We list the amount of product you need to purchase in order to receive lot pricing under the orange ribbon, so you always know how to get the best deal. The same discount is applied for each lot you purchase."
            String actualPopoverText = getTextSafe(PopoverTemplate.popoverContent)

            assert (actualPopoverText == expectedPopoverText)

            assert mouseOver(actualQuantityDiscountRowPriceXpath)

        }

        String expectedPrice = new Money(defaultPrice)
        String expectedPriceAndUom = "$expectedPrice/$uom"
        String actualPriceAndUom = getTextSafe(actualQuantityDiscountRowPriceXpath)

        // The if statement below accounts for scenario where divide price is triggered.
        // Removing text so it doesn't get conflated with price and UOM.
        if(actualPriceAndUom.contains('\nOnly')) {
            int actualPriceAndUomLineBreakOnlyLocation = actualPriceAndUom.indexOf('\nOnly')
            actualPriceAndUom = actualPriceAndUom.substring(0, actualPriceAndUomLineBreakOnlyLocation)
        }

        assert (actualPriceAndUom == expectedPriceAndUom)
    }

    private String getExpectedQuantityDiscountLabel(boolean doesQuantityDiscountHaveLotDiscount, def quantityDiscountSection, boolean isFirstRow){
        if(doesQuantityDiscountHaveLotDiscount && (quantityDiscountSection['quantity_discount_start_qty'] == null)){
            return "Regularly:"
        }
        else{
            int quantityDiscountStartQuantity = quantityDiscountSection["quantity_discount_start_qty"]
            if(doesQuantityDiscountHaveLotDiscount){
                return "Buy in lots of $quantityDiscountStartQuantity:"
            }
            else{
                String quantityDiscountMessage = "$quantityDiscountStartQuantity" + (isFirstRow ? " or more": (" - " + quantityDiscountSection["quantity_discount_end_qty"]))
                return "Buy " + quantityDiscountMessage

            }
        }
    }

    private Boolean determineIfQuantityDiscountHasLotDiscount(String hasLotDiscount){
        switch(hasLotDiscount){
            case "Y":
                return true
            case "N":
                return false
            default:
                return null
        }
    }

    private Boolean determineIfProductIsUnavailable(String isUnavailable){
        switch(isUnavailable){
            case "Y":
                return true
            case "N":
                return false
            default:
                return null
        }
    }

    private String getExpectedMinMustBuyMessage(int minBuy, int mustBuy){
        if(minBuy > 1 && mustBuy > 1){
            int minMustBuy
            if((minBuy % mustBuy) > 0) {
                int minBuyDividedByMustBuy = (minBuy / mustBuy)
                minMustBuy = (minBuyDividedByMustBuy + 1) * mustBuy
            }
            else{
                minMustBuy = minBuy
            }
            return "You must buy a minimum of ${minMustBuy} and in additional increments of ${mustBuy} due to manufacturer packaging."
        }
        else if (minBuy > 1 && mustBuy <= 1){
            return "You must buy a minimum of ${minBuy} due to manufacturer packaging."
        }
        else if (minBuy <= 1 && mustBuy > 1){
            return "You must buy a minimum of ${mustBuy} at a time due to manufacturer packaging."
        }
        else{
            return ""
        }
    }

    private int getMinMustDropdownQuantity(int minBuyQuantity, int mustBuyQuantity){
        if((minBuyQuantity > mustBuyQuantity) && (minBuyQuantity % mustBuyQuantity) > 0){
            int minBuyDividedByMustBuy = (minBuyQuantity/mustBuyQuantity)
            return mustBuyQuantity * (minBuyDividedByMustBuy + 1)
        }
        else{
            return Math.max(minBuyQuantity, mustBuyQuantity)
        }
    }
}

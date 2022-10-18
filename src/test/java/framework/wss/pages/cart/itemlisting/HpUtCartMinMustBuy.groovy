package framework.wss.pages.cart.itemlisting

import above.RunWeb
import wss.pages.cart.CartItemBox
import wss.pages.cart.ViewCartPage

class HpUtCartMinMustBuy extends RunWeb{

    ViewCartPage viewCartPage
    CartItemBox cartItemBox

    void testSetup(String itemNumber, int minBuy, int mustBuy){
        viewCartPage = new ViewCartPage()

        int quantityToAddToCart = minBuy > 1 ? minBuy : mustBuy

        if(!viewCartPage.addItemToCart(itemNumber, quantityToAddToCart)){
            report("Skipping remaining tests due to failure to add product to cart.")
            return
        }

        cartItemBox = new CartItemBox(itemNumber)
    }

    //isIncrementing:
    //false = decrementing
    //true = incrementing
    void incrementingAndDecrementingItem(boolean isIncrementing, int minBuy, int mustBuy){
        int quantityBoundary = getQuantityBoundary(minBuy, mustBuy)
        for(int i = 1; i <= 5; i++){
            boolean isMinOrMustBuyGreaterThanOne = (minBuy > 1 || mustBuy > 1)
            boolean isOnFirstIteration = (isIncrementing && i==1) || (!isIncrementing && i==5)
            boolean isMinOrMustBuyGreaterThanOneAndOnFirstIteration = isMinOrMustBuyGreaterThanOne && isOnFirstIteration

            assert (isMinOrMustBuyGreaterThanOneAndOnFirstIteration) ? !elementClickable(cartItemBox.decrementQuantity) : elementClickable(cartItemBox.decrementQuantity)
            assert elementClickable(cartItemBox.incrementQuantity)

            int currentQuantity = isIncrementing ? (minBuy + i) : (quantityBoundary - i)
            assert isIncrementing ? cartItemBox.incrementQuantity() : cartItemBox.decrementQuantity()

            sleep(5000)

            assert cartItemBox.getQuantity() == currentQuantity
            assert verifyIfCartItemBoxTotalIsAccurate(cartItemBox, currentQuantity)
        }
    }

    int getQuantityBoundary(int minBuy, int mustBuy){
        if(mustBuy == 1){
            return minBuy + 5
        }
        else{
            if(minBuy==1){
                return mustBuy * 6
            }
            else {
                return minBuy + (mustBuy * 5)
            }
        }
    }

    void testingAnItemNumber(String itemNumber, int minBuy = 1, int mustBuy = 1){
        testSetup(itemNumber, minBuy, mustBuy)

        assert cartItemBox.getQuantity() == minBuy

        incrementingAndDecrementingItem(true, minBuy, mustBuy)
        incrementingAndDecrementingItem(false, minBuy, mustBuy)

        int otherQuantity = (minBuy + (mustBuy * 15))
        boolean setQuantitySuccess = cartItemBox.setQuantity(otherQuantity)
        assert setQuantitySuccess
        assert !elementVisible(cartItemBox.selectableQuantityMinMustBuy)

        boolean doesQuantityMatch = (cartItemBox.getQuantity() == otherQuantity)
        assert doesQuantityMatch

        assert viewCartPage.emptyCart()
    }

    void testingItemNumberWithMinOrMustBuy(String itemNumber, int minBuy, int mustBuy){
        testSetup(itemNumber, minBuy, mustBuy)

        assert cartItemBox.getQuantity() == Math.max(minBuy, mustBuy)

        testingMustBuyQuantityDropdown(true, minBuy, mustBuy)
        testingMustBuyQuantityDropdown(false, minBuy, mustBuy)
        testingMaxQuantitySelect(minBuy, mustBuy)

        assert viewCartPage.emptyCart()
    }

    void testingMustBuyQuantityDropdown(boolean isIncrementing, int minBuy, int mustBuy){
        def minMustBuyDropdownOptions = cartItemBox.getMinMustBuyDropdownElements()
        assert minMustBuyDropdownOptions.size() == 16

        int index = 1
        for(minMustBuyDropdownOption in minMustBuyDropdownOptions){
            if(index == 16){
                assert minMustBuyDropdownOption.getText() == 'Other'
            }
            else{
                boolean isMinBuyActive = (minBuy > 1)
                boolean isMustBuyActive = (mustBuy > 1)
                int indexToUse = (!isMinBuyActive && isMustBuyActive) ? (index) : (index - 1)
                int mustBuyTimeIndex = (indexToUse * mustBuy)
                int quantityAtIndex = isMinBuyActive ? (minBuy + mustBuyTimeIndex) : mustBuyTimeIndex
                assert minMustBuyDropdownOption.getText() == quantityAtIndex.toString()
            }
            index++
        }

        int quantityBoundary = getQuantityBoundary(minBuy, mustBuy)
        for(int i = 1; i <= 5; i++){

            assert selectByValue(cartItemBox.selectableQuantityMinMustBuy, "Other")

            boolean isMinOrMustBuyGreaterThanOne = (minBuy > 1 || mustBuy > 1)
            boolean isOnFirstIteration = (isIncrementing && i==1)
            boolean isMinOrMustBuyGreaterThanOneAndOnFirstIteration = isMinOrMustBuyGreaterThanOne && isOnFirstIteration

            assert (isMinOrMustBuyGreaterThanOneAndOnFirstIteration) ? !elementClickable(cartItemBox.decrementQuantity) : elementClickable(cartItemBox.decrementQuantity)
            assert elementClickable(cartItemBox.incrementQuantity)

            int currentQuantity = getCurrentQuantity(isIncrementing, i, minBuy, mustBuy, quantityBoundary)
            assert isIncrementing ? cartItemBox.incrementQuantity() : cartItemBox.decrementQuantity()

            sleep(3000)

            assert cartItemBox.getQuantity() == currentQuantity
            assert verifyIfCartItemBoxTotalIsAccurate(cartItemBox, currentQuantity)
        }
    }

    void testingMaxQuantitySelect(int minBuy, int mustBuy){
        assert selectByValue(cartItemBox.selectableQuantityMinMustBuy, "Other")
        int otherQuantity = (minBuy + (mustBuy * 15))
        assert cartItemBox.setQuantity(otherQuantity)
        assert !elementVisible(cartItemBox.selectableQuantityMinMustBuy)
        assert cartItemBox.getQuantity() == otherQuantity
    }

    int getCurrentQuantity(boolean isIncrementing, int index, int minBuy, int mustBuy, int quantityBoundary){
        int currentMustBuyInterval
        if(!isIncrementing){
            currentMustBuyInterval = quantityBoundary - (mustBuy * index)
        }
        else{
            if(minBuy == 1){
                currentMustBuyInterval = mustBuy * (index+1)
            }
            else{
                currentMustBuyInterval = minBuy + (mustBuy * index)
            }
        }
        return currentMustBuyInterval
    }

    boolean verifyIfCartItemBoxTotalIsAccurate(CartItemBox cartItemBox, int currentQuantity){
        def cartItemBoxPrice = cartItemBox.getPrice()
        def expectedCartItemBoxTotal = cartItemBoxPrice.multiply(currentQuantity)
        def actualCartItemBoxTotal = cartItemBox.getTotalPrice()
        return actualCartItemBoxTotal == expectedCartItemBoxTotal
    }

}

package framework.wss.cart

import above.RunWeb
import wss.cart.Cart
import wss.pages.cart.CartItemBox
import wss.pages.productdetail.PDPPriceTile
import wss.pages.productdetail.PDPage

class UtCartQuantityZero extends RunWeb{

    protected def pdpPriceTile
    protected def cartItem

    protected String loneItemNumber = '113980030'
    protected String loneSuffixItemNumber = '625CWM8192WWWH192'
    protected String virtualGroupingItemNumber = '756U534'

    protected String itemNumberOneLot = '271DM212H'
    protected int itemNumberOneLotQuantity = 5

    protected String itemNumberTwoLots = '27124SCRCUTL'
    protected int itemNumberTwoLotsQuantity = 61

    protected String itemNumberThreeLots = '148TLHDCCLNR'
    protected int itemNumberThreeLotsQuantity = 85

    protected String accessoryItemNumber = '132AS42D'

    def test() {

        setup('mwestacott', 'Rewards Customer creation unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test rewards customer creation ',
                 "tfsTcIds:0",
                 'logLevel:info'])

        testing(loneItemNumber)
        testing(loneSuffixItemNumber)
        testing(virtualGroupingItemNumber)

        testing(itemNumberOneLot, itemNumberOneLotQuantity, false, true, 1)
        testing(itemNumberTwoLots, itemNumberTwoLotsQuantity, false, true, 2)
        testing(itemNumberThreeLots, itemNumberThreeLotsQuantity, false, true, 3)

        testing(accessoryItemNumber, 1, true)

        closeBrowser()
    }

    private void testing(String itemNumber, int quantity = 1, boolean hasAccessory = false, boolean hasLots = false, int numberOfLots = 0){
        pdpPriceTile = new PDPPriceTile()

        addItemToCart(itemNumber, quantity, hasAccessory)
        navigatingToCart(itemNumber, hasAccessory, hasLots, numberOfLots)
        reducingQuantityToZero(itemNumber)
    }

    private void addItemToCart(String itemNumber, int quantity, boolean hasAccessory){
        def pdPage = new PDPage()
        assert pdPage.navigateToPDPWithItemNumber(itemNumber)
        assert pdpPriceTile.verifyAddAccessoryDropdown() == hasAccessory
        if(pdpPriceTile.verifyAddAccessoryDropdown()){
            assert pdpPriceTile.selectAllAccessories()
        }

        boolean wasAbleToAddProductToCart = pdpPriceTile.addToCart(quantity)
        wasAbleToAddProductToCart
    }

    private void navigatingToCart(String itemNumber, boolean hasAccessory, boolean hasLots, int numberOfLots){
        assert tryLoad("cart")

        cartItem = new CartItemBox(itemNumber)

        assert cartItem.verifyItemInCart(itemNumber)
        assert cartItem.doesItemHaveLotDiscounts() == hasLots
        if(cartItem.doesItemHaveLotDiscounts()){
            assert cartItem.getNumberOfLotDiscounts() == (numberOfLots+1)
        }
        assert cartItem.verifyAccessoryItem() == hasAccessory

    }

    private void reducingQuantityToZero(String itemNumber){
        assert cartItem.setQuantity(1)
        sleep(5000)
        boolean canDecrementQuality = cartItem.decrementQuantity()
        assert canDecrementQuality
        waitForElement(Cart.emptyCartVerbiageXpath)
        assert !cartItem.verifyItemInCart(itemNumber)
        assert !cartItem.verifyAccessoryItem()
    }
}

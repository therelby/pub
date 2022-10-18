package framework.wss.pages.cart.itemlisting.lotdiscounts

import above.RunWeb
import all.Money
import wss.api.catalog.product.Products
import wss.pages.cart.CartItemBox
import wss.pages.cart.ViewCartPage
import wsstest.cart.itemlisting.lotdiscounts.HpCartLotDiscounts

class UtCartLotDiscounts extends RunWeb{

    String itemNumberOneLotDiscount = "330ALS166"
    int itemNumberOneLotDiscountAmount = 7;

    String itemNumberTwoLotDiscounts = "561L850RED"
    int itemNumberTwoLotDiscountAmount = 19;

    String itemNumberThreeLotDiscounts = "407BUNQRTR"
    int itemNumberThreeLotDiscountAmount = 781;


    static void main(String[] args) {
        new UtCartLotDiscounts().testExecute([

                browser      : 'chrome',
                remoteBrowser: false,
//                browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])

    }

    def test() {

        setup('mwestacott', 'Cart - Lot discounts - Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart lot discounts',
                 "PBI:0", 'logLevel:info'])

        assert openAndTryLoad('cart')

        testingQuantityDiscountItem(itemNumberOneLotDiscount, 1, itemNumberOneLotDiscountAmount)
        testingQuantityDiscountItem(itemNumberTwoLotDiscounts, 2, itemNumberTwoLotDiscountAmount)
        testingQuantityDiscountItem(itemNumberThreeLotDiscounts, 3, itemNumberThreeLotDiscountAmount)

        closeBrowser()
    }

    static boolean testingQuantityDiscountItem(String itemNumber, int numberOfExpectedTiers, int quantity){
        RunWeb r = run()
        def productLotDiscounts = HpCartLotDiscounts.getProductLotDiscounts(itemNumber)
        assert productLotDiscounts.size() == numberOfExpectedTiers

        ViewCartPage viewCartPage = new ViewCartPage()
        assert viewCartPage.addItemToCart(itemNumber, quantity)

        CartItemBox cartItem = new CartItemBox(itemNumber)

        Products api = new Products(itemNumber)
        assert api != null

        def priceInfoSections = api.getValues("price")[1]
        def expectedProductPrice = priceInfoSections["productPrice"]
        def expectedLotDiscounts = priceInfoSections["quantityDiscounts"]
        def actualLotDiscounts = cartItem.getAllLotDiscounts()

        assert (numberOfExpectedTiers+1) == actualLotDiscounts.size()

        for(int i = 0; i < actualLotDiscounts.size(); i++){
            String actualLotQuantityAndMoneyAmountBase = actualLotDiscounts[i].getText()
            String testBrowser = r.testBrowser.minus("remote")
            String actualLotQuantityAndMoneyAmountSplitCharacter = testBrowser == 'safari' ? ' ' : ' '
            String[] actualLotQuantityAndMoneyAmountArray = actualLotQuantityAndMoneyAmountBase.split(actualLotQuantityAndMoneyAmountSplitCharacter)

            int actualLotQuantity = actualLotQuantityAndMoneyAmountArray[0].toInteger()
            Money actualLotMoneyAmount = new Money(actualLotQuantityAndMoneyAmountArray[1])
            if(i==0){
                assert actualLotQuantity == 1
                assert actualLotMoneyAmount == expectedProductPrice
            }
            else{
                assert actualLotQuantity == expectedLotDiscounts[i-1]['quantity']

                String expectedMoneyAmountBase = expectedLotDiscounts[i-1]['price']
                Money expectedMoneyAmount = new Money(expectedMoneyAmountBase)
                assert actualLotMoneyAmount == expectedMoneyAmount
            }
        }
        assert viewCartPage.emptyCart()
    }

}

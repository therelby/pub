package framework.wss.pages.cart.itemlisting.pluspricing

import above.RunWeb
import all.Money
import all.util.StringUtil
import wss.pages.cart.CartItemBox
import wss.pages.cart.ViewCartPage
import wss.user.userurllogin.UserUrlLogin
import wsstest.cart.itemlisting.pluspricing.HpCartPlusPricing

class UtCartPlusPricing extends RunWeb{

    def regularUserWithoutOrderHistory = ['Regular User', 26126723, false, 0, 0]
    def regularUserWithOrderHistory = ['Regular User', 387645, true, 1, 0]
    def regularUserWithOrderHistoryOver1000 = ['Regular User', 12184033, true, 1, 1000.00]
    def platinumUser = ['Platinum', 241217, false, 0, 0]
    def platinumPlusUser = ['Platinum WebPlus', 918, false, 0, 0]
    def plusUser = ['Web Plus', 8011, false, 0, 0]

    String loneProductOnSale = "575300491"
    String virtualGroupingProductOnSale = "164BMLDVLBKD"

    String loneProductP1 = "118PLT2AS014"
    String loneSuffixProductP1 = "999MPBWL10IPREM"
    String virtualGroupingProductP1 = "222TO908BOTT"

    String loneProductP5 = "178Z2RGWMS"

    static void main(String[] args) {
        new UtCartPlusPricing().testExecute([

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

        setup('mwestacott', 'Cart - MAP - Plus Pricing Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart map plus pricing',
                 "tfsTcIds:0", 'logLevel:info'])

        assert HpCartPlusPricing.getProductsCartPlusPricing(5, "Lone", "P1").size() == 5
        assert HpCartPlusPricing.getProductsCartPlusPricing(5, "Lone Suffix", "P1").size() == 5
        assert HpCartPlusPricing.getProductsCartPlusPricing(5, "Virtual Grouping", "P1").size() == 5

        assert HpCartPlusPricing.getProductsCartPlusPricing(5, "Lone", "P5").size() == 5
        assert HpCartPlusPricing.getProductsCartPlusPricing(5, "Lone Suffix", "P5").size() == 0
        assert HpCartPlusPricing.getProductsCartPlusPricing(5, "Virtual Grouping", "P5").size() == 0

        assert HpCartPlusPricing.getProductsCartPlusPricing(5, "Lone", "Sale_Price").size() == 5
        assert HpCartPlusPricing.getProductsCartPlusPricing(5, "Lone Suffix", "Sale_Price").size() == 0
        assert HpCartPlusPricing.getProductsCartPlusPricing(5, "Virtual Grouping", "Sale_Price").size() == 5

        assert openAndTryLoad('plus')

        testingWithSpecificUser(regularUserWithoutOrderHistory)
        testingWithSpecificUser(regularUserWithOrderHistory)
        testingWithSpecificUser(regularUserWithOrderHistoryOver1000)
        testingWithSpecificUser(platinumUser)
        testingWithSpecificUser(platinumPlusUser)
        testingWithSpecificUser(plusUser)

        closeBrowser()
    }

    def testingWithSpecificUser(def userForTesting){
        assert new UserUrlLogin().loginAs(userForTesting[1].toString())

        testingWithSpecificProduct(userForTesting, loneProductOnSale, "Sale_Price")
        testingWithSpecificProduct(userForTesting, virtualGroupingProductOnSale, "Sale_Price")

        testingWithSpecificProduct(userForTesting, loneProductP1, "P1")
        testingWithSpecificProduct(userForTesting, loneSuffixProductP1, "P1")
        testingWithSpecificProduct(userForTesting, virtualGroupingProductP1, "P1")

        testingWithSpecificProduct(userForTesting, loneProductP5, "P5")
    }

    def testingWithSpecificProduct(def userForTesting, String itemNumber, String lowestPrice){
        def itemNumberUnderTest = getIndividualProductCartPlusPricing(itemNumber)[0]

        ViewCartPage viewCartPage = new ViewCartPage()

        assert viewCartPage.navigate()
        assert viewCartPage.emptyCart()

        if(!viewCartPage.addItemToCart(itemNumber)){
            report(0,"Skipping remaining tests due to failure to add product to cart.")
            return
        }

        CartItemBox cartItemBox = new CartItemBox(itemNumber)

        Money expectedPrice = HpCartPlusPricing.getExpectedPrice(itemNumberUnderTest, userForTesting[0], lowestPrice)
        Money actualPrice = cartItemBox.getPrice()

        assert actualPrice == expectedPrice

        assert viewCartPage.emptyCart()
    }

    static String queryIndividualProductCartPlusPricing = '''
    DECLARE @item_number VARCHAR(20) = '$itemNumber';
    
    SELECT PRD.item_number_id
        , PRD.item_number
        , PRD.P1
        , PRD.P4
        , prd.P5
        , PRD.saleprice as Sale_Price
    FROM tblProducts PRD
    WHERE
        PRD.[location] = 851
        AND PRD.hide_item = 'N'
        and prd.unavailable = 'N'
        and prd.item_number = @item_number
    '''

    /**
     * Method returns data related to specific product for testing;
     * mustIsLarger1 - true:mustBuy > 1, false: mustBuy == 1;
     */
    static def getIndividualProductCartPlusPricing(String itemNumber) {
        RunWeb r = run()
        Map binding = [itemNumber: itemNumber]
        String query = StringUtil.formatWithMap(queryIndividualProductCartPlusPricing, binding)
        String connectionName = r.getCurrentEnvironment() == 'test' ? 'wss-ro-test' : 'wss-ro'
        def results = r.dbSelect(query, connectionName)
        r.dbClose(connectionName)
        return results
    }

}

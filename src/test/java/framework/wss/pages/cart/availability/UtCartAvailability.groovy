package framework.wss.pages.cart.availability

import above.RunWeb
import wss.pages.cart.ViewCartPage
import wss.pages.cart.CartItemBox

class UtCartAvailability extends RunWeb{

    def loneProductUnavailable = '959927027'
    def loneSuffixProductUnavailable = '625MPM8192W SLVR192'
    def virtualGroupingProductUnavailable = '8441438R4NV'

    def loneProductAvailable = '34027162'
    def loneSuffixProductAvailable = '999CCC5     CL10'
    def virtualGroupingProductAvailable = '171T184246DZ'

    static void main(String[] args) {
        new UtCartAvailability().testExecute([

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

        setup('mwestacott', 'Cart - Product Availability',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart product availability',
                 "tfsTcIds:0", 'logLevel:info'])

        assert openAndTryLoad('cart')

        testingProductAvailability("Lone", false)
        testingProductAvailability("Lone Suffix", false)
        testingProductAvailability("Virtual Grouping", false)

        testingProductAvailability("Lone", true)
        testingProductAvailability("Lone Suffix", true)
        testingProductAvailability("Virtual Grouping", true)

        assert closeBrowser()
    }

    void testingProductAvailability(String itemType, boolean isUnavailable){
        ViewCartPage viewCartPage = new ViewCartPage()
        String itemNumber = getProductBasedOnItemTypeAndAvailability(itemType, isUnavailable)
        if(!viewCartPage.addItemToCart(itemNumber, 1)){
            report("Skipping remaining tests due to failure to add $itemNumber to cart.")
            return
        }

        CartItemBox cartItemBox = new CartItemBox(itemNumber)

        assert viewCartPage.getAllItems().size() == 1
        assert isUnavailable == cartItemBox.verifyProductUnavailableRibbon()
        if(isUnavailable){
            List<Map> messages = viewCartPage.getAboveMessagesData()
            assert messages.size() == 1
            Map message = messages[0]
            assert message['message'] == viewCartPage.unavailableErrorMessage
        }
        else{
            assert viewCartPage.getAboveMessagesData().size() == 0
        }

        assert viewCartPage.emptyCart()
    }

    String getProductBasedOnItemTypeAndAvailability(String itemType, boolean isUnavailable){
        switch(itemType){
            case "Lone":
                return isUnavailable ? loneProductUnavailable : loneProductAvailable
                break
            case "Lone Suffix":
                return isUnavailable ? loneSuffixProductUnavailable : loneSuffixProductAvailable
                break
            case "Virtual Grouping":
                return isUnavailable ? virtualGroupingProductUnavailable : virtualGroupingProductAvailable
                break
        }
    }
}

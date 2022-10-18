package framework.wss.pages.cart.availability

import above.RunWeb
import wss.pages.cart.ViewCartPage

class UtCartAvailabilityMultiple extends RunWeb{

    def loneProductUnavailable = '959927027'
    def loneSuffixProductUnavailable = '625MPM8192W SLVR192'
    def virtualGroupingProductUnavailable = '8441438R4NV'

    def loneProductAvailable = '34027162'
    def loneSuffixProductAvailable = '999CCC5     CL10'
    def virtualGroupingProductAvailable = '171T184246DZ'

    static void main(String[] args) {
        new UtCartAvailabilityMultiple().testExecute([

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

        testingMultipleAvailableProducts([loneProductAvailable, loneSuffixProductAvailable])
        testingMultipleAvailableProducts([loneProductAvailable, virtualGroupingProductAvailable])
        testingMultipleAvailableProducts([loneSuffixProductAvailable, virtualGroupingProductAvailable])
        testingMultipleAvailableProducts([loneProductAvailable, loneSuffixProductAvailable, virtualGroupingProductAvailable])

        testingMultipleUnavailableProducts([loneProductUnavailable, loneSuffixProductUnavailable])
        testingMultipleUnavailableProducts([loneProductUnavailable, virtualGroupingProductUnavailable])
        testingMultipleUnavailableProducts([loneSuffixProductUnavailable, virtualGroupingProductUnavailable])
        testingMultipleUnavailableProducts([loneProductUnavailable, loneSuffixProductUnavailable, virtualGroupingProductUnavailable])

        testingMultipleAvailableAndUnavailableProducts([loneProductAvailable, loneProductUnavailable])
        testingMultipleAvailableAndUnavailableProducts([loneProductAvailable, loneProductUnavailable, loneSuffixProductUnavailable])
        testingMultipleAvailableAndUnavailableProducts([loneProductAvailable, loneProductUnavailable, loneSuffixProductUnavailable, virtualGroupingProductUnavailable])

        testingMultipleAvailableAndUnavailableProducts([loneProductAvailable, loneProductUnavailable, loneSuffixProductAvailable, virtualGroupingProductUnavailable])

        testingMultipleAvailableAndUnavailableProducts([loneProductAvailable, loneSuffixProductAvailable, virtualGroupingProductAvailable, loneProductUnavailable, loneSuffixProductUnavailable, virtualGroupingProductUnavailable])

        assert closeBrowser()
    }

    void testingMultipleAvailableProducts(def availableProducts){
        ViewCartPage viewCartPage = new ViewCartPage()
        for(availableProduct in availableProducts){
            if(!viewCartPage.addItemToCart(availableProduct, 1)){
                report("Skipping remaining tests due to failure to add $availableProduct to cart.")
                return
            }
        }

        assert viewCartPage.getAllItems().size() == availableProducts.size()
        assert viewCartPage.getAboveMessagesData().size() == 0

        assert viewCartPage.emptyCart()
    }

    void testingMultipleUnavailableProducts(def unavailableProducts){
        ViewCartPage viewCartPage = new ViewCartPage()
        for(unavailableProduct in unavailableProducts){
            if(!viewCartPage.addItemToCart(unavailableProduct, 1)){
                report("Skipping remaining tests due to failure to add $unavailableProduct to cart.")
                return
            }
        }

        assert viewCartPage.getAllItems().size() == unavailableProducts.size()
        List<Map> messages = viewCartPage.getAboveMessagesData()
        assert messages.size() == 1
        Map message = messages[0]
        assert message['message'] == viewCartPage.unavailableErrorMessage

        assert viewCartPage.emptyCart()
    }

    void testingMultipleAvailableAndUnavailableProducts(def availableAndUnavailableProducts){
        ViewCartPage viewCartPage = new ViewCartPage()
        for(availableOrUnavailableProduct in availableAndUnavailableProducts){
            if(!viewCartPage.addItemToCart(availableOrUnavailableProduct, 1)){
                report("Skipping remaining tests due to failure to add $availableOrUnavailableProduct to cart.")
                return
            }
        }

        assert viewCartPage.getAllItems().size() == availableAndUnavailableProducts.size()
        List<Map> messages = viewCartPage.getAboveMessagesData()
        assert messages.size() == 1
        Map message = messages[0]
        assert message['message'] == viewCartPage.unavailableErrorMessage

        assert viewCartPage.emptyCart()
    }
}

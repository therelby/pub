package framework.wss.pages.cart.whenwillireceivemyorder

import above.RunWeb
import above.azure.AzureDevOpsWorkItems
import all.Money
import wss.item.CustomItem
import wss.pages.cart.CartBottomCheckout
import wss.pages.cart.CartWhenWillIReceiveMyOrderModal
import wss.pages.cart.ViewCartPage
import wss.user.userurllogin.UserUrlLogin

class UtCartWhenWillIReceiveMyOrderDiscount extends RunWeb{
    String plusUser = 25035591
    String itemNumber = "101991952"

    static void main(String[] args) {
        new UtCartWhenWillIReceiveMyOrderDiscount().testExecute([

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

        setup('mwestacott', 'Cart - When Will I Receive My Order',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart when will i receive my order',
                 "tfsTcIds:0", 'logLevel:info'])

        assert openAndTryLoad('hp')

        testingByUser()

        assert closeBrowser()
    }

    void testingByUser(){
        assert tryLoad('myaccount')
        assert new UserUrlLogin().loginAs(plusUser)

        testingItemNumber(itemNumber)
    }

    void testingItemNumber(String itemNumber){
        ViewCartPage viewCartPage = new ViewCartPage()
        assert viewCartPage.emptyCart(true)
        assert viewCartPage.addItemToCart(itemNumber, 1)

        CartBottomCheckout cartBottomCheckout = new CartBottomCheckout()
        def shippingOptionsData = cartBottomCheckout.getShippingOptionsData()

        handlingFreeCommonCarrier(shippingOptionsData)
        handlingCommonCarrierWithLiftgate(shippingOptionsData)

        int expectedShippingOptionsSize = shippingOptionsData.size()

        assert viewCartPage.wasAbleToLoadWhenWillIReceiveMyOrderModal()
        CartWhenWillIReceiveMyOrderModal cartWhenWillIReceiveMyOrderModal = new CartWhenWillIReceiveMyOrderModal()
        assert cartWhenWillIReceiveMyOrderModal.isWhenWillIReceiveMyOrderHeaderTitleAccurate()

        for(int i = 1; i<= expectedShippingOptionsSize; i++){
            def shippingOptionsDataRow = shippingOptionsData[i-1]

            String shippingOptionXpath = cartWhenWillIReceiveMyOrderModal.getWhenWillIReceiveMyOrderHeaderListItemButtonXpath(i)
            assert jsClick(shippingOptionXpath)

            String shippingOptionText = shippingOptionsDataRow['name']
            String whenWillIReceiveMyOrderHeaderListItemShippingMethod = cartWhenWillIReceiveMyOrderModal.getWhenWillIReceiveMyOrderHeaderListItemShippingMethod()
            assert (whenWillIReceiveMyOrderHeaderListItemShippingMethod == shippingOptionText)

            boolean isShippingDateExpectedToAppear = (shippingOptionText in ["Second Day", "Next Day"])
            boolean doesShippingDateAppear = verifyElement(CartWhenWillIReceiveMyOrderModal.whenWillIReceiveMyOrderHeaderListItemDeliveryTag)
            assert (doesShippingDateAppear == isShippingDateExpectedToAppear)

            if(isShippingDateExpectedToAppear){
                String expectedShippingDate = cartWhenWillIReceiveMyOrderModal.getExpectedShippingDate(shippingOptionText)
                String actualShippingDate = getTextSafe(CartWhenWillIReceiveMyOrderModal.whenWillIReceiveMyOrderHeaderListItemDeliveryTag)
                assert (actualShippingDate == expectedShippingDate)
            }

            String expectedShippingOptionPriceString = shippingOptionsDataRow['price']
            Money expectedShippingOptionPrice = new Money(expectedShippingOptionPriceString)
            Money whenWillIReceiveMyOrderHeaderListItemPricing = cartWhenWillIReceiveMyOrderModal.getWhenWillIReceiveMyOrderHeaderListItemPricing()

            assert (whenWillIReceiveMyOrderHeaderListItemPricing == expectedShippingOptionPrice)

            doesManufacturerMessageAppearAsExpected(shippingOptionText)
            doesCommonCarrierMessageAppearAsExpected(shippingOptionText)
        }

        assert cartWhenWillIReceiveMyOrderModal.wasAbleToCloseWhenWillIReceiveMyOrderModal()
    }

    void handlingFreeCommonCarrier(def shippingOptionsData){
        int index = 0;
        if(shippingOptionsData["name"].contains("Free Common Carrier")){
            for(shippingOptionData in shippingOptionsData){
                if(shippingOptionData["name"].equals("Free Common Carrier")){
                    shippingOptionData["name"] = "Common Carrier"
                    break
                }
                index++
            }
        }
    }

    void handlingCommonCarrierWithLiftgate(def shippingOptionsData){
        if(shippingOptionsData["name"].contains("Common Carrier W/ Liftgate")){
            for(shippingOptionData in shippingOptionsData){
                if(shippingOptionData["name"].equals("Common Carrier W/ Liftgate")){
                    shippingOptionsData.remove(shippingOptionData)
                    break
                }
            }
        }
    }

    void doesManufacturerMessageAppearAsExpected(String shippingOptionText){
        RunWeb r = run()

        boolean isProductExpectedToShowManufacturerMessage = false
        boolean isShippingMethodExpectedToShowManufacturerMessage = (shippingOptionText in ["Second Day", "Next Day"])
        boolean isManufacturerMessageExpectedToAppear = (isProductExpectedToShowManufacturerMessage && isShippingMethodExpectedToShowManufacturerMessage)
        boolean doesManufacturerMessageAppear = r.verifyElement(CartWhenWillIReceiveMyOrderModal.whenWillIReceiveMyOrderContainerActivePanelDisclaimer)
        boolean doesManufacturerMessageAppearAsExpected = (doesManufacturerMessageAppear == isManufacturerMessageExpectedToAppear)

        assert doesManufacturerMessageAppearAsExpected

        if(isManufacturerMessageExpectedToAppear){
            String expectedManufacturerMessageText = CartWhenWillIReceiveMyOrderModal.whenWillIReceiveMyOrderContainerActivePanelDisclaimerText
            String actualManufacturerMessageText = r.getTextSafe(CartWhenWillIReceiveMyOrderModal.whenWillIReceiveMyOrderContainerActivePanelDisclaimer)

            assert (actualManufacturerMessageText == expectedManufacturerMessageText)
        }

    }

    void doesCommonCarrierMessageAppearAsExpected(String shippingOptionText){
        RunWeb r = run()
        boolean isCommonCarrierMessageExpectedToAppear = (shippingOptionText == "Common Carrier")
        boolean doesCommonCarrierMessageAppear = r.verifyElement(CartWhenWillIReceiveMyOrderModal.whenWillIReceiveMyOrderHeaderDeliveryDisclaimer)
        boolean doesCommonCarrierMessageAppearAsExpected = (doesCommonCarrierMessageAppear == isCommonCarrierMessageExpectedToAppear)
        assert doesCommonCarrierMessageAppearAsExpected

        if(isCommonCarrierMessageExpectedToAppear){
            String expectedCommonCarrierMessageText = CartWhenWillIReceiveMyOrderModal.whenWillIReceiveMyOrderHeaderDeliveryDisclaimerText
            String actualCommonCarrierMessageText = r.getTextSafe(CartWhenWillIReceiveMyOrderModal.whenWillIReceiveMyOrderHeaderDeliveryDisclaimer)

            assert (actualCommonCarrierMessageText == expectedCommonCarrierMessageText)
        }
    }
}

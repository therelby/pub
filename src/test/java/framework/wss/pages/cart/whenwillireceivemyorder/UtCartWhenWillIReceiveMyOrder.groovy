package framework.wss.pages.cart.whenwillireceivemyorder

import above.RunWeb
import all.Money
import wss.item.CustomItem
import wss.pages.cart.CartBottomCheckout
import wss.pages.cart.CartWhenWillIReceiveMyOrderModal
import wss.pages.cart.ViewCartPage
import wss.user.userurllogin.UserUrlLogin
import above.azure.AzureDevOpsWorkItems
import wss.webadmin.WebAdmin
import wss.webadmin.shippingcalculator.WebAdminShippingCalculator

class UtCartWhenWillIReceiveMyOrder extends RunWeb{
    String residentialTestUser = "655"
    String commercialTestUser = "8613901"

    String nonCommonCarrierItem = "107991782"
    String unavailableNonCommonCarrierItem = "164CMBKLSNVY"
    String unavailableCommonCarrierItem = "103991920"
    String availableCommonCarrierItem = "882TBC2HR2LP"
    String optionalLiftgateCommonCarrierItem = "177FF300L"
    String dropShipProduct = "010FI24102"
    String customProduct = "Custom"

    static void main(String[] args) {
        new UtCartWhenWillIReceiveMyOrder().testExecute([

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

        testingByUser(residentialTestUser)
        testingByUser(commercialTestUser)

        assert closeBrowser()
    }

    void testingByUser(String testUser){
        assert tryLoad('myaccount')
        assert new UserUrlLogin().loginAs(testUser)

        testingItemNumber(nonCommonCarrierItem)
        testingItemNumber(unavailableNonCommonCarrierItem)
        testingItemNumber(unavailableCommonCarrierItem)
        testingItemNumber(availableCommonCarrierItem)
        testingItemNumber(optionalLiftgateCommonCarrierItem)
        testingItemNumber(dropShipProduct)
//        testingItemNumber(customProduct)
    }

    void testingItemNumber(String itemNumber){
        ViewCartPage viewCartPage = new ViewCartPage()
        def shippingOptionsDeliveryDays = null
        assert viewCartPage.emptyCart(true)
        if(itemNumber == 'Custom'){
            String filePath = AzureDevOpsWorkItems.downloadAttachment("label.jpg", 257686, 'QA-Webstaurantstore ' +'Projects')
            assert new CustomItem().customizeItemAndAddToCart(filePath)
        }
        else {
            assert viewCartPage.addItemToCart(itemNumber, 1)
        }

        CartBottomCheckout cartBottomCheckout = new CartBottomCheckout()
        def shippingOptionsData = cartBottomCheckout.getShippingOptionsData()

        if(itemNumber in [nonCommonCarrierItem, unavailableNonCommonCarrierItem]){
            String userCartCode = viewCartPage.getCardID()
            assert newTab()
            def listOfTabs = getTabs()
            assert switchToTab(listOfTabs[1])
            assert WebAdmin.loginToWebAdmin()

            String url = getUrl('hp') + WebAdminShippingCalculator.url
            assert tryLoad(url)

            WebAdminShippingCalculator webAdminShippingCalculator = new WebAdminShippingCalculator()
            assert webAdminShippingCalculator.ableToGenerateShippingCalculatorResult("Cart code", userCartCode, null)

            shippingOptionsDeliveryDays = webAdminShippingCalculator.getDeliveryDaysInformation(shippingOptionsData.size())
            for(shippingOptionsDeliveryDay in shippingOptionsDeliveryDays){
                String currentShippingOption = shippingOptionsDeliveryDay['shippingMethod']
                int currentNumberOfDeliveryDays = shippingOptionsDeliveryDay['deliveryDays']

                Date date = new Date()
                Calendar currentTime = Calendar.getInstance()
                currentTime.setTime(date)
                if(currentShippingOption == 'Ground'){
                    Calendar groundCutoff = settingUpCutoffDate(currentShippingOption)
                    if(currentTime > groundCutoff){
                        currentNumberOfDeliveryDays++
                    }
                }
                else if(currentShippingOption in ["Second Day Air", "Next Day Air"]){
                    Calendar priorityCutoff = settingUpCutoffDate(currentShippingOption)
                    if(currentTime > priorityCutoff){
                        currentNumberOfDeliveryDays++
                    }
                }
                if(currentNumberOfDeliveryDays > 3){
                    currentNumberOfDeliveryDays--
                }
                shippingOptionsDeliveryDay['deliveryDays'] = currentNumberOfDeliveryDays

            }

            assert closeTab()
            assert switchToTab(listOfTabs[0])
        }

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

            boolean isShippingDateExpectedToAppear = (shippingOptionText in ["Second Day", "Next Day"]) && !(itemNumber in [dropShipProduct,customProduct])
            boolean doesShippingDateAppear = verifyElement(CartWhenWillIReceiveMyOrderModal.whenWillIReceiveMyOrderHeaderListItemDeliveryTag)
            assert (doesShippingDateAppear == isShippingDateExpectedToAppear)

            if(isShippingDateExpectedToAppear){
                def deliveryDaysDataRow = shippingOptionsDeliveryDays[i-1]
                int deliveryDays = deliveryDaysDataRow["deliveryDays"]

                String expectedShippingDate = cartWhenWillIReceiveMyOrderModal.getExpectedShippingDate(shippingOptionText, deliveryDays)
                String actualShippingDate = getTextSafe(CartWhenWillIReceiveMyOrderModal.whenWillIReceiveMyOrderHeaderListItemDeliveryTag)
                assert (actualShippingDate == expectedShippingDate)
            }

            String expectedShippingOptionPriceString = shippingOptionsDataRow['price']
            Money expectedShippingOptionPrice = new Money(expectedShippingOptionPriceString)
            Money whenWillIReceiveMyOrderHeaderListItemPricing = cartWhenWillIReceiveMyOrderModal.getWhenWillIReceiveMyOrderHeaderListItemPricing()

            assert (whenWillIReceiveMyOrderHeaderListItemPricing == expectedShippingOptionPrice)

            doesManufacturerMessageAppearAsExpected(itemNumber, shippingOptionText)
            doesCommonCarrierMessageAppearAsExpected(shippingOptionText)
        }

        assert cartWhenWillIReceiveMyOrderModal.wasAbleToCloseWhenWillIReceiveMyOrderModal()
    }

    private Calendar settingUpCutoffDate(String shippingMethod){
        Date date = new Date()
        Calendar currentTime = Calendar.getInstance()
        currentTime.setTime(date)
        if(shippingMethod == 'Ground'){
            currentTime.set(Calendar.HOUR_OF_DAY, 12)
            currentTime.set(Calendar.MINUTE, 0)
            return currentTime
        }
        else if(shippingMethod in ["Second Day Air", "Next Day Air"]){
            currentTime.set(Calendar.HOUR_OF_DAY, 14)
            currentTime.set(Calendar.MINUTE, 0)
            return currentTime
        }
        else{
            return null
        }
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

    void doesManufacturerMessageAppearAsExpected(String itemNumber, String shippingOptionText){
        RunWeb r = run()

        boolean isProductExpectedToShowManufacturerMessage = (itemNumber in [dropShipProduct, customProduct])
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

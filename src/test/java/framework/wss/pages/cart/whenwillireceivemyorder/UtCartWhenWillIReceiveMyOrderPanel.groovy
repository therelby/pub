package framework.wss.pages.cart.whenwillireceivemyorder

import above.RunWeb
import above.azure.AzureDevOpsWorkItems
import all.util.CalendarUtil
import wss.api.shoppingcart.CartResults
import wss.item.CustomItem
import wss.pages.cart.CartBottomCheckout
import wss.pages.cart.CartWhenWillIReceiveMyOrderModal
import wss.pages.cart.ViewCartPage
import wss.user.userurllogin.UserUrlLogin
import wss.webadmin.WebAdmin
import wss.webadmin.shippingcalculator.WebAdminShippingCalculator

class UtCartWhenWillIReceiveMyOrderPanel extends RunWeb{
    String residentialTestUser = "655"
    String commercialTestUser = "8613901"

    String nonCommonCarrierItem = "107991782"
    String unavailableNonCommonCarrierItem = "164CMBKLSNVY"
    String unavailableCommonCarrierItem = "103991920"
    String availableCommonCarrierItem = "882TBC2HR2LP"
    String optionalLiftgateCommonCarrierItem = "177FF300L"
    String dropShipProduct = "010FI24102"
    String customProduct = "178GDC40WC"

    static void main(String[] args) {
        new UtCartWhenWillIReceiveMyOrderPanel().testExecute([

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
        assert new ViewCartPage().emptyCart(true)

        addingItemNumberToCart(nonCommonCarrierItem)
        addingItemNumberToCart(unavailableNonCommonCarrierItem)
        addingItemNumberToCart(unavailableCommonCarrierItem)
        addingItemNumberToCart(availableCommonCarrierItem)
        addingItemNumberToCart(optionalLiftgateCommonCarrierItem)
        addingItemNumberToCart(dropShipProduct)
//        addingItemNumberToCart(customProduct)
        testingItemNumbers()
    }

    void addingItemNumberToCart(String itemNumber){
        ViewCartPage viewCartPage = new ViewCartPage()
        if(itemNumber == '178GDC40WC'){
            String filePath = AzureDevOpsWorkItems.downloadAttachment("label.jpg", 257686, 'QA-Webstaurantstore ' +'Projects')
            assert new CustomItem().customizeItemAndAddToCart(filePath, itemNumber)
        }
        else {
            assert viewCartPage.addItemToCart(itemNumber, 1)
        }
    }

    void testingItemNumbers(){
        ViewCartPage viewCartPage = new ViewCartPage()
        sleep(5000)
        def itemNumbersInCart = getExpectedModalContent()
        int numberOfItemsInCart = itemNumbersInCart.size()

        assert viewCartPage.wasAbleToLoadWhenWillIReceiveMyOrderModal()
        CartWhenWillIReceiveMyOrderModal cartWhenWillIReceiveMyOrderModal = new CartWhenWillIReceiveMyOrderModal()
        assert cartWhenWillIReceiveMyOrderModal.isWhenWillIReceiveMyOrderHeaderTitleAccurate()

        CartBottomCheckout cartBottomCheckout = new CartBottomCheckout()
        def shippingOptionsData = cartBottomCheckout.getShippingOptionsData()

        String userCartCode = viewCartPage.getCardID()
        assert newTab()
        def listOfTabs = getTabs()
        assert switchToTab(listOfTabs[1])
        assert WebAdmin.loginToWebAdmin()

        String url = getUrl('hp') + WebAdminShippingCalculator.url
        assert tryLoad(url)

        WebAdminShippingCalculator webAdminShippingCalculator = new WebAdminShippingCalculator()
        assert webAdminShippingCalculator.ableToGenerateShippingCalculatorResult("Cart code", userCartCode, null)

        def shippingOptionsDeliveryDays = webAdminShippingCalculator.getDeliveryDaysInformation(shippingOptionsData.size())
        for(shippingOptionsDeliveryDay in shippingOptionsDeliveryDays){
            String currentShippingOption = shippingOptionsDeliveryDay['shippingMethod']
            int currentNumberOfDeliveryDays = shippingOptionsDeliveryDay['deliveryDays']

            Date date = new Date()
            Calendar currentTime = Calendar.getInstance()
            currentTime.setTime(date)
            Calendar cutoff = settingUpCutoffDate(currentShippingOption)
            if(currentTime > cutoff){
                currentNumberOfDeliveryDays++
            }
            if(currentNumberOfDeliveryDays > 3){
                currentNumberOfDeliveryDays--
            }
            shippingOptionsDeliveryDay['deliveryDays'] = currentNumberOfDeliveryDays

        }

        assert closeTab()
        assert switchToTab(listOfTabs[0])

        for(int i = 0; i < numberOfItemsInCart; i++){
            def itemNumberBeingTested = itemNumbersInCart.get(i)

            String itemNumber = itemNumberBeingTested[0]

            String expectedThumbnail = itemNumberBeingTested[1]
            String actualThumbnailXpath = cartWhenWillIReceiveMyOrderModal.getWhenWillReceiveMyOrderContainerListItemThumbnailXpath(i+1)
            String actualThumbnail = getAttributeSafe(actualThumbnailXpath, "src")

            assert (actualThumbnail == expectedThumbnail)

            String expectedDescription = itemNumberBeingTested[2]
            String actualDescriptionXpath = cartWhenWillIReceiveMyOrderModal.getWhenWillReceiveMyOrderContainerListItemDescriptionXpath(i+1)
            String actualDescription = getTextSafe(actualDescriptionXpath)

            assert (actualDescription == expectedDescription)

            String calendarIconXpath = cartWhenWillIReceiveMyOrderModal.getWhenWillIReceiveMyOrderContainerDeliveryListCalendarIconXpath(i+1)
            assert verifyElement(calendarIconXpath)

            String expectedDeliveryEstimateLabel = getExpectedDeliveryEstimateLabel(itemNumber)
            String actualDeliveryEstimateLabelXpath = cartWhenWillIReceiveMyOrderModal.getWhenWillIReceiveMyOrderContainerDeliveryListItemDeliveryEstimateLabelXpath(i+1)
            String actualDeliveryEstimateLabel = getTextSafe(actualDeliveryEstimateLabelXpath)

            assert (actualDeliveryEstimateLabel == expectedDeliveryEstimateLabel)

            String expectedDeliveryEstimateDate = getExpectedDeliveryEstimateDate(itemNumber, shippingOptionsDeliveryDays[0]['deliveryDays'])
            String actualDeliveryEstimateDateXpath = cartWhenWillIReceiveMyOrderModal.getWhenWillIReceiveMyOrderContainerDeliveryListItemDeliveryEstimateDateXpath(i+1)
            String actualDeliveryEstimateDate = getTextSafe(actualDeliveryEstimateDateXpath)

            assert (actualDeliveryEstimateDate == expectedDeliveryEstimateDate)
        }

        assert cartWhenWillIReceiveMyOrderModal.wasAbleToCloseWhenWillIReceiveMyOrderModal()
    }

    private Calendar settingUpCutoffDate(String shippingMethod){
        Date date = new Date()
        Calendar currentTime = Calendar.getInstance()
        currentTime.setTime(date)
        if(shippingMethod in ['Ground', 'Common Carrier']){
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

    def getExpectedModalContent(){
        def itemNumbersInCart = new ViewCartPage().getAllItems()
        def expectedModalContent = []
        int count = 0
        for(itemNumberInCart in itemNumbersInCart){
            String itemNumber = itemNumberInCart['itemNumber']
            String thumbnailXpath = itemNumberInCart['imageXpath']
            String thumbnail = getAttributeSafe(thumbnailXpath, 'src')

            String hyperlinkDescriptionXpath = itemNumberInCart['descriptionXpath']
            boolean doesDescriptionHaveHyperlink = verifyElement(hyperlinkDescriptionXpath)
            String description
            if(doesDescriptionHaveHyperlink){
                description = getTextSafe(hyperlinkDescriptionXpath)
            }
            else{
                String hyperlinkNoDescriptionXpath = itemNumberInCart['descriptionNoHyperlinkXpath']
                description = getTextSafe(hyperlinkNoDescriptionXpath)
            }

            expectedModalContent.add([itemNumber, thumbnail, description])

            count++
        }
        return expectedModalContent
    }

    String getExpectedDeliveryEstimateLabel(String itemNumber){
        switch(itemNumber){
            case "XIN1T":
            case "010FI24102":
            case "882TBC2HR2LP":
                return "Usually Ships in"
                break
            case "177FF300L":
            case "164CMBKLSNVY":
            case "107991782":
            case "103991920":
                return "Estimated Delivery"
                break
            default:
                return ""
                break
        }
    }

    String getExpectedDeliveryEstimateDate(String itemNumber, Integer deliveryDays = null){
        Date currentDate = new Date()
        switch(itemNumber){
            case "XIN1T":
                return ""
                break
            case "010FI24102":
                return "Through 4PM EST"
                break
            case "882TBC2HR2LP":
                return "17 Weeks"
                break
            case "177FF300L":
            case "164CMBKLSNVY":
            case "107991782":
            case "103991920":
                Date firstDate = CalendarUtil.shippingCalculatorAddBusinessDays(deliveryDays, currentDate, true)
                Date lastDate = CalendarUtil.shippingCalculatorAddBusinessDays(2, firstDate, true)
                return "${formatDateAsString(firstDate)} - ${formatDateAsString(lastDate)}"
                break
            default:
                return ""
                break
        }
    }

    private String formatDateAsString(Date date){
        return "${date.format('EEE, MMM dd')}"
    }
}

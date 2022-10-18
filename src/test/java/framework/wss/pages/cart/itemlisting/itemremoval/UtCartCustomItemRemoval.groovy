package framework.wss.pages.cart.itemlisting.itemremoval

import above.RunWeb
import wss.item.custom.CustomCuttingBoard
import wss.item.custom.HatcoLampToolPage
import wss.pages.cart.CartCustomItemBox
import wss.pages.cart.ViewCartPage
import wss.item.CustomItem
import above.azure.AzureDevOpsWorkItems

class UtCartCustomItemRemoval extends RunWeb{

    static void main(String[] args) {
        new UtCartCustomItemRemoval().testExecute([

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

        setup('mwestacott', 'Cart - Custom Item Removal | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart custom item removal',
                 "tfsTcIds:0", 'logLevel:info'])

//        String initialUrl = "https://www.dev.webstaurantstore.com/?login_as_user=$userId"
        assert openAndTryLoad('hp')

//        testingCustomCuttingBoard()
//        testingHatcoLamp()
        testingStandardCustomProduct()

        assert closeBrowser()
    }

    void testingRemovalOfCustomProduct(){
        ViewCartPage viewCartPage = new ViewCartPage()
        assert viewCartPage.getItemsInCart().size() == 1

        CartCustomItemBox cartCustomItemBox = new CartCustomItemBox(0)
        assert cartCustomItemBox.deleteItemAndCheckNotPresent()
    }

    void testingCustomCuttingBoard(){
        addCustomCuttingBoardToCart()
        testingRemovalOfCustomProduct()
    }

    void addCustomCuttingBoardToCart(){
        CustomCuttingBoard customCuttingBoard = new CustomCuttingBoard()
        customCuttingBoard.navigate()
        customCuttingBoard.setRandomBoard(1)
        sleep(5000)
        assert customCuttingBoard.addCustomBoardToCart()
    }

    void testingHatcoLamp(){
        addHatcoLampToCart()
        testingRemovalOfCustomProduct()
    }

    void addHatcoLampToCart(){
        HatcoLampToolPage hatcoLampToolPage = new HatcoLampToolPage()
        assert hatcoLampToolPage.navigateToHatcoLampTool()
        selectHatcoMountingOption()
        assert hatcoLampToolPage.selectOverallLengthInInches()
        assert hatcoLampToolPage.selectShadeOption()
        assert tryClick(hatcoLampToolPage.addToCartXpath)
        sleep(3000)
        assert currentUrl() == getUrl('cart')
    }

    void selectHatcoMountingOption(){
        boolean isMountingTypeAcceptable = false
        while(!isMountingTypeAcceptable){
            HatcoLampToolPage hatcoLampToolPage = new HatcoLampToolPage()
            assert hatcoLampToolPage.selectMountingOption()
            Map selectedOptions = hatcoLampToolPage.getAllSelectedOptions()
            String mountingType = selectedOptions.Mounting.selection
            isMountingTypeAcceptable = (mountingType == 'S Mount' || mountingType == 'ST Mount' || mountingType == 'C Mount' || mountingType == 'CT Mount')
        }
    }

    void testingStandardCustomProduct(){
        addStandardCustomLampToCart()
        testingRemovalOfCustomProduct()
    }

    void addStandardCustomLampToCart(){
        String filePath = AzureDevOpsWorkItems.downloadAttachment("label.jpg", 257686, 'QA-Webstaurantstore ' +'Projects')
        assert new CustomItem().customizeItemAndAddToCart(filePath)
        waitForElement(ViewCartPage.headerXpath)
        String expectedUrl = getUrl('cart')
        String currentUrl = getCurrentUrl()
        boolean isUrlCorrect = (currentUrl == expectedUrl)
        assert isUrlCorrect
    }
}

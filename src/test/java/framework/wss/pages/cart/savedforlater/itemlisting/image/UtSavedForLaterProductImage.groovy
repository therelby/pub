package framework.wss.pages.cart.savedforlater.itemlisting.image

import above.RunWeb
import wss.item.ItemUtil
import wss.pages.cart.CartItemBox
import wss.pages.cart.ViewCartPage
import wss.pages.cart.savedforlater.SavedForLaterItemBox
import wss.pages.cart.savedforlater.SavedForLaterSection
import wss.user.userurllogin.UserUrlLogin
import wsstest.cart.saveforlater.image.HpSavedForLaterProductImage

class UtSavedForLaterProductImage extends RunWeb{

    static Integer pbiNumber = 630607

    Integer regularUser = 8613901
    Integer platinumUser = 1772539
    Integer plusUser = 25035591
    Integer platinumPlusUser = 241217

    def loneProductWithPrimaryFirstImage = '10200007'
    def loneProductWithoutPrimaryFirstImage = '100FOLDCHAFE'
    def loneSuffixProductWithPrimaryFirstImage = '625APTSPB   BL500'
    def loneSuffixProductWithoutPrimaryFirstImage = '128OC32B    COMBO150'
    def virtualGroupingProductWithPrimaryFirstImage = '100ECONCHAFE'
    def virtualGroupingProductWithoutPrimaryFirstImage = '489SC40XL'

    static void main(String[] args) {
        new UtSavedForLaterProductImage().testExecute([

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

        setup('mwestacott', 'Cart - Saved For Later - Product Image | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart saved for later product image',
                 "PBI: $pbiNumber", 'logLevel:info'])

        testingQueryScenarios()

        assert openAndTryLoad('cart')

        testingUser(regularUser)
        testingUser(platinumUser)
        testingUser(plusUser)
        testingUser(platinumPlusUser)

        assert closeBrowser()
    }

    void testingQueryScenarios(){
        testingQuery(5, "Lone", false)
        testingQuery(5, "Lone Suffix", false)
        testingQuery(5, "Virtual Grouping", false)
        testingQuery(5, "Lone", true)
        testingQuery(1, "Lone Suffix", true)
        testingQuery(1, "Virtual Grouping", true)
    }

    void testingQuery(int quantity, String itemType, boolean hasPrimaryFirstImage){
        def savedForLaterProducts = HpSavedForLaterProductImage.getSavedForLaterProducts(quantity, itemType, hasPrimaryFirstImage)
        assert savedForLaterProducts.size() == quantity
    }

    void testingUser(Integer userId){
        def user = new UserUrlLogin().loginAs(userId.toString())
        assert user != null

        testingProductImage(loneProductWithPrimaryFirstImage)
        testingProductImage(loneProductWithoutPrimaryFirstImage)
        testingProductImage(loneSuffixProductWithPrimaryFirstImage)
        testingProductImage(loneSuffixProductWithoutPrimaryFirstImage)
        testingProductImage(virtualGroupingProductWithPrimaryFirstImage)
        testingProductImage(virtualGroupingProductWithoutPrimaryFirstImage)
    }

    void testingProductImage(String itemNumber){
        ViewCartPage viewCartPage = new ViewCartPage()
        SavedForLaterSection savedForLater = new SavedForLaterSection()

        assert viewCartPage.emptyCart()
        assert savedForLater.removeAllItems()

        if(!viewCartPage.addItemToCart(itemNumber, 1)){
            report("Skipping remaining tests due to failure to add $itemNumber to cart.")
            return
        }

        assert viewCartPage.getAllItems().size() == 1

        CartItemBox cartItemBox = new CartItemBox(itemNumber)

        assert cartItemBox.verifySaveForLater()
        if(!cartItemBox.clickSaveForLater()){
            report("Skipping remaining tests due to failure to move product to Save for Later.")
            return
        }

        int numberOfSavedForLaterProducts = savedForLater.getSavedForLaterItemNumbers().size()
        if(numberOfSavedForLaterProducts != 1){
            report("Skipping remaining tests due to failure to test product by itself.")
            return
        }

        def expectedProductImages = ItemUtil.getProductImages(itemNumber)
        def expectedFirstProductImage = expectedProductImages[0]
        String expectedSavedForLaterItemBoxImage = expectedFirstProductImage['image_url']

        SavedForLaterItemBox savedForLaterItemBox = new SavedForLaterItemBox(itemNumber)
        String actualSavedForLaterItemBoxImage = savedForLaterItemBox.getImageUrl()

        assert actualSavedForLaterItemBoxImage == expectedSavedForLaterItemBoxImage

        String expectedSavedForLaterItemBoxUrl = getUrl('hp') + HpSavedForLaterProductImage.getExpectedProductUrl(itemNumber)
        String actualSavedForLaterItemBoxUrl = savedForLaterItemBox.getImageLink()

        assert actualSavedForLaterItemBoxUrl == expectedSavedForLaterItemBoxUrl

        assert viewCartPage.emptyCart()
        assert savedForLater.removeAllItems()
    }
}

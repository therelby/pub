package framework.wss.pages.cart.itemlisting.image

import above.RunWeb
import wss.item.ItemUtil
import wss.pages.cart.CartItemBox
import wss.pages.cart.ViewCartPage
import wsstest.cart.itemlisting.image.HpCartProductImage

class UtCartProductImage extends RunWeb{

    static Integer pbiNumber = 630607

    def loneProduct = '100FOLDCHAFE'
    def loneSuffixProduct = '128OC32B    COMBO150'
    def virtualGroupingProduct = '167J04942'

    static void main(String[] args) {
        new UtCartProductImage().testExecute([

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

        setup('mwestacott', 'Cart - Product Image | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart product image',
                 "PBI: $pbiNumber", 'logLevel:info'])

        assert HpCartProductImage.getProductsWithFirstImageNotPrimary(5, "Lone").size() == 5
        assert HpCartProductImage.getProductsWithFirstImageNotPrimary(5, "Lone Suffix").size() == 5
        assert HpCartProductImage.getProductsWithFirstImageNotPrimary(5, "Virtual Grouping").size() == 5

        assert openAndTryLoad('cart')

        testingProductImage(loneProduct)
        testingProductImage(loneSuffixProduct)
        testingProductImage(virtualGroupingProduct)

        assert closeBrowser()
    }

    void testingProductImage(String itemNumber){
        ViewCartPage viewCartPage = new ViewCartPage()
        if(!viewCartPage.addItemToCart(itemNumber, 1)){
            report("Skipping remaining tests due to failure to add $itemNumber to cart.")
            return
        }

        assert viewCartPage.getAllItems().size() == 1

        def expectedProductImages = ItemUtil.getProductImages(itemNumber)
        def expectedFirstProductImage = expectedProductImages[0]
        String expectedCartItemBoxImage = expectedFirstProductImage['image_url']

        CartItemBox cartItemBox = new CartItemBox(itemNumber)
        String actualCartItemBoxImage = cartItemBox.getImageUrl()

        assert actualCartItemBoxImage == expectedCartItemBoxImage

        String expectedCartItemBoxUrl = getUrl('hp') + HpUtCartProductImage.getExpectedProductUrl(itemNumber)
        String actualCartItemBoxUrl = cartItemBox.getImageLink()

        assert actualCartItemBoxUrl == expectedCartItemBoxUrl

        assert viewCartPage.emptyCart()
    }
}

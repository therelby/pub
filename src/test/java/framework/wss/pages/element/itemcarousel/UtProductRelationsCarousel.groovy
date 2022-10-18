package framework.wss.pages.element.itemcarousel

import above.RunWeb
import wss.pages.cart.CartItemBox
import wss.pages.cart.ViewCartPage
import wss.pages.element.carousel.ProductRelationsCarousel

class UtProductRelationsCarousel extends RunWeb {
    static void main(String[] args) {
        new UtProductRelationsCarousel().testExecute()
    }

    // Test
    def test() {

        setup('kyilmaz', 'UtItemCarousel',
                ['product:wss', 'tfsProject:Automation.Framework',
                 'keywords:unit test', "PBI:0", 'logLevel:info'])

        testAddFirstToCart()
        testViewDetailsVg()
//        testReadLargeItemBox()
//        testReadLargeCategoryBox()
//        testType()
    }

    def testType() {
        def ic = new ProductRelationsCarousel(ProductRelationsCarousel.Types.CUSTOMERS_ALSO_VIEWED)
        assert ic.getType() == "CUSTOMERS_ALSO_VIEWED"
        assert ic.type.toString() == "CUSTOMERS_ALSO_VIEWED"
    }

    def testReadLargeItemBox() {
        tryLoad("https://www.dev.webstaurantstore.com/acopa-6-bright-white-rolled-edge-china-saucer-case/303BWRESAU6.html")
        scrollToBottom()
        def ic = new ProductRelationsCarousel(ProductRelationsCarousel.Types.OTHER_PRODUCTS_FROM_THIS_LINE)
        log ic.getItems().toString()
    }

    def testReadLargeCategoryBox() {
        tryLoad("https://www.dev.webstaurantstore.com/acopa-6-bright-white-rolled-edge-china-saucer-case/303BWRESAU6.html")
        scrollToBottom()
        def ic = new ProductRelationsCarousel(ProductRelationsCarousel.Types.RELATED_ITEMS)
        log ic.getItems().toString()
    }

    def testAddFirstToCart() {
        tryLoad('https://www.dev.webstaurantstore.com/wna-comet-aptfk-petites-4-1-5-black-tasting-fork-case/625APTFKB%20%20%20BL500.html')
        def itemNumber = null
        def carousels = ProductRelationsCarousel.getAllCarouselDataOnPDP(1)
        def addedToCart = false
        def cartPage = new ViewCartPage()
        for (carouselMap in carousels) {
            def carousel = carouselMap.value
            def itemBox = carousel.getFirstInStockItem()
            if (itemBox) {
                if (carousel.addToCart(itemBox, 1)) {
                    cartPage.navigate()
                    def cartItem = new CartItemBox(itemBox.itemNumber)
                    if (cartItem.itemNumber) {
                        addedToCart = true
                        itemNumber = itemBox.itemNumber
                    } else {
                        report("Could not find item ${itemBox.itemNumber} in cart after adding it from carousel.")
                        tryLoad('https://www.dev.webstaurantstore.com/california-umbrella-crly903-201-95-lb-black-umbrella-base/222CRLY903BK.html')
                        break
                    }
                    break
                }
            }
            if (itemNumber) {
                break
            }
        }
        if (!addedToCart) {
            report("Could not add any items from carousels at url ${getCurrentUrl()}")
        }
        assert itemNumber
    }

    void testViewDetailsVg() {
        tryLoad('https://www.dev.webstaurantstore.com/california-umbrella-crly903-201-95-lb-black-umbrella-base/222CRLY903BK.html')
        def yman = new ProductRelationsCarousel(ProductRelationsCarousel.Types.YOU_MAY_ALSO_NEED)
        def items = yman.getItems()
        assert items[0]
    }

}

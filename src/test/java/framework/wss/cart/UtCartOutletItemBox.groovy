package framework.wss.cart

import above.RunWeb
import wss.item.ItemUtil
import wss.pages.cart.CartOutletItemBox
import wss.pages.cart.ViewCartPage
import wss.pages.productdetail.PDPPriceTile

class UtCartOutletItemBox extends RunWeb {
    static void main(String[] args) {
        new UtCartOutletItemBox().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])
    }

    int pbi = 0
    int outletItemsAmount = 2

    def test() {
        setup([
                author  : 'ikomarov',
                title   : 'Outlet Item Box functionality | Cart',
                PBI     : pbi,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'verify outlet item in cart',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        ViewCartPage viewCartPage = new ViewCartPage()

        List outletItems = ItemUtil.getOutletItem(10, "Lone").take(outletItemsAmount)

        List expectedItemsInCart = []

        for (item in outletItems) {
            Map idInCatalog = [:]
            String baseItemNumber = item?.item_number
            String outletListingID = item?.num
            String itemCatalogLink = item?.url

            idInCatalog['catalogListingID'] = outletListingID
            idInCatalog['catalogItemNumber'] = baseItemNumber

            tryLoad(itemCatalogLink)
            waitForPage()
            new PDPPriceTile().addToCart()
            waitForElement(PDPPriceTile.outletAddedSuccessXpath)
            new ViewCartPage().navigate()
            waitForPage()

            // add to Cart multiple not outlet items
            new ViewCartPage().addMultipleItemsToCart(["423AB12SS", "220KNSNTKU7P"])
            waitForPage()

            List numbers = viewCartPage.getOutletItemNumbers()

            assert numbers[0]['cartOutletListingID'] == outletListingID
            assert numbers[0]['cartBaseItemNumber'] == baseItemNumber

            // ViewCartPage method to get Listing ID and Base Item Number from Outlet boxes on Cart Page
            assert (viewCartPage.getOutletItemNumbers() as boolean)

            expectedItemsInCart << idInCatalog

            CartOutletItemBox cartOutletItemBox = new CartOutletItemBox(outletListingID, baseItemNumber)
            assert cartOutletItemBox.verifyOutletItem(outletListingID, baseItemNumber)
        }

        List cartBoxesIds = viewCartPage.getOutletItemNumbers()
        assert cartBoxesIds.size() == outletItemsAmount

        // adding more Outlet items to cart, changed the Outlet item box index
        assert expectedItemsInCart[0]['catalogListingID'] == cartBoxesIds[1]['cartOutletListingID']
        assert expectedItemsInCart[0]['catalogItemNumber'] == cartBoxesIds[1]['cartBaseItemNumber']

        assert expectedItemsInCart[1]['catalogListingID'] == cartBoxesIds[0]['cartOutletListingID']
        assert expectedItemsInCart[1]['catalogItemNumber'] == cartBoxesIds[0]['cartBaseItemNumber']

        CartOutletItemBox cartOutletItemBox = new CartOutletItemBox(cartBoxesIds[1]['cartOutletListingID'], cartBoxesIds[1]['cartBaseItemNumber'])
        assert cartOutletItemBox.getDescriptionOutletItem()
        assert cartOutletItemBox.getOumDescriptionOutletItem()
        assert cartOutletItemBox.getUneditableQuantityMessage()

        tryLoad()
        assert !cartOutletItemBox.verifyOutletItem(expectedItemsInCart[1]['catalogListingID'], expectedItemsInCart[1]['catalogItemNumber'])
        assert !viewCartPage.getOutletItemNumbers()

        closeBrowser()
    }
}

package framework.wss.pages.element.compareproducts

import above.RunWeb
import wss.item.itembox.ProductListingItemBox
import wss.pages.element.CompareProductsElement
import wss.pages.productlisting.ListingPage

class UtCompareProduct extends RunWeb {
    def test() {

        setup('vdiachuk', 'Compare Product on PLP Element unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test element plp listing compare product page',
                 'PBI: 0',
                 'logLevel:info'])

        tryLoad("/13447/commercial-combination-refrigerators-freezers.html")

        CompareProductsElement compareProductsElement = new CompareProductsElement()
        assert compareProductsElement.isPresent()
        assert compareProductsElement.isVisible()
        assert !compareProductsElement.isSelectingOn()


        assert compareProductsElement.switchOnCompareProducts()
        assert compareProductsElement.isSelectingOn()

        ListingPage listingPage = new ListingPage()
        List itemNumbers = listingPage.getItemNumbersOnPage()
        List itemsToCompare = itemNumbers.shuffled().take(3)
        itemsToCompare.each {
            ProductListingItemBox productListingItemBox = new ProductListingItemBox(it)
            productListingItemBox.setItem()
            String iconXpath = productListingItemBox.getXpath('compareProductButton')
            log "click: $it: " + click(iconXpath)
        }
        assert compareProductsElement.getQuantityOfCheckedItems() == 3

        refresh()

        resizeBrowser(200, 600)
        assert compareProductsElement.isPresent()
        assert !compareProductsElement.isVisible()
        assert !compareProductsElement.isSelectingOn()

        closeBrowser()
        tryLoad("homepage")
        assert !compareProductsElement.isPresent()
        assert !compareProductsElement.isVisible()
        assert !compareProductsElement.isSelectingOn()
    }
}

package framework.wss.pages.compareproducts

import above.RunWeb
import wss.item.productlisting.ProductListingCompareProducts
import wss.pages.productlisting.ListingPage

class UtCompareProduct extends RunWeb {

    def test() {

        //inherited from micurtis
		setup('kyilmaz', 'UtCompareProduct',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test,compare products', 'logLevel:debug'])

        log 'Unit testing for Compare Products class...'


String testUrl = "https://www.dev.webstaurantstore.com/2865/pot-forks-carving-forks.html"
        openAndTryLoad(testUrl)

        ListingPage lp = new ListingPage()
        def allItemsOnPage = lp.getItemNumbersOnPage()

        ProductListingCompareProducts comp1 = new ProductListingCompareProducts(allItemsOnPage.first())
        click(comp1.compareProductsChromeButtonXpath)
        assert comp1.checkCompareCheckmark()
        assert comp1.isItemSelectedForCompare()
        assert comp1.uncheckCompareCheckmark()
        assert !comp1.isItemSelectedForCompare()

    }
}

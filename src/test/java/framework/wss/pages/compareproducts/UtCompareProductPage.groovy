package framework.wss.pages.compareproducts

import above.RunWeb
import wss.item.productlisting.ProductListingCompareProducts
import wss.pages.compareproducts.CompareProductsPage
import wss.pages.productlisting.ListingPage

class UtCompareProductPage extends RunWeb {

    def test() {

        setup('vdiachuk', 'Compare Product Page unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test compare product page',
                 'PBI: 0',
                 'logLevel:info'])


        String testUrl = "https://www.dev.webstaurantstore.com/compareproducts/?items=176rsk6,176ci10sghd"
        openAndTryLoad(testUrl)

        CompareProductsPage compareProductsPage = new CompareProductsPage()
        assert compareProductsPage.isCompareProductsPage()
        assert compareProductsPage.getItemNumbersFromUrl().contains("176rsk6")
        assert compareProductsPage.getItemNumbersFromUrl().contains("176ci10sghd")
        assert compareProductsPage.getProductQuantity() == 2
        log compareProductsPage.getItemNumbersFromPage()
        assert compareProductsPage.getItemNumbersFromPage().contains("176rsk6")
        assert compareProductsPage.getItemNumbersFromPage().contains("176ci10sghd")


 /*       tryLoad("homepage")
        assert compareProductsPage.isCompareProductsPage() == false
        assert compareProductsPage.getItemNumbersFromUrl() == []
        assert compareProductsPage.getProductQuantity() == 0
        log compareProductsPage.getItemNumbersFromPage().size() == 0*/
    }
}

package framework.wss.pages.servicepage

import above.RunWeb
import wss.item.productlisting.ProductListingCompareProducts
import wss.pages.compareproducts.CompareProductsPage
import wss.pages.productlisting.ListingPage
import wss.pages.servicepage.RequestQuotePage

class UtRequestQuote extends RunWeb {


    def test() {

        setup('vdiachuk', 'Request Quote Page unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test request quote page',
                 'PBI: 0',
                 'logLevel:info'])


        tryLoad("homepage")
        RequestQuotePage requestQuotePage = new RequestQuotePage()
        assert requestQuotePage.getAllErrorMessages() == []
        assert requestQuotePage.checkErrorMessagePresent("noName") == false
        assert requestQuotePage.checkErrorMessagePresent() == false
        assert requestQuotePage.checkErrorMessagePresent("FakeKey")== false

        assert requestQuotePage.navigateRequestQuote()

        assert requestQuotePage.getAllErrorMessages() == []

        assert click(RequestQuotePage.requestQuoteButtonXpath)
        def actualMessages = requestQuotePage.getAllErrorMessages()
        log actualMessages
        assert requestQuotePage.checkErrorMessagePresent("noName")
        assert requestQuotePage.checkErrorMessagePresent()
        assert requestQuotePage.checkErrorMessagePresent("FakeKey")== false


    }
}

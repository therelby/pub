package framework.wss.pages.cart.itemlisting.itemremoval

import above.RunWeb
import wss.pages.account.YourQuotesPage
import wss.pages.cart.QuotedShoppingCartBox
import wss.pages.cart.ViewCartPage
import wss.webadmin.quote.WebAdminCreateQuote
import wss.webadmin.WebAdmin

class UtCartQuotedItemRemoval extends RunWeb{

    static Integer userId = 8613901

    static String loneProduct = '100BTWINEBAL'
    static String loneSuffixProduct = '100TWINE    SM'
    static String virtualGroupingProduct = '124COL24HCBK'

    static void main(String[] args) {
        new UtCartQuotedItemRemoval().testExecute([

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

        setup('mwestacott', 'Cart - Standard Item Removal | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart standard item removal',
                 "tfsTcIds:0", 'logLevel:info'])

        String initialUrl = "https://www.dev.webstaurantstore.com/?login_as_user=$userId"
        assert openAndTryLoad(initialUrl)

        ViewCartPage viewCartPage = new ViewCartPage()
        assert viewCartPage.navigate()
        assert viewCartPage.emptyCart()

        removeItemFromCart([loneProduct])
        removeItemFromCart([loneSuffixProduct])
        removeItemFromCart([virtualGroupingProduct])

        removeItemFromCart([loneProduct, loneSuffixProduct])
        removeItemFromCart([loneProduct, virtualGroupingProduct])
        removeItemFromCart([loneSuffixProduct, virtualGroupingProduct])

        removeItemFromCart([loneProduct, loneSuffixProduct, virtualGroupingProduct])

        assert closeBrowser()
    }

    void removeItemFromCart(def itemNumbers){
        WebAdminCreateQuote webAdminCreateQuote = new WebAdminCreateQuote()
        assert WebAdmin.loginToWebAdmin()
        assert webAdminCreateQuote.navigate()
        assert webAdminCreateQuote.createSimpleQuoteP1(userId.toString(), "Automation Quote", itemNumbers)

        YourQuotesPage yourQuotesPage = new YourQuotesPage()
        assert yourQuotesPage.navigate()

        def quoteDate = yourQuotesPage.getCurrentQuotes()
        String cartAddLink = quoteDate?.getAt(0)?.getAt('cartLink')
        def cartId = (cartAddLink?.split("=") as List)?.getAt(1)

        assert yourQuotesPage.addToCartQuoteAndVerifyCartByIndex(0)
        QuotedShoppingCartBox quotedShoppingCartBox = new QuotedShoppingCartBox(cartId)
        assert quotedShoppingCartBox.deleteQuotedBoxFromCartAndVerify()
    }
}

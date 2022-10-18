package framework.wss.webadmin.createquote

import above.RunWeb
import all.util.ListUtil
import wss.item.ItemUtil
import wss.webadmin.WebAdmin
import wss.webadmin.quote.WebAdminCreateQuote
import wss.webadmin.quote.WebAdminCreateQuoteRegularPopup

class UtWebAdminCreateQuote extends RunWeb {

    static void main(String[] args) {
        new UtWebAdminCreateQuote().testExecute([

                browser      : 'chrome',//'chrome',
                remoteBrowser: false,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])

    }

    def test() {
        final int PBI = 605862

        setup([
                author  : 'vdiachuk',
                title   : 'Create Quotes in New Web Admin unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'create quote user nwa new web admin unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        WebAdminCreateQuote webAdminCreateQuote = new WebAdminCreateQuote()
        assert !webAdminCreateQuote.navigate()
        assert !webAdminCreateQuote.isCreateQuotePage()
        assert !webAdminCreateQuote.selectQuoteType(WebAdminCreateQuote.NWAQuoteType.INVOICE)
        assert !webAdminCreateQuote.setExpirationDate('07/28/2030')

        assert WebAdmin.loginToWebAdmin()
        assert webAdminCreateQuote.navigate()
        assert webAdminCreateQuote.generateQuoteByCartId('WRBZN2')
        refresh()
        assert webAdminCreateQuote.isCreateQuotePage()
        assert webAdminCreateQuote.selectQuoteType(WebAdminCreateQuote.NWAQuoteType.INVOICE)

        assert webAdminCreateQuote.setExpirationDate('07/28/2030')

        assert webAdminCreateQuote.getRegularItemLinesQuantity() == WebAdminCreateQuote.REGULAR_ITEMS_QUANTITY
        assert webAdminCreateQuote.addNewRegularItemLine()
        assert webAdminCreateQuote.getRegularItemLinesQuantity() == WebAdminCreateQuote.REGULAR_ITEMS_QUANTITY + 1

        WebAdminCreateQuoteRegularPopup webAdminCreateQuoteRegularPopup = new WebAdminCreateQuoteRegularPopup()
        assert !webAdminCreateQuoteRegularPopup.isPresent()
        sleep(500)
        assert jsClick(WebAdminCreateQuote.regularItemNumberInputXpath)
        assert webAdminCreateQuoteRegularPopup.isPresent()
        assert webAdminCreateQuoteRegularPopup.addItem('3493048BBESP', WebAdminCreateQuoteRegularPopup.PriceTypeTiers.P1, 7)

        refresh()
        assert webAdminCreateQuote.addRegularItem('177W50CKR', WebAdminCreateQuoteRegularPopup.PriceTypeTiers.P3, 3, 3)

        refresh()
        def itemsData = [
                [itemNumber: '177W50CKR', priceType: WebAdminCreateQuoteRegularPopup.PriceTypeTiers.P3, quantity: 3],
                [itemNumber: '50016W', priceType: WebAdminCreateQuoteRegularPopup.PriceTypeTiers.P8, quantity: 1],
                [itemNumber: '795PTOKFT4', priceType: WebAdminCreateQuoteRegularPopup.PriceTypeTiers.P2, quantity: 8],
                [itemNumber: '3493048TCB', priceType: WebAdminCreateQuoteRegularPopup.PriceTypeTiers.P1, quantity: 10],
                [itemNumber: '3493030ANTWA', priceType: WebAdminCreateQuoteRegularPopup.PriceTypeTiers.P5, quantity: 1]
        ]
        assert webAdminCreateQuote.addRegularItems(itemsData) == itemsData

        def generalItems = ItemUtil.getItemsGeneral(100, 'lone').each {
            it['priceType'] = ListUtil.getRandomElement(WebAdminCreateQuoteRegularPopup.PriceTypeTiers.values() as List)
            it['quantity'] = 1
        }

        assert webAdminCreateQuote.addRegularItems(generalItems).size() > 3

        generalItems = []
        for (i in 0..99) {
            generalItems.add([itemNumber: '177W50CKR', priceType: WebAdminCreateQuoteRegularPopup.PriceTypeTiers.P1, quantity: 3])
        }
        refresh()
        assert webAdminCreateQuote.addRegularItems(generalItems).size() == 100
    }
}

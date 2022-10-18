package framework.wss.webadmin.createquote

import above.RunWeb
import wss.webadmin.WebAdmin
import wss.webadmin.quote.WebAdminCreateQuote
import wss.webadmin.quote.WebAdminCreateQuoteRegularPopup

class UtWebAdminCreateQuoteMassAddItems extends RunWeb{

    static void main(String[] args) {
        new UtWebAdminCreateQuoteMassAddItems().testExecute([

                browser      : 'chrome',//'chrome',
                remoteBrowser: true,
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


        assert WebAdmin.loginToWebAdmin()
        assert webAdminCreateQuote.navigate()
//
       def generalItems = []
        for (i in 0..19) {
            generalItems.add([itemNumber: '177W50CKR', priceType: WebAdminCreateQuoteRegularPopup.PriceTypeTiers.P1, quantity: 3])
        }
   //     generalItems = [[itemNumber: '177W50CKR', priceType: WebAdminCreateQuoteRegularPopup.PriceTypeTiers.P1, quantity: 3]]
        def addedItems=  webAdminCreateQuote.addRegularItems(generalItems)
        log addedItems
        log addedItems.size()

 //       webAdminCreateQuote.addRegularItem('177W50CKR', WebAdminCreateQuoteRegularPopup.PriceTypeTiers.P3, 3, 3)
//
    }
}

package framework.wss.pages.account.yourquote

import above.RunWeb
import wss.pages.account.YourQuotesPage
import wss.user.UserType
import wss.user.userurllogin.UserUrlLogin

class UtYourQuotesDataConverter extends RunWeb {


    static void main(String[] args) {
        new UtYourQuotesDataConverter().testExecute([

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
        final int PBI = 616807

        setup([
                author  : 'vdiachuk',
                title   : 'Your Quotes unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'quote your data converter unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        assert YourQuotesPage.convertToDate("4/27/22 at 1:24 PM") == new Date('Mon Mar 04 01:24:00 EST 2024')
        assert YourQuotesPage.convertToDate("4/27/22 atFAKE 1:24 PM") == null

        assert YourQuotesPage.convertToDate( "5/11/22 at 1:57 PM") == new Date('Sat Nov 05 01:57:00 EDT 2022')

    }
}

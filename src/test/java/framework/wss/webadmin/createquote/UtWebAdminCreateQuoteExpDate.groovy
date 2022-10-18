package framework.wss.webadmin.createquote

import above.RunWeb
import wss.webadmin.WebAdmin
import wss.webadmin.quote.WebAdminCreateQuote

class UtWebAdminCreateQuoteExpDate extends RunWeb {

    static void main(String[] args) {
        new UtWebAdminCreateQuoteExpDate().testExecute([

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
                keywords: 'expiration date create quote user nwa new web admin unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        WebAdminCreateQuote webAdminCreateQuote = new WebAdminCreateQuote()


        assert WebAdmin.loginToWebAdmin()
        assert webAdminCreateQuote.navigate()

        // this is replacement unit test for setAttribute way (not working)

        //    WebDriver driver = getWebDriver()
        //    JavascriptExecutor js = (JavascriptExecutor) driver
        // jsExecutor.executeScript("arguments[0].value='$text'", element)
        //    WebElement expDateElement = find(WebAdminCreateQuote.expirationDateXpath)
        //   js.executeScript("arguments[0].removeAttribute('readonly')",expDateElement)
        assert webAdminCreateQuote.setExpirationDate('07/28/2030')


    }
}

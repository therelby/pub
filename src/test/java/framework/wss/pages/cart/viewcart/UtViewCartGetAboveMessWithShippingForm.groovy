package framework.wss.pages.cart.viewcart

import above.RunWeb
import all.Money
import wss.pages.cart.CartQuickCheckout
import wss.pages.cart.ViewCartPage
import wss.user.UserType
import wss.user.userurllogin.UserUrlLogin

class UtViewCartGetAboveMessWithShippingForm extends RunWeb {

    static void main(String[] args) {
        new UtViewCartGetAboveMessWithShippingForm().testExecute([

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

    def test() {
        final int PBI = 594476
        setup([
                author  : 'vdiachuk',
                title   : 'Cart Above Messages with Shipping Form | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' cart item above messages error shipping form unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        tryLoad()
        UserUrlLogin userUrlLogin = new UserUrlLogin()
        userUrlLogin.loginNewUser(UserType.REGULAR_USER, PBI)
        ViewCartPage viewCartPage = new ViewCartPage()
        viewCartPage.addItemToCart('55316JAR', 1)
        viewCartPage.addItemToCart('971FLTV30B', 1)


        String zipXpath = "//input[@id='zipcode']"
        String changeButtonXpath = "//button[@title='Change Zip/Postal Code']"
        String calculateButtonXpath = "//input[@id='calculate_button']"
        String shippingFormDivXpath = "//div[contains(@class,'shipping_message')]"
        // item with above message
        viewCartPage.addItemToCart('871HTF1195', 1)

        log "wait change: " + waitForElement(changeButtonXpath)
        log "click change: " + click(changeButtonXpath)

        log "wait zip: " + waitForElement(zipXpath)
        log "set zip: " + setText(zipXpath, '345')
        log "click calculate: " + click(calculateButtonXpath)
        log "shipping form wait: " + waitForElement(shippingFormDivXpath)
        log "--"

        def aboveMessagesData = viewCartPage.getAboveMessagesData()
        // only one error message - without Shipping Request form error/alert
        assert aboveMessagesData.size()==1
        assert aboveMessagesData.getAt(0).getAt('type')== "Error"

    }
}

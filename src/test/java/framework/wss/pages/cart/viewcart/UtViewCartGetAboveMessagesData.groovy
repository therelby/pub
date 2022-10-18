package framework.wss.pages.cart.viewcart

import above.RunWeb
import wss.pages.cart.CartBottomCheckout
import wss.pages.cart.ViewCartPage
import wss.pages.element.HelpTile

class UtViewCartGetAboveMessagesData extends RunWeb {
    static void main(String[] args) {
        new UtViewCartGetAboveMessagesData().testExecute([

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
        final int PBI = 573448
        //https://tfs.clarkinc.biz/DefaultCollection/Automation%20Projects/_sprints/taskboard/Storefront%20Automation/Automation%20Projects/Automation%20Sprint%2032?workitem=573448

        setup([
                author  : 'ikomarov',
                title   : 'Cart Messages above Item Box | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' cart messages above item box get data unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        String itemNumber = "871HTF1195"
        String unavailableItemMessage = "One or more products in your cart are not available at this time. Please remove them before checking out."
        String countryRestrictionMessage = "One or more items in your cart are not available for shipment to your country. Please remove them before checking out."
        String taxesInfoMessage = "Duties and Taxes must be paid at or before delivery for all international shipments. All prices listed are in US dollars."
        String errorMessageBackground = "rgba(248, 208, 200, 1)"
        String infoMessageBackground = "rgba(204, 235, 243, 1)"

        new ViewCartPage().addItemToCart(itemNumber, 1)

        new HelpTile().waitForAnyElement([CartBottomCheckout.nonUsShippingLinkXpath, CartBottomCheckout.changeZipButtonXpath])
        if (verifyElement(CartBottomCheckout.changeZipButtonXpath)) {
            jsClick(CartBottomCheckout.changeZipButtonXpath)
        }
        waitForElement(CartBottomCheckout.nonUsShippingLinkXpath)
        jsClick(CartBottomCheckout.nonUsShippingLinkXpath)

        selectByVisibleText(CartBottomCheckout.countrySelectXpath, "Mexico")

        jsClick(CartBottomCheckout.calculateButtonXpath)

        sleep(3000)

        List messages = new ViewCartPage().getAboveMessagesData()

        assert messages.size() == 2

        for (int i = 0; i < messages.size(); i++) {
            Map message = messages[i]
            assert message["index"] == i
            if (i == 0) {
                assert message['type'] == "Error"
                assert message['message'] == unavailableItemMessage
                assert message['messageBackgroundColor'] == errorMessageBackground
            } else if (i ==1) {
                assert message['type'] == "Error"
                assert message['message'] == countryRestrictionMessage
                assert message['messageBackgroundColor'] == errorMessageBackground
            } else if (i == 2) {
                assert message['type'] == "Info"
                assert message['message'] == taxesInfoMessage
                assert message['messageBackgroundColor'] == infoMessageBackground
            }
        }

        tryLoad()
        assert new ViewCartPage().getAboveMessagesData() == []
    }
}

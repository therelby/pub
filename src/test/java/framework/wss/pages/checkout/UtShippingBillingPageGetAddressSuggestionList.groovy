package framework.wss.pages.checkout

import above.RunWeb
import wss.pages.checkout.ShippingBillingPage

class UtShippingBillingPageGetAddressSuggestionList extends RunWeb {
    void test() {
        setup([author  : 'ggroce',
               title   : 'Shipping Billing getAddressSuggestionList unit test',
               product : 'wss',
               PBI     : 0,
               project : 'Webstaurant.StoreFront',
               keywords: 'unit test shippingbillingpage getAddressSuggestionList'])


        def partialAddressInput = "1425 S Murray Blvd C"
        def expectedSuggestedAddress1 = "1425 S Murray Blvd Colorado Springs CO 80916"
        def expectedSuggestedAddress2 = "1425 S Murray Rd Caro MI 48723"

        testWithNoArg(partialAddressInput, expectedSuggestedAddress1, expectedSuggestedAddress2)
        testWithArg(partialAddressInput, expectedSuggestedAddress1)
    }

    void testWithNoArg(def partialAddressInput, def expectedSuggestedAddress1, def expectedSuggestedAddress2) {
        ShippingBillingPage shippingBillingPage = new ShippingBillingPage()

        tryLoad('shippingAndBilling')
        setText(ShippingBillingPage?.billAddress, partialAddressInput)

        def suggestedAddresses = null
        assert (suggestedAddresses = shippingBillingPage?.getAddressSuggestionList()) as boolean
        assert suggestedAddresses[0] == expectedSuggestedAddress1
        assert suggestedAddresses[1] == expectedSuggestedAddress2
    }

    void testWithArg(def partialAddressInput, def expectedSuggestedAddress) {
        ShippingBillingPage shippingBillingPage = new ShippingBillingPage()

        tryLoad('shippingAndBilling')
        uncheckCheckbox(ShippingBillingPage?.sameAsBillingCheckbox)
        scrollToBottom()
        setText(ShippingBillingPage?.shipAddress, partialAddressInput)

        def suggestedAddresses = null
        assert (suggestedAddresses =
                shippingBillingPage?.getAddressSuggestionList(ShippingBillingPage?.shipAddressSuggestionList)) as boolean
        assert suggestedAddresses[0] == expectedSuggestedAddress
    }
}

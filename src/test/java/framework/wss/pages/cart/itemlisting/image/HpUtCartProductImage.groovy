package framework.wss.pages.cart.itemlisting.image

import above.RunWeb
import wss.api.catalog.product.Products

class HpUtCartProductImage extends RunWeb{
    static String getExpectedProductUrl(String itemNumber){
        RunWeb r = run()
        def productAPI = new Products(itemNumber, [allowGroupingProducts: true, ignoreVisibilityFilters: true, showHidden: true])
        if (!productAPI.verifyStatusCodeSuccess()) {
            r.addIssueTrackerEvent("Can not Navigate to PDP for product: [$itemNumber], Product API call issue ")
            return ''
        }
        return productAPI.getValues('link')
    }
}

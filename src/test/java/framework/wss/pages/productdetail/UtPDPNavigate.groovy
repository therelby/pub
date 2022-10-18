package framework.wss.pages.productdetail

import above.RunWeb
import wss.api.catalog.product.Products
import wss.item.ItemUtil
import wss.pages.productdetail.PDPage

class UtPDPNavigate extends RunWeb {
    def test() {

        setup('vdiachuk', 'PDPage Product Detail Page Navigation unit test  | Framework Self Testing Tool',
                ['product:wss| dev,prod,test', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test pdpage pdp product detail page navigation ',
                 "tfsTcIds:265471",
                 'logLevel:info'])


        ["lone", "suffix", "loneSuffix"].each {
            List itemNumbers = ItemUtil.getItemByType(it, 5)
            log itemNumbers
            itemNumbers.each {
                log "=="
                PDPage pdPage = new PDPage()
                String itemNumber = it['Item_Number']
                log "Checking item number: [$itemNumber]"
                assert pdPage.navigateToPDPWithItemNumber(itemNumber)
                assert pdPage.isPDPage()
            }
        }

        /*def productAPI = new Products("57541424318 LFT", [allowGroupingProducts: true, ignoreVisibilityFilters: true, showHidden: true])
         //   log productAPI.getValues('')
         log productAPI.verifyStatusCodeSuccess()
         log productAPI.getStatusCode()
         log productAPI.getValues('link')*/

    }

}

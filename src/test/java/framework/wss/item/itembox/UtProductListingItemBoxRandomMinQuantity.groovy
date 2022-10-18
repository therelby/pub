package framework.wss.item.itembox

import above.RunWeb
import wss.item.itembox.ProductListingItemBox

class UtProductListingItemBoxRandomMinQuantity extends RunWeb {

    static void main(String[] args) {
        new UtProductListingItemBoxRandomMinQuantity().testExecute([

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
        final int PBI = 564970
        setup([
                author  : 'vdiachuk',
                title   : 'Product Listing Item box - Random Quantity for Min buy | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'plp product listing page min quantity unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        tryLoad("/52185/computer-accessories.html")
        ProductListingItemBox productListingItemBox = new ProductListingItemBox("139K62398AM")
        log "focusing: " + productListingItemBox.focus()
        String selectMinXpath = productListingItemBox.getXpath("quantityDropDown")
        log "--"
        (0..10).each {
            assert productListingItemBox.selectRandomMinDropdownQuantity()
            log getTextSafe(selectFirstSelectedOption(selectMinXpath))
        }

        ProductListingItemBox productListingItemBox1 = new ProductListingItemBox("139K55786WW")
        log "focusing: " + productListingItemBox1.focus()
        String selectMinXpath1 = productListingItemBox1.getXpath("quantityDropDown")
        log "--"
        (0..10).each {
            assert productListingItemBox1.selectRandomMinDropdownQuantity()
            log getTextSafe(selectFirstSelectedOption(selectMinXpath1))
        }

        ProductListingItemBox productListingItemBox2 = new ProductListingItemBox("139K39122AM")
        log "focusing: " + productListingItemBox2.focus()
        productListingItemBox2.setDetailsAndAddToCart()

        ProductListingItemBox productListingItemBox3 = new ProductListingItemBox("139K60004US")
        log "focusing: " + productListingItemBox3.focus()
        assert productListingItemBox3.setDetailsAndAddToCart()
//
        log "--"
        tryLoad("/search/222ato908s1bb.html")
        ProductListingItemBox productListingItemBox4 = new ProductListingItemBox('222ATO908S1BBL')
        log "focusing: " + productListingItemBox4.focus()
        assert productListingItemBox4.setDetailsAndAddToCart()
        log productListingItemBox4

        tryLoad()
        assert productListingItemBox1.selectRandomMinDropdownQuantity() == true
    }
}

package framework.wss.pages

import above.RunWeb
import wss.pages.productlisting.ListingPage

/**
 * Unit test for Listing page class
 *
 * @author micurtis
 *
 */

class UtListingPage extends RunWeb {


    // Test
    def test() {

        //inherited from micurtis
		setup('kyilmaz', 'UtListingPage',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test,listing page', 'logLevel:debug'])

        log 'Unit testing for Listing Page class...'

        def pageUrls = [
                // Category page
                "https://www.dev.webstaurantstore.com/10849/cleavers.html",
                // Speciailized page
                "https://www.dev.webstaurantstore.com/specializedpage.cfm?index=19910&forcecacheupdate=1",
                // Mix and Match grouping page
                "https://www.dev.webstaurantstore.com/purchasing-group.cfm?i=12684041",
                // Search page
                "https://www.dev.webstaurantstore.com/search/forks.html"
        ]

        for (url in pageUrls) {

            if(openAndTryLoad(url)) {
                ListingPage lp = new ListingPage()

                def allItemNumbers = lp.getAllItemNumbers()
                def pageItemNumbers = lp.getItemNumbersOnPage()

                assert allItemNumbers != null
                assert !allItemNumbers.isEmpty()
                assert pageItemNumbers != null
                assert !pageItemNumbers.isEmpty()
            } else {
                run().log("(!) Unable to navigate to page: $url")
            }
        }
    }
}

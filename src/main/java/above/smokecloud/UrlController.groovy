package above.smokecloud

import above.RunWeb

class UrlController extends RunWeb {


    /** Get dynamic URLs by name */
    String getSmokeUrl(String url) {

        return

        // normal URLs
        if (!url.startsWith('//')) {
            return url
        }

        // dynamic URLs
        String result
        switch (url) {

            case '//category-category':
                result = '/15037/commercial-restaurant-ranges.html'
                break
            case '//shop-all':
                List shopAllPages = ["/restaurant-equipment.html",
                        "/refrigeration-equipment.html",
                        "/restaurant-smallwares.html",
                        "/restaurant-storage-transport.html",
                        "/restaurant-tabletop-supplies.html",
                ]
                result = shopAllPages.shuffled()[0]
                break
            case '//search-page-result':
                List searchPages = ["/search/table.html",
                        "/search/top.html",
                        "/search/paper.html",
                        "/search/red.html",
                ]
                result = searchPages.shuffled()[0]
                break
            case '//search-page-no-results':
                List searchPagesNoResult = ["/search/sdfsdf.html",
                                    "/search/aaaaaaa.html",
                                    "/search/abracadabra.html",
                ]
                result = searchPagesNoResult.shuffled()[0]
                break
            case '//leaf-category':
                List leafCategories = ["/4227/flatware-holders-and-flatware-organizers.html",
                        "/43507/tape-tape-dispensers.html",
                        "/42005/reusable-plastic-bowls.html",
                        ]
                result = leafCategories.shuffled()[0]
                break

            case '//product-details-page':
                List productPages = [ "/5-qt-white-shell-shaped-plastic-bowl-19-x-12-7-8/271344WH.html",
                        "/bobs-red-mill-25-lb-gluten-free-super-fine-blanched-almond-flour/1044999B25.html",
                        "/cooking-performance-group-s60-n-natural-gas-10-burner-60-range-with-2-standard-ovens-360-000-btu/351S60N.html",
                        "/tablecraft-123465-better-burger-10-oz-white-melamine-tumbler/808123465.html",
                        "/ecochoice-8-oz-smooth-double-wall-kraft-compostable-paper-hot-cup-case/5008DWPLAKR.html",
                        "/avantco-a-19r-hc-29-solid-door-reach-in-refrigerator/178A19RHC.html",
                        "/thunder-group-cr9035pr-10-oz-pure-red-melamine-mug-pack/271CR9035PR.html",
                        ]
                result = productPages.shuffled()[0]
                break

            case '//vendor-simple-page':
                List vendorPages = [ "/vendor/bedford.html",
                        "/vendor/bear-mountain.html",
                        "/vendor/arctic-industries.html",
                        "/vendor/astra.html",
                        ]
                result = vendorPages.shuffled()[0]
                break

            case '//specialized-page':
                List specializePages = ["/specializedpage.cfm?index=17482",
                        "/feature/3061/3m-cleaners/",
                        "/specializedpage.cfm?index=17531",
                ]
                        result = specializePages.shuffled()[0]
                break

            default:
                throw new Exception("getSmokeUrl: unknoun name '$url'")
                break

        }
        return result
    }

}

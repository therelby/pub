package framework.wss.pages.specializedpage

import above.RunWeb
import wss.pages.element.Breadcrumb
import wss.pages.specializedpage.SpecializedPage


class UtSpecializedPage extends RunWeb {
    def test() {

        setup('vdiachuk', 'Specialized Page class  unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test specialized page ',
                 "tfsTcIds:265471",
                 'logLevel:info'])
        tryLoad("homepage")


        SpecializedPage specializedPage = new SpecializedPage(15652)
        assert specializedPage.navigateToSP()
        assert specializedPage.pageError == false
        assert specializedPage.redirected == false
        assert specializedPage.realUrl == getCurrentUrl()
        String legacyUrl = specializedPage.getLegacyUrl()
        String modernUrl = specializedPage.getModernUrl()
        log "specializedPage.getLegacyUrl(): " + legacyUrl
        log "specializedPage.getModernUrl(): " + modernUrl
        assert legacyUrl.endsWith("/specializedpage.cfm?index=15652")
        assert modernUrl.endsWith("/feature/15652/xxx.html")
        assert specializedPage.navigateToSP(true)
        assert specializedPage.realUrl.contains("/storefront.")



        assert specializedPage.navigateToModernUrl()
        assert specializedPage.realUrl == getCurrentUrl()
        assert specializedPage.redirected == true
        assert specializedPage.pageError == false

        assert specializedPage.navigateToLegacyUrl()
        assert specializedPage.realUrl == getCurrentUrl()
        assert specializedPage.redirected == false
        assert specializedPage.pageError == false
        assert specializedPage.navigateToLegacyUrl(true)
        assert specializedPage.realUrl.contains("/storefront.")

        log "=="
        //checking one with modern real url
        SpecializedPage specializedPage2 = new SpecializedPage(16805)
        assert specializedPage2.navigateToSP()
        assert specializedPage2.pageError == false
        assert specializedPage2.redirected == true
        assert specializedPage2.realUrl == getCurrentUrl()
        String legacyUrl2 = specializedPage2.getLegacyUrl()
        String modernUrl2 = specializedPage2.getModernUrl()
        log "specializedPage2.getLegacyUrl(): " + legacyUrl2
        log "specializedPage2.getModernUrl(): " + modernUrl2
        assert legacyUrl2.endsWith("/specializedpage.cfm?index=16805")
        assert modernUrl2.endsWith("/feature/16805/xxx.html")
        assert specializedPage2.navigateToSP(true)
        assert specializedPage2.realUrl.contains("/storefront.")



        assert specializedPage2.navigateToModernUrl()
        assert specializedPage2.realUrl == getCurrentUrl()
        assert specializedPage2.redirected == false
        assert specializedPage2.pageError == false
        assert specializedPage2.navigateToModernUrl(true)
        assert specializedPage2.realUrl.contains("/storefront.")


        assert specializedPage2.navigateToLegacyUrl()
        assert specializedPage2.realUrl == getCurrentUrl()
        assert specializedPage2.redirected == true
        assert specializedPage2.pageError == false
        assert specializedPage2.navigateToLegacyUrl(true)
        assert specializedPage2.realUrl.contains("/storefront.")


        log "=="
        // Check SP with Error message
        SpecializedPage spSorryPage = new SpecializedPage(234455)
        assert !spSorryPage.navigateToSP()
        assert spSorryPage.pageError == true
        assert spSorryPage.redirected == false
        assert spSorryPage.realUrl == getCurrentUrl()
        String legacyUrlerr = spSorryPage.getLegacyUrl()
        String modernUrlerr = spSorryPage.getModernUrl()
        assert legacyUrlerr.endsWith("/specializedpage.cfm?index=234455")
        assert modernUrlerr.endsWith("/feature/234455/xxx.html")
        assert !spSorryPage.navigateToSP(true)
        assert spSorryPage.realUrl.contains("/storefront.")



        assert spSorryPage.navigateToModernUrl()
        assert spSorryPage.realUrl == getCurrentUrl()
        assert spSorryPage.redirected == false
        assert spSorryPage.pageError == true
        spSorryPage.navigateToModernUrl(true)
        assert spSorryPage.realUrl.contains("/storefront.")


        assert spSorryPage.navigateToLegacyUrl()
        assert spSorryPage.realUrl == getCurrentUrl()
        assert spSorryPage.redirected == false
        assert spSorryPage.pageError == true
        assert spSorryPage.navigateToLegacyUrl(true)
        assert spSorryPage.realUrl.contains("/storefront.")


        String urlType1 = SpecializedPage.getUrlType("https://www.dev.webstaurantstore.com/specializedpage.cfm?index=234455")
        log "urlType1: " + urlType1
        String urlType2 = SpecializedPage.getUrlType("https://www.dev.webstaurantstore.com/feature/16805/custom-mints/")
        log "urlType2: " + urlType2

        String urlType3 = SpecializedPage.getUrlType("https://www.dev.webstaurantstore.com/search/table.html?category=3787&order=relevancy_desc&parts=y")
        log "urlType3: " + urlType3
        assert urlType1 == "legacy"
        assert urlType2 == "modern"
        assert urlType3 == "unknown"
    }

}

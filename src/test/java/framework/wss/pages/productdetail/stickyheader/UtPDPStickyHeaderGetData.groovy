package framework.wss.pages.productdetail.stickyheader

import above.RunWeb
import wss.pages.productdetail.PDPStickyHeader
import wss.user.userurllogin.UserUrlLogin

class UtPDPStickyHeaderGetData extends RunWeb {
    def test() {
        int pbi = 503011
        setup('vdiachuk', 'PDPage Product Detail Page Sticky Header get data unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test pdpage pdp product detail page sticky header get data buttons basic',
                 "PBI:$pbi",
                 'logLevel:info'])

        PDPStickyHeader pdpStickyHeader = new PDPStickyHeader()
        tryLoad("https://www.dev.webstaurantstore.com/extra-virgin-olive-oil-3-liter-tin/101OLIVEVIRG.html")
        pdpStickyHeader.getNavigationButtonsData() == []
        assert pdpStickyHeader.getBaseData() == [:]

        scrollToBottom()
        def baseData = pdpStickyHeader.getBaseData()
        log "" + baseData
        assert baseData['starRating'] == 5
        assert baseData['addToCartButtonPresent'] == false
        assert baseData['configureButtonPresent'] == false
        assert pdpStickyHeader.getPrice() == "\$17.98"
        assert pdpStickyHeader.getUOM() == "Each"
        assert pdpStickyHeader.getProductImage() == 'https://www.dev.webstaurantstore.com/images/products/thumbnails/72806/1934116.jpg'
        assert pdpStickyHeader.getReviewsQuantity() == 176


        def buttonsData = pdpStickyHeader.getNavigationButtonsData()
        assert buttonsData.size() == 3
        assert buttonsData.every() { it['active'] == false }
        assert buttonsData.collect() { it["index"] } == [0, 1, 2]

        tryLoad("https://www.dev.webstaurantstore.com/extra-virgin-olive-oil-3-liter-tin/101OLIVEVIRG.html#review-section")
        buttonsData = pdpStickyHeader.getNavigationButtonsData()
        assert buttonsData.find() { it['text'] == 'Reviews' }?.getAt("active") == true

        tryLoad("https://www.dev.webstaurantstore.com/extra-virgin-olive-oil-3-liter-tin/101OLIVEVIRG.html#details-group")
        buttonsData = pdpStickyHeader.getNavigationButtonsData()
        assert buttonsData.find() { it['text'] == 'Details' }?.getAt("active") == true
        log "" + buttonsData

        log "--"
        tryLoad()
        UserUrlLogin userUrlLogin = new UserUrlLogin()
        log "login: " + userUrlLogin.loginNewUser('Platinum', pbi)
        tryLoad("https://www.dev.webstaurantstore.com/advance-tabco-spec-line-tvlg-249-24-x-108-14-gauge-open-base-stainless-steel-commercial-work-table/109TVLG249.html")
        scrollToBottom()
        assert pdpStickyHeader.getPrice() == "\$1,199.00"
        assert pdpStickyHeader.getUOM() == "Each"
        assert pdpStickyHeader.getProductImage() == "https://www.dev.webstaurantstore.com/images/products/thumbnails/8652/1417892.jpg"
        baseData = pdpStickyHeader.getBaseData()
        assert baseData['addToCartButtonPresent'] == true
        assert baseData['configureButtonPresent'] == false
        //log baseData
        assert pdpStickyHeader.getReviewsQuantity() == null


        log "--"
        tryLoad("https://www.dev.webstaurantstore.com/structural-concepts-b2432h-oasis-black-24-1-2-heated-self-service-display-case-merchandiser-208-240v/740B2432H.html")
        scrollToBottom()
        baseData = pdpStickyHeader.getBaseData()
        assert baseData['addToCartButtonPresent'] == false
        assert baseData['configureButtonPresent'] == true
        assert baseData['starRating'] == null
        assert pdpStickyHeader.getReviewsQuantity() == null
        log "--"
        tryLoad("https://www.dev.webstaurantstore.com/choice-economy-4-qt-half-size-stainless-steel-chafer/100ECONHALF.html")
        scrollToBottom()
        assert pdpStickyHeader.getReviewsQuantity() == 69

    }
}

package framework.wss.pages.productdetail

import above.RunWeb
import wss.pages.productdetail.PDPage

class UtPDPWarrantyData extends RunWeb {

    def test() {

        setup('vdiachuk', 'PDP get warranty data on  Product Detail Page unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test warranty data pdp product detail page ',
                 "tfsTcIds:265471",
                 'logLevel:info'])

        tryLoad("https://www.dev.webstaurantstore.com/continental-dl3rffe-pt-hd-86-solid-half-door-extra-wide-dual-temperature-pass-through-refrigerator-freezer-freezer/270DL3RFFHPT.html")
        PDPage pdPage = new PDPage()
        def warData1 = pdPage.getWarrantyInfoData()
        assert warData1['text'] == "RESIDENTIAL USERS: Continental assumes no liability for parts or labor coverage for component failure or other damages resulting from installation in non-commercial or residential applications. The right is reserved to deny shipment for residential usage; if this occurs, you will be notified as soon as possible."
        assert warData1['header'] == "Warranty Info"
        log "" + warData1
        log "--"

        tryLoad("https://www.dev.webstaurantstore.com/luxor-lp27-b-heavy-duty-2-shelf-a-v-cart-32-x-24-x-27/445LP27BK.html")
        def warData2 = pdPage.getWarrantyInfoData()
        assert warData2['text'] == ''
        assert warData2['header'] == ''
        log "" + warData2
    }
}
package framework.wss.pages.productdetail

import above.RunWeb
import wss.pages.productdetail.PDPage

class UtPDPDownloadData extends RunWeb {

    def test() {

        setup('vdiachuk', 'PDP get Downloads data on  Product Detail Page unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test downloads data pdp product detail page ',
                 "tfsTcIds:265471",
                 'logLevel:info'])

        tryLoad("https://www.dev.webstaurantstore.com/continental-dl3rffe-pt-hd-86-solid-half-door-extra-wide-dual-temperature-pass-through-refrigerator-freezer-freezer/270DL3RFFHPT.html")
        PDPage pdPage = new PDPage()
        def warData1 = pdPage.getDownloadsData()
        assert warData1.size() == 4
        log '' + warData1
        log "--"

        //no Downloads page
        tryLoad()
        assert pdPage.getDownloadsData() == []
    }
}

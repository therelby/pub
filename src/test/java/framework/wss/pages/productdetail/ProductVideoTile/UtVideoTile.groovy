package framework.wss.pages.productdetail.ProductVideoTile

import above.RunWeb
import wss.pages.productdetail.PDPProductVideoTile.ProductVideoTilePage


class UtVideoTile extends RunWeb {

    static void main(String[] args) {
        new UtVideoTile().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',

        ])
    }

    void test() {
        int PBI = 712093
        setup([
                author  : 'rvijayalayan',
                title   : 'PDP - Video Tile - More Video List ',
                PBI     : PBI,
                product : 'wss|dev',
                project : 'Webstaurant.Storefront',
                keywords: 'pdp product detail page video tile unit test',
                logLevel: 'info',
        ])

        String urlWithPDPVideoTile = "/cambro-cba184864v4580-camshelving-basics-plus-vented-4-shelf-add-on-unit-18-x-48-x-64/214BAS4864V4.html"
        String urlWithoutPDPVideoTile = "/avantco-ice-uc-280-fa-26-air-cooled-undercounter-full-cube-ice-machine-299-lb/194UC280FA.html"
        ProductVideoTilePage productVideoTilePage = new ProductVideoTilePage()
        tryLoad(urlWithPDPVideoTile)

        productVideoTilePage.clickAboutButton()
        productVideoTilePage.clickcambroBasicsIntro()
        assert productVideoTilePage.getAboutDescription()
        assert productVideoTilePage.getVideoIdPdp(0)

        productVideoTilePage.clickTranscriptButton()
        productVideoTilePage.clickcambroBasicsIntro()
        assert productVideoTilePage.getTranscriptDescription()
        assert productVideoTilePage.getVideoIdPdp(1)


        productVideoTilePage.clickEmbedButton()
        productVideoTilePage.clickcambroBasicsIntro()
        assert productVideoTilePage.verifyEmbedDescriptionisVisible()


        tryLoad(urlWithoutPDPVideoTile)
        assert !productVideoTilePage.verifyEmbedDescriptionisVisible()


    }
}

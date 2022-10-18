package framework.wss.pages.productdetail.videoTile

import above.RunWeb
import wss.pages.productdetail.PDPVideoTile

class UtPDPVideoTile extends RunWeb {
    static void main(String[] args) {
        new UtPDPVideoTile().testExecute([
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

    void test() {
        int PBI = 712044
        setup([
                author  : 'ingoie',
                title   : 'PDP - Video Tile - More Video List ',
                PBI     : PBI,
                product : 'wss|dev',
                project : 'Webstaurant.Storefront',
                keywords: 'pdp product detail page video tile unit test',
                logLevel: 'info',
        ])

        String urlWithVideoTile = "/cambro-cba184864v4580-camshelving-basics-plus-vented-4-shelf-add-on-unit-18-x-48-x-64/214BAS4864V4.html"
        PDPVideoTile pdpVideoTile = new PDPVideoTile()
        tryLoad(urlWithVideoTile)

        def videoTileData = pdpVideoTile.getVideoTileData()
        assert videoTileData.size()
        assert videoTileData.find() { it['href'] }.index == 0
        log videoTileData

        assert click(pdpVideoTile.videoTileMoreVideoHyperLinksXpatch)
        assert click(pdpVideoTile.videoPlayButtonXpath)
        assert click(pdpVideoTile.aboutButtonXpath)
        assert click(pdpVideoTile.transcriptButtonXpath)
        assert click(pdpVideoTile.embedButtonXpath)
        assert click(pdpVideoTile.videoFullScreenButtonXpath)
    }
}

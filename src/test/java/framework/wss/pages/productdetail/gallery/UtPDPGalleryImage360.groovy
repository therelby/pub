package framework.wss.pages.productdetail.gallery

import above.RunWeb
import wss.pages.productdetail.PDPGallery

class UtPDPGalleryImage360 extends RunWeb {

    static void main(String[] args) {
        new UtPDPGalleryImage360().testExecute([

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
        final int PBI = 523214
        setup([
                author  : 'vdiachuk',
                title   : 'PDP Gallery 360 image | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' pdp product detail page gallery image 360',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        String urlWithImage360 = "/waring-wsc160-16-electric-crepe-maker-120v/929WSC160.html"
        String urlWithout360 = "/avantco-ice-uc-280-fa-26-air-cooled-undercounter-full-cube-ice-machine-299-lb/194UC280FA.html"
        tryLoad(urlWithImage360)
        PDPGallery pdpGallery = new PDPGallery()
        assert pdpGallery.isImage360()
        assert pdpGallery.isImage360Thumbnail()

        assert click(PDPGallery.image360ThumbLinkXpath)

        def galleryData = pdpGallery.getImage360Data()
        assert galleryData.size() == 24
        assert galleryData.find() { it['active'] }.index == 0
        log "--"
        assert click(PDPGallery.image360NextButtonXpath)
        assert click(PDPGallery.image360NextButtonXpath)
        galleryData = pdpGallery.getImage360Data()
        assert galleryData.size() == 24
        assert galleryData.find() { it['active'] }.index == 22
        log "--"
        //  sleep(1000)
        assert click(PDPGallery.image360PrevButtonXpath)
        assert click(PDPGallery.image360PrevButtonXpath)
        galleryData = pdpGallery.getImage360Data()
        assert galleryData.size() == 24
        assert galleryData.find() { it['active'] }.index == 0
        log galleryData

        tryLoad(urlWithout360)
        assert !pdpGallery.isImage360()
        assert !pdpGallery.isImage360Thumbnail()
        assert pdpGallery.getImage360Data() == []
    }
}

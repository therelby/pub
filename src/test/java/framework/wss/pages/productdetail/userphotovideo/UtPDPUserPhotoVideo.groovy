package framework.wss.pages.productdetail.userphotovideo

import above.RunWeb
import wss.pages.productdetail.PDPStickyHeader
import wss.pages.productdetail.PDPUserPhotoVideo

class UtPDPUserPhotoVideo extends RunWeb {

    static void main(String[] args) {
        new UtPDPUserPhotoVideo().testExecute([

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

        setup('vdiachuk', 'PDPage Product Detail Page User Submitted Photo Video unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test pdpage pdp product detail page user add submitted photo video file ',
                 "PBI:0",
                 'logLevel:info'])

        tryLoad("https://www.dev.webstaurantstore.com/avantco-a-19r-hc-29-solid-door-reach-in-refrigerator/178A19RHC.html")
        PDPUserPhotoVideo pdpUserPhotoVideo = new PDPUserPhotoVideo()
        assert pdpUserPhotoVideo.isModulePresent()

        assert pdpUserPhotoVideo.getData().size() > 0

// 6903215WEB , 57777233, 57724369 641SPCK85FK
        //tryLoad("https://www.dev.webstaurantstore.com/choice-8-5-oz-round-plastic-spice-general-use-storage-container-with-black-lid/641SPCK85FK.html")
        //strange review thumbnails tryLoad('https://www.dev.webstaurantstore.com/webstaurantstore-1-pint-clear-polycarbonate-measuring-cup/6903215WEB.html')

        //strange review thumbnails tryLoad("https://www.dev.webstaurantstore.com/medi-first-disposable-plastic-tweezers-4-1-2/57777233.html")
        //strange review thumbnails tryLoad tryLoad('https://www.dev.webstaurantstore.com/medique-24369-medi-first-9-g-hydrocortisone-1-anti-itch-cream-packet-box/57724369.html')
        tryLoad('https://www.dev.webstaurantstore.com/g/4031/8-5-oz-round-plastic-induction-lined-spice-container-with-lid')
        scrollTo(PDPUserPhotoVideo.moduleDivXpath)

        assert pdpUserPhotoVideo.getData().size() > 0

        tryLoad()
        assert !pdpUserPhotoVideo.isModulePresent()


    }
}

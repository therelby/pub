package framework.wss.pages.productdetail.gallery

import above.RunWeb
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import wss.pages.productdetail.PDPGallery

class UtPDPGallery3D extends RunWeb {

    static void main(String[] args) {
        new UtPDPGallery3D().testExecute([

                browser      : 'chrome',
                remoteBrowser: false,
            //    browserVersionOffset: -1,   // use specific browser version
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
                title   : 'PDP Gallery 3D image | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' pdp product detail page gallery image 3D',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        String urlWith3D = "/avantco-a-49r-hc-54-solid-door-reach-in-refrigerator/178A49RHC.html"

        PDPGallery pdpGallery = new PDPGallery()
//        tryLoad(urlWith3D)
//        assert pdpGallery.is3DImageContainer()
//        assert pdpGallery.get3DContainerClassAfterHoldAndMove() == PDPGallery.image3DNotActiveClass
//        assert click(PDPGallery.image3DThumbXpath)
//
//
//        sleep(5000)
//        assert pdpGallery.get3DContainerClassAfterHoldAndMove() == PDPGallery.image3DActiveClass
//
//       //with 3d and zoom image problem check
//        tryLoad('/regency-4-keg-green-epoxy-keg-rack-24-x-60-x-34/460EG2460KR1.html')
//        //PDPGallery pdpGallery = new PDPGallery()
//        assert pdpGallery.is3DImageContainer()
//        assert pdpGallery.get3DContainerClassAfterHoldAndMove() == PDPGallery.image3DNotActiveClass
//        assert click(PDPGallery.image3DThumbXpath)
//
//
//        sleep(5000)
//        assert pdpGallery.get3DContainerClassAfterHoldAndMove() == PDPGallery.image3DActiveClass

        tryLoad("/choice-2-oz-red-plastic-shot-cup-pack/999CC2R.html")
      //  setLogLevel('debug')

        assert pdpGallery.is3DImageContainer()
        assert pdpGallery.get3DContainerClassAfterHoldAndMove() == PDPGallery.image3DNotActiveClass
        assert click(PDPGallery.image3DThumbXpath)

        sleep(5000)
    //   assert pdpGallery.get3DContainerClassAfterHoldAndMove() == PDPGallery.image3DActiveClass

      log   pdpGallery.get3DContainerClassAfterHoldAndMove()
    }
}

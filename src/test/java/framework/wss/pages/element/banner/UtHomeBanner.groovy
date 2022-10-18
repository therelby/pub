package framework.wss.pages.element.banner

import above.RunWeb
import wss.pages.element.banner.HomeBanner

class UtHomeBanner extends RunWeb{
    def test() {

        setup('vdiachuk', 'Home Banner/slideshow unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test home homepage banner slideshow slide',
                 'PBI: 0',
                 'logLevel:info'])


        tryLoad('homepage')
        sleep(2000)
        HomeBanner homeBanner = new  HomeBanner()
        assert homeBanner.isBannerPresent()
        assert homeBanner.getQuantityOfNavigationDots()>2 //4
        assert homeBanner.clickDotByNumber(3)
        assert homeBanner.getActiveBannerNumber() == 3
        log homeBanner.getQuantityOfPictureElements()



        tryLoad('https://www.google.com/')
        assert !homeBanner.isBannerPresent()
        assert homeBanner.getQuantityOfNavigationDots() == 0
        assert homeBanner.clickDotByNumber(3) == false
        assert homeBanner.getActiveBannerNumber() == -1




    }
}

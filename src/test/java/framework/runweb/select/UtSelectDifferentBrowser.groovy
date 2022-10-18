package framework.runweb.select

import above.RunWeb
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.Select
import wss.item.productdetail.ProductDetailsPage
import wss.user.UserQuickLogin
import wsstest.checkout.confirmorder.autoreorder.AutoReorderUtils

/*
*   Unit test for RunWeb simple Select for different Browses
*   @vdiachuk
*/
class UtSelectDifferentBrowser extends RunWeb{
    def test() {


        setup('vdiachuk', 'simple Select in different browsers Unit Test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:select selectByIndex browsers ' +
                        'different  unit test',
                 "tfsTcIds:265471",
                 'logLevel:info'])

/*
        String url = "https://demoqa.com/select-menu"
        String selectColorXpath = "//*[@id='oldSelectMenu']"
        openBrowser(url)
        selectByIndex(selectColorXpath,1)*/
        String url = "https://www.dev.webstaurantstore.com/choice-12-oz-coffee-print-poly-paper-hot-cup-case/50012C.html"
        tryLoad('homepage')
      //  UserQuickLogin.loginAs('3312691')
        tryLoad(url)
        log "--"
        tryLoad('homepage')
        log "--"
        tryLoad('https://www.dev.webstaurantstore.com/ecochoice-8-oz-smooth-double-wall-kraft-compostable-paper-hot-cup-case/5008DWPLAKR.html')
        log "--"
        tryLoad('https://www.dev.webstaurantstore.com/ecochoice-8-oz-smooth-double-wall-kraft-compostable-paper-hot-cup-case/5008DWPLAKR.html')

        //    openBrowser(url)
        /*  // AutoReorderUtils.addItemToCart()
           ProductDetailsPage.turnAutoReorderOn()
           ProductDetailsPage.selectAutoReorderFrequency(1)*/


    }
}

package framework.runweb.funcwindow

import above.RunWeb
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver

class UtFuncWindowPartUrl extends RunWeb {
    def test() {

        setup('vdiachuk', 'FuncWindow get Window partial url method unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test window part partial url ',
                 "tfsTcIds:265471", 'logLevel:info'])
        tryLoad('homepage')
        WebDriver driver = getWebDriver()



    //    ((JavascriptExecutor) driver).executeScript('window.open("http://www.google.com/");')
        ((JavascriptExecutor) driver).executeScript('window.open("https://www.dev.webstaurantstore.com/4317/single-rack-dishwashers-and-double-rack-dishwashers.html?forcecacheupdate=1&withinval=490CONSRXL2C");')
        ((JavascriptExecutor) driver).executeScript('window.open("https://www.dev.webstaurantstore.com/refrigeration-equipment.html");')
        ((JavascriptExecutor) driver).executeScript('window.open("https://www.dev.webstaurantstore.com/47663/walk-in-refrigeration.html");')
        ((JavascriptExecutor) driver).executeScript("window.open();")

        //Getting titles of all windows
      log switchToWindowUrlPart("www.google.com/")



    }
}

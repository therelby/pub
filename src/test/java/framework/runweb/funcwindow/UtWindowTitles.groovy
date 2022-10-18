package framework.runweb.funcwindow

import above.RunWeb
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver

class UtWindowTitles extends RunWeb {
    static void main(String[] args) {
        new UtWindowTitles().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    def test() {

        setup('vdiachuk', 'FuncWindow get Window titles method unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test window titles ',
                 "tfsTcIds:265471", 'logLevel:info'])
        tryLoad('homepage')
        WebDriver driver = getWebDriver()



        ((JavascriptExecutor) driver).executeScript('window.open("http://www.google.com/");')
        ((JavascriptExecutor) driver).executeScript('window.open("https://www.dev.webstaurantstore.com/4317/single-rack-dishwashers-and-double-rack-dishwashers.html?forcecacheupdate=1&withinval=490CONSRXL2C");')
        ((JavascriptExecutor) driver).executeScript('window.open("https://www.dev.webstaurantstore.com/refrigeration-equipment.html");')
        ((JavascriptExecutor) driver).executeScript('window.open("https://www.dev.webstaurantstore.com/47663/walk-in-refrigeration.html");')
        ((JavascriptExecutor) driver).executeScript("window.open();")

        //Getting titles of all windows
        List listOfTitles = getWindowTitles()

        assert listOfTitles.contains("WebstaurantStore: Restaurant Supplies & Foodservice Equipment")
        assert listOfTitles.contains("")
        assert listOfTitles.contains('Walk-In Refrigerators, Coolers, & Freezers')
        assert listOfTitles.contains('Commercial Refrigerators & Freezers - WebstaurantStore')
        assert listOfTitles.contains('Page Not Found | WebstaurantStore')
        assert listOfTitles.contains("Google")

    }
}

package framework.runweb.select


import above.RunWeb
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.Select


/*
*   Unit test for RunWeb Select Multy functionality
*   @vdiachuk
*/

class UtSelectMulty extends RunWeb {

    static void main(String[] args) {
        new UtSelectMulty().testExecute([

                browser      : 'chrome',// 'remotechrome-lt',//'chrome',//'edge',//'chrome',//'safari'
                remoteBrowser: false,//true,//false,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                //   parallelThreads: 1,
                //  runType: 'Regular' ,
                //  browserVersionOffset: -1
        ])
    }

    def test() {

        setup('vdiachuk', 'Select Multiple Unit Test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:select deselect multy unit test',
                 "tfsTcIds:265471",
                 'logLevel:info'])

        String selectXpath = "//select[contains(@name,'multipleselect')]"
        String page = "https://www.softwaretestingmaterial.com/sample-webpage-to-automate/"
        log("Page:$page")

        log openAndMaximize(page)
        log selectByIndex(selectXpath, 1)
        takeScreenshot()

        WebElement element = find(selectXpath)
        Select select = new Select(element)

        assert selectByIndex(selectXpath, 1)
        assert selectByValue(selectXpath, "msselenium")
        assert selectByVisibleText(selectXpath, "Selenium")
        sleep(2000)
        assert deselectByVisibleText(selectXpath, "Selenium")
        assert deselectByValue(selectXpath, "msselenium")

        assert selectAllSelectedOptions(selectXpath) == select.getAllSelectedOptions()
        assert selectGetOptions(selectXpath) == select.getOptions()
        assert selectFirstSelectedOption(selectXpath) == select.getFirstSelectedOption()
        // sleep(3000)
        select.deselectAll()
        assert selectFirstSelectedOption(selectXpath) == null

        assert selectByIndex(selectXpath, 0)
        assert deselectByIndex(selectXpath, 0)

        String url2 = "https://the-internet.herokuapp.com/dropdown"
        tryLoad(url2)
        String select2Xpath = "//select[@id='dropdown']"

        assert selectByIndex(select2Xpath, 1)
        log takeScreenshot()

    }
}
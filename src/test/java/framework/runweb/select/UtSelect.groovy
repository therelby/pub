package framework.runweb.select

import above.RunWeb
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.Select


/*
*   Unit test for RunWeb Select functionality
*   @vdiachuk
*/


class UtSelect extends RunWeb{

    static void main(String[] args) {
        new UtSelect().testExecute([

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


        setup('vdiachuk', 'Select Unit Test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:select deselect unit test',
                 "tfsTcIds:265471",
                 'logLevel:info'])


        String selectXpath = "//select[@name='country']"
        String page = "http://demo.guru99.com/test/newtours/register.php"
        log("Page:$page")
        openBrowser(page)

       log "selecting:"+ selectByIndex(selectXpath,4)
       // log takeScreenshot()



        WebDriver driver = getWebDriver()
        WebElement element = driver.findElement(By.xpath(selectXpath))
        Select select = new Select(element)
        assert selectByIndex(selectXpath,2)
        assert selectAllSelectedOptions(selectXpath) == select.getAllSelectedOptions()
        assert selectGetOptions(selectXpath) == select.getOptions()
        assert selectFirstSelectedOption(selectXpath) == select.getFirstSelectedOption()
        selectDeselectAll(selectXpath)
        assert selectFirstSelectedOption(selectXpath) == select.getFirstSelectedOption()



    }


}

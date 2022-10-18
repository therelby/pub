package framework.runweb

import above.RunWeb
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class ClickUnitTest extends RunWeb {


    def test() {

        setup('vdiachuk', 'Click functionality unit test | Framework Self Testing Tool',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront',
               "tfsTcIds:265471",
               'keywords:unit test click jsClick ',
               'logLevel:info'])


        WebDriver driver = getWebDriver()
        String url = "https://demoqa.com/buttons"
        String regularButtonXpath = "//button[text()='Click Me']"
        String regularButtonResultXpath = "//*[@id='dynamicClickMessage']"
        String expectedRegularButtonResult = "You have done a dynamic click"

        openAndMaximize(url)
        waitForPage()


        //check regular click
        assert !(getText(regularButtonResultXpath))
        assert click(regularButtonXpath)
        assert getText(regularButtonResultXpath) == expectedRegularButtonResult


        //check regular click WebElement
        assert refresh()
        assert !getText(regularButtonResultXpath)
        assert click(find(regularButtonXpath))
        assert getText(regularButtonResultXpath) == expectedRegularButtonResult


        //check jsClick
        assert refresh()
        assert !getText(regularButtonResultXpath)
        waitForPage()
        assert jsClick(regularButtonXpath)
        assert getText(regularButtonResultXpath) == expectedRegularButtonResult

        //check jsClick WebElement
        assert refresh()
        assert !getText(regularButtonResultXpath)
        waitForPage()
        assert jsClick(find(regularButtonXpath))
        assert getText(regularButtonResultXpath) == expectedRegularButtonResult


        //check tryClick xpath
        assert refresh()
        assert !getText(regularButtonResultXpath)
        assert tryClick(regularButtonXpath)
        assert getText(regularButtonResultXpath) == expectedRegularButtonResult

        //check tryClick element
        assert refresh()
        assert !getText(find(regularButtonResultXpath))
        assert tryClick(find(regularButtonXpath),3)
        assert getText(regularButtonResultXpath) == expectedRegularButtonResult



    }
}

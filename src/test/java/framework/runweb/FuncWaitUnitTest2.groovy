package framework.runweb

import above.RunWeb
import org.openqa.selenium.WebDriver

class FuncWaitUnitTest2 extends  RunWeb {
    static void main(String[] args) {
        new FuncWaitUnitTest2().testExecute([
                browser      : 'chrome',//'safari',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }
    def test() {

        setup('vdiachuk', 'FuncWait unit test | Framework Self ' +
                'Testing Tool',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test wait page load ',
               "tfsTcIds:265471", 'logLevel:info'])
        wss.webadmin.WebAdmin.open()
        WebDriver driver = getWebDriver()

        // driver.navigate().to("https://www.dev.webstaurantstore.com/newwebadmin/marketing:specializedpages/")
        driver.get("https://www.dev.webstaurantstore.com/newwebadmin/marketing:specializedpages/update/id/17482/")
       // find("//*[@id='backToListing']/a").click()
        assert !verifyElement("//a[text()='New Specialized Page']")
        assert waitForPage(0)
        assert !verifyElement("//a[text()='New Specialized Page']")

     //   assert !verifyElement(find("//a[text()='New Specialized Page']"))
    }
}
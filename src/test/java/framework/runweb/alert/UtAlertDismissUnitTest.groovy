package framework.runweb.alert

import above.RunWeb
import org.openqa.selenium.WebDriver

class UtAlertDismissUnitTest extends RunWeb {


    def test() {

        setup('vdiachuk', 'Alert dismiss functionality unit test | Framework Self Testing Tool',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test alert alerts dismiss ',
               'logLevel:info'])

        RunWeb r = run()
        WebDriver driver = r.getWebDriver()
        String url = "https://demoqa.com/alerts"
        String alert2ButtonXpath = "//button[@id='confirmButton']"
        String expectedTextResult = "You selected Cancel"
        String checkRusultXpath = "//span[@id='confirmResult']"

        r.openAndMaximize(url)
        r.waitForPage()


        //checking dismiss alert
        assert r.click(alert2ButtonXpath)
        assert r.isAlertPresent()
        assert r.dismissAlert()
        assert !r.isAlertPresent()
        String actualText = r.getText(checkRusultXpath)
        assert actualText.contains(expectedTextResult)


    }
}

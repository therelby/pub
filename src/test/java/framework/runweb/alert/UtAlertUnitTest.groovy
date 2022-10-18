package framework.runweb.alert

import above.RunWeb
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions


class UtAlertUnitTest extends RunWeb {
    static void main(String[] args) {
        new UtAlertUnitTest().testExecute([

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

        setup('vdiachuk', 'Alert functionality unit test | Framework Self Testing Tool',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test alert alerts ', 'logLevel:info'])

        RunWeb r = run()
        WebDriver driver = r.getWebDriver()
        String url = "https://demoqa.com/alerts"
        String alert1ButtonXpath = "//button[@id='alertButton']"
        String alertText = "You clicked a button"

        r.openAndMaximize(url)
        r.waitForPage()


        assert r.click(alert1ButtonXpath)
        sleep(1000)
        assert r.isAlertPresent()
        assert r.getAlertText() == alertText
        assert r.acceptAlert()
        assert !r.isAlertPresent()




    }

}

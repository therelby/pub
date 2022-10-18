package framework.runweb.alert

import above.RunWeb
import groovy.time.TimeCategory
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions


class UtAlertWithTextUnitTest extends RunWeb {

    static void main(String[] args) {
        new UtAlertWithTextUnitTest().testExecute([

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

        setup('vdiachuk', 'Alert with sending text functionality unit test | Framework Self Testing Tool',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test alert alerts prompt send text ',
               'logLevel:info'])

        RunWeb r = run()
        WebDriver driver = r.getWebDriver()
        String url = "https://demoqa.com/alerts"
        String alert1ButtonXpath = "//button[@id='promtButton']"
        String alertText = "Please enter your name"
        String sampleText = "SaMpLe"
        String checkRusultXpath = "//span[@id='promptResult']"

        r.openAndMaximize(url)
        r.waitForPage()
        def dataOn = new Date()



        // checking set text to alert
        log "-1"
        assert r.click(alert1ButtonXpath)
        log "0"
        assert r.isAlertPresent()
        log "1"
        assert r.getAlertText() == alertText
        log "2"
        r.setTextToAlert(sampleText)
        log "3"
        assert r.acceptAlert()
        log "4"
        assert !r.isAlertPresent()
        log "5"
        String actualText = r.getText(checkRusultXpath)
        assert actualText.contains(sampleText)
        log "6"

        //checking dismiss alert
        assert r.click(alert1ButtonXpath)
        sleep(500)
        assert r.isAlertPresent()
        log "7"
        assert r.dismissAlert()
        log "8"
        assert ! r.isAlertPresent()
        log "7"

        def dataOff = new Date()
        log 'time: ' + TimeCategory.minus(dataOn, dataOff)

    }

}

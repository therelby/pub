package framework.runweb.funcwindow

import above.RunWeb
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

class UtFuncWindowOriginalWindow extends RunWeb {

    def test() {

        setup('vdiachuk', 'FuncWindow Original Browser Window unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test window switch handle ' +
                        'original ',
                 "tfsTcIds:265471", 'logLevel:info'])
        tryLoad('homepage')
        WebDriver driver = getWebDriver()
        /*  Actions actions = getAction()
          actions.sendKeys(Keys.chord(Keys.CONTROL, "n")).build().perform()*/
        def originalHandle = getOriginalWindowHandle()
        log "OriginalWindowHandle:" + originalHandle

        // open 5 new windows
        ((JavascriptExecutor) driver).executeScript("window.open();")
        ((JavascriptExecutor) driver).executeScript("window.open();")
        ((JavascriptExecutor) driver).executeScript("window.open();")
        ((JavascriptExecutor) driver).executeScript("window.open();")
        ((JavascriptExecutor) driver).executeScript("window.open();")

        def handles = getWindowHandles()
        log "handles: $handles"

        assert switchToWidow(handles[3])

        tryLoad('cart')
        assert switchToOriginalWindow()
        tryLoad('http://docs.groovy-lang.org/')
        assert switchToWidow(handles[2])
        tryLoad('https://medium.com/')
        sleep(2000)
        closeAllWindowsButOriginal()
        //check all windows properly closed
        assert getWindowHandles().size()==1
        assert getWindowHandles().contains(getOriginalWindowHandle())

    }
}

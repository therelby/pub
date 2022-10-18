package framework.runweb.funcwindow

import above.RunWeb
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

class UtFuncWidow extends RunWeb {
    static void main(String[] args) {
        new UtFuncWidow().testExecute([

                browser      : 'safari',// 'remotechrome-lt',//'chrome',//'edge',//'chrome',//'safari'
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

        setup('vdiachuk', 'FuncWindow unit test | Framework Self ' +
                'Testing Tool',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test window switch handle ',
               "tfsTcIds:265471", 'logLevel:info'])
        String url = "https://demoqa.com/links"
        String url2 = 'https://www.webstaurantstore.com/'
        String homeLinkXpath = '//a[@id="simpleLink"]'
        String title = 'ToolsQA'
        String title2 = "WebstaurantStore: Restaurant Supplies & Foodservice Equipment"

        tryLoad(url)
        String originalHandle = getWindowHandle()
        log "Original window handle:$originalHandle"
        WebElement link = find(homeLinkXpath)
        // clicking link to open it in New Widow..
        Actions actions = getAction()
        actions.keyDown(Keys.SHIFT).click(link).keyUp(Keys.SHIFT).build().perform()
        Thread.sleep(6000)
        def handles = getWindowHandles()
        log "Widow Handles after open new window:" + handles
        tryLoad(url2)
        log "title url2:" + getTitle()
        assert getTitle() == title2
        for (handle in handles) {
            if (handle != originalHandle) {
                switchToWidow(handle)
                break
            }
        }
        log "title url:" + getTitle()
        assert getTitle() == title
        assert switchToWindowTitle(title2)
        log "title url2:" + getTitle()
        assert getTitle() == title2
        assert closeWindow()
        log "title url:" + getTitle()
        assert getTitle() == title
        assert !closeWindow()
    }
}

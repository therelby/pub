package framework.runweb.funcscreenshot

import above.RunWeb
import org.openqa.selenium.WebElement

class UtFuncScreenshot extends RunWeb {
    static void main(String[] args) {
        new UtFuncScreenshot().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }
    def test() {

        setup('vdiachuk', 'Func Screenshot unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test func screenshot webelement page file',
                 'PBI: 0',
                 'logLevel:info'])

        tryLoad("homepage")
        String mainMenuXpath = "//ul[@data-testid='navbar']"
        String pathFull = takeScreenshot()
        log "pathFull: " + pathFull
        assert pathFull.size() > 1

        String pathElByXpath = takeScreenshot(mainMenuXpath)
        log "pathElByXpath: " + pathElByXpath
        assert pathElByXpath.size()>1

        WebElement element = find(mainMenuXpath)
        String pathElByWebElement = takeScreenshot(element)
        log "pathElByWebElement: " + pathElByWebElement
        assert pathElByWebElement.size()>1

    }
}

package framework.runweb.funcwindow.tab

import above.RunWeb

class UtSwitchTabSafariTwitter extends RunWeb {
    static void main(String[] args) {
        new UtSwitchTabSafariTwitter().testExecute([

                browser      : 'safari',//'chrome',
                remoteBrowser: true,
                //  browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])
    }

    def test() {
        final int PBI = 562613
        setup([
                author  : 'vdiachuk',
                title   : 'Switch Tabs | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'tab switch browsers unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        tryLoad("/metro-super-erecta-n466bc-chrome-mobile-wire-shelving-unit-with-rubber-casters-21-x-60-x-69/461N466BC.html")

        String linkOpensOtherTab = "//a[@data-testid='twitterButton']"
        def handlesBefore = getWindowHandles()

        log handlesBefore
        log getTitle()

        click(linkOpensOtherTab)
        sleep(3000)
        log "--"
        def handlesAfter = getWindowHandles()
        getOriginalWindowHandle()
        log handlesAfter
        log getTitle()
        def secondHandle = handlesAfter.find() { it != originalWindowHandle }
        log secondHandle
        log "--"
        log switchToWidow(secondHandle)
        waitForPage()
        log getPageText()
        log takeScreenshot()
        log "TITLE Twitter: ${getTitle()}"
        String buttonXpath = "//span[text()='Sign up']"

        log "Button Present: ${getTextSafe(buttonXpath)} "
        assert verifyElement(buttonXpath)

    }
}

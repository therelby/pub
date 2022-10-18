package framework.runweb.funcwindow.tab

import above.RunWeb

class UtSwitchTabSafari extends RunWeb {

    static void main(String[] args) {
        new UtSwitchTabSafari().testExecute([

                browser      : 'safari',//'chrome',
                remoteBrowser: false,
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
        tryLoad("/g/3730/t-s-b-0206-deck-mounted-single-hole-pantry-faucet-with-12-swing-nozzle")

        String linkOpensOtherTab = "//a[text()='Terms of Service']"
        def handlesBefore = getWindowHandles()
        String webstTitle = "T&S B-0206 Deck Mounted Single Hole Pantry Faucet with 12\" Swing Nozzle"
        def googleTitle = "Google Terms of Service – Privacy & Terms – Google"
        log handlesBefore
        assert getTitle() == webstTitle

        click(linkOpensOtherTab)
        sleep(1000)
        log "--"
        def handlesAfter = getWindowHandles()
        getOriginalWindowHandle()
        log handlesAfter
        log getTitle()
        def secondHandle = handlesAfter.find() { it != handlesBefore[0] }
        log secondHandle
        log "--"
       // log switchToWidow(secondHandle)

        switchToWindowTitle(googleTitle)
        assert getTitle() == googleTitle
        assert  switchToWindowTitle(webstTitle)
        assert getTitle() == webstTitle
    }
}

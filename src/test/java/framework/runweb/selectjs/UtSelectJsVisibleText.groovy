package framework.runweb.selectjs

import above.RunWeb

class UtSelectJsVisibleText extends RunWeb {
    static void main(String[] args) {
        new UtSelectJsVisibleText().testExecute([
                browser      : 'chrome',//'safari',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    def test() {
        setup([
                author  : 'vdiachuk',
                title   : 'Select by Visible Text using JavaScript | Framework Self Testing Tool',
                PBI     : 551800,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'runweb funcaction js javascript select',
                logLevel: 'info',
        ])
        String selectXpath = "//select[@name='country']"
        String url = "https://demo.guru99.com/test/newtours/register.php"
        String visibleText = "ANGOLA"
        String visibleText2 = "ALBANIA"
        tryLoad(url)

        assert jsSelectByVisibleText(selectXpath, visibleText)
        assert getTextSafe(selectFirstSelectedOption(selectXpath)) == "ANGOLA"
        sleep(1000)
        jsScrollTo(selectXpath)
        log takeScreenshot()
        assert jsSelectByVisibleText(find(selectXpath), visibleText2)
        assert getTextSafe(selectFirstSelectedOption(selectXpath)) == "ALBANIA"
        sleep(1000)
        log takeScreenshot()
        refresh()

// NO SELECT element present
        log "--"
        log "Visible Text - no element"
        String fakeXpath = "//select[@name='FAKENAME']"
        assert jsSelectByVisibleText(find(fakeXpath), visibleText2) == false
        assert jsSelectByVisibleText(fakeXpath, visibleText2) == false

        closeBrowser()
    }

}

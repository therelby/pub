package framework.runweb.selectjs

import above.RunWeb

class UtSelectJsIndex extends RunWeb {

    static void main(String[] args) {
        new UtSelectJsIndex().testExecute([
                browser      : 'chrome',//'safari',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    def test() {
        setup([
                author  : 'vdiachuk',
                title   : 'Select by Index using JavaScript | Framework Self Testing Tool',
                PBI     : 551800,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'runweb funcaction js javascript select',
                logLevel: 'info',
        ])
        String selectXpath = "//select[@name='country']"
        String url = "https://demo.guru99.com/test/newtours/register.php"

        tryLoad(url)

        assert jsSelectByIndex(selectXpath, 4)
        assert getTextSafe(selectFirstSelectedOption(selectXpath)) == "ANGOLA"
        sleep(1000)
        jsScrollTo(selectXpath)
        log takeScreenshot()
        assert jsSelectByIndex(find(selectXpath), 0)
        assert getTextSafe(selectFirstSelectedOption(selectXpath)) == "ALBANIA"
        sleep(1000)
        log takeScreenshot()
        refresh()

// NO SELECT element present
        String fakeXpath = "//select[@name='FAKENAME']"
        assert jsSelectByIndex(fakeXpath, 4) == false
        assert jsSelectByIndex(find(fakeXpath), 4) == false

        closeBrowser()
    }


}
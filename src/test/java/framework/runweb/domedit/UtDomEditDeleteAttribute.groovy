package framework.runweb.domedit

import above.RunWeb

class UtDomEditDeleteAttribute extends RunWeb {
    static void main(String[] args) {
        new UtDomEditDeleteAttribute().testExecute([

                browser      : 'chrome',//'chrome',
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
        final int PBI = 647394

        setup([
                author  : 'vdiachuk',
                title   : 'Delete Attribute unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'runweb delete attribute dom edit unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        tryLoad()
        // by xpath
        String xpath = "(//input[@id='searchval'])[1]"
        assert getAttributeSafe(xpath, 'name') == 'searchval'
        assert deleteAttribute(xpath, 'name')
        assert getAttributeSafe(xpath, 'name') == ""

        // by element
        String buttonXpath = "//button[@aria-label='Submit Feedback']"
        assert deleteAttribute(find(buttonXpath), 'class')
        assert getAttributeSafe(buttonXpath, 'class') == ''

        // negative
        String fakeXpath = "//button[@aria-label='fakeXpath']"
        assert deleteAttribute(find(fakeXpath), 'class') == false
        assert deleteAttribute(fakeXpath, 'class') == false
    }
}
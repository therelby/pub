package framework.runweb.funcread

import above.RunWeb

class UtFuncReadGetTagName extends RunWeb {
    static void main(String[] args) {
        new UtFuncReadGetTagName().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    def test() {
        setup([
                author  : 'vdiachuk',
                title   : 'Get Tag Name functionality Unit Tests | Framework Self Testing Tool',
                PBI     : 0,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'runweb funcread get tag webelement self unit test',
                logLevel: 'info',
        ])

        tryLoad('homepage')
        String liXpath = "//*[@id='product-categories']"
        String fakeXpath = "//*[@id='FAKEID']"
        assert 'li' == getTagName(liXpath)
        assert 'li' == getTagName(find(liXpath))
        assert getTagName(fakeXpath) == null
        log "--"
        assert getTagName(find(fakeXpath)) == null
    }
}
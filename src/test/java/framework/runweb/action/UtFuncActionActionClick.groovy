package framework.runweb.action

import above.RunWeb

class UtFuncActionActionClick extends RunWeb{

    static void main(String[] args) {
        new UtFuncActionActionClick().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }
    def test() {
        setup('vdiachuk', 'Action Click method unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test  action click ',
                 'PBI: 0',
                 'logLevel:info'])



        def menuLastXpath = "//*[@data-testid='flyout-nav']//a[11]"

        assert actionClick(menuLastXpath)==false
        tryLoad()
        assert actionClick(menuLastXpath)==true
        assert getCurrentUrl()?.contains("/categories.html")

    }
}

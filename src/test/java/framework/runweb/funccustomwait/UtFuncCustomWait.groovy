package framework.runweb.funccustomwait

import above.RunWeb

class UtFuncCustomWait extends RunWeb {

    static void main(String[] args) {
        new UtFuncCustomWait().testExecute([

                browser      : 'chrome',// 'remotechrome-lt',//'chrome',//'edge',//'chrome',//'safari'
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

        setup('vdiachuk', 'Custom Wait RunWeb unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test wait runweb custom',
                 'PBI: 653225',
                 'logLevel:info'])

        tryLoad()
        assert waitForElement("//button[@aria-label='Submit Feedback']", 0)
        assert waitForElement("//button[@aria-label='FAKE']", 1) == false

        assert waitForNoElement("//button[@aria-label='Submit Feedback']", 0) == false
        assert waitForNoElement("//button[@aria-label='FAKE']", 1)


        assert waitForElementClickable("//button[@aria-label='Submit Feedback']", 0)
        assert waitForElementClickable("//a[@data-testid='my-account']", 1) == false

        assert waitForElementUnclickable("//a[@data-testid='my-account']", 1)
        assert waitForElementUnclickable("//button[@aria-label='Submit Feedback']", 1) == false

        assert waitForElementVisible("//a[@data-testid='my-account']", 1) == false
        assert waitForElementVisible("//button[@aria-label='Submit Feedback']", 1)

        assert waitForElementInvisible("//a[@data-testid='my-account']", 1)
        assert waitForElementInvisible("//button[@aria-label='Submit Feedback']", 1) == false

    }
}

package framework.runweb.funccustomwait

import above.RunWeb

class UtFuncCustomWaitAttributeNotEmpty extends RunWeb {
    static void main(String[] args) {
        new UtFuncCustomWaitAttributeNotEmpty().testExecute([

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
        def inputXpath = "(//input[@id='searchval'])[1]"
        def attr = "name"
        assert waitForAttributeNotEmpty(inputXpath, attr, 1)
        assert waitForAttributeNotEmpty(inputXpath, 'FAKEATTribure', 1) == false
    }
}

package framework.runweb.funccustomverify

import above.RunWeb

class UtVerifyScrollable extends RunWeb{

    static void main(String[] args) {
        new UtVerifyScrollable().testExecute([

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

        setup('vdiachuk', 'Verify Scrollable RunWeb unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test runweb scrollable',
                 'PBI: 705938',
                 'logLevel:info'])


        tryLoad('/lancaster-table-seating-mahogany-diamond-back-bar-height-chair-with-2-1-2-padded-seat/164BWDMVBKKD.html#')

        def xpath = "//ul[@id='suffixhold']"
        def  fakeXpath = "//ul[@id='FAKEXPATH']"

        assert verifyScrollable(xpath)
        assert verifyScrollable(find(xpath))
        assert verifyScrollable(fakeXpath) == null
        assert verifyScrollable(find(fakeXpath)) == null

        tryLoad("/black-and-white-variegated-polyester-cotton-blend-bakers-twine-2-lb-cone/100BKTWINEBK.html")

        assert verifyScrollable(xpath) == false
        assert verifyScrollable(find(xpath)) == false
        assert verifyScrollable(fakeXpath) == null
        assert verifyScrollable(find(fakeXpath)) == null
    }
}

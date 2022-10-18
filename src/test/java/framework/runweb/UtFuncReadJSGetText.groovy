package framework.runweb

import above.RunWeb
import wss.pages.productdetail.PDPReview
import wss.user.userurllogin.UserUrlLogin

class UtFuncReadJSGetText extends RunWeb {
    static void main(String[] args) {
        new UtFuncReadJSGetText().testExecute([
                browser      : 'chrome',
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

        setup('vdiachuk', 'Blue Tarp Test Cases | My Account ',
                ['product:wss',
                 'tfsProject:Webstaurant.StoreFront',
                 'keywords:account myaccount account blue tarp net 30 terms ',
                 "tfsTcIds:265471",
                 'logLevel:info',
                 'note:BlueTarp same as Net30Terms'])

        tryLoad("homepage")
        def xpath = "//button[@value='Search']"
        assert jsGetText(xpath) == getText(xpath)

    }
}



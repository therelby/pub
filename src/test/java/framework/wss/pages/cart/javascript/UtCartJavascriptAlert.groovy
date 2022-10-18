package framework.wss.pages.cart.javascript

import above.RunWeb
import wss.pages.element.GlobalMenu
import wss.pages.element.MetaElement

class UtCartJavascriptAlert extends RunWeb{
    static void main(String[] args) {
        new UtCartJavascriptAlert().testExecute([

                browser      : 'chrome',
                remoteBrowser: false,
//                browserVersionOffset: -1,   // use specific browser version
                environment  : 'dev',        // use specific environment
                runType      : 'Debug',//'Regular'          //Debug
                /*   parallelThreads: 1,
               runType: 'Regular' ,
               browserVersionOffset: -1   */
        ])

    }

    def test() {

        setup('mwestacott', 'Cart - Javascript Alert | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test cart javascript alert',
                 "tfsTcIds:0", 'logLevel:info'])

        assert openAndTryLoad('cart')
        assert verifyElement(MetaElement.hiddenJavascriptAlert)
        assert !elementVisible(MetaElement.hiddenJavascriptAlert)

        String expectedText = "<div class=\"alert alert-error\"><strong>Please enable JavaScript in your browser to keep shopping WebstaurantStore</strong></div>"
        String actualText = getAttributeSafe(MetaElement.hiddenJavascriptAlert, "innerHTML").replace('\n', '').trim()

        assert (actualText == expectedText)

        closeBrowser()
    }

}

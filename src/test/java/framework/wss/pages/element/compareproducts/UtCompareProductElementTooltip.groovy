package framework.wss.pages.element.compareproducts

import above.RunWeb
import wss.pages.compareproducts.CompareProductItemBox
import wss.pages.element.CompareProductsElement

class UtCompareProductElementTooltip extends RunWeb {


    static void main(String[] args) {
        new UtCompareProductElementTooltip().testExecute([

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
        setup(
                author: 'vdiachuk',
                title: "Compare Product Element no PLP - Button Tooltip | Framework Unit test",
                PBI: 622414,
                product: 'wss|test,dev',
                project: 'Webstaurant.StoreFront',
                keywords: 'compare product element tooltip',
                logLevel: 'info',
                //dbEnvironmentDepending:'wss',
        )

        tryLoad("/13447/commercial-combination-refrigerators-freezers.html")

        CompareProductsElement compareProductsElement = new CompareProductsElement()
        assert compareProductsElement.isPresent()
        assert compareProductsElement.isVisible()


        assert compareProductsElement.getCompareProductsButtonTooltip() == CompareProductsElement.tooltipMessageText
        assert compareProductsElement.checkTooltipMessage()

        tryLoad()
        assert compareProductsElement.getCompareProductsButtonTooltip() == ""
        assert !compareProductsElement.checkTooltipMessage()
    }
}
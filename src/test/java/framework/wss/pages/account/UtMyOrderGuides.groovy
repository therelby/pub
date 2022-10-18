package framework.wss.pages.account

import above.RunWeb
import wss.pages.account.MyOrderGuides
import wss.user.UserQuickLogin

class UtMyOrderGuides extends RunWeb {

    static void main(String[] args) {
        new UtMyOrderGuides().testExecute([

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
        final int PBI = 533438
        setup([
                author  : 'vdiachuk',
                title   : 'Account Order Guide unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'account my order guide product unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        tryLoad()
        MyOrderGuides myOrderGuides = new MyOrderGuides()
        def userId = 5329643
        def pdpUrl = "visions-black-pet-plastic-12-thermoform-catering-deli-tray-case/130TRAY12.html"
        UserQuickLogin.loginAs(userId.toString())
        tryLoad(pdpUrl)
        assert !myOrderGuides.isOrderGuidesCategoryPage()
        click("//*[@class='list-menu__order-guide']//a")
        waitForPage()

        log getCurrentUrl().contains(MyOrderGuides.myProductsUrl)
        log "--"
        log getTextSafe(MyOrderGuides.categoryListXpath + MyOrderGuides.categoryListHeaderXpath)
        assert myOrderGuides.isOrderGuidesCategoryPage()

    }
}

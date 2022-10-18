package framework.wss.pages.element.datalayer

import above.RunWeb
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import wss.pages.element.DataLayerElement
import wss.pages.productdetail.PDPPriceTile

class UtDataLayerPDP extends RunWeb {
    static void main(String[] args) {
        new UtDataLayerPDP().testExecute([

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

        setup('vdiachuk', 'Data Layer on PDP Element unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: datalayer data layer unit test element pdp  ',
                 'PBI: 626603',
                 'logLevel:info'])

        tryLoad('/american-tables-seating-qas-36-46-black-plain-single-back-fully-upholstered-booth/132QAS36BK.html')


        PDPPriceTile pdpPriceTile = new PDPPriceTile()
        assert pdpPriceTile.addToCart()


        // wait until events worked and stored
        sleep(3000)
        waitForPage()

        DataLayerElement dataLayerElement = new DataLayerElement()

        def dataLayer = dataLayerElement.getDataLayerEventsData()
        println dataLayer
        assert dataLayer.any() { it?.keySet()?.contains('ecommerce') }
        def dataEcommerce = dataLayerElement.getEcommerceEventsData()

        log dataEcommerce
        def eventNames = dataEcommerce.collect() { it['event'] }
        assert eventNames.contains('productPageView')
        assert eventNames.contains('addToCart')

        WebDriver driver = getWebDriver()
        Object len = ((JavascriptExecutor) driver).executeScript("return dataLayer.length")
        log len
        log dataLayer.size()
    }
}

package framework.wss.pages.element.datalayer

import above.RunWeb
import wss.pages.element.DataLayerElement

class UtDataLayerPLP extends RunWeb {
    static void main(String[] args) {
        new UtDataLayerPLP().testExecute([

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

        setup('vdiachuk', 'Data Layer on PLP Element unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: datalayer data layer unit test element plp  ',
                 'PBI: 626603',
                 'logLevel:info'])

        tryLoad('https://www.dev.webstaurantstore.com/specializedpage.cfm?index=24478')


        String addToCart = "//input[@data-testid='itemAddCart']"
        //  log scrollTo(addToCart)

        log click(addToCart)
        log click("(//input[@data-testid='itemAddCart'])[2]")
        // wait until events worked and stored
        sleep(3000)
        waitForPage()

        DataLayerElement dataLayerElement = new DataLayerElement()

        def dataLayer = dataLayerElement.getDataLayerEventsData()
        println dataLayer
        assert dataLayer.any() { it?.keySet()?.contains('ecommerce') }
        def dataEcommerce = dataLayerElement.getEcommerceEventsData()

        log "-- ECOMMERCE LOG"
        assert dataEcommerce.collect() { it['ecommerce'] }.every() { it.keySet().contains('add') }
        assert dataEcommerce.collect() { it.ecommerce.add }.every() { it.keySet().contains('products') }
      //  log "" + dataEcommerce
    }
}

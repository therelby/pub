package framework.wss.pages.productdetail.banner

import above.RunWeb
import all.Money
import wss.pages.productdetail.PDPWhatWeOffer
import wss.pages.productdetail.banner.PDPSideBySideBanner
import wss.pages.productdetail.modal.PDPInstallationServicesModal
import wss.user.userurllogin.UserUrlLogin

class UtPDPSBSBanner extends RunWeb {
    static void main(String[] args) {
        new UtPDPSBSBanner().testExecute([

                browser      : 'chrome',//chrome, edge safari
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
        final int PBI = 520021
        setup([
                author  : 'vdiachuk',
                title   : 'PDP, Side by Side Banner(SBS) unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' pdp product detail page sbs side by unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        PDPSideBySideBanner pdpSideBySideBanner = new PDPSideBySideBanner()
        tryLoad('/scotsman-n0422a-1-prodigy-plus-series-22-15-16-air-cooled-nugget-ice-machine-420-lb/720N0422A1.html')
        assert pdpSideBySideBanner.getSBSProductPrice().getValue() > 0
        assert pdpSideBySideBanner.getSBSProductUOM() == 'Each'
        // no Banner
        tryLoad()
        def priceNoPrice = pdpSideBySideBanner.getSBSProductPrice()
        assert priceNoPrice.getValue() == 0
        def uomSBSItem = pdpSideBySideBanner.getSBSProductUOM()
        assert uomSBSItem == ''

    }
}

package framework.wss.pages.productdetail.modal.externalWarranty

import above.RunWeb
import wss.pages.productdetail.PDPPriceTile
import wss.pages.productdetail.modal.PDPExternalWarrantyModal


class UtExternalWarrantyModal extends RunWeb {

    static void main(String[] args) {
        new UtExternalWarrantyModal().testExecute([

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
        final int PBI = 561599
        setup([
                author  : 'ikomarov',
                title   : 'PDP, External Warranty modal unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' pdp product detail page external warranty modal unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        PDPExternalWarrantyModal pdpExternalWarrantyModal = new PDPExternalWarrantyModal()

        String pageWithModal = "https://www.dev.webstaurantstore.com/backyard-pro-bpf4g-4-gallon-liquid-propane-outdoor-deep-fryer-90-000-btu/554BPF4G.html"
        tryLoad(pageWithModal)
        assert !pdpExternalWarrantyModal.isExternalWarrantyModal()
        click(PDPPriceTile.addToCartButtonXpath)
        assert pdpExternalWarrantyModal.isExternalWarrantyModal()


        assert pdpExternalWarrantyModal.addPaidOptionExternalWarranty(0)
        assert pdpExternalWarrantyModal.addPaidOptionExternalWarranty(-1)
        assert pdpExternalWarrantyModal.addPaidOptionExternalWarranty(7)
        assert pdpExternalWarrantyModal.addPaidOptionExternalWarranty()
        closeBrowser()

        tryLoad()
        assert !pdpExternalWarrantyModal.isExternalWarrantyModal()
        assert !pdpExternalWarrantyModal.addPaidOptionExternalWarranty()
        closeBrowser()
    }
}

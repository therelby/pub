package framework.wss.pages.productdetail.modal.installationservices

import above.RunWeb
import wss.pages.productdetail.PDPWhatWeOffer
import wss.pages.productdetail.modal.PDPInstallationServicesModal
import wss.user.userurllogin.UserUrlLogin

class UtPDPInstallationServicesModal extends RunWeb {
    static void main(String[] args) {
        new UtPDPInstallationServicesModal().testExecute([

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
        final int PBI = 520021
        setup([
                author  : 'vdiachuk',
                title   : 'PDP, Installation Services modal unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' pdp product detail page installation service modal unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
// with modal
        tryLoad()
        UserUrlLogin userUrlLogin = new UserUrlLogin()
        log "user login:" + userUrlLogin.loginAs("10695891")
        tryLoad("/moffat-e35d6-p85m8-turbofan-full-size-electric-digital-controller-convection-oven-and-8-tray-holding-cabinet-proofer-with-mechanical-controls-208v-3-phase/952E35DP85P3.html")
        PDPWhatWeOffer pdpWhatWeOffer = new PDPWhatWeOffer()
        assert pdpWhatWeOffer.clickAddInstallationService()
        PDPInstallationServicesModal installationServicesModal = new PDPInstallationServicesModal()
        assert installationServicesModal.isModalPresent()
        assert getTextSafe(PDPInstallationServicesModal.modalSubHeaderXpath) == PDPInstallationServicesModal.modalSubHeaderText
        assert installationServicesModal.closeModalByXButton()
        assert !installationServicesModal.closeModalByXButton()

        assert pdpWhatWeOffer.clickAddInstallationService()
        assert installationServicesModal.closeModalByClickingOutside()
        assert !installationServicesModal.closeModalByClickingOutside()

        assert pdpWhatWeOffer.clickAddInstallationService()
        assert installationServicesModal.closeModalByCancelButton()
        assert !installationServicesModal.closeModalByCancelButton()

        assert pdpWhatWeOffer.clickAddInstallationService()
        assert installationServicesModal.addService()
        assert !installationServicesModal.addService()

        // no modal
        tryLoad()
        assert !pdpWhatWeOffer.clickAddInstallationService()
        assert !installationServicesModal.isModalPresent()
        assert !installationServicesModal.closeModalByXButton()
        assert !installationServicesModal.closeModalByClickingOutside()
        assert !installationServicesModal.closeModalByCancelButton()
        assert  !installationServicesModal.addService()
    }
}

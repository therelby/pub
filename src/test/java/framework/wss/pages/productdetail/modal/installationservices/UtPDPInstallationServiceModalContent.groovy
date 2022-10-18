package framework.wss.pages.productdetail.modal.installationservices

import above.RunWeb
import all.Money
import wss.pages.productdetail.PDPWhatWeOffer
import wss.pages.productdetail.modal.PDPInstallationServicesModal
import wss.user.userurllogin.UserUrlLogin

class UtPDPInstallationServiceModalContent extends RunWeb {
    static void main(String[] args) {
        new UtPDPInstallationServiceModalContent().testExecute([

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

        assert getTextSafe(PDPInstallationServicesModal.selectingServiceHeaderXpath) == PDPInstallationServicesModal.selectingServiceHeaderText
        log getTextSafe(PDPInstallationServicesModal.selectingServiceSubHeaderXpath)
        log getTextSafe(PDPInstallationServicesModal.selectingServiceBlockXpath)
        log "--"
        assert getTextSafe(PDPInstallationServicesModal.surveyHeaderXpath) == PDPInstallationServicesModal.surveyHeaderText
        log getTextSafe(PDPInstallationServicesModal.surveyBlockXpath)
        log "--"
        assert installationServicesModal.isFullServiceActive()
        assert !installationServicesModal.isFinalConnectionsActive()
        log "click final connections: " + click(PDPInstallationServicesModal.finalConnectionsButtonLabelXpath)
        assert !installationServicesModal.isFullServiceActive()
        assert installationServicesModal.isFinalConnectionsActive()
        sleep(1000)
        log "click full service: " + click(PDPInstallationServicesModal.fullServiceButtonLabelXpath)
        assert installationServicesModal.isFullServiceActive()
        assert !installationServicesModal.isFinalConnectionsActive()

        sleep(1000)
        assert installationServicesModal.switchFinalConnections()
        sleep(1000)
        assert installationServicesModal.switchFullService()

        tryLoad()
      //  UserUrlLogin userUrlLogin = new UserUrlLogin()
        log "user login:" + userUrlLogin.loginAs("10695891")
        tryLoad("/moffat-e35d6-p85m8-turbofan-full-size-electric-digital-controller-convection-oven-and-8-tray-holding-cabinet-proofer-with-mechanical-controls-208v-3-phase/952E35DP85P3.html")
      //  PDPWhatWeOffer pdpWhatWeOffer = new PDPWhatWeOffer()
        assert pdpWhatWeOffer.clickAddInstallationService()
    //    PDPInstallationServicesModal installationServicesModal = new PDPInstallationServicesModal()
        assert installationServicesModal.isModalPresent()
        assert installationServicesModal.getHaulAwayPrice() == new Money('82.50')
        assert installationServicesModal.getFullServicePrice() == new Money('1,017.50')
        assert installationServicesModal.getFinalConnectionsPrice() == new Money('770.00')
        assert installationServicesModal.getIncludeSurveyPrice() == new Money('0.0')
        assert click(PDPInstallationServicesModal.differentFloorYesButtonXpath)
        assert click(PDPInstallationServicesModal.hasElevatorNoButtonXpath)
        assert installationServicesModal.getStairQtyPrice() == new Money('55.00')
        assert installationServicesModal.getTotalPrice() == new Money('1,155.00')
        assert installationServicesModal.getAllPresentPricesSum() == new Money('1,155.00')
        log installationServicesModal.setStairQty(10)

        log "--"
        tryLoad()
        assert installationServicesModal.getHaulAwayPrice() == null
        assert installationServicesModal.getFullServicePrice() == null
        assert installationServicesModal.getFinalConnectionsPrice() == null
        assert installationServicesModal.getIncludeSurveyPrice() == null
        assert installationServicesModal.getStairQtyPrice() == null
        assert !click(PDPInstallationServicesModal.differentFloorYesButtonXpath)
        assert !click(PDPInstallationServicesModal.hasElevatorNoButtonXpath)
        assert installationServicesModal.getTotalPrice() == null
        assert installationServicesModal.getAllPresentPricesSum() == new Money('0')
        log installationServicesModal.setStairQty(10)


    }
}

package framework.wss.pages.productdetail.whatWeOffer

import above.RunWeb
import wss.pages.productdetail.PDPWhatWeOffer
import wss.user.UserQuickLogin

class UtPDPWhatWeOffer extends RunWeb {

    static void main(String[] args) {
        new UtPDPWhatWeOffer().testExecute([

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
        final int PBI = 523214
        setup([
                author  : 'vdiachuk',
                title   : 'PDP, What We Offer unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' pdp product detail page we offer unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])
        PDPWhatWeOffer pdpWhatWeOffer = new PDPWhatWeOffer()
        tryLoad()
        assert pdpWhatWeOffer.isExtendedWarranty() == false
        assert pdpWhatWeOffer.isInstallationService() == false
        assert pdpWhatWeOffer.getExtendedWarrantyTooltip() == ''
        assert pdpWhatWeOffer.getInstallationServiceTooltip() == ''
        assert pdpWhatWeOffer.getCoverageStartingPrice() == null
        String pageWith = "https://www.dev.webstaurantstore.com/avantco-crm-7-hc-white-countertop-display-refrigerator-with-swing-door-4-1-cu-ft/360CRM7HCW.html"
        tryLoad(pageWith)
        assert pdpWhatWeOffer.isExtendedWarranty()
        assert pdpWhatWeOffer.getExtendedWarrantyTooltip() == PDPWhatWeOffer.extendedWarrantyTooltipText
        assert pdpWhatWeOffer.getCoverageStartingPrice().value == 50.99

        log getTextSafe(PDPWhatWeOffer.extendedWarrantyHeaderXpath)
        log getTextSafe(PDPWhatWeOffer.extendedWarrantySubHeaderXpath)

        tryLoad()
        UserQuickLogin.loginAs('1860157')
        tryLoad('https://www.dev.webstaurantstore.com/avantco-a-19r-hc-29-solid-door-reach-in-refrigerator/178A19RHC.html')
        assert pdpWhatWeOffer.getInstallationServiceTooltip() == PDPWhatWeOffer.installationServiceTooltipText
        assert pdpWhatWeOffer.isInstallationService()

        log getTextSafe(PDPWhatWeOffer.addInstallationButtonXpath)
        log getTextSafe(PDPWhatWeOffer.installationServiceSubHeaderXpath)

    }
}
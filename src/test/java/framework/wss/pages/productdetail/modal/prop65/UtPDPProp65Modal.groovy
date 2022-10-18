package framework.wss.pages.productdetail.modal.prop65

import above.RunWeb
import wss.pages.productdetail.PDPage
import wss.pages.productdetail.modal.PDPProp65Modal

class UtPDPProp65Modal extends RunWeb {

    static void main(String[] args) {
        new UtPDPProp65Modal().testExecute([

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
        final int PBI = 535655
        setup([
                author  : 'vdiachuk',
                title   : 'PDP, Prop 65 Warning modal unit test | Framework Self Testing Tool ',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: ' pdp product detail page prop 65 warning modal unit test',
                logLevel: 'info',
                // 'noPositiveChecksPrinting',
        ])

        // with modal
        tryLoad('/dax-n16814bt-8-1-2-x-14-black-digital-enlargement-wood-frame/328DAX16814B.html')
        PDPProp65Modal pdpProp65Modal = new PDPProp65Modal()
        assert !pdpProp65Modal.isProp65Modal()
        log click(PDPage.prop65ButtonXpath)

        assert pdpProp65Modal.isProp65Modal()
        assert pdpProp65Modal.getModalBodyText() == 'This product can expose you to chemicals including lead, which are known to the State of California to cause cancer, birth defects, or other reproductive harm. For more information, go to www.p65warnings.ca.gov.'
        assert pdpProp65Modal.getModalBodyInnerHtml() == 'This product can expose you to chemicals including lead, which are known to the State of California to cause cancer, birth defects, or other reproductive harm. For more information, go to <a href="https://www.p65warnings.ca.gov" target="_blank" rel="nofollow">www.p65warnings.ca.gov</a>.'
        assert getTextSafe(PDPProp65Modal.warningXpath) == PDPProp65Modal.warningText
    }
}

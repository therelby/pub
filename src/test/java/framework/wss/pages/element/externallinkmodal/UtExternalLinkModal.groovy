package framework.wss.pages.element.externallinkmodal

import above.RunWeb
import wss.pages.element.modal.ExternalLinkModal

class UtExternalLinkModal extends RunWeb {
    def test() {

        setup('vdiachuk', 'External Link Modal unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords: unit test modal external link pdp resources downloads product detail page',
                 'PBI: 0',
                 'logLevel:info'])

        ExternalLinkModal externalLinkModal = new ExternalLinkModal()
        // page with External Link Resources
        tryLoad('https://www.dev.webstaurantstore.com/avaweigh-pcs40tk-40-lb-digital-price-computing-scale-with-tower-legal-for-trade-with-thermal-label-printer/334PCS40TK.html')
        click("//a[@data-toggle]")
        assert externalLinkModal.isModal()
        assert externalLinkModal.closeModalByButton()
        click("//a[@data-toggle]")
        assert externalLinkModal.closeModalByX()
        click("//a[@data-toggle]")
        assert externalLinkModal.clickVisitExternal()
        closeBrowser()
        tryLoad('https://www.dev.webstaurantstore.com/avaweigh-pcs40tk-40-lb-digital-price-computing-scale-with-tower-legal-for-trade-with-thermal-label-printer/334PCS40TK.html')
        click("//a[@data-toggle]")
        assert externalLinkModal.clickAndCheckFunctionality()

        closeBrowser()
        // page without External link Resources
        tryLoad('https://www.dev.webstaurantstore.com/g/104/shoes-for-crews-26819-pearl-women-s-medium-width-black-water-resistant-soft-toe-non-slip-athletic-shoe')
        assert externalLinkModal.isModal() == false
        assert externalLinkModal.closeModalByButton() == false
        assert externalLinkModal.closeModalByX() == false
        assert externalLinkModal.clickVisitExternal() == false
        assert externalLinkModal.clickAndCheckFunctionality() == false

        closeBrowser()
    }
}

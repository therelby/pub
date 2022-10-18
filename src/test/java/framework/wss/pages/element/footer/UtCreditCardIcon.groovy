package framework.wss.pages.element.footer

import above.RunWeb
import wss.pages.element.footer.CreditCardIcon

class UtCreditCardIcon extends RunWeb {
    def test() {

        setup('vdiachuk', 'Credit Card Icons - Footer - Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test footer credit card icon',
                 "tfsTcIds:265471",
                 'logLevel:info'])


        CreditCardIcon cardIcon = new CreditCardIcon()
        tryLoad('homepage')
        assert cardIcon.isCreditIconPresent()
        assert cardIcon.getIconsQuantity() == CreditCardIcon.creditCardIcons.size()
        assert cardIcon.getCreditIconNames() == CreditCardIcon.creditCardIcons['name']
        assert cardIcon.isAllIconsPresent()

        log "--"

        tryLoad("https://www.google.com/")
        assert !cardIcon.isCreditIconPresent()
        assert cardIcon.getIconsQuantity() == 0
        assert cardIcon.getCreditIconNames() == []
        assert !cardIcon.isAllIconsPresent()


    }
}

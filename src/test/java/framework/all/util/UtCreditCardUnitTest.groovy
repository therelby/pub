package framework.all.util

import above.RunWeb
import wss.creditcard.CreditCardGenerator
import wss.myaccount.Billing
import wss.user.UserQuickLogin


class UtCreditCardUnitTest extends RunWeb {

    static void main(String[] args) {
        new UtCreditCardUnitTest().testExecute([:])
    }


    def test() {

        setup('vdiachuk', 'Credit Card generator unit test | Framework Self Testing Tool',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront',
               "tfsTcIds:265471", 'keywords:unit test credit card ' +
                      'creditcardutil genereator util ',
               'logLevel:info'])


        //WebDriver driver = getWebDriver()
        String url = getUrl("default")

        openBrowser(url)
        String cardNumber = CreditCardGenerator.getCard()
        log "Single credit card:$cardNumber"
        assert cardNumber.size() > 0

        String specialCard = CreditCardGenerator.getSpecialCard("Visa", '4')
        log "special card with leading zeros:$specialCard"
        //Checking visa with leading zeros
        assert specialCard.contains("0000") && !specialCard.contains('-')

        String specialCardDashes = CreditCardGenerator.getSpecialCard("Visa", '0', true)
        log "special card with dashes:$specialCardDashes"
        assert specialCardDashes.contains('-')

        def listOfCards = CreditCardGenerator.getCards(3, "MasterCard", '0', false)
        log "List of cards without dashes:" + listOfCards.toPrettyString()
        assert listOfCards.size() == 3
        for (def creditCardNumber in listOfCards) {
            assert creditCardNumber.size() == 16 && !creditCardNumber.contains("-")
        }

        def listOfCardsDashes = CreditCardGenerator.getCards(8, "Discover", '3', true)
        log "list of cards with dashes:" + listOfCardsDashes.toPrettyString()
        assert listOfCardsDashes.size() == 8
        assert listOfCardsDashes.any { element -> (element.size() == 19 && element.contains("-")) }
        assert listOfCardsDashes.any { element -> element.contains("000") }

        //checking char by char method
        String homeUrl = getUrl("homepage")
        assert tryLoad(homeUrl)
        UserQuickLogin.loginUser()
        Billing.navigateToBillingPage()

        assert CreditCardGenerator.typeByCharactersToXpath(Billing.billingCompany, specialCard)
        assert CreditCardGenerator.typeByCharactersToXpath(Billing.billingCompany, specialCardDashes)


    }
}

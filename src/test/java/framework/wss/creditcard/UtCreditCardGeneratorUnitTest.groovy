package framework.wss.creditcard

import above.RunWeb
import wss.cart.Cart
import wss.creditcard.CreditCardGenerator
import wss.user.UserQuickLogin

class UtCreditCardGeneratorUnitTest extends RunWeb{
    def test() {

        setup('vdiachuk', ' Credit Card Generator unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test credit card generator',
                 "tfsTcIds:265471", 'logLevel:info'])

        tryLoad("homepage")


        String creditCardDashes = CreditCardGenerator.getSpecialCard('', '0', true);
        log "Credit card with Dashes:" + creditCardDashes;
        assert  creditCardDashes.size()>=19
        assert  creditCardDashes.contains("-")


    }
}

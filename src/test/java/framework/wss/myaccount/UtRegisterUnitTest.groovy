package framework.wss.myaccount

import above.RunWeb
import wss.myaccount.Shipping
import wss.user.Register
import wss.user.UserQuickLogin

class UtRegisterUnitTest extends RunWeb {
    def test() {

        setup('vdiachuk', 'Register new Account Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test myaccount register ' +
                        'registration new user',
                 "tfsTcIds:265471", 'logLevel:info'])


        Integer quantityOfTry = 20
       // String homePage = getUrl("homepage")

        tryLoad('homepage')

        for (int i = 0; i < quantityOfTry;i++) {
            assert Register.navigateToRegisterPage()
            tryLoad('homepage')
        }


    }
}

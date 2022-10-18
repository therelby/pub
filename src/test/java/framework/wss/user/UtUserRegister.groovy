package framework.wss.user

import above.RunWeb

class UtUserRegister extends RunWeb {


    // Test
    def test() {

        setup('vdiachuk', 'user.Register Unit test | Framework Self Testing Tool',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront'
               , 'keywords:unit test  user Register Registration new User',
               "tfsTcIds:265471", 'logLevel:info'])


        String homePage = getUrl("homepage")
        openBrowser(homePage)


   //  assert Register.registerNewAccount(EmailUtil.getEmail(), ("QAautomation-"+StringUtil.getRandomDigits(3)))


    }
}
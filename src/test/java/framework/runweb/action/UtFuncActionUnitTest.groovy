package framework.runweb.action

import above.RunWeb
import wss.user.Register
import wss.user.UserQuickLogin

class UtFuncActionUnitTest extends RunWeb {
    def test() {

        setup('vdiachuk', 'Actions Unit test | Framework Self Testing Tool',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test actions action scrollto scroll',
               "tfsTcIds:265471", 'logLevel:info'])

        String buttonXpath = "//button[@aria-label='Submit Feedback']"
        tryLoad("homepage")

        assert scrollTo(buttonXpath)
        assert scrollTo(UserQuickLogin.registerLink)
        assert scrollTo(find(buttonXpath))

    }

}

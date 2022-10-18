package framework.runweb

import above.RunWeb

import wss.myaccount.Shipping
import wss.user.UserQuickLogin

class UtSetGetText extends RunWeb {
    // Test
    def test() {


        setup('vdiachuk', 'Set Text, Get text Unit Test | Framework Self Testing Tool',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords: unit test setText getText',
               "tfsTcIds:265471",
               'logLevel:info'])

        String name ="QAautomation"
        String homePage = getUrl("homepage")
        openBrowser(homePage)
        UserQuickLogin.loginUser("Regular User")
        Shipping.navigateToShippingPage()
        assert setText(Shipping.addNewNameXpath, name)
        assert getAttribute(Shipping.addNewNameXpath, 'value') == name
        assert getText(Shipping.headerXpath) == Shipping.headerText


    }
}
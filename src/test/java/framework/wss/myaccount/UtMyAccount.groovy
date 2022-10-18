package framework.wss.myaccount

import above.RunWeb
import wss.myaccount.AutoReorder
import wss.myaccount.DetailsSettings
import wss.myaccount.MyAccount
import wss.myaccount.Net30Terms
import wss.myaccount.PlusAccountSubscription
import wss.user.UserQuickLogin

class UtMyAccount extends RunWeb {
    def test() {

        setup('vdiachuk', 'Unit test for different classes in My Account Unit test | Framework Self ' +
                'Testing Tool',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test myaccount account autoreorder ',
               "tfsTcIds:265471", 'logLevel:info'])


        String homePage = getUrl("homepage")
        openBrowser(homePage)

        UserQuickLogin.loginUser("Regular User")
        assert AutoReorder.navigateToAutoReorderPage()
        waitForPage()
        assert DetailsSettings.navigateToDetailsSettings()
        waitForPage()
        assert DetailsSettings.checkPONumberCheckbox()
        assert DetailsSettings.uncheckPONumberCheckbox()
        assert MyAccount.navigateMyAccountPage()
        waitForPage()

        assert PlusAccountSubscription.navigateToPlusSubscription()
        assert Net30Terms.navigateToNet30Terms()
        assert PlusAccountSubscription.createNewPlusSubscription()


    }
}

package framework.wss.pages.webadmin

import above.RunWeb
import wss.pages.webadmin.ABTestManager
import wss.user.userurllogin.UserUrlLogin

class UtABTestManager extends RunWeb {

    def defaultFeatureId = ABTestManager.idConfirmOrder

    // Test
    def test() {

        setup('kyilmaz', 'UtABTestManager',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test webadmin ab test', 'logLevel:debug'])
        //testLoginFirst()
        testNavigate()
        testClickActionByIndex()
        testClickActionByDescription()
        closeBrowser()
    }

    /**
     * Used if testing for login beforehand. Generally unneeded, but generated a different toast at the time of writing.
     */
    def testLoginFirst() {
        tryLoad('/')
        def login = new UserUrlLogin()
        login.loginAs('11925071')
    }

    def testNavigate() {
        def ab = new ABTestManager(defaultFeatureId)
        assert (getCurrentUrl() == "https://www.dev.webstaurantstore.com/newwebadmin/marketing:features/edit/featureid/$defaultFeatureId")
    }

    def testClickActionByIndex() {
        def ab = new ABTestManager(defaultFeatureId)
        assert ab.clickActionByIndex(1)
    }

    def testClickActionByDescription() {
        def ab = new ABTestManager(defaultFeatureId)
        assert ab.clickActionByDescription('For all users')
    }
}

package framework.wss.api.user

import above.Execute
import wsstest.checkout.confirmorder.autoreorder.AutoReorderUtils

Execute.suite([
        new UtThreadingUserTest.regularUser(),
        new UtThreadingUserTest.plusUser(),
        new UtThreadingUserTest.platinumUser(),
        new UtThreadingUserTest.platinumPlusUser()
], 4)

class UtThreadingUserTest {
    class regularUser extends UtUserCreationApi {

        def tcIds = [0]

        // Test
        def test() {

            setup('kyilmaz', 'UtUserCreationApi',
                    ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                     'keywords:unit test', "tfsTcIds:${tcIds.join(',')}", 'logLevel:info'])
            testCreateRegularUser()
            AutoReorderUtils.uniqueUserLogin(0, 'regular')
        }
    }

    class plusUser extends UtUserCreationApi {

        def tcIds = [0]

        // Test
        def test() {

            setup('kyilmaz', 'UtUserCreationApi',
                    ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                     'keywords:unit test', "tfsTcIds:${tcIds.join(',')}", 'logLevel:info'])
            testCreatePlusUser()
            AutoReorderUtils.uniqueUserLogin(0, 'webplus')
        }
    }

    class platinumUser extends UtUserCreationApi {

        def tcIds = [0]

        // Test
        def test() {

            setup('kyilmaz', 'UtUserCreationApi',
                    ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                     'keywords:unit test', "tfsTcIds:${tcIds.join(',')}", 'logLevel:info'])
            testCreatePlatinumUser()
            AutoReorderUtils.uniqueUserLogin(0, 'platinum')
        }
    }

    class platinumPlusUser extends UtUserCreationApi {

        def tcIds = [0]

        // Test
        def test() {

            setup('kyilmaz', 'UtUserCreationApi',
                    ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                     'keywords:unit test', "tfsTcIds:${tcIds.join(',')}", 'logLevel:info'])
            testCreatePlatinumPlusUser()
            AutoReorderUtils.uniqueUserLogin(0, 'platinumPlus')
        }
    }
}


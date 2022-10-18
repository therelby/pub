package framework.wss.webadmin

import above.RunWeb

import wss.webadmin.WebAdmin
import wss.webadmin.systemsettings.WebAdminManageSystemSetting

class WebAdminManageSystemSettingsUnitTest extends RunWeb {
    def test() {

        setup('vdiachuk', 'Unit test for WebAdminSystemSettings | Framework Self ' +
                'Testing Tool',
              ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test webadmin admin newwebadmin ' +
                      'settings system manage ',
               "tfsTcIds:265471", 'logLevel:info'])


        String defaultPage = getUrl("default")
        openBrowser(defaultPage)
        deleteAllCookies()
        assert WebAdmin.loginToWebAdmin()
        //   assert WebAdminManageSystemSetting.navigateToSystemSettings()
        //   assert WebAdminManageSystemSetting.navigateToSystemSettingsParameter("265")

        //!!next change important parameter - do not forget to change it back it you do unit test!!
        assert WebAdminManageSystemSetting.setParameterToNewWebadminSettings("265", "true")
        assert WebAdminManageSystemSetting.setParameterToNewWebadminSettings("265", "false")

    }
}

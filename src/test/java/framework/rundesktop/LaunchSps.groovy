package framework.rundesktop

import all.VariableStorage
import sps.common.SpsApp

/**
 * Unit Test for SPS function
 * @author ttoanle
 */
class LaunchSps extends SpsApp {

    def tcId = 265465 // debugging testcase
    String userType
    String language
    String userName
    String password
    String variable

    LaunchSps(String variable){
        this.variable = variable

    }

    public data(){
        def data = VariableStorage.getData(variable)
        println data
        this.userType = data.userType
        this.language = data.language
        this.userName = data.userName
        this.password = data.password
    }

    public test() {
        setup('ttoanle', 'File Unit test | Framework Self Testing Tool',
                ['product:wss-sps', 'tfsProject:SPS%20.NET%20Rewrite', 'keywords:unit test sps', 'logLevel:debug'])
        data()
        startSpsAndSetUpLoginPage('871',userType,language)
        waitForElementDisplayed(loginPage.loginPageHeader)
        loginPage.loginToApplication(userType,userName,password)
    }
}




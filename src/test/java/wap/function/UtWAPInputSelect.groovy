package wap.function

import above.RunWeb
import wap.common.WAPLogin
import wap.common.wapFunction.wapSelect.WAPSelect
import wap.common.wapFunction.wapSelect.WAPSelectInputDropDown

class UtWAPInputSelect extends RunWeb {

    def userName
    def password

    UtWAPInputSelect(def userName = WAPLogin.usernameDev, def password = WAPLogin.passwordDev) {
        this.userName = userName
        this.password = password
    }
    static void main(String[] args) {
        new UtWAPInputSelect().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    def test() {

        setup('ttoanle', 'Admin Portal | DropDown Unit Text',
                ['product:wap', 'tfsProject:Webstaurant.AdminPortal',
                 'keywords:unit test table function',
                 'PBI: 0',
                 'logLevel:info'])
        WAPLogin webAdminPortal = new WAPLogin()
        assert webAdminPortal.loginAndLoadPage('Virtual Grouping','/External/Virtual%20Grouping/Search')
        WAPSelect dropDown = new WAPSelectInputDropDown("//label[text()='Product ID']/parent::div/following-sibling::div")
        assert dropDown.inputValueForDropDown('10207123')
        assert dropDown.selectValue('10207123')
        assert dropDown.removeAllValue()
        refresh()
        log(dropDown.getAllValueRelatedTo('1102'))

    }
}

package wap.function

import above.RunWeb
import wap.common.WAPLogin
import wap.common.wapFunction.wapSelect.WAPSelect
import wap.common.wapFunction.wapSelect.WAPSelectDropDownInput


class UtWAPSelectInput extends RunWeb {

    def userName
    def password

    UtWAPSelectInput(def userName = WAPLogin.usernameDev, def password = WAPLogin.passwordDev) {
        this.userName = userName
        this.password = password
    }
    static void main(String[] args) {
        new UtWAPSelectInput().testExecute([
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
        WAPSelect dropDown = new WAPSelectDropDownInput("//label[text()='Created By']/parent::div/following-sibling::div")
        assert dropDown.openDropDown()
        assert dropDown.selectValue('aanjier')
        assert dropDown.removeSelectedValue('aanjier')
        assert dropDown.selectValue(['aanjier','ahansen','caiello'])

        refresh()
        def valuesFropDropDown = dropDown.getAllValueFromDropDown()
        assert valuesFropDropDown.contains('aanjier')
        assert valuesFropDropDown.contains('ahansen')
        assert valuesFropDropDown.contains('zkarpenko')
        assert dropDown.closeDropDown()
    }
}

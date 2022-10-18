package wap.function

import above.RunWeb
import wap.common.WAPLogin
import wap.common.wapFunction.wapSelect.WAPSelect
import wap.common.wapFunction.wapSelect.WAPSelectDropDown

class UtWAPSelect extends RunWeb {

    def userName
    def password

    UtWAPSelect(def userName = WAPLogin.usernameDev, def password = WAPLogin.passwordDev) {
        this.userName = userName
        this.password = password
    }
    static void main(String[] args) {
        new UtWAPSelect().testExecute([
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

        WAPSelect dropDown = new WAPSelectDropDown("//label[text()='Status']/parent::div/following-sibling::div")
        assert webAdminPortal.loginAndLoadPage('Virtual Grouping','/External/Virtual%20Grouping/Search')
        assert dropDown.openDropDown()
        assert dropDown.selectValue('Inactive')

        refresh()
        def dropDownValues = dropDown.getAllValueFromDropDown()
        assert dropDownValues.contains('Active')
        assert dropDownValues.contains('Inactive')
        assert dropDownValues.contains('All')
        assert dropDown.selectValue('All')
        assert waitForElementInvisible(dropDown.dropDownXpath,1)

    }
}

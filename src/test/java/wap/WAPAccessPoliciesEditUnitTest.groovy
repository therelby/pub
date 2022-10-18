package wap

import above.RunWeb
import wap.common.WAPLogin
import wap.common.wapadmin.accesspolicies.WAPAccessPoliciesEdit

class WAPAccessPoliciesEditUnitTest extends RunWeb {
    def userName
    def password
    WAPAccessPoliciesEditUnitTest(def userName = WAPLogin.usernameDev, def password=WAPLogin.passwordDev){
        this.userName = userName
        this.password = password
    }
    static void main(String[] args) {
        new WAPAccessPoliciesEditUnitTest().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }
    def test() {
        setup('ehero', 'AdminPortal Edit Policies unit test | Framework Self ' +
                'Testing Tool',
                ['product:wap', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test adminportal admin vendor ' +
                        'portal ',
                 "tfsTcIds:549266", 'logLevel:info'])

        def wap = new WAPLogin()
        def edit = new WAPAccessPoliciesEdit(userName,password)
        edit.loginAndLoadEditAccessPolicyPage()
        edit.isEditPoliciesPage()
        edit.selectPolicyTypeDropdown('White List')
        edit.getUsersDataFromDropdown()
        edit.getDepartmentDataFromDropdown()
        edit.getUsersFromUsersField()
        edit.getDepartmentsFromDepartmentsField()
        edit.setUsername('ehero')
        edit.setDepartment('111 Staff(The Restaurant Store, Inc')
    }
}

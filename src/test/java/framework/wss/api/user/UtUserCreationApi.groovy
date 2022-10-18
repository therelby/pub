package framework.wss.api.user

import above.RunWeb
import wss.api.user.UserCreationApi
import wss.api.user.UserLookupApi

class UtUserCreationApi extends RunWeb {

    static void main(String[] args) {
        new UtUserCreationApi().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    def pbi = 0

    // Test
    def test() {

        setup('kyilmaz', 'UtUserCreationApi',
                ['product:wss', 'tfsProject:Automation.Projects',
                 'keywords:unit test', "PBI:$pbi", 'logLevel:info'])
        testCreateRegularUser()
        testCreatePlusUser()
        testCreatePlatinumUser()
        testCreatePlatinumPlusUser()
        testCreateSupervisorUser()
        testCreateSubordinateUser()
        testCreateSubordinateNeedsApprovalUser()
    }

    void testCreateRegularUser() {
        testApiInstance(UserCreationApi.createUniqueRegularUser(0, false), 'Regular')
    }

    void testApiInstance(UserCreationApi userCreation, userType) {
        def success = userCreation.getValues('success') == true
        def userIndex = userCreation.getValues('userIndex')
        check(success, "$userType user creation success = $success")
        check(userIndex != 0, "$userType user creation userIndex = $userIndex")
        UserLookupApi userLookup = new UserLookupApi(userCreation.currentEmail)
        userLookup.printResult()
        def lookupSuccess = userLookup.getValues('success')
        check(userIndex != 0 && success, "$userType user lookup success (testing UserCreationApi $userType) = $lookupSuccess. User: ${userCreation.getIdentifiers()}")
        userCreation.printResult()
        userLookup.printResult()
    }

    void testCreatePlusUser() {
        testApiInstance(UserCreationApi.createUniqueRegularUser(0, true), 'WebPlus')
    }

    void testCreatePlatinumUser() {
        testApiInstance(UserCreationApi.createUniquePlatinumUser(0, false), 'Platinum')
    }

    void testCreatePlatinumPlusUser() {
        testApiInstance(UserCreationApi.createUniquePlatinumUser(0, true), 'PlatinumPlus')
    }

    void testCreateSupervisorUser() {
        testApiInstance(UserCreationApi.createUniqueSupervisorUser(0), 'Supervisor')
    }

    void testCreateSubordinateUser() {
        testApiInstance(UserCreationApi.createUniqueSubordinateUser(0, false), 'Subordinate')
    }

    void testCreateSubordinateNeedsApprovalUser() {
        testApiInstance(UserCreationApi.createUniqueSubordinateUser(0, true), 'SubordinateNeedsApproval')
    }
}

package framework.wss.user

import above.RunWeb
import all.Money
import all.util.Addresses
import wss.api.user.UserCreationApi
import wss.api.user.UserLookupApi
import wss.checkout.Checkout
import wss.item.ItemUtil
import wss.user.UserUtil
import wss.user.userurllogin.UserUrlLogin

class UtRewardsCustomerCreation extends RunWeb {

    UserLookupApi user = null
    String userIdUnderTest = null

    protected Addresses residentialAddress = [
            name           : "Non Subordinate Test User",
            companyName    : "",
            address        : "3238 Landings North Dr",
            address2       : "",
            city           : 'Atlanta',
            zip            : "30331-6272",
            phone          : "6145555522",
            country        : "United States",
            countryCode    : "US",
            state          : "GA",
            destinationType: "Residential"
    ]

    def test() {

        setup('mwestacott', 'Rewards Customer creation unit test  | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront',
                 'keywords:unit test rewards customer creation ',
                 "tfsTcIds:0",
                 'logLevel:info'])

        userSetup('regular') //Regular user
        userSetup('platinum') //Platinum user
        userSetup('webplus') //Plus user
        userSetup('platinumPlus') //Platinum Plus user
        userSetup('regularRewards') //Regular Rewards user
        userSetup('platinumRewards') //Platinum Rewards user
        userSetup('webplusRewards') //Plus Rewards user
        userSetup('platinumPlusRewards') //Platinum Plus Rewards user

        closeBrowser()
    }

    protected boolean userSetup(String userTypeUnderTest){
        assert (userTypeUnderTest in ['regular', 'webplus', 'platinum', 'platinumplus', 'platinumPlus', 'regularRewards', 'webplusRewards', 'platinumRewards', 'platinumplusRewards', 'platinumPlusRewards'])

        boolean doesUserHaveRewards = userTypeUnderTest.contains("Rewards")
        String uniqueIdentifier = "_rewards_unit_test_9_${doesUserHaveRewards ? "has_rewards" : "no_rewards"}"

        user = getUserForTesting(0, userTypeUnderTest, uniqueIdentifier, residentialAddress, 0, false)
        assert (user != null)
        assert (isUserUsingCorrectSettings(userTypeUnderTest, user))
        userIdUnderTest = user.getUserIndex().toString()
        return new UserUrlLogin().loginAs(userIdUnderTest)

    }

    protected boolean isUserUsingCorrectSettings(String userTypeUnderTest, def user){
        boolean isPlatinum = user.userInfo['isPlatinum']
        boolean hasPlus = user.userInfo['hasPlus']
        boolean doesUserHaveRewards = UserUtil.doesUserHaveRewardsSubscription(user.getUserIndex(), false)
        switch(userTypeUnderTest){
            case "regular":
                return !isPlatinum && !hasPlus && !doesUserHaveRewards
                break
            case "webplus":
                return !isPlatinum && hasPlus && !doesUserHaveRewards
                break
            case "platinum":
                return isPlatinum && !hasPlus && !doesUserHaveRewards
                break
            case "platinumPlus":
                return isPlatinum && hasPlus && !doesUserHaveRewards
                break
            case "regularRewards":
                return !isPlatinum && !hasPlus && doesUserHaveRewards
                break
            case "webplusRewards":
                return !isPlatinum && hasPlus && doesUserHaveRewards
                break
            case "platinumRewards":
                return isPlatinum && !hasPlus && doesUserHaveRewards
                break
            case "platinumPlusRewards":
                return isPlatinum && hasPlus && doesUserHaveRewards
                break
            default:
                return false
                break
        }
    }

    static UserLookupApi getUserForTesting(Integer workItemId, String customerType = 'regular', String uniqueModifier = '',
                                           Addresses userAddress,
                                           Integer supervisorId,
                                           Boolean hasOptionalInformation)
    {
        customerType = customerType.toLowerCase()
        UserLookupApi user = new UserLookupApi(workItemId, customerType.minus("rewards"), uniqueModifier)
        if (!user.verifySuccess()) {
            String accountType = hasOptionalInformation ? "Restaurant - Chain" : "Other"
            String companyName = hasOptionalInformation ? "AutomationWSS" : ""
            UserCreationApi createUser = createUserForTesting(customerType.minus("rewards"), workItemId, supervisorId, userAddress, accountType, companyName, uniqueModifier)
            sleep(5000)

            assert (createUser.verifySuccess())
            user = new UserLookupApi(createUser.currentEmail)

            if(customerType.contains("rewards")){
                UserUtil.createUserWithRewardsSubscription(user.getUserIndex())
            }

        }
        return user
    }

    protected static UserCreationApi createUserForTesting(String customerType, Integer workItemId, Integer supervisorId
                                                          , Addresses userAddress, String accountType, String companyName, String uniqueModifier){
        String emailAddress = UserCreationApi.generateEmailFromWorkItemId(workItemId, customerType, uniqueModifier)
        String customerName = UserCreationApi.getNameFromWorkItemId(workItemId)
        Boolean hasPlus = (customerType in ['webplus', 'platinumplus']) ? true : false
        Boolean isPlatinum = (customerType in ['platinum', 'platinumplus']) ? true : false
        Boolean isSupervisor = (customerType == 'supervisor') ? true : false
        Boolean needsApproval = (customerType == 'subordinateneedsapproval') ? true : false
        String password = '1automationWss'
        return new UserCreationApi(emailAddress, customerName, hasPlus, isPlatinum, isSupervisor, supervisorId, needsApproval, userAddress, password, accountType, companyName )
    }
}

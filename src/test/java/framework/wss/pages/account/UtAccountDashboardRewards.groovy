package framework.wss.pages.account

import above.RunWeb
import wss.pages.account.element.AccountDashboardRewardsElement
import wss.user.userurllogin.UserUrlLogin

class UtAccountDashboardRewards extends RunWeb {
    static void main(String[] args) {
        new UtAccountDashboardRewards().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    def test() {
        final int PBI = 0
        setup([
                author  : 'gkohlhaas',
                title   : 'My Account Rewards Dashboard element | Framework Self Testing Tool',
                PBI     : PBI,
                product : 'wss',
                project : 'Webstaurant.StoreFront',
                keywords: 'My Account dashboard Rewards Ad and WebstaurantRewards menu',
                logLevel: 'info'
        ])

        UserUrlLogin userUrlLogin = new UserUrlLogin()
        AccountDashboardRewardsElement rewardsElement = new AccountDashboardRewardsElement()
        userUrlLogin.loginAs('253') //non-rewards user index
        assert rewardsElement.navigate()
        assert rewardsElement.isAccountDashboard()
        assert rewardsElement.isMainContainerPresent()
        assert rewardsElement.isRewardsAdPresent()
        assert rewardsElement.isDismissOptionPresent()
        def dismissText = rewardsElement.getDismissOptionText()
        assert dismissText == rewardsElement.expectedValues.dismissText
        assert !rewardsElement.isRewardsMenuOptionPresent()
        assert !rewardsElement.clickRewardsMenuOption()
        assert rewardsElement.clickRewardsAd()
        def getUrl = getCurrentUrl()
        assert getUrl == "https://www.dev.webstaurantstore.com/rewards/"
        back()
        assert rewardsElement.isAccountDashboard()
        assert rewardsElement.isRewardsAdPresent()
        assert rewardsElement.isDismissOptionPresent()
        assert rewardsElement.clickDismissOption()
        assert !rewardsElement.isRewardsAdPresent()
        refresh()
        assert !rewardsElement.isRewardsAdPresent()
        closeBrowser()

        userUrlLogin.loginAs('15594571') //rewards user index
        assert rewardsElement.navigate()
        assert rewardsElement.isAccountDashboard()
        assert rewardsElement.isMainContainerPresent()
        assert !rewardsElement.isRewardsAdPresent()
        assert !rewardsElement.isDismissOptionPresent()
        assert rewardsElement.isRewardsMenuOptionPresent()
        def menuText = rewardsElement.getRewardsMenuText()
        assert menuText == rewardsElement.expectedValues.rewardsMenuText
        assert rewardsElement.clickRewardsMenuOption()
        def rewardsUrl = getCurrentUrl()
        assert rewardsUrl == "https://www.dev.webstaurantstore.com/myaccount/payment/#credits"
        closeBrowser()
    }
}
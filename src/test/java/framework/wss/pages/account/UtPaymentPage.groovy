package framework.wss.pages.account

import above.RunWeb
import all.util.Addresses
import wss.pages.account.PaymentPage
import wss.user.UserDetail
import wss.user.UserUtil
import wss.user.userurllogin.UserUrlLogin


class UtPaymentPage extends RunWeb{

    static void main(String[] args) {
        new UtPaymentPage().testExecute([:])
    }
    def PBI = 638177

    void test() {

        setup([
                author  : 'jglisson',
                title   : 'Payment Page Unit Tests',
                PBI     : 638177,
                product : 'wss|dev',
                project : 'Payments',
                keywords: 'payment page unit tests'
        ])

        String userType = UserUrlLogin.userRegular
        UserUrlLogin userUrlLogin = new UserUrlLogin()
        UserDetail userDetail = userUrlLogin.loginNewUser(userType, PBI)
        UserUtil.createUserWithRewardsSubscription(userDetail.detail.index.toInteger())
        //UserDetail userDetail = userUrlLogin.loginAs('13503927')

        PaymentPage paymentPage = new PaymentPage()
        assert paymentPage.navigate()

        assert paymentPage.getAllSavedCards().size() == 0

        /** tests that Get card type returns correct values, incidentally tests Adding new cards*/
        String visaCard = wss.creditcard.CreditCardGenerator.getSpecialCard('Visa')
        assert paymentPage.addNewCreditCard('Visa', visaCard,'111','11','2022')
        assert paymentPage.getDefaultCard() == 'Visa'
        paymentPage.navigate()
        assert paymentPage.addNewCreditCard('Mastercard', wss.creditcard.CreditCardGenerator.getSpecialCard('Mastercard'),'111','11','2022')
        paymentPage.navigate()
        assert paymentPage.addNewCreditCard('American Express', wss.creditcard.CreditCardGenerator.getSpecialCard('American Express'),'111','11','2022')
        paymentPage.navigate()
        assert paymentPage.addNewCreditCard('Discover', wss.creditcard.CreditCardGenerator.getSpecialCard('Discover'),'111','11','2022')
        paymentPage.navigate()
        assert paymentPage.addNewCreditCard('Rewards', wss.creditcard.CreditCardGenerator.getSpecialCard('Rewards'),'111','11','2022')

        assert paymentPage.cardExists('Visa')
        assert paymentPage.cardExists('Mastercard')
        assert paymentPage.cardExists('American Express')
        assert paymentPage.cardExists('Discover')
        assert !paymentPage.cardExists('Failure')

        assert paymentPage.getNickname('Visa') == ''
        assert paymentPage.getExpirationDate('Visa') == '11/2022'
        assert paymentPage.getMask('Visa') == visaCard.substring(12)

        assert paymentPage.getCardType('Visa') == 'Visa'
        assert paymentPage.getCardType('Mastercard') == 'Mastercard'
        assert paymentPage.getCardType('American Express') == 'American Express'
        assert paymentPage.getCardType('Discover') == 'Discover'
        assert paymentPage.getCardType('Rewards') == 'Rewards'

        assert paymentPage.getAllSavedCards().size() == 5

        /** Verifying getting details returns nulls when the values don't exist*/
        assert paymentPage.getPlusCard() == null
        assert paymentPage.getCardType('Test') == null
        assert paymentPage.getNickname('Test') == null
        assert paymentPage.getExpirationDate('Test') == null
        assert paymentPage.getMask('Test') == null

        /** Testing changing billing address, both Residential and Commercial*/
        assert paymentPage.changeBillingAddress(Addresses.usResidentialAddressAL)
        paymentPage.navigate()
        assert paymentPage.changeBillingAddress((Addresses.usCommercialAddressMT))

        /** setCardDefault returns true when it is set to default or already there only*/
        assert paymentPage.setCardDefault('Visa')
        assert paymentPage.setCardDefault('Visa')
        assert !paymentPage.setCardDefault('Test')

        /** Can delete saved cards that exist*/
        assert paymentPage.deleteSavedCard('Visa')
        assert !paymentPage.deleteSavedCard('Test')
        closeBrowser()

        /**Checking we can get plus cards*/
        userType = UserUrlLogin.userPlus
        userUrlLogin = new UserUrlLogin()
        userUrlLogin.loginNewUser(userType, PBI)
        paymentPage.navigate()
        assert paymentPage.getPlusCard() != null
        closeBrowser()


    }
}

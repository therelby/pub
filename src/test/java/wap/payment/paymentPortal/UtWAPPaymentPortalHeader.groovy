package wap.payment.paymentPortal

import above.RunWeb
import wap.common.WAPLogin
import wap.common.payment.paymentPortal.WAPPaymentPortal
import wap.common.payment.paymentPortal.WAPPaymentPortalHeader
import wap.common.wapData.WAPPaymentPortalData

class UtWAPPaymentPortalHeader extends RunWeb {

    static void main(String[] args) {
        new UtWAPPaymentPortalHeader().testExecute([
                browser      : 'chrome',
                remoteBrowser: true,
                environment  : 'dev',
                runType      : 'debug'
        ])
    }

    def pbi = 706476
    WAPPaymentPortal wapPaymentPortal
    String userName = WAPLogin.usernameDev
    String password = WAPLogin.passwordDev
    String orderNumber



    void test() {

        setup([author  : 'dpylant',
               title   : 'Web Admin Portal Payment Header Unit Test',
               product : 'wss|dev',
               PBI     : pbi,
               project : 'Webstaurant.StoreFront',
               keywords: 'WAP Unit Test'

        ])


        /**
         * Tests for logging into the payment portal
         * */
        wapPaymentPortal = new WAPPaymentPortal(userName, password)
        assert wapPaymentPortal.navigateToPaymentPortal()

        /**
         * Tests for the landing page
         * */
        WAPPaymentPortalHeader wapPaymentPortalHeader = new WAPPaymentPortalHeader()
        LinkedHashMap orderNumList = WAPPaymentPortalData.getOrderNumNyPaymentProcessor('WorldPay')
        orderNumber = orderNumList.SalesOrderReferenceIdentifier
        assert wapPaymentPortalHeader.setTextSearchBox(orderNumber)
        assert wapPaymentPortalHeader.clickSearchButton()

        /**
         * Tests for the header information
         * */
        assert wapPaymentPortalHeader.setTextHeaderSearchBox(orderNumber)
        assert wapPaymentPortalHeader.clickDropDownSearchButton()
        assert wapPaymentPortalHeader.getOrderNumFromHeader().length() > 0
        assert wapPaymentPortalHeader.getDMSOrderFromHeader().length() > 0
        assert wapPaymentPortalHeader.getDivisionNameFromHeader().length() > 0
        assert wapPaymentPortalHeader.getLogoTypesFromHeader().length() > 0

        /**
         * Tests for the Billing Address information
         * */
        assert wapPaymentPortalHeader.getBillingName().length() > 0
        assert wapPaymentPortalHeader.getBillingAddress1().length() > 0
        assert wapPaymentPortalHeader.getBillingAddress2().length() > 0
        assert wapPaymentPortalHeader.getBillingCity().length() > 0
        assert wapPaymentPortalHeader.getBillingCountry().length() > 0

        /**
         * Tests for comparing the Shipping Address information
         * */
        assert wapPaymentPortalHeader.getShippingName().length() > 0
        assert wapPaymentPortalHeader.getShippingAddress1().length() > 0
        assert wapPaymentPortalHeader.getShippingAddress2().length() > 0
        assert wapPaymentPortalHeader.getShippingCity().length() > 0
        assert wapPaymentPortalHeader.getShippingCountry().length() > 0

        /**
         * Test for the Current Order Total information
         * */
        assert wapPaymentPortalHeader.getOrderTotal().length() > 0

        /**
         * Test for clicking each link in the header
         * */
        assert wapPaymentPortalHeader.clickProfitReportLink()
        assert wapPaymentPortalHeader.clickTransactionLink()
        assert wapPaymentPortalHeader.clickChargebackLink()

    }
}



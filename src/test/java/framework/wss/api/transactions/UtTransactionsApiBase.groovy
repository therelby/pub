package framework.wss.api.transactions

import above.RunWeb
import all.util.StringUtil
import wss.api.transactions.TransactionsApiBase

class UtTransactionsApiBase extends RunWeb {

    static void main(String[] args) {
        new UtTransactionsApiBase().testExecute([:])
    }

    void test() {

        setup([
                author  : 'jglisson',
                title   : 'Account Updater File creator unit tests',
                PBI     : 594385,
                product : 'wss|dev',
                project : 'Automation Projects',
                keywords: 'account updater'
        ])

        testTAPICallWithoutBody()
        testTAPICallWithoutBody()
    }

    void testTAPICallWithoutBody(){
        String endpoint = '/order/${salesOrderReferenceIdentifier}'
        String requestMethod = 'GET'
        Map parameters = [:]
        String body = ''

        parameters['salesOrderReferenceIdentifier'] = '72754280'

        TransactionsApiBase tapiOrder = new TransactionsApiBase(endpoint, requestMethod, body)

        assert tapiOrder.callApi(parameters)
        assert tapiOrder.getStatusCode() == 200
        assert tapiOrder.getResponseBodyContent().size() > 0

        tapiOrder = new TransactionsApiBase(endpoint, requestMethod, body, 'webstaurantstore')

        assert tapiOrder.callApi(parameters)
        assert tapiOrder.getStatusCode() == 200
        assert tapiOrder.getResponseBodyContent().size() > 0
    }

    void testTAPICallWithBody(){
        String endpoint = '/Job/accountupdater/sendnotifications'
        String requestMethod = 'POST'

        TransactionsApiBase tapiSendNotifications = new TransactionsApiBase(endpoint, requestMethod, '','webstaurantstore')

        assert tapiSendNotifications.callApi()
        assert tapiSendNotifications.getStatusCode() == 200
        assert tapiSendNotifications.getResponseBodyContent().size() == 0

        tapiSendNotifications = new TransactionsApiBase(endpoint, requestMethod)

        assert tapiSendNotifications.callApi()
        assert tapiSendNotifications.getStatusCode() == 200
        assert tapiSendNotifications.getResponseBodyContent().size() == 0
    }


}

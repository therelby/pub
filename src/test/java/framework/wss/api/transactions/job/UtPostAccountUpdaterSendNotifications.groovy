package framework.wss.api.transactions.job

import above.RunWeb
import wss.api.transactions.job.PostAccountUpdaterSendNotifications

class UtPostAccountUpdaterSendNotifications extends RunWeb {

    static void main(String[] args) {
        new UtPostAccountUpdaterSendNotifications().testExecute([:])
    }

    void test() {

        setup([
                author  : 'jglisson',
                title   : 'Account Updater- SendNotifications Job RabbitMQ posts',
                PBI     : 673877,
                product : 'wss|dev',
                project : 'Payments',
                keywords: 'account updater RabbitMQ'
        ])

        //Only one real test here since we can't trigger the endpoint call to fail due to lack of parameters

        PostAccountUpdaterSendNotifications jobAccountUpdaterSendNotifications = new PostAccountUpdaterSendNotifications()
        assert jobAccountUpdaterSendNotifications.getStatusCode() == 200

    }

}

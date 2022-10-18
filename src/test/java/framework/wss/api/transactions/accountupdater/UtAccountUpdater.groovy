package framework.wss.api.transactions.accountupdater

import above.RunWeb
import wss.api.transactions.accountupdater.AccountUpdater

class UtAccountUpdater extends RunWeb{

    static void main(String[] args) {
        new UtAccountUpdater().testExecute([:])
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

        AccountUpdater accountUpdater = new AccountUpdater()

        assert accountUpdater.getRabbitMQRequest('123','test.txt').size() != 0
        /** Account Updater Query function testing */

        String newJob = accountUpdater.insertJobAccountUpdater()//Will not be run so negative case
        String finishedJob = '2' //Existing, finished job

        assert newJob.size() > 0

        assert accountUpdater.checkJobStatusAccountUpdater(finishedJob)
        assert !accountUpdater.checkJobStatusAccountUpdater(newJob)

        assert accountUpdater.waitAccountUpdaterFileProcessed(finishedJob)
        assert !accountUpdater.waitAccountUpdaterFileProcessed(newJob)

        assert accountUpdater.executeAccountUpdaterSprocs()

        /** RabbitMQ publishing testing */

        String fileLocation = 'https://internalassets.dev.webstaurantstore.com/payments/testing/josh/updater5.txt'
        String jobRunId = accountUpdater.insertJobAccountUpdater()

        assert accountUpdater.publishRabbitMQMassTransitMessage(jobRunId, fileLocation)

    }
}
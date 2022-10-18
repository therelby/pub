package framework.wss.api.rabbitmq

import above.RunWeb
import com.rabbitmq.client.AMQP
import wss.api.rabbitmq.RabbitMQApiBase
import wss.api.transactions.accountupdater.AccountUpdater

class UtRabbitMQApiBase extends RunWeb{

    static void main(String[] args) {
        new UtRabbitMQApiBase().testExecute([:])
    }

    void test() {

        setup([
                author  : 'jglisson',
                title   : 'Rabbit MQ API Base unit tests',
                PBI     : 594385,
                product : 'wss|dev',
                project : 'Automation Projects',
                keywords: 'account updater'
        ])

        def host = 'transactions-api-amqp.home.clarkinc.biz.backends.tech'
        def virtualHost = '/transactions'
        def exchange = 'TransactionsApplicationMassTransitAccountUpdaterConsumersResponseFileReady'
        def routingKey = 'TransactionsApplicationMassTransitAccountUpdaterConsumersResponseFileReady'
        String queue = 'z-automation-RabbitMQUnitTest'

        RabbitMQApiBase rabbitMQApiBase = new RabbitMQApiBase(virtualHost, host)

        assert rabbitMQApiBase.queueCreate(queue,exchange,routingKey)

        /**Publish Message Setup and testing*/
        AccountUpdater accountUpdater = new AccountUpdater()
        String jobRunId = accountUpdater.insertJobAccountUpdater()

        String payload = '{\n' +
                '"messageId": "00060000-ac12-0242-4f49-08d9df48f4c1",\n' +
                '"conversationId": "00060000-ac12-0242-c1a8-08d9df48f4c4",\n' +
                '"sourceAddress": "rabbitmq://rabbitmq.home.clarkinc.biz.backends.tech/8c5cd194b7d2_TransactionsApi_bus_yydyyyfcnebrr3mwbdc761nef1?temporary=true",\n' +
                '"destinationAddress": "rabbitmq://rabbitmq.home.clarkinc.biz.backends.tech/Transactions.Application.MassTransit.AccountUpdater.Messages:ResponseFileReady",\n' +
                '"messageType": [\n' +
                '"urn:message:Transactions.Application.MassTransit.AccountUpdater.Messages:ResponseFileReady"\n' +
                '],\n' +
                '"message": {\n' +
                '"jobRunId": %s,\n' +
                '"filename": "/payments/testing/josh/updater5.txt"\n' +
                '},\n' +
                '"sentTime": "2022-01-24T14:51:08.6584649Z",\n' +
                '"headers": {\n' +
                '"MT-Activity-Id": "00-aadcdbbac4a8e1429f5df0c5d5387be6-3feb957811242142-00"\n' +
                '},\n' +
                '"host": {\n' +
                '"machineName": "8c5cd194b7d2",\n' +
                '"processName": "Transactions.Api",\n' +
                '"processId": 27,\n' +
                '"assembly": "Transactions.Api",\n' +
                '"assemblyVersion": "1.0.0.0",\n' +
                '"frameworkVersion": "5.0.13",\n' +
                '"massTransitVersion": "7.2.4.0",\n' +
                '"operatingSystemVersion": "Unix 4.19.104.0"\n' +
                '}\n' +
                '}'

        payload = String.format(payload, jobRunId)

        Map headers = [:]
        headers['Content-Type'] = 'application/vnd.masstransit+json'
        headers['MT-Activity-Id'] = '00-aadcdbbac4a8e1429f5df0c5d5387be6-3feb957811242142-00'
        headers['publishId'] = 1

        AMQP.BasicProperties properties = new AMQP.BasicProperties()
        properties.builder()
                .messageId('00060000-ac12-0242-4f49-08d9df48f4c1')
                .deliveryMode(2)
                .contentType('application/vnd.masstransit+json')
                .headers(headers)



        assert rabbitMQApiBase.publishMessage(exchange, routingKey, payload, properties)
        //assert !rabbitMQApiBase.publishMessage('Bad Exchange 123', routingKey, payload, properties)

        /** Testing queue functionality*/
        assert rabbitMQApiBase.queueWaitForMessages(queue)
        assert rabbitMQApiBase.queueMessageCount(queue) == 1
        assert rabbitMQApiBase.queuePurge(queue)
        assert rabbitMQApiBase.queueMessageCount(queue) == 0
        rabbitMQApiBase.publishMessage(exchange, routingKey, payload, properties)

        /** Get Messages, testing requeue = true and false */
        List messages = rabbitMQApiBase.getQueueMessages(queue, true)
        assert messages.size() == 1
        assert messages[0].payload.size() > 0
        assert rabbitMQApiBase.queueWaitForMessages(queue)
        assert rabbitMQApiBase.queueMessageCount(queue) == 1

        messages = rabbitMQApiBase.getQueueMessages(queue, false)
        assert messages.size() == 1
        assert messages[0].payload.size() > 0
        assert !rabbitMQApiBase.queueWaitForMessages(queue)
        assert rabbitMQApiBase.queueMessageCount(queue) == 0

        assert rabbitMQApiBase.queueDelete(queue)

        rabbitMQApiBase.disconnect()
    }
}

package framework.wss.api.transactions.application

import above.RunWeb
import wss.api.transactions.application.GetFnboStatus

class UtGetFnboStatus extends RunWeb{

    static void main(String[] args) {
        new UtGetFnboStatus().testExecute([:])
    }
    def PBI = 506371

    void test() {

        setup([
                author  : 'jglisson',
                title   : 'GET FnboStatus Unit Tests',
                PBI     : PBI,
                product : 'wss|dev',
                project : 'Payments',
                keywords: 'FNBO Status'
        ])

        /** Testing getApplicationUniqueIdentifier returns a value for each type, and empty on fail*/
        for (type in GetFnboStatus.statusTypes){
            assert GetFnboStatus.getApplicationUniqueIdentifier(type) != ''
        }
        assert GetFnboStatus.getApplicationUniqueIdentifier('TestFail') == ''


        /**Testing getFnboStatus successful constructor and api call*/
        String applicationUniqueIdentifier = GetFnboStatus.getApplicationUniqueIdentifier('approved')
        GetFnboStatus getFnboStatus = new GetFnboStatus(applicationUniqueIdentifier)

        assert getFnboStatus.verifySuccess()
        assert getFnboStatus.getStatusCode() == 200
        assert getFnboStatus.response != null
        assert getFnboStatus.response.applicationStatus.size() > 0
        assert getFnboStatus.response.applicationUniqueIdentifier.size() > 0
        assert getFnboStatus.response.applicationUrl.size() > 0
        assert getFnboStatus.response.expirationDate.size() > 0
        assert getFnboStatus.response.tokens.size() == 0

        /**Testing getFnboStatus failure constructor and api call*/
        getFnboStatus = new GetFnboStatus('TestFail')

        assert !getFnboStatus.verifySuccess()
        assert getFnboStatus.getStatusCode() == 400
        assert getFnboStatus.response == null
    }



}

package framework.wss.api.transactions.paymentserver

import above.RunWeb
import wss.api.transactions.TransactionsApiUtil
import wss.api.transactions.paymentserver.PostLogEvent
import java.text.SimpleDateFormat

class UtPostLogEvent extends RunWeb{

    static void main(String[] args) {
        new UtPostLogEvent().testExecute([:])

    }

    int PBI = 713918

    def test() {


        setup([
                author  : 'jglisson',
                title   : 'TAPI Post LogEvent Unit Tests',
                PBI     : PBI,
                product : 'wss|dev',
                project : 'Payments',
                keywords: 'transactions logevent'
        ])

        String scriptName = 'Automation Testing'
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat('YYY-MM-dd hh:mm:ss.SSS')
        String eventDate = simpleDateFormat.format(new Date())

        testPostLogEvent(scriptName, eventDate)
        testGetTblUserLogData(scriptName, eventDate)
    }

    void testPostLogEvent(String scriptName, String eventDate){
        Integer userIndex = TransactionsApiUtil.getPaymentsUserByDivision()
        String action = 'TAPI Eventlog'
        Integer quantity = 1
        String comment = 'Unit Test'
        String ipAddress = '192.168.1.1'
        String division = 'webstaurantstore'

        PostLogEvent postLogEvent = new PostLogEvent(userIndex,action,quantity,comment,scriptName,ipAddress,eventDate)
        assert postLogEvent.getStatusCode() == 201

        postLogEvent = new PostLogEvent(userIndex,action,quantity,comment,scriptName,ipAddress,eventDate, division)
        assert postLogEvent.getStatusCode() == 201

        postLogEvent = new PostLogEvent(userIndex,action,quantity,comment,scriptName,ipAddress,eventDate,'Failure')
        assert postLogEvent.getStatusCode() == 401
    }

    void testGetTblUserLogData(String scriptName, String eventDate){
        List tblUserLogData = PostLogEvent.getTblUserLogData(scriptName,eventDate)
        assert tblUserLogData.size() > 0

        tblUserLogData = PostLogEvent.getTblUserLogData('Failure',eventDate)
        assert tblUserLogData.size() == 0
    }
}

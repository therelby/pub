package framework.wss.api.transactions

import above.RunWeb
import wss.api.transactions.TransactionsApiUtil
import wss.user.UserDetail
import wss.user.userurllogin.UserUrlLogin

class UtTransactionsApiUtil extends RunWeb{

    static void main(String[] args) {
        new UtTransactionsApiUtil().testExecute([:])

    }

    def test() {
        final int PBI = 713918

        setup([
                author  : 'jglisson',
                title   : 'Transaction API Utilities Unit Tests',
                PBI     : PBI,
                product : 'wss|dev',
                project : 'Payments',
                keywords: 'transactions api utilities'
        ])

        testGetPaymentsUserByDivision()
    }

    void testGetPaymentsUserByDivision(){
        Integer userIndex
        for (division in TransactionsApiUtil.divisions){
            userIndex = TransactionsApiUtil.getPaymentsUserByDivision(division, false)
            assert userIndex > 0

            if (division == 'hometownprovisions'){continue}//Hometown only has null guids
            userIndex = TransactionsApiUtil.getPaymentsUserByDivision(division, true)
            assert userIndex > 0
            assert TransactionsApiUtil.getPaymentsUserGuid(userIndex,division).size() > 0
        }

        userIndex = TransactionsApiUtil.getPaymentsUserByDivision()
        assert userIndex > 0

        assert TransactionsApiUtil.getPaymentsUserByDivision('Failure') == null
    }
}

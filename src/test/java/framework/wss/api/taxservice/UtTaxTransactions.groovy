package framework.wss.api.taxservice

import above.RunWeb
import wss.api.shoppingcart.CartResults
import wss.api.taxservice.TaxTransactions
import wss.api.taxservice.model.taxtransactions.TaxTransactionsResultModel
import wss.item.ItemUtil
import wss.pages.cart.ViewCartPage
import wss.user.UserDetail
import wss.user.UserType
import wss.user.userurllogin.UserUrlLogin

class UtTaxTransactions extends RunWeb {
    static void main(String[] args) {
        new UtTaxTransactions().testExecute([
                browser      : 'chrome',
                remoteBrowser: false,
                environment  : 'dev',
                runType      : 'Debug',
        ])
    }

    def pbi = 0

    void test() {
        setup([
                author  : 'kyilmaz',
                title   : 'UtTransactions',
                PBI     : pbi,
                product : 'wss',
                project : 'Automation.Framework',
                keywords: 'unit test',
                logLevel: 'info'
        ])
        testIntegrationFullCall()
        testIntegrationSingularItem()
    }

    void testIntegrationFullCall() {
        def item = ItemUtil.getItemsGeneral(3, 'lone')['itemNumber']
        UserDetail user = new UserUrlLogin().loginNewUser(UserType.REGULAR_USER, 0)
        def userIndex = user.detail.index
        new ViewCartPage().addMultipleItemsToCart(item)
        CartResults callCart = new CartResults(userIndex)
        def callTransactions = new TaxTransactions(pbi, user.detail.addresses, 15.00, callCart)
        TaxTransactionsResultModel data = callTransactions.getResultModel()
        log "Results:"
        data.lineItems.jurisdictionResults.taxAmount
        log data.lineItems.jurisdictionResults
        log data.toPrettyJson()
        assert data
        assert data.lineItems.size() == 3
    }

    void testIntegrationSingularItem() {
        List<Map> item = ItemUtil.getItemsGeneral(3, 'lone')['itemNumber']
        UserDetail user = new UserUrlLogin().loginNewUser(UserType.REGULAR_USER, 0)
        def userIndex = user.detail.index
        new ViewCartPage().addMultipleItemsToCart(item)
        CartResults callCart = new CartResults(userIndex)
        def callTransactions = new TaxTransactions(pbi, user.detail.addresses, 15.00, callCart.resultModel.first())
        TaxTransactionsResultModel data = callTransactions.getResultModel()
        log "Results:"
        data.lineItems.jurisdictionResults.taxAmount
        log data.lineItems.jurisdictionResults
        log data.toPrettyJson()
        assert data
        assert data.lineItems.size() == 1
    }
}

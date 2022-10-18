package framework.wss.mappricing.b

import above.RunWeb
import wss.creditcard.CreditCardGenerator
import wss.webadmin.WebAdmin
import wss.webadmin.itemdetail.WebAdminItemDetail
import wsstest.mappricing.quantitydiscount.TcMapQuantityDiscountsP4Check2

class UtMapBOverrideEnablUnitTest extends RunWeb {
    def test() {

        setup('vdiachuk', ' MAP B unit test | Framework Self ' +
                'Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test map b style',
                 "tfsTcIds:265471", 'logLevel:info'])

        tryLoad("homepage")
        WebAdmin.loginToWebAdmin()
        def product = ["item_number": '124ELEBME   NATURAL']
        def testCases = []
        def queryResults = [:]
        //checking visibility without additional spaces in the middle of item number

        assert WebAdminItemDetail.navigateToItemDetailsPage('124ELEBME   NATURAL')
        assert WebAdminItemDetail.waitForVisibilityOfProductDetailsPage('124ELEBME NATURAL', 60)


    }
}

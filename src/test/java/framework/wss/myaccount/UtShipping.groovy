package framework.wss.myaccount

import above.RunWeb
import all.util.Addresses
import all.util.StringUtil
import wss.myaccount.Shipping
import wss.user.UserQuickLogin
import wsstest.account.testingtool.UserHelper

class UtShipping extends RunWeb {

    def test() {

        setup('vdiachuk', 'My Account,Shipping Unit test | Framework Self Testing Tool',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test myaccount shipping',
                 "tfsTcIds:265471", 'logLevel:info'])


        String homePage = getUrl("homepage")
        openBrowser(homePage)
        String name = "QAautomationUn"

        UserQuickLogin.loginUser("Regular User")
        /*     Shipping.navigateToShippingPage()
             waitForPage()
             assert getCurrentUrl().contains(Shipping.urlShipping)
             assert Shipping.getAllshippingAddresses() instanceof List



            assert Shipping.createNewDefaultIntShippingAddr("Canada", "automationName", "Saint-Laurent", "QC",
                    "3235 Rue Jean-Gascon", "", "H4R 3B5"
                    , "4072677812", "QAcompany")
             assert Shipping.deleteDefaultShippingAddress()

             assert Shipping.createNewUSShippingAddr("automationName", "1001 Shoreview Dr", "apt. 3", "32807",
                     "4072677812", "QAcompany")
             assert Shipping.deleteDefaultShippingAddress()

             assert Shipping.createNewDefaultShippingAddr(name)
             assert Shipping.deleteDefaultShippingAddress()*/


        // log "Shipping addresses with conditons:" + Shipping.getDBShippingAddressesWithTaxConditions('0', '0', '0')


        ///test ex
        [1..10].each {
            String uniqueName1 = UserHelper.shippingNameDefault + "-" + StringUtil.randomString(5)
            assert Shipping.createNewUSShippingAddr(uniqueName1, Addresses.usResidentialAddressCA.address,
                    Addresses.usResidentialAddressCA.address2,
                    Addresses.usResidentialAddressCA.zip
                    ,
                    Addresses.usResidentialAddressCA.phone,
                    "QAcompany")
            assert Shipping.deleteDefaultShippingAddress()
        }

    }
}

package framework.wss.user

import above.RunWeb
import all.util.Addresses
import wsstest.product.productdetailpage.map.hPDPMapTest
import wss.api.user.UserCreationApi
class UtUserTotalAmount extends RunWeb{

    // Test
    def test() {

        setup('mwestacott', 'UtUserTotalAmount',
                ['product:wss', 'tfsProject:Webstaurant.StoreFront', 'keywords:unit test user util userUtil utility',
                 "tfsTcIds:488132", 'logLevel:info'])



        Addresses residentialAddress = [
                name           : "Non Subordinate Test User",
                companyName    : "",
                address        : "3238 Landings North Dr",
                address2       : "",
                city           : 'Atlanta',
                zip            : "30331-6272",
                phone          : "6145555522",
                country        : "United States",
                countryCode    : "US",
                state          : "GA",
                destinationType: "Residential"
        ]
        Addresses commercialAddress = [
                name           : "Test User Non Subordinate",
                companyName    : "AutomationWss",
                address        : "7702 Woodland Center Blvd",
                address2       : "#150",
                city           : "Tampa",
                zip            : "33614-2425",
                phone          : "6145555522",
                country        : "United States",
                countryCode    : "US",
                state          : "FL",
                destinationType: "Commercial",
        ]

//        Addresses commercialAddress = UserCreationApi.defaultAddress
//        Addresses residentialAddress = Addresses.usResidentialAddressFL

        //Tests to see you can create user without order history
        assert hPDPMapTest.getUserForTesting(488132, 'regular', '21', commercialAddress, 0, true, false, 0) != null
        assert hPDPMapTest.getUserForTesting(488132, 'regular', '25', residentialAddress, 0, false, false, 0) != null

        //Tests to see you can create a user with 1 order and $1,000.00 spent
        assert hPDPMapTest.getUserForTesting(488132, 'regular', '23', commercialAddress, 0, true, true, 1000.00) != null
        assert hPDPMapTest.getUserForTesting(488132, 'regular', '26', residentialAddress, 0, false, true, 1000.00) != null

        //Tests to see you can create a user with 2 orders and $0.00 spent
        assert hPDPMapTest.getUserForTesting(488132, 'regular', '121', commercialAddress, 0, true, true, 0.00, 2) != null
        assert hPDPMapTest.getUserForTesting(488132, 'regular', '125', residentialAddress, 0, false, true, 0.00, 2) != null

        //Tests to see you can create a user with 2 orders and $1,000.00 spent
        assert hPDPMapTest.getUserForTesting(488132, 'regular', '123', commercialAddress, 0, true, true, 1000.00, 2) != null
        assert hPDPMapTest.getUserForTesting(488132, 'regular', '126', residentialAddress, 0, false, true, 1000.00, 2) != null

    }
}
